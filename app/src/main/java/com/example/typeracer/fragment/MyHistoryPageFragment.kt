package com.example.typeracer.fragment

import com.example.typeracer.BR
import com.example.typeracer.R
import com.example.typeracer.databinding.FragmentMyHistoryPageBinding
import com.example.typeracer.viewModel.MyHistoryPageVM
import org.koin.android.viewmodel.ext.android.viewModel


class MyHistoryPageFragment : BaseFragment<FragmentMyHistoryPageBinding, MyHistoryPageVM>() {

    private val vm by viewModel<MyHistoryPageVM>()

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_my_history_page

    override fun getViewModel() = vm

}

