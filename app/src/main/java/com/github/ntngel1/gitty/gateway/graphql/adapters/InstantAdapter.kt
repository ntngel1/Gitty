/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.gateway.graphql.adapters

import com.apollographql.apollo.api.CustomTypeAdapter
import com.apollographql.apollo.api.CustomTypeValue
import org.threeten.bp.Instant
import org.threeten.bp.format.DateTimeFormatter

class InstantAdapter : CustomTypeAdapter<Instant> {

    override fun decode(value: CustomTypeValue<*>): Instant {
        val encodedInstant = value.value.toString()
        return Instant.parse(encodedInstant)
    }

    override fun encode(value: Instant): CustomTypeValue<*> {
        val decodedInstant = DateTimeFormatter.ISO_INSTANT.format(value)
        return CustomTypeValue.GraphQLString(decodedInstant)
    }
}