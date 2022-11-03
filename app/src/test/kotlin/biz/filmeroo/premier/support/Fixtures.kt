package biz.filmeroo.premier.support

import biz.filmeroo.premier.api.*

object Fixtures {

    fun cake(id: Long): ApiCakeItem {
        return ApiCakeItem(
            title = "Lemon cheesecake",
            desc = "A cheesecake made of lemon",
            image = "https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg",
        )
    }

    fun cakeList(): List<ApiCakeItem> {
        return listOf(cake(123), cake(456),cake(123), cake(456),cake(456),
            cake(123), cake(456),cake(123), cake(456),cake(456),
            cake(123), cake(456),cake(123), cake(456),cake(456),
            cake(123), cake(456),cake(123), cake(456),cake(456))
    }
}
