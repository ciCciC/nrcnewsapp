package com.koray.nrcnewsapp

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.util.AttributeSet
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.koray.nrcnewsapp.core.design.articlelist.ArticleListFragment
import com.koray.nrcnewsapp.core.design.category.CategoryItemFragment
import com.koray.nrcnewsapp.core.design.util.FragmentAnimation
import com.koray.nrcnewsapp.core.domain.ArticleItemModel
import com.koray.nrcnewsapp.core.network.repository.NrcRepository
import com.koray.nrcnewsapp.core.network.viewmodel.CategorySelectionModel
import com.koray.nrcnewsapp.core.network.viewmodel.CustomViewModelFactory
import com.koray.nrcnewsapp.core.network.viewmodel.LiveArticlesModel
import javax.inject.Singleton


@Singleton
class NrcActivity : AppCompatActivity(),
    CategoryItemFragment.OnListFragmentInteractionListener {

    private val nrcRepository: NrcRepository by inject()
    private val categoryItemFragment: CategoryItemFragment by lazy {
        CategoryItemFragment.newInstance()
    }

    private val categorySelectionModel: CategorySelectionModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(findViewById(R.id.my_toolbar))

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {} // Night mode is not active, we're using the light theme
            Configuration.UI_MODE_NIGHT_YES -> {

            } // Night mode is active, we're using dark theme
        }

//        this.getAllCategories()
//        this.getAllArticles()
        initCategoryListFragment()
        initArticleRandomListFragment()
    }

    private fun getAllCategories(){
        val model = ViewModelProviders.of(this, CustomViewModelFactory(nrcRepository)).get(LiveArticlesModel::class.java)
        model.getCategories().observe(this, Observer<List<String>> { model ->
            model.forEach { x -> println("categorie: $x") }
        })
    }

    private fun getAllArticles() {
        val model = ViewModelProviders.of(this, CustomViewModelFactory(nrcRepository)).get(LiveArticlesModel::class.java)
        model.getArticles().observe(this, Observer<List<ArticleItemModel>> { models ->
            models.forEach { article -> println("Retrieved: $article") }
        })
    }

    private fun initCategoryListFragment() {
//        val categoryItemFragment: CategoryItemFragment = CategoryItemFragment.newInstance()
        FragmentAnimation.rightBottomToLeftTop(supportFragmentManager)
            .add(R.id.category_list_container,
                categoryItemFragment,
                CategoryItemFragment.getTagName())
            .addToBackStack(null)
            .commit()
    }

    private fun initArticleRandomListFragment(){
        val articleListFragment: ArticleListFragment = ArticleListFragment.newInstance(1)
        FragmentAnimation.rightBottomToLeftTop(supportFragmentManager)
            .add(R.id.article_random_list_container,
                articleListFragment,
                ArticleListFragment.getTagName())
            .addToBackStack(null)
            .commit()
    }

    override fun onListFragmentInteraction(category: String?) {
        categorySelectionModel.setCategory(category!!)
        showFillInMsgUiThread(category)
        println("Clicked category")
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
}

inline fun <reified T> Activity.inject(): Lazy<T> = lazy {
    (this.applicationContext as? BaseApplication)?.ctx?.getBean(T::class.java)!!
}