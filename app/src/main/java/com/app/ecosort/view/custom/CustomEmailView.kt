package com.app.storyfy.loginwithanimation.customView

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText

class CustomEmailView : TextInputEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val email = s.toString()
                error = if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    "Invalid email address"
                } else {
                    null
                }
            }
        })
    }
    fun isValid(): Boolean {
        val email = text.toString()
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}
