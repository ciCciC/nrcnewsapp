package com.koray.nrcnewsapp.core.design.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.koray.nrcnewsapp.R
import com.koray.nrcnewsapp.core.util.AnimationEffect


class MenuItemInfoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu_item_info, container, false)
        val shareIconView = view.findViewById<ImageView>(R.id.info_ic_share)
        val yogaGifView = view.findViewById<ImageView>(R.id.headerImage)

        Glide
            .with(this)
            .load(R.drawable.animation_prayer)
            .into(yogaGifView)

        val rotate = AnimationEffect.rotate(duration = 2 * 200)
        shareIconView.setOnClickListener(fun(it: View) {
            it.startAnimation(rotate)
        })
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = MenuItemInfoFragment()

        fun getTagName(): String {
            return MenuItemInfoFragment::class.java.name
        }
    }
}