package com.example.typeracer.view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

class BlockedSelectionEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextInputEditText(context, attrs, defStyleAttr) {

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        //on selection move cursor to end of text
        setSelection(length())
    }

}