package com.mikitazayanchkouski.imageskmp.core.domain.logging

interface ImagesAppLogger {
    fun debug(message: String)
    fun error(message: String, throwable: Throwable? = null)
    fun info(message: String)
    fun warning(message: String)
}