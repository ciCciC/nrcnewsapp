package com.koray.nrcnewsapp.core.design.newspage


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.koray.nrcnewsapp.R
import com.koray.nrcnewsapp.core.design.category.MyCategoryItemRecyclerViewAdapter
import com.koray.nrcnewsapp.core.design.newspage.NewsPageFragment.CategoryOnListInteractionListener
import com.koray.nrcnewsapp.core.design.newspage.NewsPageFragment.OnListFragmentInteractionListener
import com.koray.nrcnewsapp.core.design.newspage.dummy.NewsPageDummyContent
import com.koray.nrcnewsapp.core.design.newspage.dummy.NewsPageDummyContent.DummyItem
import kotlinx.android.synthetic.main.fragment_article_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class NewsPageRecyclerViewAdapter(
    private val mValues: List<DummyItem>,
    private val mListener: OnListFragmentInteractionListener?,
    private val mCategoryListener: CategoryOnListInteractionListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private val CATEGORY = 1
    private val ARTICLE = 2

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as DummyItem
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mValues[position].viewType == NewsPageDummyContent.ViewType.CATEGORY)
            CATEGORY else ARTICLE
    }

    // TODO
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View

        return if(viewType == CATEGORY) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_category_list, parent, false)
            XTest(view, mCategoryListener)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_article_item, parent, false)
            YTest(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mValues[position]

        when (holder) {
            is XTest -> {
                with(holder.mView) {
                    tag = item
                }
            }
            is YTest -> {
                holder.mContentView.text = item.content
                with(holder.mView) {
                    tag = item
                    setOnClickListener(mOnClickListener)
                }
            }
        }
    }

    override fun getItemCount(): Int = mValues.size

    // TODO: Refactor!!
    open inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
    }

    // TODO: Refactor!!
    inner class XTest(mView: View, listener: CategoryOnListInteractionListener?) : ViewHolder(mView) {

        private val categoriesList: MutableList<String> = ArrayList()

        init {
            val recyclerView: RecyclerView = mView.findViewById(R.id.category_fragment_list)
            recyclerView.layoutManager =
                LinearLayoutManager(mView.context, RecyclerView.HORIZONTAL, false) as RecyclerView.LayoutManager?

            (1..10).forEach{ _ -> categoriesList.add("games")}
            arrayListOf("games", "physics", "technology")
                .forEach { x -> categoriesList.add(x) }

            recyclerView.adapter = MyCategoryItemRecyclerViewAdapter(
                categoriesList,
                listener
            )
        }
    }

    // TODO: Refactor!!
    inner class YTest(@NonNull mView: View) : ViewHolder(mView) {
        // Op deze manier haal je een element op, apart bij ieder classe
        val testImage: ImageView = mView.article_item_image
        val mContentView: TextView = mView.article_item_content
    }
}
