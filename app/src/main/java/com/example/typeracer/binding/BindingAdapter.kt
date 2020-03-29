package com.example.typeracer.binding

import android.view.View
import androidx.annotation.ColorInt
import com.google.android.material.tabs.TabLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.typeracer.adapter.DividerItemDecoration


@BindingAdapter("android:visibility")
fun visibility(view: View, visibility: Boolean) {
    if (visibility) view.visibility = View.VISIBLE else view.visibility = View.GONE
}

@BindingAdapter("pagerAdapter")
fun setPagerAdapter(view: ViewPager, adapter: PagerAdapter) {
    view.adapter = adapter
}

@BindingAdapter("pager")
fun bindViewPagerTabs(view: TabLayout, pagerView: ViewPager?) {
    view.setupWithViewPager(pagerView, true)
}

@BindingAdapter("itemDecoration")
fun RecyclerView.itemDecoration(@ColorInt color: Int) {
    val decorator =  DividerItemDecoration (color, 2)
    addItemDecoration(decorator)
}
