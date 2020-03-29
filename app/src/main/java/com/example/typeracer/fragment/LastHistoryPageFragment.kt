package com.example.typeracer.fragment

import com.example.typeracer.BR
import com.example.typeracer.R
import com.example.typeracer.databinding.FragmentLastHistoryPageBinding
import com.example.typeracer.viewModel.LastHistoryPageVM
import org.koin.android.viewmodel.ext.android.viewModel


class LastHistoryPageFragment : BaseFragment<FragmentLastHistoryPageBinding, LastHistoryPageVM>() {

    private val vm by viewModel<LastHistoryPageVM>()

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_last_history_page

    override fun getViewModel() = vm

}

