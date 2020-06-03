package ru.mertsalovda.animation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    private val mDuration = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)
    }

    fun onTextViewClick(view: View) {
//        onValueAnimator(view)
//        onObjectAnimator(view)
//        onPropertyValueHolder(view)
//        onAnimatorSet(view)
//        view.animate().translationX(100f).scaleX(3f).start()
    }

    fun onValueAnimator(view: View) {
        ValueAnimator.ofFloat(1f, 3f, 1f).apply {
            duration = mDuration
            addUpdateListener {
                view.scaleX = it.animatedValue as Float
                view.scaleY = it.animatedValue as Float
            }
            repeatCount = 1
            start()
        }
    }

    fun onObjectAnimator(view: View) {
        val oaScaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 3f, 1f)
        val oaScaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 3f, 1f)
        oaScaleX.duration = mDuration
        oaScaleY.duration = mDuration

        oaScaleX.repeatCount = 1
        oaScaleY.repeatCount = 1

        oaScaleX.start()
        oaScaleY.start()
    }

    private fun onPropertyValueHolder(view: View) {
        val pvhX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 3f, 1f)
        val pvhY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 3f, 1f)

        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY).apply {
            duration = mDuration
            repeatCount = 1
            start()
        }

    }

    private fun onAnimatorSet(view: View) {
        val oaScaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 0f, 2f)
        val oaScaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0f, 2f)
        val oaTranslationX = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0f, 100f, -100f, 0f)

        oaScaleX.duration = mDuration
        oaScaleY.duration = mDuration
        oaTranslationX.duration = mDuration

        AnimatorSet().apply {
            play(oaScaleX).with(oaScaleY).before(oaTranslationX)
            start()
        }
    }
}
