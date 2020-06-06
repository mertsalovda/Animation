package ru.mertsalovda.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.animation.addListener
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.mertsalovda.animation.SampleAdapter.SampleViewHolder

class SampleItemAnimator : DefaultItemAnimator() {

    override fun canReuseUpdatedViewHolder(viewHolder: ViewHolder) = true

    override fun recordPreLayoutInformation(
        state: RecyclerView.State,
        viewHolder: ViewHolder,
        changeFlags: Int,
        payloads: MutableList<Any>
    ): ItemHolderInfo {
        val textInfo = TextInfo()
        textInfo.setFrom(viewHolder)
        return textInfo
    }

    override fun recordPostLayoutInformation(
        state: RecyclerView.State,
        viewHolder: ViewHolder
    ): ItemHolderInfo {
        val textInfo = TextInfo()
        textInfo.setFrom(viewHolder)
        return textInfo
    }

    override fun animateChange(
        oldHolder: ViewHolder,
        newHolder: ViewHolder,
        preInfo: ItemHolderInfo,
        postInfo: ItemHolderInfo
    ): Boolean {
        val holder = newHolder as SampleViewHolder
        val preColorTextInfo = preInfo as TextInfo
        val postColorTextInfo = postInfo as TextInfo
        val view = holder.itemView as TextView
        val moveAway =
            ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0f, 400f)
        val moveComeBlack =
            ObjectAnimator.ofFloat(view, View.TRANSLATION_X, -400f, 0f)
        val bgAnim = AnimatorSet()
        bgAnim.playSequentially(moveAway, moveComeBlack)
        val oldTextRotate = ObjectAnimator.ofFloat(view, "alpha", 0.8f, 0f)
        val newTextRotate = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        oldTextRotate.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                view.text = preColorTextInfo.text
            }

            override fun onAnimationStart(animation: Animator?) {
                view.text = postColorTextInfo.text
            }
        })

        val textAnim = AnimatorSet()
        textAnim.playSequentially(oldTextRotate, newTextRotate)
        val overallAnim = AnimatorSet()
        overallAnim.apply {
            duration = 1000L
            playTogether(bgAnim, textAnim)
            addListener {
                dispatchAnimationFinished(newHolder)
            }
            start()
        }
        return super.animateChange(oldHolder, newHolder, preInfo, postInfo)
    }

    private class TextInfo : ItemHolderInfo() {
        lateinit var text: String

        override fun setFrom(holder: ViewHolder): ItemHolderInfo {
            if (holder is SampleViewHolder) {
                text = (holder.itemView as TextView).text.toString()
            }
            return super.setFrom(holder)
        }
    }
}