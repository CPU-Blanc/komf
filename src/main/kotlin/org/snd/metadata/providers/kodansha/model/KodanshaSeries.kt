package org.snd.metadata.providers.kodansha.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class KodanshaSeries(
    val id: Int,
    val title: String,
    val genres: List<KodanshaGenre>?,
    val creators: List<KodanshaCreator>?,
    val completionStatus: String?,
    val description: String?,
    val ageRating: String?,
    val thumbnails: List<KodanshaThumbnail>?,
    val publisher: String?,
    val readableUrl: String?,
)

@JsonClass(generateAdapter = true)
data class KodanshaGenre(
    val name: String,
    val id: Int
)
