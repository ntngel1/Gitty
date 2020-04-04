package com.github.ntngel1.gitty.gateway.rest.gateways

import android.content.SharedPreferences
import com.github.ntngel1.gitty.domain.gateways.AuthorizationGateway
import com.github.ntngel1.gitty.gateway.rest.entities.CreateAccessTokenResponse
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class RestAuthorizationGateway @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) : AuthorizationGateway {

    /**
     * Github doesn't provide GraphQL API to create access token, so we need to use REST endpoint.
     * It was decided not to use retrofit for a single request, so here we send it manually.
     */
    override fun createAccessToken(
        code: String,
        state: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String
    ): Single<String> {
        return Single.fromCallable {
            val requestBody = FormBody.Builder()
                .add("client_id", clientId)
                .add("client_secret", clientSecret)
                .add("code", code)
                .add("redirect_uri", redirectUri)
                .add("state", state)
                .build()

            val request = Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .addHeader("Accept", "application/json")
                .post(requestBody)
                .build()

            val response = okHttpClient.newCall(request)
                .execute()

            response.body?.let { body ->
                gson.fromJson(body.string(), CreateAccessTokenResponse::class.java)
                    .accessToken
            } ?: error("") // TODO FIX
        }.subscribeOn(Schedulers.io())
    }

    override fun saveAccessToken(accessToken: String): Completable = Completable.fromCallable {
        sharedPreferences.edit()
            .putString(ACCESS_TOKEN_KEY, accessToken)
            .commit()
    }

    override fun getAccessToken(): Maybe<String> = Maybe.fromCallable {
        sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    }

    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"
    }
}