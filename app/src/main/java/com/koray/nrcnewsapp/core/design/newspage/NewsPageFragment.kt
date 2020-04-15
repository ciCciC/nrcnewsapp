package com.koray.nrcnewsapp.core.design.newspage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.koray.nrcnewsapp.R
import com.koray.nrcnewsapp.core.design.category.CategoryOnListInteractionListener
import com.koray.nrcnewsapp.core.design.util.inject
import com.koray.nrcnewsapp.core.domain.*
import com.koray.nrcnewsapp.core.network.repository.ArticleRepository
import com.koray.nrcnewsapp.core.network.repository.CategoryRepository
import com.koray.nrcnewsapp.core.network.viewmodel.ArticleSelectionModel
import com.koray.nrcnewsapp.core.network.viewmodel.CategorySelectionModel
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_page_list, container, false)

        fetchCategoryNames()
//        fetchArticles()

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = NewsPageRecyclerViewAdapter(
                    newsPageItemList,
                    newsPageItemMap,
                    listenerNewsPage,
                    categoryListener
                )
            }
        }
        return view
    }

    private fun fetchCategoryNames() {
        fetchDummyCategoryNames()
//        val model = ViewModelProviders.of(
//            this,
//            CustomViewModelFactory(categoryRepository)
//        ).get(LiveCategoriesModel::class.java)
//
//        model.getCategories()
//            .observe(viewLifecycleOwner, Observer { categoryList ->
//                newsPageItemList.add(
//                    CategoryListItemModel(
//                        categoryList,
//                        NewsPageItemModel.ItemType.CATEGORY
//                    )
//                )
//                newsPageItemMap[NewsPageItemModel.ItemType.CATEGORY] = categoryList
//            })
    }

    private fun fetchDummyCategoryNames() {
        val categoryList =
            arrayListOf("games", "physics", "technology").map { x -> CategoryItemModel(x) }
                .toMutableList()
        categoryList.addAll((1..10).map { CategoryItemModel("Lolz") })

        val categoryListItemModel =
            CategoryListItemModel(categoryList, NewsPageItemModel.ItemType.CATEGORY)
        newsPageItemList.add(categoryListItemModel)
        newsPageItemMap[NewsPageItemModel.ItemType.CATEGORY] = categoryList
    }

    private fun fetchArticles() {
        fetchDummyArticleItems()
//        val model = ViewModelProviders.of(this, CustomViewModelFactory(articleRepository))
//            .get(LiveArticlesModel::class.java)
//        model.getArticleItems().observe(viewLifecycleOwner, Observer { models ->
//            newsPageItemList.addAll(models)
//        })
    }

    private fun fetchDummyArticleItems() {
        (1..10).forEach { _ ->
            newsPageItemList.add(
                ArticleItemModel(
                    "http://www.nrc.nl/nieuws/2020/03/20/vreedzaam-leven-met-cyborgs-a3994387",
                    "https://images.nrc.nl/UBptTebL0NGY2WXHE0lFHBfJguE=/640x384/smart/filters:no_upscale()/s3/static.nrc.nl/bvhw/files/2020/03/data56631545-f25ac3.jpg",
                    "Cyborgs",
                    "In gesprek met James Lovelock, de profeet van moeder aarde",
                    "Met kunstmatige intelligentie kunnen we de aarde redden, denkt James Lovelock. In de jaren zeventig ontwikkelde de 100-jarige chemicus de Gaia-hypothese, over de aarde als zelfregulerend systeem. Klimaatverandering bedreigt dat mechanisme.",
                    NewsPageItemModel.ItemType.ARTICLE
                )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categorySelectionModel.getCategory().observe(viewLifecycleOwner, Observer { category ->
            println("Selected DERP: $category")
        })

        fetchArticles()
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