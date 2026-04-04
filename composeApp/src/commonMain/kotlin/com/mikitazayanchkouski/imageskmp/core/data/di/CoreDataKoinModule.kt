package com.mikitazayanchkouski.imageskmp.core.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.mikitazayanchkouski.imageskmp.core.data.logging.KermitLogger
import com.mikitazayanchkouski.imageskmp.core.data.network.HttpClientFactory
import com.mikitazayanchkouski.imageskmp.core.domain.logging.PexelsLogger
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.DatabaseFactory
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.ImagesDatabase
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.LocalImagesDataSource
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.RoomLocalImagesDataSource
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.KtorRemoteImagesDataSource
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.RemoteImagesDataSource
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.repository.OfflineFirstImagesRepository
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.repository.ImagesRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformCoreDataModule: Module

val commonCoreDataModule = module {
    single<ImagesRepository> {
        OfflineFirstImagesRepository(
            remoteDataSource = get<RemoteImagesDataSource>(),
            localDataSource = get<LocalImagesDataSource>()
        )
    }
    single<RemoteImagesDataSource> {
        KtorRemoteImagesDataSource(httpClient = get<HttpClient>())
    }
    single<LocalImagesDataSource> {
        RoomLocalImagesDataSource(imagesDatabase = get<ImagesDatabase>())
    }
    single<HttpClient> {
        HttpClientFactory(pexelsLogger = get<PexelsLogger>())
            .create(engine = get<HttpClientEngine>())
    }
    single<PexelsLogger> {
        KermitLogger
    }
    single<ImagesDatabase> {
        get<DatabaseFactory>()
            .createDatabase()
            .setDriver(driver = BundledSQLiteDriver())
            .build()
    }

    includes(platformCoreDataModule)
}