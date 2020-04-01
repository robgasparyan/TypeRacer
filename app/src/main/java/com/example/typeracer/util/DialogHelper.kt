package com.example.typeracer.util

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LifecycleObserver
import com.example.typeracer.R
import com.example.typeracer.model.CountDownDialog
import com.example.typeracer.repo.model.Race
import com.example.typeracer.view.CountDownTimerDialog
import com.example.typeracer.view.CustomDialog

object DialogHelper {

    fun showWinDialog(context: Context?, race: Race, posBtnCb: () -> Unit?, negBtnCb: () -> Unit?) {
        context?.let {
            val subtitle = String.format(it.getString(R.string.finish_dialog_subtitle), race.wpm)
            CustomDialog(it)
                .setTitleStrRes(R.string.finish_dialog_title)
                .setSubTitle(subtitle)
                .setMessageStrRes(R.string.finish_dialog_message)
                .setPositiveButton(R.string.save, posBtnCb)
                .setNegativeButton(android.R.string.cancel, negBtnCb)
                .show()
        }
    }

    fun showTimeoutDialog(context: Context?, maxTime: String) {
        context?.let {
            CustomDialog(it)
                .setTitleStrRes(R.string.timeout_dialog_title)
                .setSubTitle(String.format(it.getString(R.string.timeout_dialog_subtitle), maxTime))
                .setMessageStrRes(R.string.timeout_dialog_message)
                .setPositiveButton(android.R.string.ok) {}
                .show()
        }
    }

    fun showTimerDialog(context: Activity, dialogModel: CountDownDialog): LifecycleObserver {
        return CountDownTimerDialog(context, dialogModel.time)
            .setBackgroundDim(0.7f)
            .addLastText(R.string.start)
            .setCompletedListener(dialogModel.completedListener)
            .safeShow()
    }

}