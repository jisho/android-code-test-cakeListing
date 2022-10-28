package biz.filmeroo.premier.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DividerItemDecoration
import biz.filmeroo.premier.R
import biz.filmeroo.premier.api.ApiFilm
import biz.filmeroo.premier.api.ApiGenre
import biz.filmeroo.premier.app.SingletonHolder
import biz.filmeroo.premier.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FilmPresenter.View {

    @Inject internal lateinit var presenter: FilmPresenter
    @Inject internal lateinit var adapter: FilmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        presenter.start(this)

        if(SingletonHolder.instance?.genreMap == null)
            presenter.loadGenre(this)
    }

    private fun setupView() {
        setContentView(R.layout.activity_main)
        val linearLayoutManager = LinearLayoutManager(this)
        recycler.layoutManager = linearLayoutManager
        recycler.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(this, linearLayoutManager.orientation).apply {
            setDrawable(AppCompatResources.getDrawable(this@MainActivity, R.drawable.divider)!!)
        }
        recycler.addItemDecoration(dividerItemDecoration)
        adapter.setOnClick { id -> DetailActivity.start(this, id) }
    }

    override fun onDestroy() {
        presenter.stop()
        super.onDestroy()
    }

    override fun displayResults(results: List<ApiFilm>) {
        adapter.submitList(results)
    }

    override fun displayError() {
        Toast.makeText(this, R.string.connection_error, Toast.LENGTH_SHORT).show()
    }

    override fun displayGenreResults(results: ApiGenre) {
        SingletonHolder.instance?.genreMap = results.genres.associate { it.id to it.name } as HashMap<Int, String>
    }
}
