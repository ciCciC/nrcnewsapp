package com.koray.nrcnewsapp

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.koray.nrcnewsapp.core.domain.ArticleItemModel
import com.koray.nrcnewsapp.core.domain.CategoryItemModel
import com.koray.nrcnewsapp.core.domain.NewsPageItemModel
import com.koray.nrcnewsapp.core.network.viewmodel.LiveArticleSelectionModel
import com.koray.nrcnewsapp.core.network.viewmodel.LiveCategorySelectionModel
import com.koray.nrcnewsapp.core.network.viewmodel.LiveToolbarArrow
import com.koray.nrcnewsapp.core.ui.articlepage.ArticlePageFragment
import com.koray.nrcnewsapp.core.ui.category.CategoryOnListInteractionListener
import com.koray.nrcnewsapp.core.ui.info.MenuItemInfoFragment
import com.koray.nrcnewsapp.core.ui.login.LiveAccountModel
import com.koray.nrcnewsapp.core.ui.newspage.NewsPageOnListFragmentInteractionListener
import com.koray.nrcnewsapp.core.util.AnimationEffect
import com.koray.nrcnewsapp.core.util.ChangeBackgroundOnTouch
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
    private lateinit var toolbarText: TextView
    private lateinit var toolbarArrow: ImageView

    private val menuItemInfoFragment: MenuItemInfoFragment = MenuItemInfoFragment.newInstance()

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContentView(R.layout.activity_main)
        setCustomToolbar()
        setGoogleSignInClient()
        checkSignIn()
    }

    private fun checkSignIn() {
        this.liveAccountModel
            .getAccount()
            .observe(this, Observer { googleSignInAccount ->
                if (googleSignInAccount.isLoggedIn) {
                    val navController = findNavController(R.id.nav_host_fragment)
                    navController.navigate(R.id.newsPageFragment)
                } else {
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

    @SuppressLint("ClickableViewAccessibility")
    private fun setCustomToolbar() {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_app)

        toolbarText = findViewById(R.id.toolbar_title)
        toolbarArrow = findViewById(R.id.toolbar_arrow)
        toolbarArrow.visibility = View.INVISIBLE

        val rotate = AnimationEffect.rotate(duration = 2 * 200)

        toolbarArrow.setOnClickListener {
            it.startAnimation(rotate)
            onBackPressed()
        }

        toolbarArrow.setOnTouchListener(ChangeBackgroundOnTouch(resources))

        liveToolbarArrow.getStatus()
            .observe(this, Observer { status -> toolbarArrow.visibility = status })

        liveCategorySelectionModel.getCategory()
            .observe(this, Observer { categoryItem ->
                run {
                    val categoryDisplay = categoryItem.display!!
                    toolbarText.text =
                        "${categoryDisplay[0].toUpperCase() + categoryDisplay.substring(1)}"
                }
            }
            )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when (item?.itemId) {
            R.id.menu_info -> {
                if (!menuItemInfoFragment.isVisible) {
                    val navController = findNavController(R.id.nav_host_fragment)
                    navController.navigate(R.id.menuItemInfoFragment)
                    liveToolbarArrow.showArrow()
                }
                return true
            }
            R.id.menu_logout -> {
                this.singOut()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//    private fun initInfoFragment() {
//        val slide = FragmentAnimation.slide(supportFragmentManager)
//        commitFragment(slide, menuItemInfoFragment, MenuItemInfoFragment.getTagName())
//    }

//    private fun initNewsPageFragment() {
//        val rightBottomToLeftTop = FragmentAnimation.rightBottomToLeftTop(supportFragmentManager)
//        commitFragment(rightBottomToLeftTop, newsPageFragment, ArticlePageFragment.getTagName())
//    }

    private fun initArticlePageFragment() {
        val articlePageFragment: ArticlePageFragment = ArticlePageFragment.newInstance()
        val rightToLeftAnim = FragmentAnimation.rightToLeftAnim(supportFragmentManager)
        commitFragment(rightToLeftAnim, articlePageFragment, ArticlePageFragment.getTagName())
    }

    private fun commitFragment(
        fragmentTransaction: FragmentTransaction,
        fragment: Fragment,
        fragmentTag: String,
        containerId: Int = R.id.news_page_container
    ) {
        fragmentTransaction.add(
            containerId,
            fragment,
            fragmentTag
        )
            .addToBackStack(null)
            .commit()
    }

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