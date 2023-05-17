package com.koray.nrcnewsapp.core.ui.newspage


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.koray.nrcnewsapp.R
import com.koray.nrcnewsapp.core.domain.ArticleItemModel
import com.koray.nrcnewsapp.core.domain.CategoryItemListModel
import com.koray.nrcnewsapp.core.domain.CategoryItemModel
import com.koray.nrcnewsapp.core.domain.NewsPageItemModel
import com.koray.nrcnewsapp.core.network.viewmodel.LiveCategorySelectionModel
import com.koray.nrcnewsapp.core.ui.category.CategoryOnListInteractionListener
import com.koray.nrcnewsapp.core.ui.viewholders.ArticleViewHolder
import com.koray.nrcnewsapp.core.ui.viewholders.BaseViewHolder
import com.koray.nrcnewsapp.core.ui.viewholders.CategoryListViewHolder
import com.koray.nrcnewsapp.core.util.ImageManager
import java.util.*


class NewsPageRecyclerViewAdapter(
    private val mValues: List<NewsPageItemModel>,
    private val mValuesMap: Map<NewsPageItemModel.ItemType, Any>,
    private val mListenerNewsPage: NewsPageOnListFragmentInteractionListener?,
    private val mCategoryListener: CategoryOnListInteractionListener?,
    private val liveCategoryLiveData: LiveCategorySelectionModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private val CATEGORY = 1
    private val ARTICLE = 2

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as NewsPageItemModel
            mListenerNewsPage?.onListFragmentInteraction(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mValues[position].itemType == NewsPageItemModel.ItemType.CATEGORY)
            CATEGORY else ARTICLE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View

        return if(viewType == CATEGORY) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_category_list, parent, false)
            CategoryListViewHolder(view,
                ((mValues.firstOrNull() as CategoryItemListModel).categoryItemList as MutableList<CategoryItemModel>),
//                (mValuesMap[NewsPageItemModel.ItemType.CATEGORY] as MutableList<CategoryItemModel>),
                mCategoryListener,
                liveCategoryLiveData
            )
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_article_item, parent, false)
            ArticleViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mValues[position]

        when (holder) {
            is CategoryListViewHolder -> {
                with(holder.mView) {
                    tag = item
                }
            }
            is ArticleViewHolder -> {
                if(item is ArticleItemModel){
                    ImageManager.loadImage(holder.mView, holder.articleItemImg, item.imageLink.toString())
                    holder.topic.text = item.topic?.uppercase(Locale.ROOT)
                    holder.title.text = item.title
                    holder.teaser.text = item.teaser

                    with(holder.mView) {
                        tag = item
                        setOnClickListener(mOnClickListener)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = mValues.size
}
