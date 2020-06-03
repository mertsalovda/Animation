package ru.mertsalovda.animation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.ac_main.*

class MainActivity : AppCompatActivity(), SampleAdapter.Callback {

    private val mAdapter = SampleAdapter(this)
    private val mAnimator: RecyclerView.ItemAnimator = SampleItemAnimator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)

        recycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
            itemAnimator = mAnimator
        }

    }

    override fun onItemClick(view: View) {
        val itemPosition = recycler.getChildAdapterPosition(view);
        if (itemPosition != RecyclerView.NO_POSITION) {
            mAdapter.changeItemNumber(itemPosition);
        }
    }
}