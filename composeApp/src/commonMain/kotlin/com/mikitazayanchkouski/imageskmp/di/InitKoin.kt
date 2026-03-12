package com.mikitazayanchkouski.imageskmp.di

import com.mikitazayanchkouski.imageskmp.core.data.di.commonCoreDataModule
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.di.commonListAndDetailsPresentationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            commonCoreDataModule,
            commonListAndDetailsPresentationModule
        )
    }
}