package ru.unknowncoder.rossetireportsautomation.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.unknowncoder.rossetireportsautomation.R
import ru.unknowncoder.rossetireportsautomation.model.response.MemesResponseBody
import ru.unknowncoder.rossetireportsautomation.ui.MemeCellView

class MemeListAdapter : RecyclerView.Adapter<MemeListAdapter.Holder>() {

    private var data: List<MemesResponseBody> = listOf()

    private lateinit var view: View
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): Holder {
        view = MemeCellView(viewGroup.context)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.textView.text = data[position].title
        Glide.with(view).load(data[position].photoUrl).into(holder.imageView)
        holder.likeBtn.isSelected = data[position].isFavorite
    }

    fun setData(data: List<MemesResponseBody>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.TV)
        val imageView: ImageView = view.findViewById(R.id.IV)
        val likeBtn: ImageButton = view.findViewById(R.id.likeBtn)
    }
}