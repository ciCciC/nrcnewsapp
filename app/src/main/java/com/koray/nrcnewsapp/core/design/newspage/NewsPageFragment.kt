package com.koray.nrcnewsapp.core.design.newspage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.koray.nrcnewsapp.R
import com.koray.nrcnewsapp.core.design.category.CategoryItemRecyclerViewAdapter
import com.koray.nrcnewsapp.core.design.category.CategoryOnListInteractionListener
import com.koray.nrcnewsapp.core.domain.ArticleItemModel
import com.koray.nrcnewsapp.core.domain.CategoryItemListModel
import com.koray.nrcnewsapp.core.domain.CategoryItemModel
import com.koray.nrcnewsapp.core.domain.NewsPageItemModel
import com.koray.nrcnewsapp.core.network.repository.ArticleRepository
import com.koray.nrcnewsapp.core.network.repository.CategoryRepository
import com.koray.nrcnewsapp.core.network.viewmodel.CategorySelectionModel
import com.koray.nrcnewsapp.core.network.viewmodel.CustomViewModelFactory
import com.koray.nrcnewsapp.core.network.viewmodel.LiveArticleModel
import com.koray.nrcnewsapp.core.network.viewmodel.LiveCategoriesModel
import com.koray.nrcnewsapp.core.util.inject
import java.util.*
import kotlin.collections.ArrayList


class NewsPageFragment : Fragment() {

    private var newsPageListener: NewsPageOnListFragmentInteractionListener? = null
    private var categoryListener: CategoryOnListInteractionListener? = null

    private val categoryRepository: CategoryRepository by inject()
    private val articleRepository: ArticleRepository by inject()

    private val categorySelectionModel: CategorySelectionModel by activityViewModels()

    private val liveArticleModel: LiveArticleModel by lazy {
        ViewModelProvider(this, CustomViewModelFactory(articleRepository))
        .get(LiveArticleModel::class.java)
    }

    private val newsPageItemList: MutableList<NewsPageItemModel> = ArrayList()
    private val newsPageItemMap: MutableMap<NewsPageItemModel.ItemType, Any> =
        EnumMap(NewsPageItemModel.ItemType::class.java)

    private var newsPagerAdapter: NewsPageRecyclerViewAdapter? = null

    private lateinit var selectedCategory: CategoryItemModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_page_list, container, false)
        val scrollUpButton = view.findViewById<FloatingActionButton>(R.id.buttonScrollUp)
        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        val loadingView = view.findViewById<LinearLayout>(R.id.included_progress_bar)

        fetchCategoryNames()

        // Set the adapter
        newsPagerAdapter = NewsPageRecyclerViewAdapter(
            newsPageItemList,
            newsPageItemMap,
            newsPageListener,
            categoryListener,
            categorySelectionModel
        )

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = newsPagerAdapter

        scrollUpButton.setOnClickListener { v ->
            recyclerView.smoothScrollToPosition(0)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categorySelectionModel.getCategory().observe(viewLifecycleOwner, Observer { category ->
            selectedCategory = category
            run {
                categorySelectionModel.getCategoryMapsViewHolder().forEach { (nextId, mappedView) ->
                    this.handleCategorySelectionView(category.hashCode(), nextId, mappedView)
                }
            }

            // TODO: pre logic for loading bar
            liveArticleModel.loading.value = true

            fetchArticleItems(category)
        })

        if (!this::selectedCategory.isInitialized) {
            autoLoadArticles()
        }
    }

    private fun fetchCategoryNames() {
//        fetchDummyCategoryNames()

        val liveCategoriesModel = ViewModelProvider(this, CustomViewModelFactory(categoryRepository))
            .get(LiveCategoriesModel::class.java)

        liveCategoriesModel.requestCategories()

        liveCategoriesModel.getCategories().observe(viewLifecycleOwner, Observer { categoryList ->
            val categoryItemListModel = CategoryItemListModel(
                categoryList.map { categoryItemModel ->
                    if (categoryItemModel.topic.equals("news")) {
                        categoryItemModel.selected = true
                    }

                    val backgroundImgIdentifier = resources.getIdentifier(
                        categoryItemModel.topic, "drawable",
                        context?.packageName
                    )

                    categoryItemModel.img = backgroundImgIdentifier
                    categoryItemModel
                },
                NewsPageItemModel.ItemType.CATEGORY
            )

            categorySelectionModel.setCashCategories(categoryList)
            newsPageItemList.add(categoryItemListModel)
            newsPageItemMap[NewsPageItemModel.ItemType.CATEGORY] = categoryList
        })
    }

    private fun fetchDummyCategoryNames() {
        val dummyCategoryList = arrayListOf(
            CategoryItemModel("latest news", "news", 0, selected = true),
            CategoryItemModel("games", "games", 0),
            CategoryItemModel("physics", "physics", 0),
            CategoryItemModel("technology", "technology", 0),
            CategoryItemModel("astronomy", "astronomy", 0)
        )
        val categoryList = dummyCategoryList.map { cat ->
            val backgroundImgIdentifier = resources.getIdentifier(
                cat.topic, "drawable",
                context?.packageName
            )

            cat.img = backgroundImgIdentifier
            cat
        }

        categorySelectionModel.setCashCategories(categoryList)

        val categoryListItemModel =
            CategoryItemListModel(categoryList, NewsPageItemModel.ItemType.CATEGORY)
        newsPageItemList.add(categoryListItemModel)
        newsPageItemMap[NewsPageItemModel.ItemType.CATEGORY] = categoryList
    }

    private fun fetchArticleItems(category: CategoryItemModel) {
//        fetchDummyArticleItems()

        liveArticleModel.requestArticlesByCategory(category.topic!!)

        liveArticleModel.getArticlesByCategory()
            .observe(viewLifecycleOwner, Observer { models ->
                newsPageItemList.removeAll { item -> item.itemType!! == NewsPageItemModel.ItemType.ARTICLE }
                newsPageItemList.addAll(models)
                newsPagerAdapter?.notifyDataSetChanged()
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

        newsPagerAdapter?.notifyDataSetChanged()
    }

    private fun handleCategorySelectionView(
        targetId: Int,
        nextId: Int,
        mappedView: CategoryItemRecyclerViewAdapter.CategoryItemViewHolder
    ) {
        mappedView.mImage.alpha = if (targetId == nextId.hashCode()) 1F else 0.27F
    }

    private fun autoLoadArticles() {

        val initCategory = categorySelectionModel.getCashedCategories().values.toList()
            .getOrElse(0) { CategoryItemModel("latest news", "news", 0) }

        fetchArticleItems(initCategory)
        categorySelectionModel.setCategory(initCategory)
        selectedCategory = initCategory

//        categorySelectionModel.getCashedCategories().observe(viewLifecycleOwner, Observer { categoryList ->
//            val initCategory = categoryList.getOrElse(0){CategoryItemModel("latest news", "news", 0)}
//            fetchArticleItems(initCategory)
//            categorySelectionModel.setCategory(initCategory)
//            selectedCategory = initCategory
//        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is NewsPageOnListFragmentInteractionListener)
            newsPageListener = context

        if (context is CategoryOnListInteractionListener)
            categoryListener = context

        if (context !is NewsPageOnListFragmentInteractionListener || (context !is CategoryOnListInteractionListener))
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
    }

    override fun onDetach() {
        super.onDetach()
        newsPageListener = null
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