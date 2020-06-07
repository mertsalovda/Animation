package ru.mertsalovda.animation

import android.animation.AnimatorSet
import android.graphics.drawable.Animatable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.ac_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)

        val shrug = shrugAndroid.drawable as Animatable
        val blinking = blinkingAndroid.drawable as Animatable

        shrugAndroid.setOnClickListener {
            shrug.start()
        }
        blinkingAndroid.setOnClickListener {
            blinking.start()
        }
    }
}