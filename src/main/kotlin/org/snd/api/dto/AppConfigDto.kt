package org.snd.api.dto

import com.squareup.moshi.JsonClass
import org.snd.mediaserver.UpdateMode
import org.snd.metadata.MediaType
import org.snd.metadata.NameMatchingMode
import org.snd.metadata.model.ReadingDirection
import org.snd.metadata.model.TitleType

@JsonClass(generateAdapter = true)
data class AppConfigDto(
    val komga: KomgaConfigDto,
    val kavita: KavitaConfigDto,
    val discord: DiscordConfigDto,
    val metadataProviders: MetadataProvidersConfigDto,
)

@JsonClass(generateAdapter = true)
data class KomgaConfigDto(
    val baseUri: String,
    val komgaUser: String,
    val eventListener: EventListenerConfigDto,
    val notifications: NotificationConfigDto,
    val metadataUpdate: MetadataUpdateConfigDto,
)

@JsonClass(generateAdapter = true)
data class KavitaConfigDto(
    val baseUri: String,
    val eventListener: EventListenerConfigDto,
    val notifications: NotificationConfigDto,
    val metadataUpdate: MetadataUpdateConfigDto,
)

@JsonClass(generateAdapter = true)
data class NotificationConfigDto(
    val libraries: Collection<String>
)

@JsonClass(generateAdapter = true)
data class MetadataUpdateConfigDto(
    val default: MetadataProcessingConfigDto,
    val library: Map<String, MetadataProcessingConfigDto>
)

@JsonClass(generateAdapter = true)
data class MetadataProcessingConfigDto(
    val aggregate: Boolean,
    val bookCovers: Boolean,
    val seriesCovers: Boolean,
    val updateModes: Set<UpdateMode>,
    val postProcessing: MetadataPostProcessingConfigDto

)

@JsonClass(generateAdapter = true)
data class MetadataPostProcessingConfigDto(
    val seriesTitle: Boolean,
    val titleType: TitleType,
    val alternativeSeriesTitles: Boolean?,
    val orderBooks: Boolean,
    val readingDirectionValue: ReadingDirection?,
    val languageValue: String?,
)

@JsonClass(generateAdapter = true)
data class DiscordConfigDto(
    val webhooks: Map<Int, String>?,
    val seriesCover: Boolean,
    val imgurClientId: String?,
)

@JsonClass(generateAdapter = true)
data class EventListenerConfigDto(
    val enabled: Boolean,
    val libraries: Collection<String>
)

@JsonClass(generateAdapter = true)
data class MetadataProvidersConfigDto(
    val malClientId: String,
    val nameMatchingMode: NameMatchingMode,
    val defaultProviders: ProvidersConfigDto,
    val libraryProviders: Map<String, ProvidersConfigDto>,
)

@JsonClass(generateAdapter = true)
data class ProvidersConfigDto(
    val mangaUpdates: ProviderConfigDto,
    val mal: ProviderConfigDto,
    val nautiljon: ProviderConfigDto,
    val aniList: ProviderConfigDto,
    val yenPress: ProviderConfigDto,
    val kodansha: ProviderConfigDto,
    val viz: ProviderConfigDto,
    val bookWalker: ProviderConfigDto,
)

@JsonClass(generateAdapter = true)
data class ProviderConfigDto(
    val nameMatchingMode: NameMatchingMode?,
    val priority: Int,
    val enabled: Boolean,
    val mediaType: MediaType,
    val seriesMetadata: SeriesMetadataConfigDto,
    val bookMetadata: BookMetadataConfigDto,
)

@JsonClass(generateAdapter = true)
data class SeriesMetadataConfigDto(
    val status: Boolean,
    val title: Boolean,
    val summary: Boolean,
    val publisher: Boolean,
    val readingDirection: Boolean,
    val ageRating: Boolean,
    val language: Boolean,
    val genres: Boolean,
    val tags: Boolean,
    val totalBookCount: Boolean,
    val authors: Boolean,
    val releaseDate: Boolean,
    val thumbnail: Boolean,
    val links: Boolean,
    val books: Boolean,
    val useOriginalPublisher: Boolean,
    val originalPublisherTagName: String?,
    val englishPublisherTagName: String?,
    val frenchPublisherTagName: String?,
)

@JsonClass(generateAdapter = true)
data class BookMetadataConfigDto(
    val title: Boolean,
    val summary: Boolean,
    val number: Boolean,
    val numberSort: Boolean,
    val releaseDate: Boolean,
    val authors: Boolean,
    val tags: Boolean,
    val isbn: Boolean,
    val links: Boolean,
    val thumbnail: Boolean,
)
