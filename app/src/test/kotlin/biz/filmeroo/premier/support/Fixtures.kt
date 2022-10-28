package biz.filmeroo.premier.support

import biz.filmeroo.premier.api.ApiFilm
import biz.filmeroo.premier.api.ApiFilmListResponse
import biz.filmeroo.premier.api.ApiGenre
import biz.filmeroo.premier.api.ApiGenreItem

object Fixtures {

    fun film(id: Long): ApiFilm {
        return ApiFilm(
            id = id,
            title = "Waterworld",
            overview = "Kevin Costner on a boat",
            posterPath = "/123.jpg",
            backdropPath = "/456.jpg",
            voteAverage = "5.5",
            genreId= listOf(28)
        )
    }

    fun filmList(): List<ApiFilm> {
        return listOf(film(123), film(456))
    }

    fun filmResponse(): ApiFilmListResponse {
        return ApiFilmListResponse(filmList())
    }

    fun genreList(): ApiGenre {
        return ApiGenre(listOf(ApiGenreItem("Action",28)))
    }
}
