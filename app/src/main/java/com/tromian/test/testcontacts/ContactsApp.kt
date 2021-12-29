package com.tromian.test.testcontacts

import android.app.Application
import android.content.Context
import com.tromian.test.testcontacts.di.AppComponent
import com.tromian.test.testcontacts.di.DaggerAppComponent

class ContactsApp : Application() {
    private var _appComponent: AppComponent? = null
    internal val appComponent: AppComponent
        get() = checkNotNull(_appComponent) {
            "AppComponent isn't initialized"
        }

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.factory().create(
            this,
            getString(R.string.random_user_api_url)
        )
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is ContactsApp -> appComponent
        else -> this.applicationContext.appComponent
    }