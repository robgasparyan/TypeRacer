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
import com.example.typeracer.repo.model.Race
import com.example.typeracer.util.toDecimal2
import com.example.typeracer.view.CustomDialog
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
        vm.showFinishDialog.observe(this as LifecycleOwner, Observer(this::showWinDialog))
    }

    private fun showWinDialog(race: Race) {
        context?.let {
            val subtitle =
                String.format(it.getString(R.string.finish_dialog_subtitle), race.wpm.toDecimal2())
            CustomDialog(it)
                .setTitleStrRes(R.string.finish_dialog_title)
                .setSubTitle(subtitle)
                .setMessageStrRes(R.string.finish_dialog_message)
                .setPositiveButton(R.string.save, View.OnClickListener {
                    vm.saveRaceResult(race)
                    vm.prepareNewRace()
                }).setNegativeButton(android.R.string.cancel, View.OnClickListener {
                    vm.prepareNewRace()
                }).show()
        }
    }

    override fun onResume() {
        super.onResume()
        vm.lifecycleLiveData.value = Lifecycle.Event.ON_RESUME
    }

    override fun onPause() {
        super.onPause()
        vm.lifecycleLiveData.value = Lifecycle.Event.ON_PAUSE
    }

}
