package com.example.typeracer.fragment

import com.example.typeracer.BR
import com.example.typeracer.R
import com.example.typeracer.databinding.FragmentHistoryBinding
import com.example.typeracer.viewModel.HistoryVM
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class HistoryFragment : BaseFragment<FragmentHistoryBinding, HistoryVM>() {

    private val vm: HistoryVM by viewModel { parametersOf(activity?.supportFragmentManager) }

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_history

    override fun getViewModel() = vm

}
