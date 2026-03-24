package com.mikitazayanchkouski.imageskmp.core.data.di

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.DatabaseFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual val platformCoreDataModule = module {
    single<HttpClientEngine> {
        Darwin.create()
    }
    single<DatabaseFactory> {
        DatabaseFactory()
    }
}