package ru.mertsalovda.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.widget.TextView
import androidx.core.animation.addListener
import androidx.dynamicanimation.animation.*
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
        val preAlphaTextInfo = preInfo as TextInfo
        val postAlphaTextInfo = postInfo as TextInfo
        val view = holder.itemView as TextView

        val moveAway = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0f, 400f)
        val moveComeBlack = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, -400f, 0f)
        val moveTextAnim = AnimatorSet()
        moveTextAnim.playSequentially(moveAway, moveComeBlack)

        val oldTextAlpha = ObjectAnimator.ofFloat(view, View.ALPHA, 0.8f, 0f)
        val newTextAlpha = ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f)
        oldTextAlpha.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.text = preAlphaTextInfo.text
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.text = postAlphaTextInfo.text
            }
        })

        val alphaTextAnim = AnimatorSet()
        alphaTextAnim.playSequentially(oldTextAlpha, newTextAlpha)
        val overallAnim = AnimatorSet()
        overallAnim.apply {
            duration = 1000L
            playTogether(moveTextAnim, alphaTextAnim)
            addListener {
                dispatchAnimationFinished(newHolder)
            }
            start()
        }

        return super.animateChange(oldHolder, newHolder, preInfo, postInfo)
    }

    override fun animateAdd(holder: ViewHolder?): Boolean {
        val textView = (holder as SampleViewHolder).itemView as TextView

        val transitionY =
            SpringAnimation(textView, object : FloatPropertyCompat<View>("translationY") {
                override fun setValue(view: View?, value: Float) {
                    view!!.translationY = value
                }

                override fun getValue(view: View?): Float {
                    return view!!.translationY
                }
            })
        val springForceY = SpringForce(0f).apply {
            stiffness = SpringForce.STIFFNESS_MEDIUM
            dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        }
        transitionY.spring = springForceY
        textView.y += 200f
        transitionY.start()
        return super.animateAdd(holder)
    }

    override fun animateRemove(holder: ViewHolder?): Boolean {
        val textView = (holder as SampleViewHolder).itemView as TextView
        val flingY = FlingAnimation(textView, DynamicAnimation.TRANSLATION_Y)
        flingY.setStartVelocity(5000f)
            .setMinValue(0f)
            .setMaxValue(textView.translationY + 500)
            .setFriction(1.1f)
            .start()
        return super.animateRemove(holder)
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