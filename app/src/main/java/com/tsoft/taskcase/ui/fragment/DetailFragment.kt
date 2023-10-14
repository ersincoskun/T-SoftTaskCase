package com.tsoft.taskcase.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.tsoft.taskcase.base.BaseFragment
import com.tsoft.taskcase.databinding.FragmentDetailBinding
import com.tsoft.taskcase.model.ImageHit
import com.tsoft.taskcase.utils.loadGlideImage
import com.tsoft.taskcase.utils.onSingleClickListener
import com.tsoft.taskcase.utils.showDefaultError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val args: DetailFragmentArgs by navArgs()
    private var imageHit: ImageHit? = null
    private var isAddedFavorite = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        prepareUI()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun prepareUI() {
        imageHit = args.imageHitArg
        binding.apply {
            imageHit?.let { safeImageHit ->
                imageView.loadGlideImage(safeImageHit.largeImageURL)
                civUserProfilePicture.loadGlideImage(safeImageHit.userImageURL)
                tvUsername.text = safeImageHit.user
                tvCommentCount.text = safeImageHit.comments.toString()
                tvDownloadCount.text = safeImageHit.downloads.toString()
                tvLikeCount.text = safeImageHit.likes.toString()
                tvViewCount.text = safeImageHit.views.toString()
            } ?: kotlin.run {
                navigateBackStack()
                context.showDefaultError()
            }
        }
    }

    private fun setListeners() {
        binding.ivAddFavoriteIcon.onSingleClickListener {

        }
    }

}