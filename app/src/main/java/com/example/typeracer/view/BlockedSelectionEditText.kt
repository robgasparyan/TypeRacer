package com.example.typeracer.view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

class BlockedSelectionEditText : TextInputEditText {

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, style: Int) : super(context, attrs, style)

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        //on selection move cursor to end of text
        setSelection(length())
    }

}