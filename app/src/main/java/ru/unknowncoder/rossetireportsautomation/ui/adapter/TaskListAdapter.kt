package ru.unknowncoder.rossetireportsautomation.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import ru.unknowncoder.rossetireportsautomation.R
import ru.unknowncoder.rossetireportsautomation.ui.TaskCardView

class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.Holder>() {

    private var data: List<DocumentSnapshot> = listOf()

    private lateinit var view: View
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): Holder {
        view = TaskCardView(viewGroup.context)
        return Holder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.taskTitle.text = data[position].getString("title")
//        Glide.with(view).load(data[position].photoUrl).into(holder.imageView)
        holder.taskDescription.text = data[position].getString("techSecurity")
    }

    fun setData(data: List<DocumentSnapshot>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val taskTitle: TextView = view.findViewById(R.id.taskTitle)
//        val imageView: ImageView = view.findViewById(R.id.IV)
        val taskDescription: TextView= view.findViewById(R.id.taskDescription)
    }
}