package biz.filmeroo.premier.main

import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import biz.filmeroo.premier.R
import biz.filmeroo.premier.api.ApiCakeItem
import biz.filmeroo.premier.base.inflate
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.item_cake.view.*
import javax.inject.Inject

internal class CakeAdapter @Inject constructor(private val picasso: Picasso) :
    ListAdapter<ApiCakeItem, CakeAdapter.Holder>(diffCallback) {

    init {
        setHasStableIds(true)
    }

    private var onClick: ((ApiCakeItem) -> Unit)? = null

    fun setOnClick(onClick: (ApiCakeItem) -> Unit) {
        this.onClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(parent.inflate(R.layout.item_cake))

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(getItem(position))

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ApiCakeItem) {
            itemView.title.text = item.title
            itemView.overview.text = item.desc
            val cornerRadius = itemView.resources.getDimensionPixelSize(R.dimen.image_corner_radius)
            picasso
                .load(item.image)
                .transform(RoundedCornersTransformation(cornerRadius, 0))
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(itemView.image)
            itemView.setOnClickListener { onClick?.invoke(item) }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ApiCakeItem>() {
            override fun areItemsTheSame(@NonNull old: ApiCakeItem, @NonNull new: ApiCakeItem) =
                old.title == new.title

            override fun areContentsTheSame(@NonNull old: ApiCakeItem, @NonNull new: ApiCakeItem) =
                old == new
        }
    }
}
