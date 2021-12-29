package com.tromian.test.testcontacts.data.network

import retrofit2.http.GET
import retrofit2.http.Query


interface RandomUserApi {

    @GET
    suspend fun getContactList(
        @Query(value = "results") results: Int = DEFAULT_CONTACTS_AMOUNT
    ): ContactListResponse

    companion object {
        const val DEFAULT_CONTACTS_AMOUNT = 50
    }

}