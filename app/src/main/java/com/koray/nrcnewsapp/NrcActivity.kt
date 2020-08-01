package com.koray.nrcnewsapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.koray.nrcnewsapp.core.design.articlepage.ArticlePageFragment
import com.koray.nrcnewsapp.core.design.category.CategoryOnListInteractionListener
import com.koray.nrcnewsapp.core.design.info.MenuItemInfoFragment
import com.koray.nrcnewsapp.core.design.newspage.NewsPageFragment
import com.koray.nrcnewsapp.core.design.newspage.NewsPageOnListFragmentInteractionListener
import com.koray.nrcnewsapp.core.domain.ArticleItemModel
import com.koray.nrcnewsapp.core.domain.CategoryItemModel
import com.koray.nrcnewsapp.core.domain.NewsPageItemModel
import com.koray.nrcnewsapp.core.network.viewmodel.ArticleSelectionModel
import com.koray.nrcnewsapp.core.network.viewmodel.CategorySelectionModel
import com.koray.nrcnewsapp.core.util.FragmentAnimation
import javax.inject.Singleton


@Singleton
class NrcActivity : AppCompatActivity(),
    CategoryOnListInteractionListener,
    NewsPageOnListFragmentInteractionListener {

    private val categorySelectionModel: CategorySelectionModel by viewModels()
    private val articleItemSelectionModel: ArticleSelectionModel by viewModels()
    private lateinit var toolbarText: TextView

    private val menuItemInfoFragment: MenuItemInfoFragment = MenuItemInfoFragment.newInstance()
    private val newsPageFragment: NewsPageFragment = NewsPageFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setCustomToolbar()

//        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
//            Configuration.UI_MODE_NIGHT_NO -> {
//            } // Night mode is not active, we're using the light theme
//            Configuration.UI_MODE_NIGHT_YES -> {
//
//            } // Night mode is active, we're using dark theme
//        }

        if(savedInstanceState == null){
            initNewsPageFragment()
        }
    }

    private fun setCustomToolbar() {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_app)

        toolbarText = findViewById(R.id.toolbar_title)

        categorySelectionModel.getCategory()
            .observe(this, Observer { selected -> toolbarText.text = selected })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when (item?.itemId) {
            R.id.menu_info -> {
                if(!menuItemInfoFragment.isVisible){initInfoFragment()}
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initInfoFragment() {
        val slide = FragmentAnimation.slide(supportFragmentManager)
        commitFragment(slide, menuItemInfoFragment, MenuItemInfoFragment.getTagName())
    }

    private fun initNewsPageFragment() {
        val rightBottomToLeftTop = FragmentAnimation.rightBottomToLeftTop(supportFragmentManager)
        commitFragment(rightBottomToLeftTop, newsPageFragment, ArticlePageFragment.getTagName())
    }

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
        println("Selected: " + category?.name)
        categorySelectionModel.setCategory(category?.name.toString())
    }

    override fun onListFragmentInteraction(newsPageItem: NewsPageItemModel?) {
        if (newsPageItem is ArticleItemModel) {
            articleItemSelectionModel.setArticleItemModel(newsPageItem)
            initArticlePageFragment()
        }
    }
}