package com.example.typeracer.fragment

import com.example.typeracer.BR
import com.example.typeracer.R
import com.example.typeracer.databinding.FragmentTopHistoryPageBinding
import com.example.typeracer.viewModel.TopHistoryPageVM
import org.koin.android.viewmodel.ext.android.viewModel


class TopHistoryPageFragment : BaseFragment<FragmentTopHistoryPageBinding, TopHistoryPageVM>() {

    private val vm by viewModel<TopHistoryPageVM>()

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_top_history_page

    override fun getViewModel() = vm

}
