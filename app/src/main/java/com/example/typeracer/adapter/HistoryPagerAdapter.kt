package com.example.typeracer.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.typeracer.R
import com.example.typeracer.fragment.TopHistoryPageFragment
import com.example.typeracer.fragment.LastHistoryPageFragment
import com.example.typeracer.fragment.MyHistoryPageFragment


class HistoryPagerAdapter(
    context: Context,
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    enum class PageType(val pos: Int) {
        LAST(0),
        TOP(1),
        MY(2)
    }

    private val appContext: Context = context.applicationContext

    override fun getItem(position: Int): Fragment {
        return when (position) {
            PageType.LAST.pos -> LastHistoryPageFragment()
            PageType.TOP.pos -> TopHistoryPageFragment()
            else -> MyHistoryPageFragment()
        }
    }

    override fun getCount() = PageType.values().size

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            PageType.LAST.pos -> appContext getString R.string.last
            PageType.MY.pos -> appContext getString R.string.my
            else -> appContext getString R.string.top
        }
    }

    private infix fun Context.getString(@StringRes res: Int) = resources.getString(res)

}