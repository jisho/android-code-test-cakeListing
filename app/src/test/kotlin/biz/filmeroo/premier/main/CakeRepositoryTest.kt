package biz.filmeroo.premier.main

import biz.filmeroo.premier.api.CakeService
import biz.filmeroo.premier.support.Fixtures
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class CakeRepositoryTest {

    private val service: CakeService = mock()

    private lateinit var repository: CakeRepository

    @Before
    fun setUp() {
        repository = CakeRepository(service)
    }

    @Test
    fun `fetches cake and returns list single`() {
        whenever(service.fetchCakeList()).thenReturn(Single.just(Fixtures.cakeList()))

        val observer = repository.fetchCake().test()
        val results = observer.values()[0]

        assertThat(results).hasSize(20)
        assertThat(results[0].title).isEqualTo("Lemon cheesecake")
    }

}