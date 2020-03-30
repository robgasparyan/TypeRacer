package com.example.typeracer.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDialog
import com.example.typeracer.R

class CustomDialog(context: Context?) : AppCompatDialog(context) {

    init {
        setContentView(R.layout.win_dialog)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
    }

    fun setTitleStrRes(@StringRes strId: Int): CustomDialog {
        findViewById<TextView>(R.id.title)?.setText(strId)
        return this
    }

    fun setSubTitle(text: String): CustomDialog {
        findViewById<TextView>(R.id.subtitle)?.text = text
        return this
    }

    fun setMessageStrRes(@StringRes strId: Int): CustomDialog {
        findViewById<TextView>(R.id.message)?.setText(strId)
        return this
    }

    fun setPositiveButton(@StringRes strId: Int, cb: View.OnClickListener? = null): CustomDialog {
        findViewById<TextView>(R.id.positive_btn)?.apply {
            setText(strId)
            setOnClickListener {
                dismiss()
                cb?.onClick(it)
            }
        }
        return this
    }

    fun setNegativeButton(@StringRes strId: Int, cb: View.OnClickListener? = null): CustomDialog {
        findViewById<TextView>(R.id.negative_btn)?.apply {
            setText(strId)
            setOnClickListener {
                dismiss()
                cb?.onClick(it)
            }
        }
        return this
    }

}