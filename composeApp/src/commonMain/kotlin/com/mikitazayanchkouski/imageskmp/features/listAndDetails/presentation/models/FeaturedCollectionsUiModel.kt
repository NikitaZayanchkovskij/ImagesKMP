package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models

// TODO: Later check, which parameters I need, and which I don't
data class FeaturedCollectionsUiModel(
    val collections: List<CollectionUiModel>,
    val page: Int,
    val perPage: Int,
    val totalResults: Int,
    val nextPage: String,
    val prevPage: String
)

data class CollectionUiModel(
    val id: String,
    val imageCategory: String,
    val description: String? = null,
    val private: Boolean,
    val mediaCount: Int,
    val photosCount: Int,
    val videosCount: Int
)
