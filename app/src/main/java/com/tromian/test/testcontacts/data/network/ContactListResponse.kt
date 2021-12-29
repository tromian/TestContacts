package com.tromian.test.testcontacts.data.network

import com.google.gson.annotations.SerializedName


data class ContactListResponse(
    @SerializedName("results")
    val contacts: List<ContactJson>
)

data class ContactJson(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: Name,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("picture")
    val picture: Picture
)

data class Name(
    @SerializedName("first")
    val first: String,
    @SerializedName("last")
    val last: String
)

data class Picture(
    @SerializedName("medium")
    val medium: String
)