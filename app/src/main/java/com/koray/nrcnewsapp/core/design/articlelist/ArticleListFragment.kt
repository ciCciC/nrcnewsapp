package com.koray.nrcnewsapp.core.design.articlelist

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.koray.nrcnewsapp.R
import com.koray.nrcnewsapp.core.design.dummy.ArticleListDummyContent

import com.koray.nrcnewsapp.core.design.dummy.ArticleListDummyContent.DummyItem
import com.koray.nrcnewsapp.core.network.viewmodel.CategorySelectionModel

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [ArticleListFragment.OnListFragmentInteractionListener] interface.
 */
class ArticleListFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

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
        val view = inflater.inflate(R.layout.fragment_article_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.articleListFragmentLayout)
        recyclerView.layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?

//        // Set the adapter
//        if (view is RecyclerView) {
//            with(view) {
//                layoutManager = when {
//                    columnCount <= 1 -> LinearLayoutManager(context)
//                    else -> GridLayoutManager(context, columnCount)
//                }
//                adapter = MyArticleListRecyclerViewAdapter(ArticleListDummyContent.ITEMS, listener)
//            }
//        }

        recyclerView.adapter = MyArticleListRecyclerViewAdapter(ArticleListDummyContent.ITEMS, listener)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categorySelectionModel.getCategory().observe(viewLifecycleOwner, Observer { category ->
            println("Selected: " + category)
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //TODO
//        if (context is OnListFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
//        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: DummyItem?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ArticleListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }

        fun getTagName(): String {
            return ArticleListFragment::class.java.name
        }
    }

}
