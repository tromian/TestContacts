package com.tromian.test.testcontacts.di

import android.app.Application
import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tromian.test.testcontacts.data.ContactRepositoryImpl
import com.tromian.test.testcontacts.data.db.ContactsDB
import com.tromian.test.testcontacts.data.network.RandomUserApi
import com.tromian.test.testcontacts.domain.ContactRepository
import com.tromian.test.testcontacts.presentation.details.DetailsFragment
import com.tromian.test.testcontacts.presentation.details.edit.EditDetailsFragment
import com.tromian.test.testcontacts.presentation.main.MainFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class RandomContactBaseUrl

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun inject(fragment: MainFragment)
    fun inject(fragment: DetailsFragment)
    fun inject(fragment: EditDetailsFragment)

    @Component.Factory
    interface Builder {

        fun create(
            @BindsInstance
            appContext: Application,
            @BindsInstance
            @RandomContactBaseUrl
            baseUrl: String,
        ): AppComponent

    }
}
@Module(includes = [NetworkModule::class, LocalDBModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideRepository(
        localDB: ContactsDB,
        weatherApi: RandomUserApi,
        appContext: Application,
    ): ContactRepository {
        return ContactRepositoryImpl(localDB, weatherApi, appContext)
    }
}

@Module
class NetworkModule {
    @Provides
    fun provideWeatherService(
        @RandomContactBaseUrl
        baseUrl: String
    ): RandomUserApi {

        val client = OkHttpClient()
            .newBuilder()
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        return retrofit.create(RandomUserApi::class.java)
    }
}

@Module
class LocalDBModule {
    @Provides
    @Singleton
    fun provideLocalDB(appContext: Application): ContactsDB {
        return ContactsDB.getInstance(appContext)
    }

}