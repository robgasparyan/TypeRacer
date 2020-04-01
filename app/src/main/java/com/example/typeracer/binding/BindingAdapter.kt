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
fun View.visibility(visibility: Boolean) {
    this.visibility = if (visibility) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("pagerAdapter")
fun ViewPager.setPagerAdapter(adapter: PagerAdapter) {
    this.adapter = adapter
}

@BindingAdapter("pager")
fun TabLayout.bindViewPagerTabs(pagerView: ViewPager?) {
    this.setupWithViewPager(pagerView, true)
}

@BindingAdapter("itemDecoration")
fun RecyclerView.itemDecoration(@ColorInt color: Int) {
    val decorator = DividerItemDecoration(color, 2)
    addItemDecoration(decorator)
}

@BindingAdapter("errorText")
fun TextInputLayout.setErrorMessage(errorMessage: String?) {
    errorMessage.let {
        this.error = it
    }
}

@BindingAdapter("dateFormatText")
fun TextView.setDateFormatText(date: Long) {
    text = DateTimeHelper.toUserFriendlyDate(context, date)
}

@BindingAdapter("openKeyboard")
fun EditText.openKeyboard(boolean: Boolean) {
    if (boolean) {
        this.requestFocus()
        val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}