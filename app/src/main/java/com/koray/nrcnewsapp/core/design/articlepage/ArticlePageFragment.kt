package com.koray.nrcnewsapp.core.design.articlepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.koray.nrcnewsapp.R
import com.koray.nrcnewsapp.core.design.viewholders.ArticlePageViewHolder
import com.koray.nrcnewsapp.core.domain.ArticleItemModel
import com.koray.nrcnewsapp.core.network.viewmodel.ArticleSelectionModel


class ArticlePageFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article_page, container, false)

        val articleItemSelectionModel: ArticleSelectionModel by activityViewModels()

        articleItemSelectionModel.getArticleItemModel().observe(viewLifecycleOwner, Observer { articleItem ->
            initArticlePage(view, articleItem)
        })

        return view
    }

    private fun initArticlePage(view: View, articleItemModel: ArticleItemModel){
        val articlePageViewHolder = ArticlePageViewHolder(view)
        articlePageViewHolder.topic.text = articleItemModel.topic
        articlePageViewHolder.title.text = articleItemModel.title
        articlePageViewHolder.teaser.text = articleItemModel.teaser
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArticlePageFragment()
        fun getTagName(): String {
            return ArticlePageFragment::class.java.name
        }
    }
}
