package com.tsoft.taskcase.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.tsoft.taskcase.R
import com.tsoft.taskcase.base.BaseFragment
import com.tsoft.taskcase.databinding.FragmentDetailBinding
import com.tsoft.taskcase.model.ImageHit
import com.tsoft.taskcase.utils.loadGlideImage
import com.tsoft.taskcase.utils.onSingleClickListener
import com.tsoft.taskcase.utils.setClickEffect
import com.tsoft.taskcase.utils.showDefaultError
import com.tsoft.taskcase.viewmodel.DetailFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val viewModel: DetailFragmentViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private var imageHit: ImageHit? = null
    private var mIsFavorite = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subLiveData()
        setListeners()
        prepareUI()
    }

    private fun subLiveData() {
        viewModel.isFavoriteLiveData.observe(viewLifecycleOwner) { isFavorite ->
            hideProgressBar()
            mIsFavorite = isFavorite
            binding.ivAddFavoriteIcon.setImageResource(if (isFavorite) R.drawable.added_favorite_icon else R.drawable.not_added_favorite_icon)
        }
    }

    private fun prepareUI() {
        removeBottomNavView()
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
                ivAddFavoriteIcon.setClickEffect()
                ivBackIcon.setClickEffect()
                showProgressBar()
                viewModel.checkIsFavorite(safeImageHit)
            } ?: kotlin.run {
                navigateBackStack()
                context.showDefaultError()
            }
        }
    }

    private fun setListeners() {
        binding.ivAddFavoriteIcon.onSingleClickListener {
            //burada rahatça !! kullanıyorum çünkü prepareUI fonksiyonu içerisinde eğer imagehit null ise
            //navigate back yapıp hata mesajı bastırıyorum
            if (mIsFavorite) viewModel.deleteFavoriteById(imageHit!!.id)
            else viewModel.addFavorite(imageHit!!)
            mIsFavorite = !mIsFavorite
            binding.ivAddFavoriteIcon.setImageResource(if (mIsFavorite) R.drawable.added_favorite_icon else R.drawable.not_added_favorite_icon)
        }
        binding.ivBackIcon.onSingleClickListener {
            navigateBackStack()
        }
    }

}