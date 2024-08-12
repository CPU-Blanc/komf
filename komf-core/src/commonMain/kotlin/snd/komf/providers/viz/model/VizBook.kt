package snd.komf.providers.viz.model

import kotlinx.datetime.LocalDate
import snd.komf.model.BookRange
import kotlin.jvm.JvmInline

@JvmInline
value class VizBookId(val value: String)

data class VizBook(
    val id: VizBookId,
    val name: String,
    val seriesName: String,
    val number: BookRange?,
    val publisher: String = "Viz",
    val releaseDate: LocalDate?,
    val description: String?,
    val coverUrl: String?,
    val genres: Collection<String>,
    val isbn: String?,
    val ageRating: AgeRating?,
    val authorStory: String?,
    val authorArt: String?,

    val allBooksId: VizAllBooksId?,
)

fun VizBook.toVizSeriesBook() = VizSeriesBook(
    id = id,
    name = name,
    seriesName = seriesName,
    number = number,
    imageUrl = null,
    final = false //TODO
)