package my.edu.tarc.travelink.ui.home.data

import java.time.LocalDate

    data class News(
        var newsTitle: String = "",
        var newsDate: LocalDate = LocalDate.now(),
        var newsDesc: String = "",
        var newsPhoto: Int,
    )

    data class SearchResult(
        val name: String = "",
        val image: Int,
        val image2: Int
    )
