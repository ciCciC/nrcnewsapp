package com.koray.nrcnewsapp

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.children
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.appbar.MaterialToolbar
import com.koray.nrcnewsapp.core.domain.ArticleItemModel
import com.koray.nrcnewsapp.core.domain.CategoryItemModel
import com.koray.nrcnewsapp.core.domain.NewsPageItemModel
import com.koray.nrcnewsapp.core.network.viewmodel.LiveArticleSelectionModel
import com.koray.nrcnewsapp.core.network.viewmodel.LiveCategorySelectionModel
import com.koray.nrcnewsapp.core.network.viewmodel.LiveToolbarArrow
import com.koray.nrcnewsapp.core.ui.LiveToolbarModel
import com.koray.nrcnewsapp.core.ui.articlepage.ArticlePageFragment
import com.koray.nrcnewsapp.core.ui.category.CategoryOnListInteractionListener
import com.koray.nrcnewsapp.core.ui.login.LiveAccountModel
import com.koray.nrcnewsapp.core.ui.newspage.NewsPageOnListFragmentInteractionListener
import com.koray.nrcnewsapp.core.util.AnimationEffect
import com.koray.nrcnewsapp.core.util.FragmentAnimation
import javax.inject.Singleton


@Singleton
class NrcActivity : AppCompatActivity(),
    CategoryOnListInteractionListener,
    NewsPageOnListFragmentInteractionListener {

    private val liveCategorySelectionModel: LiveCategorySelectionModel by viewModels()
    private val liveArticleItemSelectionModel: LiveArticleSelectionModel by viewModels()
    private val liveAccountModel: LiveAccountModel by viewModels()
    private val liveToolbarArrow: LiveToolbarArrow by viewModels()
    private val liveToolbarModel: LiveToolbarModel by viewModels()

    private lateinit var toolbar: MaterialToolbar

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContentView(R.layout.activity_main)
        setStatusBarDayNight()
        setToolbar()
        setMenuItem()
        setGoogleSignInClient()
        checkSignIn()
    }

    private fun checkSignIn() {
        this.liveAccountModel
            .getAccount()
            .observe(this, { googleSignInAccount ->

                if (googleSignInAccount.isLoggedIn) {
                    this.liveToolbarModel.show()
                    val navController = findNavController(R.id.nav_host_fragment)
                    navController.navigate(R.id.newsPageFragment)
                } else {
                    this.liveToolbarModel.hide()
                    val navController = findNavController(R.id.nav_host_fragment)
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.loginFragment, true)
                        .build()

                    navController.navigate(R.id.loginFragment, null, navOptions)
                }
            })
    }

    private fun setGoogleSignInClient() {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun setStatusBarDayNight() {
        when(resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR}
            Configuration.UI_MODE_NIGHT_YES -> {window.clearFlags(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)}
        }
    }

    private fun setToolbar() {
        toolbar = findViewById(R.id.toolbar_app)

        val imageButton = toolbar.children
            .firstOrNull { view -> view is AppCompatImageButton} as AppCompatImageButton?

        imageButton?.visibility = View.INVISIBLE

        imageButton?.setOnClickListener {
            it.startAnimation(AnimationEffect
                .rotate(duration = 2 * 200))
            onBackPressed()
        }

        liveToolbarArrow.getStatus()
            .observe(this, { status ->
                imageButton?.visibility = status
            })

        liveCategorySelectionModel.getCategory()
            .observe(this, { categoryItem ->
                    run {
                        val categoryDisplay = categoryItem.display!!
                        toolbar.title =
                            "${categoryDisplay[0].toUpperCase() + categoryDisplay.substring(1)}"
                    }
                }
            )

        liveToolbarModel.getState().observe(this, {state ->
            toolbar.visibility = state
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    private fun setMenuItem() {
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_info -> {
                    val navController = findNavController(R.id.nav_host_fragment)
                    navController.navigate(R.id.menuItemInfoFragment)
                    liveToolbarArrow.showArrow()
                    true
                }
                R.id.menu_logout -> {
                    this.singOut()
                    true
                }
                else -> super.onOptionsItemSelected(menuItem)
            }
        }
    }

    private fun initArticlePageFragment() {
        val articlePageFragment: ArticlePageFragment = ArticlePageFragment.newInstance()
        val rightToLeftAnim = FragmentAnimation.rightToLeftAnim(supportFragmentManager)
//        commitFragment(rightToLeftAnim, articlePageFragment, ArticlePageFragment.getTagName())
    }

//    private fun commitFragment(
//        fragmentTransaction: FragmentTransaction,
//        fragment: Fragment,
//        fragmentTag: String,
//        containerId: Int = R.id.news_page_container
//    ) {
//        fragmentTransaction.add(
//            containerId,
//            fragment,
//            fragmentTag
//        )
//            .addToBackStack(null)
//            .commit()
//    }

    override fun onListFragmentInteraction(category: CategoryItemModel?) {
        liveCategorySelectionModel.setCategory(category!!)
    }

    override fun onListFragmentInteraction(newsPageItem: NewsPageItemModel?) {
        if (newsPageItem is ArticleItemModel) {
            liveArticleItemSelectionModel.setArticleItemModel(newsPageItem)
            initArticlePageFragment()
            liveToolbarArrow.showArrow()
        }
    }

    override fun onBackPressed() {
        val findNavController = findNavController(R.id.nav_host_fragment)

        val destinationId = findNavController.previousBackStackEntry!!.destination.id

        if (destinationId == R.id.newsPageFragment || destinationId == R.id.loginFragment) {
            liveToolbarArrow.hideArrow()
        }

        findNavController.popBackStack()
    }

    private fun singOut() {
        liveToolbarArrow.hideArrow()
        this.googleSignInClient.signOut()
        this.liveAccountModel.signOut()
    }
}