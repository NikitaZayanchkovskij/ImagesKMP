package com.mikitazayanchkouski.imageskmp.core.data.di

import com.mikitazayanchkouski.imageskmp.core.data.logging.KermitLogger
import com.mikitazayanchkouski.imageskmp.core.data.network.HttpClientFactory
import com.mikitazayanchkouski.imageskmp.core.domain.logging.ImagesAppLogger
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformCoreDataModule: Module

val commonCoreDataModule = module {
    single<HttpClient> {
        HttpClientFactory(imagesAppLogger = get<ImagesAppLogger>())
            .create(engine = get<HttpClientEngine>())
    }
    single<ImagesAppLogger> {
        KermitLogger
    }

    includes(platformCoreDataModule)
}