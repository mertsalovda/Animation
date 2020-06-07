package ru.mertsalovda.animation

import android.graphics.drawable.Animatable2
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.ac_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)

        val shrug = shrugAndroid.drawable as Animatable2
        val blinking = blinkingAndroid.drawable as Animatable2

        shrugAndroid.setOnClickListener {
            shrug.registerAnimationCallback(object : Animatable2.AnimationCallback() {
                override fun onAnimationEnd(drawable: Drawable?) {
                    blinking.start()
                    shrug.clearAnimationCallbacks()
                }
            })
            shrug.start()
        }
        blinkingAndroid.setOnClickListener {
            blinking.registerAnimationCallback(object : Animatable2.AnimationCallback() {
                override fun onAnimationEnd(drawable: Drawable?) {
                    shrug.start()
                    blinking.clearAnimationCallbacks()
                }
            })
            blinking.start()
        }
    }
}