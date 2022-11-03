package biz.filmeroo.premier.main


import biz.filmeroo.premier.api.ApiCakeItem
import biz.filmeroo.premier.base.Presenter
import biz.filmeroo.premier.base.PresenterView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

internal class CakePresenter @Inject constructor(private val cakeRepository: CakeRepository) :
    Presenter<CakePresenter.View>() {

    override fun start(view: View) {
        super.start(view)

        try {
            addSubscription(cakeRepository.fetchCake()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { list -> view.displayCakeResults(list) },
                    { view.displayError() }
                ))
        } catch (ex: UndeliverableException) {

        }

    }

    internal interface View : PresenterView {
        fun displayCakeResults(results: List<ApiCakeItem>)
        fun displayError()
    }
}