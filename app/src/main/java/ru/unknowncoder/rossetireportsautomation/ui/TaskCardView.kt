package ru.unknowncoder.rossetireportsautomation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.view_task_card.view.*
import ru.unknowncoder.rossetireportsautomation.R

class TaskCardView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) :
    LinearLayout(context, attributeSet) {


    init {
        View.inflate(context, R.layout.view_task_card, this)
        initListeners()
    }

    private fun initListeners() {
        startWork.setOnClickListener {
            openCard()
        }

    }

    private fun openCard() {
        Toast.makeText(
            context,
            "Ok, you select this card, are you satisfied?",
            Toast.LENGTH_SHORT
        ).show()
//TODO сделать активити просмотра карточки задания

    }
}