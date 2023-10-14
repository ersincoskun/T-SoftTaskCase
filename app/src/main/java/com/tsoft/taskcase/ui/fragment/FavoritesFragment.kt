package com.tsoft.taskcase.ui.fragment

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.tsoft.taskcase.R
import com.tsoft.taskcase.base.BaseFragment
import com.tsoft.taskcase.databinding.FragmentFavoritesBinding
import com.tsoft.taskcase.model.ImageHit
import com.tsoft.taskcase.ui.FavoritesAdapter
import com.tsoft.taskcase.viewmodel.FavoritesFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>() {

    private val viewModel: FavoritesFragmentViewModel by viewModels()
    private lateinit var adapter: FavoritesAdapter
    private val mFavoritesList = mutableListOf<ImageHit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FavoritesAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subLiveData()
        binding.rvFavoritesList.adapter = adapter
        setItemTouchHelper()
        viewModel.getFavoritesList()
    }

    private fun subLiveData() {
        viewModel.favoritesListLiveData.observe(viewLifecycleOwner) { favoritesList ->
            mFavoritesList.clear()
            mFavoritesList.addAll(favoritesList)
            adapter.submitList(favoritesList)
        }
    }

    private fun setItemTouchHelper() {
        val background = ColorDrawable(Color.TRANSPARENT)
        var deleteIcon: Drawable? = null
        context?.let { safeContext ->
            deleteIcon = ContextCompat.getDrawable(safeContext, R.drawable.ic_delete)
        }
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val itemToRemove = mFavoritesList[position]
                viewModel.deleteFavoriteById(itemToRemove.id)
                adapter.notifyItemRemoved(position)
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                deleteIcon?.let { safeDeleteIcon ->
                    val itemView = viewHolder.itemView
                    val iconMargin = (itemView.height - safeDeleteIcon.intrinsicHeight) / 2

                    if (dX < 0) { // swiping left
                        background.setBounds(
                            itemView.right + dX.toInt(),
                            itemView.top,
                            itemView.right,
                            itemView.bottom
                        )
                        safeDeleteIcon.setBounds(
                            itemView.right - safeDeleteIcon.intrinsicWidth - iconMargin,
                            itemView.top + iconMargin,
                            itemView.right - iconMargin,
                            itemView.bottom - iconMargin
                        )
                    }

                    background.draw(c)

                    if (dX < 0) { // swiping left
                        safeDeleteIcon.draw(c)
                    }
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvFavoritesList)
    }

}