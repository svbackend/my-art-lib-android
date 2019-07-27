package com.svbackend.mykinotop.internal

import android.text.Editable
import android.text.TextWatcher
import android.view.View

class TextChanged(
    private val lambda: (Editable) -> Unit
) : TextWatcher {
    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

    override fun afterTextChanged(editable: Editable) {
        lambda(editable)
    }
}

class OnFocus(
    private val lambda: () -> Unit
) : View.OnFocusChangeListener {
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (hasFocus) {
            lambda()
        }
    }
}

class OnFocusOut(
    private val lambda: () -> Unit
) : View.OnFocusChangeListener {
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (!hasFocus) {
            lambda()
        }
    }
}