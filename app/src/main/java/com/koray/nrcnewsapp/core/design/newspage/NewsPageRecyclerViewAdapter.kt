package com.koray.nrcnewsapp.core.design.newspage


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.koray.nrcnewsapp.R
import com.koray.nrcnewsapp.core.design.newspage.NewsPageFragment.CategoryOnListInteractionListener
import com.koray.nrcnewsapp.core.design.newspage.NewsPageFragment.OnListFragmentInteractionListener
import com.koray.nrcnewsapp.core.design.newspage.dummy.NewsPageDummyContent.DummyItem
import com.koray.nrcnewsapp.core.design.viewholders.ArticleViewHolder
import com.koray.nrcnewsapp.core.design.viewholders.BaseViewHolder
import com.koray.nrcnewsapp.core.design.viewholders.CategoryViewHolder
import com.koray.nrcnewsapp.core.domain.ArticleItemModel
import com.koray.nrcnewsapp.core.domain.CategoryItemModel
import com.koray.nrcnewsapp.core.domain.NewsPageItemModel

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class NewsPageRecyclerViewAdapter(
    private val mValues: List<NewsPageItemModel>,
    private val mValuesMap: Map<NewsPageItemModel.ItemType, Any>,
    private val mListener: OnListFragmentInteractionListener?,
    private val mCategoryListener: CategoryOnListInteractionListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private val CATEGORY = 1
    private val ARTICLE = 2

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as NewsPageItemModel
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mValues[position].itemType == NewsPageItemModel.ItemType.CATEGORY)
            CATEGORY else ARTICLE
    }

    // TODO
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View

        return if(viewType == CATEGORY) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_category_list, parent, false)
            CategoryViewHolder(view,
                (mValuesMap[NewsPageItemModel.ItemType.CATEGORY] as MutableList<CategoryItemModel>),
                mCategoryListener)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_article_item, parent, false)
            ArticleViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mValues[position]

        when (holder) {
            is CategoryViewHolder -> {
                with(holder.mView) {
                    tag = item
                }
            }
            is ArticleViewHolder -> {
                if(item is ArticleItemModel){
                    holder.topic.text = item.topic?.toUpperCase()
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
