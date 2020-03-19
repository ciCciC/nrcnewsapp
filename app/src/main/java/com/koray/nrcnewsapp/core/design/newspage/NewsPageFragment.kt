package com.koray.nrcnewsapp.core.design.newspage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.koray.nrcnewsapp.R
import com.koray.nrcnewsapp.core.design.category.CategoryItemFragment
import com.koray.nrcnewsapp.core.design.newspage.dummy.NewsPageDummyContent
import com.koray.nrcnewsapp.core.design.newspage.dummy.NewsPageDummyContent.DummyItem
import com.koray.nrcnewsapp.core.network.viewmodel.CategorySelectionModel

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [NewsPageFragment.OnListFragmentInteractionListener] interface.
 */
class NewsPageFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null
    private var categoryListener: CategoryOnListInteractionListener? = null

    private val categorySelectionModel: CategorySelectionModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_page_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = NewsPageRecyclerViewAdapter(NewsPageDummyContent.ITEMS, listener, categoryListener)
            }
        }
        return view
    }

    private fun createViewCategory() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categorySelectionModel.getCategory().observe(viewLifecycleOwner, Observer { category ->
            println("Selected: $category")
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is OnListFragmentInteractionListener)
            listener = context

        if(context is CategoryOnListInteractionListener)
            categoryListener = context

        if( context !is OnListFragmentInteractionListener || (context !is CategoryOnListInteractionListener) )
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        categoryListener = null
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(newsPageItem: DummyItem?)
    }

    interface CategoryOnListInteractionListener {
        fun onListFragmentInteraction(category: String?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            NewsPageFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }

        fun getTagName(): String {
            return NewsPageFragment::class.java.name
        }
    }
}
