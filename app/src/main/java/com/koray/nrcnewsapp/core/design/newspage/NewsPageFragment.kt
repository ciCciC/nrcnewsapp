package com.koray.nrcnewsapp.core.design.newspage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.koray.nrcnewsapp.R
import com.koray.nrcnewsapp.core.design.category.CategoryOnListInteractionListener
import com.koray.nrcnewsapp.core.util.inject
import com.koray.nrcnewsapp.core.domain.*
import com.koray.nrcnewsapp.core.network.repository.ArticleRepository
import com.koray.nrcnewsapp.core.network.repository.CategoryRepository
import com.koray.nrcnewsapp.core.network.viewmodel.CategorySelectionModel
import com.koray.nrcnewsapp.core.network.viewmodel.CustomViewModelFactory
import com.koray.nrcnewsapp.core.network.viewmodel.LiveArticlesModel
import com.koray.nrcnewsapp.core.network.viewmodel.LiveCategoriesModel
import java.util.*
import kotlin.collections.ArrayList


class NewsPageFragment : Fragment() {

    private var listenerNewsPage: NewsPageOnListFragmentInteractionListener? = null
    private var categoryListener: CategoryOnListInteractionListener? = null

    private val categoryRepository: CategoryRepository by inject()
    private val articleRepository: ArticleRepository by inject()

    private val categorySelectionModel: CategorySelectionModel by activityViewModels()

    private val newsPageItemList: MutableList<NewsPageItemModel> = ArrayList()
    private val newsPageItemMap: MutableMap<NewsPageItemModel.ItemType, Any> =
        EnumMap(NewsPageItemModel.ItemType::class.java)

    private lateinit var newsPagerAdapter: NewsPageRecyclerViewAdapter

    private var selectedCategory = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_page_list, container, false)

        fetchCategoryNames()

        // Set the adapter
        if (view is RecyclerView) {
            newsPagerAdapter = NewsPageRecyclerViewAdapter(
                newsPageItemList,
                newsPageItemMap,
                listenerNewsPage,
                categoryListener
            )
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = newsPagerAdapter
            }
        }
        return view
    }

    private fun fetchCategoryNames() {
        fetchDummyCategoryNames()

//        val model = ViewModelProvider(this, CustomViewModelFactory(categoryRepository))
//            .get(LiveCategoriesModel::class.java)
//
//        model.getCategories().observe(viewLifecycleOwner, Observer { categoryList ->
//                newsPageItemList.add(
//                    CategoryListItemModel(
//                        categoryList.map { categoryItemModel ->
//                            var bgId = resources.getIdentifier(categoryItemModel.name, "drawable",
//                                context?.packageName)
//                            categoryItemModel.img = bgId
//                            categoryItemModel
//                        },
//                        NewsPageItemModel.ItemType.CATEGORY
//                    )
//                )
//                categorySelectionModel.setCashCategories(categoryList)
//                newsPageItemMap[NewsPageItemModel.ItemType.CATEGORY] = categoryList
//            })
    }

    private fun fetchDummyCategoryNames() {
        val dummyCategoryList = arrayListOf("games", "physics", "technology", "astronomy")
        val categoryList = dummyCategoryList.map { categoryName ->
                val backgroundImgIdentifier = resources.getIdentifier(categoryName, "drawable",
                    context?.packageName)

                CategoryItemModel(categoryName, backgroundImgIdentifier)
            }

        categorySelectionModel.setCashCategories(categoryList)

        val categoryListItemModel =
            CategoryListItemModel(categoryList, NewsPageItemModel.ItemType.CATEGORY)
        newsPageItemList.add(categoryListItemModel)
        newsPageItemMap[NewsPageItemModel.ItemType.CATEGORY] = categoryList
    }

    private fun fetchArticleItems(category: String) {
//        fetchDummyArticleItems()

        val articlesModel = ViewModelProvider(this, CustomViewModelFactory(articleRepository))
            .get(LiveArticlesModel::class.java)

        // With chosen category
        articlesModel.getAllByCategory(category).observe(viewLifecycleOwner, Observer { models ->
            newsPageItemList.removeAll { item -> item.itemType!! == NewsPageItemModel.ItemType.ARTICLE }
            newsPageItemList.addAll(models)
            newsPagerAdapter.notifyDataSetChanged()
        })
    }

    private fun fetchDummyArticleItems() {
        newsPageItemList.removeAll { item -> item.itemType!! == NewsPageItemModel.ItemType.ARTICLE }

        (1..10).forEach { _ ->
            newsPageItemList.add(
                ArticleItemModel(
                    "http://www.nrc.nl/nieuws/2020/03/20/vreedzaam-leven-met-cyborgs-a3994387",
                    "",
                    "Cyborgs",
                    "In gesprek met James Lovelock, de profeet van moeder aarde",
                    "Met kunstmatige intelligentie kunnen we de aarde redden, denkt James Lovelock. In de jaren zeventig ontwikkelde de 100-jarige chemicus de Gaia-hypothese, over de aarde als zelfregulerend systeem. Klimaatverandering bedreigt dat mechanisme.",
                    NewsPageItemModel.ItemType.ARTICLE
                )
            )
        }

        newsPagerAdapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categorySelectionModel.getCategory().observe(viewLifecycleOwner, Observer { category ->
            selectedCategory = category
            fetchArticleItems(category)
        })

        if(selectedCategory.isEmpty()){
            autoLoadArticles()
        }
    }

    private fun autoLoadArticles() {
        categorySelectionModel.getCashedCategories().observe(viewLifecycleOwner, Observer { categoryList ->
            val initCategory = categoryList.getOrElse(0){CategoryItemModel("", 0)}.name!!
            fetchArticleItems(initCategory)
            categorySelectionModel.setCategory(initCategory)
            selectedCategory = initCategory
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is NewsPageOnListFragmentInteractionListener)
            listenerNewsPage = context

        if (context is CategoryOnListInteractionListener)
            categoryListener = context

        if (context !is NewsPageOnListFragmentInteractionListener || (context !is CategoryOnListInteractionListener))
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
    }

    override fun onDetach() {
        super.onDetach()
        listenerNewsPage = null
        categoryListener = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsPageFragment()

        fun getTagName(): String {
            return NewsPageFragment::class.java.name
        }
    }
}