package com.koray.nrcnewsapp.core.design.category

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.koray.nrcnewsapp.R
import com.koray.nrcnewsapp.core.design.newspage.NewsPageFragment
import com.koray.nrcnewsapp.core.design.util.inject
import com.koray.nrcnewsapp.core.domain.CategoryItemModel
import com.koray.nrcnewsapp.core.network.repository.CategoryRepository
import com.koray.nrcnewsapp.core.network.viewmodel.CustomViewModelFactory
import com.koray.nrcnewsapp.core.network.viewmodel.LiveCategoriesModel
import kotlinx.android.synthetic.main.fragment_category_item.view.*


// Deze fragment wordt niet gebruikt, in plaats daarvan worden de categorieen
// als lijst in de vorm van View weergegeven in NewsPageFragment
class CategoryItemFragment : Fragment() {

//    private var listener: CategoryOnListInteractionListener? = null
//
//    private val categoryRepository: CategoryRepository by inject()
//
//    private val categoriesList: MutableList<CategoryItemModel> = ArrayList()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_category_list, container, false)
//
//        val recyclerView = view.findViewById<RecyclerView>(R.id.category_fragment_list)
//        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false) as RecyclerView.LayoutManager?
//
//        // fetches the category items
////        fetchCategories()
//        val currentImg = resources.getDrawable(R.drawable.test_deadstranding, null).current
//        arrayListOf("games", "physics", "technology")
//            .forEach { x -> categoriesList.add(CategoryItemModel(x)) }
//
//        recyclerView.adapter = CategoryItemRecyclerViewAdapter(
//            categoriesList,
//            listener
//        )
//
//        return view
//    }
//
//    private fun fetchCategories() {
//        val model = ViewModelProviders.of(this, CustomViewModelFactory(categoryRepository)).get(
//            LiveCategoriesModel::class.java)
//        model.getCategories().observe(viewLifecycleOwner, Observer { categoryModel ->
//            categoryModel.forEach { x -> categoriesList.add(x) }
//        })
//    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is CategoryOnListInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }
//
////    interface OnListFragmentInteractionListener {
////        fun onListFragmentInteraction(category: String?)
////    }
//
//    companion object {
//        @JvmStatic
//        fun newInstance() =
//            CategoryItemFragment()
//
//        fun getTagName(): String? {
//            return CategoryItemFragment::class.java.name
//        }
//    }
}