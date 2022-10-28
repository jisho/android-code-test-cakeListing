package biz.filmeroo.premier.detail

import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import biz.filmeroo.premier.R
import biz.filmeroo.premier.api.ApiFilm
import biz.filmeroo.premier.api.FilmService
import biz.filmeroo.premier.app.SingletonHolder
import biz.filmeroo.premier.base.inflate
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.item_film.view.image
import kotlinx.android.synthetic.main.item_film.view.rating
import kotlinx.android.synthetic.main.item_film.view.title
import kotlinx.android.synthetic.main.item_similar_film.view.*
import javax.inject.Inject

internal class FilmSimilarAdapter @Inject constructor(private val picasso: Picasso) :
    ListAdapter<ApiFilm, FilmSimilarAdapter.Holder>(diffCallback) {

    init {
        setHasStableIds(true)
    }

    private var onClick: ((Long) -> Unit)? = null
    private var genre: HashMap<Int, String>? = SingletonHolder.instance?.genreMap

    fun setOnClick(onClick: (Long) -> Unit) {
        this.onClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(parent.inflate(R.layout.item_similar_film))

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(getItem(position))

    override fun getItemId(position: Int) = getItem(position).id

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ApiFilm) {
            itemView.title.text = item.title
            itemView.rating.text = item.voteAverage
            itemView.genre.text = genre?.get(item.genreId[0])
            val cornerRadius = itemView.resources.getDimensionPixelSize(R.dimen.image_corner_radius)
            picasso
                .load(FilmService.buildImageUrl(item.posterPath))
                .transform(RoundedCornersTransformation(cornerRadius, 0))
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(itemView.image)
            itemView.setOnClickListener { onClick?.invoke(item.id) }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ApiFilm>() {
            override fun areItemsTheSame(@NonNull old: ApiFilm, @NonNull new: ApiFilm) =
                old.id == new.id

            override fun areContentsTheSame(@NonNull old: ApiFilm, @NonNull new: ApiFilm) =
                old == new
        }
    }
}
