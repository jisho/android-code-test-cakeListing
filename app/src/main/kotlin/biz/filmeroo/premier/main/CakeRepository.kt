package biz.filmeroo.premier.main

import biz.filmeroo.premier.api.*
import io.reactivex.Single
import javax.inject.Inject

internal class CakeRepository @Inject constructor(private val cakeService: CakeService) {

    fun fetchCake(): Single<List<ApiCakeItem>> {
        return cakeService.fetchCakeList()
    }
}