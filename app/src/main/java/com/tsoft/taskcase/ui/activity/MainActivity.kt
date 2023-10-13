package com.tsoft.taskcase.ui.activity

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.tsoft.taskcase.R
import com.tsoft.taskcase.base.BaseActivity
import com.tsoft.taskcase.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var lastSelectedView: View? = null

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
        setupBottomNavView()
    }

    private fun setupBottomNavView() {
        val navController = findNavController(R.id.mainFragmentContainerView)
        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Find the menu item associated with the destination id
            var view: View? = null
            for (i in 0 until binding.bottomNavigationView.menu.size()) {
                val item = binding.bottomNavigationView.menu.getItem(i)
                if (item.itemId == destination.id) {
                    view = binding.bottomNavigationView.findViewById(item.itemId)
                    break
                }
            }

            // Reset the last selected item to its original scale
            lastSelectedView?.animate()?.scaleX(1f)?.scaleY(1f)?.setDuration(200)?.start()

            // Animate the newly selected item
            view?.animate()?.scaleX(1.2f)?.scaleY(1.2f)?.setDuration(200)?.start()

            lastSelectedView = view
        }
    }

}