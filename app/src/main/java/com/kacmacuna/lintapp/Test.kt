package com.kacmacuna.lintapp

import android.content.Context
import android.widget.RelativeLayout

class NewView {
    fun create(context: Context): RelativeLayout {
        return RelativeLayout(context)
    }
}
