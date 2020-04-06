/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.gateway.graphql.adapters

import com.apollographql.apollo.api.CustomTypeAdapter
import com.apollographql.apollo.api.CustomTypeValue
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class LocalDateAdapter : CustomTypeAdapter<LocalDate> {

    override fun decode(value: CustomTypeValue<*>): LocalDate {
        val encodedDate = value.value.toString()
        return LocalDate.parse(encodedDate, DateTimeFormatter.ISO_LOCAL_DATE)
    }

    override fun encode(value: LocalDate): CustomTypeValue<*> {
        val decodedDate = DateTimeFormatter.ISO_LOCAL_DATE.format(value)
        return CustomTypeValue.GraphQLString(decodedDate)
    }
}