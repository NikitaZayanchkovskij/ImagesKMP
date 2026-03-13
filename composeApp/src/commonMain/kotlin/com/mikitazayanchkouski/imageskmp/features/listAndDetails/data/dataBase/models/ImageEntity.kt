package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/** In case of this application:
 * 1 image always have a category (Curated, Islands, Nature etc.)
 * But 1 image can either be in bookmarks, or not.
 * So 1 image entity can belong to 2 tables/entities. (One to Many)
 *
 * And also Multiple images, can belong, for example to Islands category,
 * AND be in Bookmarks.
 * Many (images) to Many (tables/entities).
 *
 * Also, there could be a case, that one image, at the same time,
 * belongs to multiple categories.
 * For example: an image of the tropical beach,
 * could belong to the Nature category, but also to Islands category.
 */
@Entity
data class ImageEntity(
    @PrimaryKey
    val imageId: Long,

    /* Возможно мне не нужно это поле?..
     * Т.к. одно изображение может принадлежать нескольким категориям.
     * К примеру изображение тропического пляжа может принадлежать одновременно
     * и категории Природа, и категории Острова..
    * Тогда наверное просто при сохранении в базу данных нужно будет в две таблицы сохранять:
    * 1) В закладки
    * (Или в категорию вкладки для TabROw, которая пришла с сервера)
    * 2) И в таблицу кросс реф
    * Типо так:
    * 1) dao.insertImage(image)
    * 2) dao.insertCrossRef(ImagesByCategoryCrossRefEntity("Islands", image.imageId))
    *
    * Since you mentioned: * "I want to have access to image's category in bookmarks screen" * and you kept val imageCategory: String inside ImageEntity.kt:
The "Helper" Field Reality: Because your database now allows an image to have multiple categories (via the CrossRef), the imageCategory field inside the ImageEntity table will only store one string (the one you set when you first created the object).
•
Is this okay? Yes! If you use that string just to show a "Tag" on the image card in the Bookmark screen, it works fine.
•
Just be aware: If Image #101 is in both "Nature" and "Sun", the ImageEntity.imageCategory might say "Nature", even if the user is looking at it in the "Sun" tab. Most developers keep a "primary_category" field exactly like you have done to simplify the UI.
    *
    * */
    val imageCategory: String,

    val isInBookmarks: Boolean,
    val width: Int,
    val height: Int,
    val url: String,
    val photographerName: String,
    val photographerUrl: String,
    val photographerId: Long,
    val avgColor: String,
    @Embedded
    val imageResolutions: ImageSrcEntity,
    val liked: Boolean,
    val description: String
)

@Entity
data class ImageSrcEntity(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)