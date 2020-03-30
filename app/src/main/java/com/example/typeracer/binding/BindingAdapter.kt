package com.example.typeracer.binding

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import com.google.android.material.tabs.TabLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.typeracer.adapter.DividerItemDecoration
import com.example.typeracer.util.DateTimeHelper
import com.google.android.material.textfield.TextInputLayout


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

@BindingAdapter("errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
    errorMessage.let {
        view.error = it
    }
}

@BindingAdapter("dateFormatText")
fun TextView.setDateFormatText(date: Long) {
    text = DateTimeHelper.toUserFriendlyDate(context, date)
}

@BindingAdapter("openKeyboard")
fun openKeyboard(view: EditText, boolean: Boolean) {
    if (boolean) {
        view.requestFocus()
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}