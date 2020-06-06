package ru.mertsalovda.animation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

    override fun onResume() {
        super.onResume()
        val recMoveAnimator = ObjectAnimator.ofFloat(recycler, View.TRANSLATION_X, -500f, 0f)
        val recAlphaAnimator = ObjectAnimator.ofFloat(recycler, View.ALPHA, 0f, 1f)

        AnimatorSet().apply {
            duration = 1000L
            playTogether(recMoveAnimator, recAlphaAnimator)
            start()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addItem -> {
                mAdapter.add()
                return true
            }
            R.id.removeItem -> {
                mAdapter.removeLastItem()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onItemClick(view: View) {
        val itemPosition = recycler.getChildAdapterPosition(view);
        if (itemPosition != RecyclerView.NO_POSITION) {
            mAdapter.changeItemNumber(itemPosition);
        }
    }
}