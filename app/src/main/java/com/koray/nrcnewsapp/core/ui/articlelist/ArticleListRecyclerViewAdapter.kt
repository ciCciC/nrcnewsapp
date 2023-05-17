//package com.koray.nrcnewsapp.core.ui.articlelist
//
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.koray.nrcnewsapp.R
//import com.koray.nrcnewsapp.core.ui.articlelist.ArticleListFragment.OnListFragmentInteractionListener
//import com.koray.nrcnewsapp.core.ui.articlelist.ArticleListDummyContent.DummyItem
//import kotlinx.android.synthetic.main.fragment_article_item.view.*
//
///**
// * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
// * specified [OnListFragmentInteractionListener].
// * TODO: Replace the implementation with code for your data type.
// */
//@Deprecated("Instead use NewsPageRecyclerViewAdapter.")
//class ArticleListRecyclerViewAdapter(
//    private val mValues: List<DummyItem>,
//    private val mListener: OnListFragmentInteractionListener?
//) : RecyclerView.Adapter<ArticleListRecyclerViewAdapter.ViewHolder>() {
//
//    private val mOnClickListener: View.OnClickListener
////    private val CATEGORY = 1
////    private val ARTICLE = 2
//
//    init {
//        mOnClickListener = View.OnClickListener { v ->
//            val item = v.tag as DummyItem
//            // Notify the active callbacks interface (the activity, if the fragment is attached to
//            // one) that an item has been selected.
//            mListener?.onListFragmentInteraction(item)
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.fragment_article_item, parent, false)
//
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = mValues[position]
//
////        holder.mIdView.text = item.id
//        holder.mContentView.text = item.content
//
//        with(holder.mView) {
//            tag = item
//            setOnClickListener(mOnClickListener)
//        }
//    }
//
//    override fun getItemCount(): Int = mValues.size
//
//    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
//        //        val mIdView: TextView = mView.item_number
//        val mContentView: TextView = mView.article_item_title
//
//        override fun toString(): String {
//            return super.toString() + " '" + mContentView.text + "'"
//        }
//    }
//}
