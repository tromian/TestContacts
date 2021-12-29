package com.tromian.test.testcontacts.utils

import com.tromian.test.testcontacts.data.db.ContactEntity
import com.tromian.test.testcontacts.data.network.ContactJson
import com.tromian.test.testcontacts.domain.Contact

fun ContactJson.toDomain() : Contact {
    return Contact(
        email = this.email,
        firstName = this.name.first,
        lastName = this.name.last,
        phone = this.phone,
        pictureUrl = this.picture.medium
    )
}

fun Contact.toEntity() : ContactEntity {
    return ContactEntity(
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        phone = this.phone,
        pictureUrl = this.pictureUrl
    )
}

fun ContactEntity.toDomain() : Contact {
    return Contact(
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        phone = this.phone,
        pictureUrl = this.pictureUrl
    )
}
