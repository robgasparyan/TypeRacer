package com.example.typeracer.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.typeracer.BR

import com.example.typeracer.R
import com.example.typeracer.activity.SignInSignUpActivity
import com.example.typeracer.databinding.FragmentProfileBinding
import com.example.typeracer.viewModel.ProfileVM
import org.koin.android.viewmodel.ext.android.viewModel


class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileVM>() {

    private val vm: ProfileVM by viewModel()

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_profile

    override fun getViewModel() = vm

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.logOutLiveData.observe(this as LifecycleOwner, Observer {
            openSignInSignUpActivity()
            activity?.finish()
        })
    }

    private fun openSignInSignUpActivity() {
        val intent = Intent(context, SignInSignUpActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        context?.startActivity(intent)
    }

}

