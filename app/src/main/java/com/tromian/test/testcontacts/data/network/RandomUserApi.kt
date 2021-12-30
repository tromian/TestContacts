package com.tromian.test.testcontacts.data.network

import retrofit2.http.GET
import retrofit2.http.Query


interface RandomUserApi {

    @GET("api/")
    suspend fun getContactList(
        @Query(value = "results") results: Int = DEFAULT_CONTACTS_AMOUNT,
        @Query(value = "nat") nat: String = DEFAULT_NAC
    ): ContactListResponse

    companion object {
        const val DEFAULT_CONTACTS_AMOUNT = 50
        const val DEFAULT_NAC = "us"
    }

}