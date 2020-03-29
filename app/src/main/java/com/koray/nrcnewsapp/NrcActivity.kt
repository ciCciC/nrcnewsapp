package com.koray.nrcnewsapp

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.koray.nrcnewsapp.core.design.articlepage.ArticlePageFragment
import com.koray.nrcnewsapp.core.design.category.CategoryItemFragment
import com.koray.nrcnewsapp.core.design.category.CategoryOnListInteractionListener
import com.koray.nrcnewsapp.core.design.newspage.NewsPageFragment
import com.koray.nrcnewsapp.core.design.newspage.NewsPageOnListFragmentInteractionListener
import com.koray.nrcnewsapp.core.design.util.FragmentAnimation
import com.koray.nrcnewsapp.core.design.util.inject
import com.koray.nrcnewsapp.core.domain.ArticleItemModel
import com.koray.nrcnewsapp.core.domain.CategoryItemModel
import com.koray.nrcnewsapp.core.domain.NewsPageItemModel
import com.koray.nrcnewsapp.core.network.repository.ArticleRepository
import com.koray.nrcnewsapp.core.network.repository.CategoryRepository
import com.koray.nrcnewsapp.core.network.viewmodel.ArticleSelectionModel
import com.koray.nrcnewsapp.core.network.viewmodel.CategorySelectionModel
import javax.inject.Singleton


@Singleton
class NrcActivity : AppCompatActivity(),
    CategoryOnListInteractionListener,
    NewsPageOnListFragmentInteractionListener {

    private val articleRepository: ArticleRepository by inject()
    private val categoryRepository: CategoryRepository by inject()
    private val categoryItemFragment: CategoryItemFragment by lazy {
        CategoryItemFragment.newInstance()
    }

    private val categorySelectionModel: CategorySelectionModel by viewModels()
    private val articleItemSelectionModel: ArticleSelectionModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // TODO: custom toolbar
//        setSupportActionBar(findViewById(R.id.my_toolbar))

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
            } // Night mode is not active, we're using the light theme
            Configuration.UI_MODE_NIGHT_YES -> {

            } // Night mode is active, we're using dark theme
        }

//        initCategoryListFragment()
        initNewsPageFragment()
//        initArticleRandomListFragment()
    }

//    private fun initArticleRandomListFragment(){
//        val articleListFragment: ArticleListFragment = ArticleListFragment.newInstance(1)
//        FragmentAnimation.rightBottomToLeftTop(supportFragmentManager)
//            .add(R.id.article_random_list_container,
//                articleListFragment,
//                ArticleListFragment.getTagName())
//            .addToBackStack(null)
//            .commit()
//    }

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
        showFillInMsgUiThread(category?.name.toString())
        println("Clicked category")
    }

    override fun onListFragmentInteraction(newsPageItem: NewsPageItemModel?) {
        if (newsPageItem is ArticleItemModel) {
            articleItemSelectionModel.setArticleItemModel(newsPageItem)
            initArticlePageFragment()
        }
    }
}