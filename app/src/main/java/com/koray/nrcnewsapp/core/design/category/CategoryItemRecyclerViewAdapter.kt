package com.koray.nrcnewsapp.core.design.category


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.koray.nrcnewsapp.R
import com.koray.nrcnewsapp.core.domain.CategoryItemModel
import kotlinx.android.synthetic.main.fragment_category_item.view.*

/**
 * Hier wordt de adapter voor Category list afgehandeld
 */
class CategoryItemRecyclerViewAdapter(
    private val mValues: List<CategoryItemModel>,
    private val mListener: CategoryOnListInteractionListener?
) : RecyclerView.Adapter<CategoryItemRecyclerViewAdapter.CategoryItemViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as CategoryItemModel
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_category_item, parent, false)
        return CategoryItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        val item = mValues[position]
        holder.mContentView.text = item.name
        holder.mImage.setBackgroundResource(R.drawable.test_deadstranding)

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    // Deze is alleen voor categorie item dus vergis je niet met die in NewsPageRecyclerViewAdapter
    inner class CategoryItemViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
//        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.category_name
        val mImage: ImageView = mView.category_item_image

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
