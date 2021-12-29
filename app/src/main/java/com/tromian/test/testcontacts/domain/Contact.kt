package com.tromian.test.testcontacts.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val email: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val pictureUrl: String
) : Parcelable
