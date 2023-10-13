package com.tsoft.taskcase.ui

import android.animation.Animator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tsoft.taskcase.databinding.ItemImageListBinding
import com.tsoft.taskcase.model.ImageHit
import com.tsoft.taskcase.utils.loadGlideImage

class ImageAdapter : PagingDataAdapter<ImageHit, ImageViewHolder>(IMAGE_COMPARATOR) {
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder.create(parent)
    }

    companion object {
        val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<ImageHit>() {
            override fun areItemsTheSame(oldItem: ImageHit, newItem: ImageHit): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: ImageHit, newItem: ImageHit): Boolean = oldItem == newItem
        }
    }
}

class ImageViewHolder(private val binding: ItemImageListBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(imageHit: ImageHit) {
        binding.apply {
            civUserProfilePicture.loadGlideImage(imageHit.userImageURL)
            tvUsername.text = imageHit.user
            imageView.loadGlideImage(imageHit.webformatURL)
            ivAddFavoriteIcon.setOnClickListener {
                if (lottieAnimationView.visibility == View.GONE) {
                    // Start the animation
                    lottieAnimationView.visibility = View.VISIBLE
                    lottieAnimationView.playAnimation()
                } else {
                    // If you need to handle the "else" case
                }
            }

            lottieAnimationView.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {
                    lottieAnimationView.visibility = View.GONE
                }

                override fun onAnimationCancel(animation: Animator) {}

                override fun onAnimationRepeat(animation: Animator) {}
            })
        }
    }

    companion object {
        fun create(parent: ViewGroup): ImageViewHolder {
            val binding = ItemImageListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return ImageViewHolder(binding)
        }
    }
}

