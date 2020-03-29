package com.example.typeracer.fragment


import com.example.typeracer.BR
import com.example.typeracer.R
import com.example.typeracer.databinding.FragmentTypeBinding
import com.example.typeracer.viewModel.TypeVM
import org.koin.android.viewmodel.ext.android.viewModel


class TypeFragment : BaseFragment<FragmentTypeBinding, TypeVM>() {

    private val vm by viewModel<TypeVM>()

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_type

    override fun getViewModel() = vm

}
