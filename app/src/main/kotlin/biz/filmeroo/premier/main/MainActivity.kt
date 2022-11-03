package biz.filmeroo.premier.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import biz.filmeroo.premier.R
import biz.filmeroo.premier.api.ApiCakeItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CakePresenter.View {

    @Inject
    internal lateinit var presenter: CakePresenter

    @Inject
    internal lateinit var adapter: CakeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        presenter.start(this)
    }

    private fun setupView() {
        setContentView(R.layout.activity_main)
        val linearLayoutManager = LinearLayoutManager(this)
        recycler.layoutManager = linearLayoutManager
        recycler.adapter = adapter
        val dividerItemDecoration =
            DividerItemDecoration(this, linearLayoutManager.orientation).apply {
                setDrawable(AppCompatResources.getDrawable(this@MainActivity, R.drawable.divider)!!)
            }
        recycler.addItemDecoration(dividerItemDecoration)

        adapter.setOnClick { item -> Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()}
    }

    override fun onDestroy() {
        presenter.stop()
        super.onDestroy()
    }

    override fun displayCakeResults(results: List<ApiCakeItem>) {
        adapter.submitList(results.distinct().toList().sortedBy { it.title })
    }

    override fun displayError() {
        Toast.makeText(this, R.string.connection_error, Toast.LENGTH_SHORT).show()
    }
}
