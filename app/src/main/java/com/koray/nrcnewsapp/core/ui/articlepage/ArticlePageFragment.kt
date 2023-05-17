//package com.koray.nrcnewsapp.core.ui.articlepage
//
//import android.graphics.Typeface
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.TextView
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.activityViewModels
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProvider
//import com.koray.nrcnewsapp.R
//import com.koray.nrcnewsapp.core.abstraction.Ctype
//import com.koray.nrcnewsapp.core.ui.viewholders.ArticlePageViewHolder
//import com.koray.nrcnewsapp.core.domain.ArticleItemModel
//import com.koray.nrcnewsapp.core.domain.ArticlePageModel
//import com.koray.nrcnewsapp.core.domain.CategoryItemModel
//import com.koray.nrcnewsapp.core.network.dto.ContentBodyDto
//import com.koray.nrcnewsapp.core.network.dto.SectionDto
//import com.koray.nrcnewsapp.core.network.helper.ErrorHandler
//import com.koray.nrcnewsapp.core.network.repository.ArticleRepository
//import com.koray.nrcnewsapp.core.network.viewmodel.*
//import com.koray.nrcnewsapp.core.util.ImageManager
//import com.koray.nrcnewsapp.core.util.inject
//import java.util.*
//
//
//class ArticlePageFragment : Fragment() {
//
//    private val articleRepository: ArticleRepository by inject()
//    private val liveCategorySelectionModel: LiveCategorySelectionModel by activityViewModels()
//    private val liveArticleItemSelectionModel: LiveArticleSelectionModel by activityViewModels()
//    private lateinit var selectedCategory: CategoryItemModel
//
//    private val liveArticleModel: LiveArticleModel by lazy {
//        ViewModelProvider(this, CustomViewModelFactory(articleRepository))
//            .get(LiveArticleModel::class.java)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_article_page, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val loadingView = view.findViewById<LinearLayout>(R.id.included_progress_bar)
//
//        liveArticleModel.loading.observe(viewLifecycleOwner, Observer { state ->
//            loadingView.visibility = if(state) View.VISIBLE else View.GONE
//        })
//
//        liveArticleItemSelectionModel.getArticleItemModel()
//            .observe(viewLifecycleOwner, Observer { articleItem ->
//                initArticlePage(view, articleItem)
//            })
//
//        ErrorHandler.getErrorState().observe(viewLifecycleOwner, Observer { errorMessage ->
//            if (errorMessage.showMessage) {
//                Toast.makeText(this.context, errorMessage.message, Toast.LENGTH_LONG).show()
//            }
//        })
//    }
//
//    private fun initArticlePage(view: View, articleItemModel: ArticleItemModel) {
////        fakeArticlePage(view, articleItemModel)
//
//        liveCategorySelectionModel.getCategory().observe(viewLifecycleOwner, Observer { category ->
//            selectedCategory = category
//        })
//
//        liveArticleModel.requestArticlePage(articleItemModel, selectedCategory.topic!!)
//
//        liveArticleModel.getArticlePage()
//            .observe(viewLifecycleOwner, Observer { articlePage ->
//                populateArticlePage(view, articlePage)
//            })
//    }
//
//    private fun fakeArticlePage(view: View, articleItemModel: ArticleItemModel) {
//        val articlePageModel = ArticlePageModel(
//            arrayOf(
//                SectionDto(
//                    "title",
//                    arrayOf(
//                        ContentBodyDto(
//                            "content",
//                            Ctype.p.name
//                        )
//                    )
//                )
//            ),
//            articleItemModel.pageLink,
//            articleItemModel.imageLink,
//            articleItemModel.topic,
//            articleItemModel.title,
//            articleItemModel.teaser
//
//        )
//        populateArticlePage(view, articlePageModel)
//    }
//
//    private fun populateArticlePage(view: View, articlePageModel: ArticlePageModel) {
//        val articlePageViewHolder = ArticlePageViewHolder(view)
//
//        ImageManager.loadImage(
//            articlePageViewHolder.mView,
//            articlePageViewHolder.headerImage,
//            articlePageModel.imageLink.toString(),
//            true
//        )
//
//        articlePageViewHolder.topic.text = articlePageModel.topic?.toUpperCase(Locale.ROOT)
//        articlePageViewHolder.title.text = articlePageModel.title
//        articlePageViewHolder.teaser.text = articlePageModel.teaser
//
//        populateArticleContent(view, articlePageModel, articlePageViewHolder)
//    }
//
//    private fun populateArticleContent(
//        view: View,
//        articlePageModel: ArticlePageModel,
//        articlePageViewHolder: ArticlePageViewHolder
//    ) {
//        articlePageModel.sectionList!!.forEach { section ->
//
//            val textColor = resources.getColor(R.color.colorTextPrimary, null)
//            var textView: TextView
//
//            if (!section.title.isNullOrBlank()) {
//                textView = TextView(view.context)
//                handleH2TextView(textView, section.title.toString(), textColor)
//                handleH2Margin(textView)
//
//                articlePageViewHolder.layoutContent.addView(textView)
//            }
//
//            val contentList =
//                section.contents!!.groupBy { x -> x.hashCode() }.map { y -> y.value[0] }
//
//            contentList.forEach { content ->
//
//                when (content.cType) {
//                    Ctype.p.toString() -> {
//                        if (!content.content.isNullOrBlank()) {
//                            textView = TextView(view.context)
//                            handleTextView(textView, content.content.toString(), textColor)
//                            handleMargin(textView)
//
//                            articlePageViewHolder.layoutContent.addView(textView)
//                        }
//                    }
//                    Ctype.img.toString() -> {
//                        if (!content.content.isNullOrBlank()) {
//                            val image = ImageView(view.context)
//                            handleImgMargin(image)
//                            articlePageViewHolder.layoutContent.addView(image)
//
//                            ImageManager.loadImage(
//                                view,
//                                image,
//                                reformatUrl(content.content.toString())
//                            )
//                        }
//                    }
//                }
//            }
//
//        }
//    }
//
//    private fun reformatUrl(url: String): String {
//        if (!url.contains("https:")) {
//            return "https:$url"
//        }
//        return url
//    }
//
//    private fun handleTextView(textView: TextView, contentValue: String, color: Int) {
//        textView.text = contentValue
//        textView.setTextColor(color)
//    }
//
//    private fun handleH2TextView(textView: TextView, contentValue: String, color: Int) {
//        this.handleTextView(textView, contentValue, color)
//        textView.textSize = 20F
//        textView.setTypeface(null, Typeface.BOLD)
//    }
//
//    private fun handleMargin(view: View) {
//        val marginLayoutParams = ViewGroup.MarginLayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//        val sideMargin = resources.getDimension(R.dimen.article_margin).toInt()
//        val margin = resources.getDimension(R.dimen.article_content_margin).toInt()
//        marginLayoutParams.setMargins(sideMargin, margin, sideMargin, margin)
//        view.layoutParams = marginLayoutParams
//    }
//
//    private fun handleH2Margin(view: View) {
//        val marginLayoutParams = ViewGroup.MarginLayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//        val sideMargin = resources.getDimension(R.dimen.article_margin).toInt()
//        val margin = resources.getDimension(R.dimen.article_content_margin).toInt()
//        marginLayoutParams.setMargins(sideMargin, margin, sideMargin, 0)
//        view.layoutParams = marginLayoutParams
//    }
//
//    private fun handleImgMargin(imageView: ImageView) {
//        val marginLayoutParams = ViewGroup.MarginLayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            500
//        )
//
//        val margin = resources.getDimension(R.dimen.article_content_margin).toInt()
//        marginLayoutParams.setMargins(0, margin, 0, margin)
//
//        imageView.adjustViewBounds = true
//        imageView.maxHeight = 500
//        imageView.layoutParams = marginLayoutParams
//    }
//
//    companion object {
//        @JvmStatic
//        fun newInstance() = ArticlePageFragment()
//        fun getTagName(): String {
//            return ArticlePageFragment::class.java.name
//        }
//    }
//}
