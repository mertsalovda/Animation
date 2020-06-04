package ru.mertsalovda.animation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.dynamicanimation.animation.*
import kotlinx.android.synthetic.main.ac_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mTranslationX: SpringAnimation
    private lateinit var mTranslationY: SpringAnimation


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)

        // SpringAnimation
        oneObject.setOnClickListener {
            mTranslationX.cancel()
            mTranslationY.cancel()

            oneObject.x = 0f
            oneObject.y = 0f

            mTranslationX.start()
            mTranslationY.start()
        }

        mTranslationX =
            SpringAnimation(oneObject, object : FloatPropertyCompat<View>("translationX") {
                override fun setValue(view: View?, value: Float) {
                    view!!.translationX = value
                }

                override fun getValue(view: View?) = view!!.translationX
            })
        SpringForce(0f).apply {
            stiffness = SpringForce.STIFFNESS_LOW
            dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
            mTranslationX.spring = this
        }

        mTranslationY =
            SpringAnimation(oneObject, object : FloatPropertyCompat<View>("translationY") {
                override fun setValue(view: View?, value: Float) {
                    view!!.translationY = value
                }

                override fun getValue(view: View?) = view!!.translationY
            })
        SpringForce(0f).apply {
            stiffness = SpringForce.STIFFNESS_LOW
            dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
            mTranslationY.spring = this
        }

        // FlingAnimation
        twoObject.setOnClickListener {
            val maxValue: Int = (layout.height - twoObject.height) / 2

            val flingY =
                FlingAnimation(twoObject, DynamicAnimation.TRANSLATION_Y)

            flingY.setStartVelocity(1000f)
                .setMinValue(0f)
                .setMaxValue(maxValue.toFloat())
                .setFriction(0.5f)
                .start()
        }

    }
}