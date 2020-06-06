package ru.mertsalovda.animation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList


internal class SampleAdapter(context: Context?) :
    RecyclerView.Adapter<SampleAdapter.SampleViewHolder>() {

    private val mCallback: Callback?
    private val mDataset: ArrayList<String>

    init {
        mCallback = if (context is Callback) context else null
        mDataset = dataset()
    }

    private fun dataset(): ArrayList<String> {
        val list: ArrayList<String> = ArrayList()
        for (i in 1..5) {
            list.add("Item $i")
        }
        return list
    }

    fun changeItemNumber(position: Int) {
        val number: Int = Random().nextInt(100)
        mDataset[position] = "Item $number"
        notifyItemChanged(position)
    }

    fun add(){
        val number: Int = Random().nextInt(100)
        mDataset.add("Item $number")
        notifyItemInserted(mDataset.size-1)
    }

    fun removeLastItem(){
        val index = mDataset.size-1
        mDataset.removeAt(index)
        notifyItemRemoved(index)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SampleViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false) as TextView
        textView.setOnClickListener {
            mCallback?.let { callback -> callback.onItemClick(it) }
        }
        return SampleViewHolder(textView)
    }

    override fun getItemCount() = mDataset.size


    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        (holder.itemView as TextView).text = mDataset[position]
    }

    interface Callback {
        fun onItemClick(view: View)
    }

    class SampleViewHolder(textView: TextView) : RecyclerView.ViewHolder(textView)

}