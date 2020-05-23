package com.koray.nrcnewsapp

import android.content.res.Configuration
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.koray.nrcnewsapp.core.design.articlepage.ArticlePageFragment
import com.koray.nrcnewsapp.core.design.category.CategoryOnListInteractionListener
import com.koray.nrcnewsapp.core.design.newspage.NewsPageFragment
import com.koray.nrcnewsapp.core.design.newspage.NewsPageOnListFragmentInteractionListener
import com.koray.nrcnewsapp.core.design.util.FragmentAnimation
import com.koray.nrcnewsapp.core.domain.ArticleItemModel
import com.koray.nrcnewsapp.core.domain.CategoryItemModel
import com.koray.nrcnewsapp.core.domain.NewsPageItemModel
import com.koray.nrcnewsapp.core.network.viewmodel.ArticleSelectionModel
import com.koray.nrcnewsapp.core.network.viewmodel.CategorySelectionModel
import javax.inject.Singleton


@Singleton
class NrcActivity : AppCompatActivity(),
    CategoryOnListInteractionListener,
    NewsPageOnListFragmentInteractionListener {

//    private val articleRepository: ArticleRepository by inject()
//    private val categoryRepository: CategoryRepository by inject()

    private val categorySelectionModel: CategorySelectionModel by viewModels()
    private val articleItemSelectionModel: ArticleSelectionModel by viewModels()
    private lateinit var toolbarText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setCustomToolbar()

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
            } // Night mode is not active, we're using the light theme
            Configuration.UI_MODE_NIGHT_YES -> {

            } // Night mode is active, we're using dark theme
        }

        initNewsPageFragment()
    }

    private fun setCustomToolbar() {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_app)

        toolbarText = findViewById(R.id.toolbar_title)
        categorySelectionModel.getCategory().observe(this, Observer { selected -> toolbarText.text = selected} )
    }

    // TODO
    private fun initNewsPageFragment() {
        val newsPageFragment: NewsPageFragment = NewsPageFragment.newInstance()
        FragmentAnimation.rightBottomToLeftTop(supportFragmentManager)
            .add(
                R.id.news_page_container,
                newsPageFragment,
                NewsPageFragment.getTagName()
            )
            .addToBackStack(null)
            .commit()
    }

    private fun initArticlePageFragment() {
        val articlePageFragment: ArticlePageFragment = ArticlePageFragment.newInstance()
        FragmentAnimation.rightBottomToLeftTop(supportFragmentManager)
            .add(
                R.id.news_page_container,
                articlePageFragment,
                ArticlePageFragment.getTagName()
            )
            .addToBackStack(null)
            .commit()
    }

    private fun showFillInMsgUiThread(msg: String) {
        this.runOnUiThread {
            initToastMsg(
                msg,
                Toast.LENGTH_LONG
            )
        }
    }

    private fun initToastMsg(msg: String, length: Int) {
        Toast.makeText(this, msg, length).show()
    }

    override fun onListFragmentInteraction(category: CategoryItemModel?) {
        categorySelectionModel.setCategory(category?.name.toString())
//        showFillInMsgUiThread(category?.name.toString())
        println("Clicked category: $category")
    }

    override fun onListFragmentInteraction(newsPageItem: NewsPageItemModel?) {
        if (newsPageItem is ArticleItemModel) {
            articleItemSelectionModel.setArticleItemModel(newsPageItem)
            initArticlePageFragment()
        }
    }
}