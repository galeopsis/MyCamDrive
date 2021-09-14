package com.galeopsis.mycamdrive.app.di

import android.app.Application
import com.galeopsis.mycamdrive.model.api.CamDriveApi
import com.galeopsis.mycamdrive.model.repository.CamDriveRepository
import com.galeopsis.mycamdrive.utils.MyCookieJar
import com.galeopsis.mycamdrive.viewmodel.MainViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://ms1.dev.camdrive.org/"

val viewModelModule = module {
    single { MainViewModel(get()) }
}

val apiModule = module {
    fun provideUserApi(retrofit: Retrofit): CamDriveApi {
        return retrofit.create(CamDriveApi::class.java)
    }

    single { provideUserApi(get()) }
}

val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }


    fun provideHttpClient(cache: Cache): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .cookieJar(MyCookieJar())
            .cache(cache)

        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }


    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()

    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }

}

val repositoryModule = module {
    fun provideUserRepository(api: CamDriveApi): CamDriveRepository {
        return CamDriveRepository(api)
    }

    single { provideUserRepository(get()) }
}