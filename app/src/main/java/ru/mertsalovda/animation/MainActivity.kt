package ru.mertsalovda.animation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)
    }

    fun onTextViewClick(view: View) {
//        onValueAnimator(view)
//        onObjectAnimator(view)
//        onPropertyValueHolder(view)
        onAnimatorSet(view)
    }

    fun onValueAnimator(view: View) {
        val va = ValueAnimator.ofFloat(1f, 3f, 1f)
        val duration = 1000L
        va.duration = duration
        va.addUpdateListener {
            view.scaleX = it.animatedValue as Float
            view.scaleY = it.animatedValue as Float
        }
        va.repeatCount = 1
        va.start()
    }

    fun onObjectAnimator(view: View) {
        val oaScaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 3f, 1f)
        val oaScaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 3f, 1f)
        val duration = 1000L
        oaScaleX.duration = duration
        oaScaleY.duration = duration

        oaScaleX.repeatCount = 1
        oaScaleY.repeatCount = 1

        oaScaleX.start()
        oaScaleY.start()
    }

    private fun onPropertyValueHolder(view: View) {
        val pvhX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 3f, 1f)
        val pvhY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 3f, 1f)

        val oa = ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY)

        oa.duration = 1000L
        oa.repeatCount = 1
        oa.start()
    }

    private fun onAnimatorSet(view: View) {
        val oaScaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 0f, 2f)
        val oaScaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0f, 2f)
        val oaTranslationX = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0f, 100f, -100f, 0f)

        val duration = 1000L

        oaScaleX.duration = duration
        oaScaleY.duration = duration
        oaTranslationX.duration = duration

        val animatorSet = AnimatorSet()

        animatorSet.play(oaScaleX).with(oaScaleY).before(oaTranslationX)
        animatorSet.start()
    }
}
