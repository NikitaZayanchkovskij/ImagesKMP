package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.ImagesDatabase

class RoomLocalImagesDataSource(
    private val imagesDataBase: ImagesDatabase
): LocalImagesDataSource {

    override fun getBookmarks() {
        println("Database is initialized, getBookmarksFunctionIsCalled")
    }
}