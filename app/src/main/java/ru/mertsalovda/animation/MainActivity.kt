package ru.mertsalovda.animation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.*
import android.util.TypedValue
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)

        val sceneRoot = findViewById<FrameLayout>(R.id.container)
        val animatedObject = findViewById<TextView>(R.id.animatedObject)
        animatedObject.setOnClickListener {
            val random = Random()
            TransitionManager.beginDelayedTransition(sceneRoot)
            animatedObject.setTextSize(TypedValue.COMPLEX_UNIT_SP, random.nextInt(17) + 16f)
        }
        animatedObject.setOnLongClickListener {
            val scene = Scene.getSceneForLayout(sceneRoot, R.layout.scene2, baseContext)
            TransitionSet().apply {
                addTransition(Fade(Fade.OUT))
                addTransition(ChangeBounds())
                addTransition(Fade(Fade.IN))
                ordering = TransitionSet.ORDERING_SEQUENTIAL
                duration = 500L
                interpolator = AccelerateDecelerateInterpolator()
                TransitionManager.go(scene, this)
            }
            true
        }
    }
}