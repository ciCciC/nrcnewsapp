package com.koray.nrcnewsapp.core.design.category


import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.koray.nrcnewsapp.R
import com.koray.nrcnewsapp.core.domain.CategoryItemModel
import com.koray.nrcnewsapp.core.network.viewmodel.CategorySelectionModel
import com.koray.nrcnewsapp.core.util.AnimationEffect
import kotlinx.android.synthetic.main.fragment_category_item.view.*

/**
 *
 * This is where the adapter for Category list is handled.
 *
 */
class CategoryItemRecyclerViewAdapter(
    private val mValues: List<CategoryItemModel>,
    private val mListener: CategoryOnListInteractionListener?,
    val categoryLiveData: CategorySelectionModel
) : RecyclerView.Adapter<CategoryItemRecyclerViewAdapter.CategoryItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_category_item, parent, false)
        return CategoryItemViewHolder(view, mListener, categoryLiveData)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        val item = mValues[position]
        holder.categoryItemModel = item
        holder.mContentView.text = item.display

        Glide
            .with(holder.mView)
            .load(if (item.img == null) R.drawable.test_deadstranding else item.img)
            .into(holder.mImage)

        if (!item.selected) {
            holder.mImage.setColorFilter(Color.GRAY, PorterDuff.Mode.SCREEN)
        }

        holder.categoryLiveData.addCategoryWithViewHolder(item.hashCode(), holder)

        with(holder.mView) {
            tag = item
        }
    }

    override fun getItemCount(): Int = mValues.size

    /**
     * This is for category item only so don't be mistaken with the one in NewsPageRecyclerViewAdapter.
     */
    inner class CategoryItemViewHolder(
        val mView: View,
        private val categoryListener: CategoryOnListInteractionListener?,
        val categoryLiveData: CategorySelectionModel
    ) : RecyclerView.ViewHolder(mView), View.OnClickListener {
        val mContentView: TextView = mView.category_name
        val mImage: ImageView = mView.category_item_image
        lateinit var categoryItemModel: CategoryItemModel

        init {
            mView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            AnimationEffect.shake(mImage, 500)
            categoryListener?.onListFragmentInteraction(categoryItemModel)
        }

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
