package com.amb.SerFee.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.amb.SerFee.R

class EmailTField: AppCompatEditText {
    private lateinit var emailIcon: Drawable
    private var doesEmailValid: Boolean = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        emailIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_email_24) as Drawable
        setCompoundDrawablesRelativeWithIntrinsicBounds(emailIcon, null, null, null)


        addTextChangedListener { text ->
            val email = text?.trim().toString()
            doesEmailValid = !email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
            error = if (doesEmailValid) null else resources.getString(R.string.invalid_email)
        }
    }

}

