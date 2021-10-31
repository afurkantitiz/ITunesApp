package com.example.casestudy.utils

import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Fragment.toast(messageToShow: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(requireContext(), messageToShow, duration).show()
}

fun Fragment.snackbar(
    layout: ConstraintLayout,
    messageToShow: String,
    duration: Int = Snackbar.LENGTH_LONG
) {
    Snackbar.make(
        layout,
        messageToShow,
        duration
    )
        .show()
}