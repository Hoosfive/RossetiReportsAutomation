package ru.unknowncoder.rossetireportsautomation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.view_meme_cell.view.*
import ru.unknowncoder.rossetireportsautomation.R

class MemeCellView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) :
    LinearLayout(context, attributeSet) {


    init {
        View.inflate(context, R.layout.view_meme_cell, this)
        initListeners(context)
    }

    private fun initListeners(context: Context) {
        shareMeme(context)
    }
}


private fun shareMeme(context: Context) {
    Toast.makeText(
        context,
        "Ok, you shared this meme with your friends, are you satisfied?",
        Toast.LENGTH_SHORT
    ).show()
}