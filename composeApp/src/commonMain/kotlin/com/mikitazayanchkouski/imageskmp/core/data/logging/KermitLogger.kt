package com.mikitazayanchkouski.imageskmp.core.data.logging

import co.touchlab.kermit.Logger
import com.mikitazayanchkouski.imageskmp.core.domain.logging.PexelsLogger

data object KermitLogger : PexelsLogger {

    override fun debug(message: String) {
        Logger.d(messageString = message)
    }

    override fun error(message: String, throwable: Throwable?) {
        Logger.e(messageString = message, throwable = throwable)
    }

    override fun info(message: String) {
        Logger.i(messageString = message)
    }

    override fun warning(message: String) {
        Logger.w(messageString = message)
    }
}