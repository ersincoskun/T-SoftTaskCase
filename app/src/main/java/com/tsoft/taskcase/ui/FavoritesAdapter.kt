package com.tsoft.taskcase.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tsoft.taskcase.databinding.ItemImageListBinding
import com.tsoft.taskcase.model.ImageHit
import com.tsoft.taskcase.utils.loadGlideImage
import com.tsoft.taskcase.utils.remove

class FavoritesAdapter() : ListAdapter<ImageHit, FavoritesAdapter.FavoritesViewHolder>(FavoritesDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            ItemImageListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) =
        with(holder.binding) {
            val imageHit = getItem(position)
            civUserProfilePicture.loadGlideImage(imageHit.userImageURL)
            tvUsername.text = imageHit.user
            imageView.loadGlideImage(imageHit.webformatURL)
            ivAddFavoriteIcon.remove()
        }

    class FavoritesViewHolder(val binding: ItemImageListBinding) :
        RecyclerView.ViewHolder(binding.root)

    class FavoritesDiffUtil : DiffUtil.ItemCallback<ImageHit>() {
        override fun areItemsTheSame(oldItem: ImageHit, newItem: ImageHit): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageHit, newItem: ImageHit): Boolean {
            return oldItem == newItem
        }
    }
}