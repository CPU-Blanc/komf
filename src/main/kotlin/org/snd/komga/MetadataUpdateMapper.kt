package org.snd.komga

import org.snd.config.MetadataUpdateConfig
import org.snd.komga.model.dto.KomgaAuthor
import org.snd.komga.model.dto.KomgaBookMetadata
import org.snd.komga.model.dto.KomgaBookMetadataUpdate
import org.snd.komga.model.dto.KomgaSeriesMetadata
import org.snd.komga.model.dto.KomgaSeriesMetadataUpdate
import org.snd.komga.model.dto.KomgaWebLink
import org.snd.metadata.comicinfo.model.AgeRating
import org.snd.metadata.comicinfo.model.ComicInfo
import org.snd.metadata.model.AuthorRole.*
import org.snd.metadata.model.BookMetadata
import org.snd.metadata.model.SeriesMetadata

class MetadataUpdateMapper(
    private val metadataUpdateConfig: MetadataUpdateConfig,
) {

    fun toBookMetadataUpdate(bookMetadata: BookMetadata?, seriesMetadata: SeriesMetadata, komgaMetadata: KomgaBookMetadata): KomgaBookMetadataUpdate =
        with(komgaMetadata) {
            val authors = (bookMetadata?.authors ?: seriesMetadata.authors).map { author -> KomgaAuthor(author.name, author.role.name) }
            KomgaBookMetadataUpdate(
                summary = getIfNotLockedOrEmpty(bookMetadata?.summary, summaryLock) ?: summary,
                releaseDate = getIfNotLockedOrEmpty(bookMetadata?.releaseDate, releaseDateLock) ?: releaseDate,
                authors = getIfNotLockedOrEmpty(authors, authorsLock) ?: komgaMetadata.authors,
                tags = getIfNotLockedOrEmpty(bookMetadata?.tags, tagsLock) ?: tags,
                isbn = getIfNotLockedOrEmpty(bookMetadata?.isbn, isbnLock) ?: isbn,
                links = getIfNotLockedOrEmpty(bookMetadata?.links?.map { KomgaWebLink(it.label, it.url) }, linksLock) ?: links
            )
        }

    fun toSeriesMetadataUpdate(patch: SeriesMetadata, metadata: KomgaSeriesMetadata): KomgaSeriesMetadataUpdate =
        with(metadata) {
            KomgaSeriesMetadataUpdate(
                status = getIfNotLockedOrEmpty(patch.status?.toString(), statusLock) ?: status,
                title = if (metadataUpdateConfig.seriesTitle) getIfNotLockedOrEmpty(patch.title, titleLock) ?: title else title,
                titleSort = if (metadataUpdateConfig.seriesTitle) getIfNotLockedOrEmpty(patch.titleSort, titleSortLock) ?: titleSort else titleSort,
                summary = getIfNotLockedOrEmpty(patch.summary, summaryLock) ?: summary,
                publisher = getIfNotLockedOrEmpty(patch.publisher, publisherLock) ?: publisher,
                readingDirection = getIfNotLockedOrEmpty(patch.readingDirection?.toString(), readingDirectionLock) ?: readingDirection,
                ageRating = getIfNotLockedOrEmpty(patch.ageRating, ageRatingLock) ?: ageRating,
                language = getIfNotLockedOrEmpty(patch.language, languageLock) ?: language,
                genres = getIfNotLockedOrEmpty(patch.genres, genresLock) ?: genres,
                tags = getIfNotLockedOrEmpty(patch.tags, tagsLock) ?: tags,
                totalBookCount = getIfNotLockedOrEmpty(patch.totalBookCount, totalBookCountLock) ?: totalBookCount,
            )
        }

    fun toComicInfo(bookMetadata: BookMetadata?, seriesMetadata: SeriesMetadata): ComicInfo {
        return ComicInfo(
            title = bookMetadata?.title,
            series = seriesMetadata.title,
            number = bookMetadata?.number?.toString(),
            count = seriesMetadata.totalBookCount,
            summary = bookMetadata?.summary,
            year = bookMetadata?.releaseDate?.year,
            month = bookMetadata?.releaseDate?.monthValue,
            day = bookMetadata?.releaseDate?.dayOfMonth,
            writer = (bookMetadata?.authors ?: seriesMetadata.authors)
                .filter { it.role == WRITER }.joinToString(",") { it.name },
            penciller = (bookMetadata?.authors ?: seriesMetadata.authors)
                .filter { it.role == PENCILLER }.joinToString(",") { it.name },
            inker = (bookMetadata?.authors ?: seriesMetadata.authors)
                .filter { it.role == INKER }.joinToString(",") { it.name },
            colorist = (bookMetadata?.authors ?: seriesMetadata.authors)
                .filter { it.role == COLORIST }.joinToString(",") { it.name },
            letterer = (bookMetadata?.authors ?: seriesMetadata.authors)
                .filter { it.role == LETTERER }.joinToString(",") { it.name },
            coverArtist = (bookMetadata?.authors ?: seriesMetadata.authors)
                .filter { it.role == COVER }.joinToString(",") { it.name },
            editor = (bookMetadata?.authors ?: seriesMetadata.authors)
                .filter { it.role == EDITOR }.joinToString(",") { it.name },
            translator = (bookMetadata?.authors ?: seriesMetadata.authors)
                .filter { it.role == TRANSLATOR }.joinToString(",") { it.name },
            publisher = seriesMetadata.publisher,
            genre = seriesMetadata.genres.joinToString(","),
            tags = bookMetadata?.tags?.joinToString(","),
            ageRating = seriesMetadata.ageRating
                ?.let { metadataRating ->
                    AgeRating.values().filter { it.ageRating != null }
                        .maxByOrNull { it.ageRating!!.coerceAtLeast(metadataRating) }?.name
                }
        )
    }

    private fun <T> getIfNotLockedOrEmpty(patched: T?, lock: Boolean): T? =
        if (patched is Collection<*> && patched.isEmpty()) null
        else if (patched != null && !lock) patched
        else null
}
