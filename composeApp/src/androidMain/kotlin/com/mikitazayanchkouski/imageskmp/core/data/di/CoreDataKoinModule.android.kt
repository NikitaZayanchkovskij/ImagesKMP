package com.mikitazayanchkouski.imageskmp.core.data.di

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.DatabaseFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformCoreDataModule = module {
    single<HttpClientEngine> {
        OkHttp.create()
    }
    single<DatabaseFactory> {
        DatabaseFactory(context = androidContext())
    }
}