package biz.filmeroo.premier.main

import biz.filmeroo.premier.support.Fixtures
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class CakePresenterTest {

    private val repository: CakeRepository = mock()
    private val view: CakePresenter.View = mock()

    private lateinit var presenter: CakePresenter

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        presenter = CakePresenter(repository)
    }

    @Test
    fun `binding fetches and displays list`() {
        val results = Fixtures.cakeList()
        whenever(repository.fetchCake()).thenReturn(Single.just(results))

        presenter.start(view)

        verify(view).displayCakeResults(results)
    }

    @Test
    fun `errors fetching results are displayed`() {
        whenever(repository.fetchCake()).thenReturn(Single.error(Exception()))

        presenter.start(view)

        verify(view).displayError()
    }
}