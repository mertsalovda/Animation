package ru.mertsalovda.animation

import android.graphics.drawable.Animatable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.ac_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)

        imageView.setOnClickListener {
            (imageView.drawable as Animatable).start()
        }
    }
}