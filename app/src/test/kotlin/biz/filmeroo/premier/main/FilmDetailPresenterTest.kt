package biz.filmeroo.premier.main

import biz.filmeroo.premier.detail.FilmDetailPresenter
import biz.filmeroo.premier.support.Fixtures
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class FilmDetailPresenterTest {

    private val repository: FilmRepository = mock()
    private val view: FilmDetailPresenter.View = mock()

    private lateinit var presenter: FilmDetailPresenter

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        presenter = FilmDetailPresenter(repository)
    }

    @Test
    fun `binding fetches and displays movie`() {
        val results = Fixtures.film(106912)
        whenever(repository.fetchMovie(106912)).thenReturn(Single.just(results))

        presenter.start(view)

        verify(view).displayMovie(results)
    }

    @Test
    fun `binding fetches and displays similar movie list`() {
        val results = Fixtures.filmList()
        whenever(repository.fetchSimilar(106912)).thenReturn(Single.just(results))

        presenter.start(view)

        verify(view).displaySimilarResults(results)
    }
}