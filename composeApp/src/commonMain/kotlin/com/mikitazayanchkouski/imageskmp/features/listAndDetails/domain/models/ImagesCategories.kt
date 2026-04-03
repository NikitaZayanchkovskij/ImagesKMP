package com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models

import imageskmp.composeapp.generated.resources.Res
import imageskmp.composeapp.generated.resources.curated_category
import imageskmp.composeapp.generated.resources.islands_category
import imageskmp.composeapp.generated.resources.nature_category
import imageskmp.composeapp.generated.resources.ocean_category
import imageskmp.composeapp.generated.resources.outdoors_category
import imageskmp.composeapp.generated.resources.search_category
import imageskmp.composeapp.generated.resources.space_category
import imageskmp.composeapp.generated.resources.sunny_morning_category
import org.jetbrains.compose.resources.StringResource

enum class ImagesCategories(
    val inServerFormat: String,
    val titleForTheUi: StringResource
) {
    CURATED(
        inServerFormat = "curated",
        titleForTheUi = Res.string.curated_category
    ),
    ISLANDS(
        inServerFormat = "islands",
        titleForTheUi = Res.string.islands_category
    ),
    NATURE(
        inServerFormat = "nature",
        titleForTheUi = Res.string.nature_category
    ),
    OUTDOORS(
        inServerFormat = "outdoors",
        titleForTheUi = Res.string.outdoors_category
    ),
    SUNNY_MORNING(
        inServerFormat = "sunny morning",
        titleForTheUi = Res.string.sunny_morning_category
    ),
    OCEAN(
        inServerFormat = "ocean",
        titleForTheUi = Res.string.ocean_category
    ),
    SPACE(
        inServerFormat = "space",
        titleForTheUi = Res.string.space_category
    ),
    SEARCH(
        inServerFormat = "search",
        titleForTheUi = Res.string.search_category
    )
}