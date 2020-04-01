package com.example.typeracer.fragment


import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.typeracer.BR
import com.example.typeracer.R
import com.example.typeracer.databinding.FragmentTypeBinding
import com.example.typeracer.model.CountDownDialog
import com.example.typeracer.model.TimeoutDialog
import com.example.typeracer.model.WinDialog
import com.example.typeracer.repo.model.Race
import com.example.typeracer.util.DialogHelper
import com.example.typeracer.viewModel.TypeVM
import org.koin.android.viewmodel.ext.android.viewModel


class TypeFragment : BaseFragment<FragmentTypeBinding, TypeVM>() {

    private val vm by viewModel<TypeVM>()

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_type

    override fun getViewModel() = vm

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.editText.doOnTextChanged { text, _, _, _ ->
            vm.textChanged(text.toString())
        }
        vm.showDialog.observe(this as LifecycleOwner, Observer {
            when (it) {
                is WinDialog -> showWinDialog(it.race)
                is TimeoutDialog -> DialogHelper.showTimeoutDialog(context, it.maxTime)
                is CountDownDialog -> showTimerDialog(it)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        vm.lifecycleLiveData.value = Lifecycle.Event.ON_RESUME
    }

    override fun onPause() {
        super.onPause()
        vm.lifecycleLiveData.value = Lifecycle.Event.ON_PAUSE
    }

    private fun showWinDialog(race: Race) {
        DialogHelper.showWinDialog(context, race, {
            vm.saveRaceResult(race)
            vm.prepareNewRace()
        }, {
            vm.prepareNewRace()
        })
    }

    private fun showTimerDialog(dialogModel: CountDownDialog) {
        activity?.let {
            lifecycle.addObserver(DialogHelper.showTimerDialog(it, dialogModel))
        }
    }

}
