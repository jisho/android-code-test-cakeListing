package biz.filmeroo.premier.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import biz.filmeroo.premier.R
import biz.filmeroo.premier.api.ApiFilm
import biz.filmeroo.premier.api.ApiGenre
import biz.filmeroo.premier.api.FilmService
import biz.filmeroo.premier.app.SingletonHolder
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity(),
    FilmDetailPresenter.View {

    @Inject
    internal lateinit var presenter: FilmDetailPresenter
    @Inject
    internal lateinit var picasso: Picasso
    @Inject
    internal lateinit var adapter: FilmSimilarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        presenter.start(this)
        setupRecycleView()

        if(SingletonHolder.instance?.genreMap == null)
            presenter.loadGenre(this)

        presenter.loadMovie(this, intent.extras?.getLong(EXTRA_ID, -1) ?: -1)
        presenter.loadSimilarMovie(this, intent.extras?.getLong(EXTRA_ID, -1) ?: -1)
    }

    private fun setupRecycleView() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler.layoutManager = linearLayoutManager
        recycler.adapter = adapter
        adapter.setOnClick { id -> DetailActivity.start(this, id) }
    }

    override fun displayMovie(movie: ApiFilm) {
        if (movie.backdropPath != null) {
            picasso.load(FilmService.buildImageUrl(movie.backdropPath)).into(film_image)
        }
        film_title.text = movie.title
        film_overview.text = movie.overview
    }

    override fun displayError() {
        Toast.makeText(this, R.string.connection_error, Toast.LENGTH_SHORT).show()
    }

    override fun displaySimilarResults(results: List<ApiFilm>) {
        adapter.submitList(results)
    }

    override fun displayGenreResults(results: ApiGenre) {
        SingletonHolder.instance?.genreMap = results.genres.associate { it.id to it.name } as HashMap<Int, String>
    }

    override fun onDestroy() {
        presenter.stop()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_ID = "extra_id"

        fun start(origin: Activity, id: Long) {
            origin.startActivity(Intent(origin, DetailActivity::class.java).apply {
                putExtra(EXTRA_ID, id)
            })
        }
    }
}
