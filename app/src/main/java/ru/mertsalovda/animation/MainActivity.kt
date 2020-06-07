package ru.mertsalovda.animation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.*
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val scenes = listOf(R.layout.scene2, R.layout.scene3, R.layout.scene4, R.layout.scene1)
    private var posotion = 0
    private var count = 1
    private lateinit var scene: Scene

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)
    }

    fun onClick(view: View) {
        val sceneRoot = findViewById<FrameLayout>(R.id.container)
        scene = Scene.getSceneForLayout(sceneRoot, scenes[posotion], baseContext)
        TransitionSet().apply {
            addTransition(ChangeBounds())
            ordering = TransitionSet.ORDERING_SEQUENTIAL
            duration = 500L
            interpolator = AccelerateDecelerateInterpolator()
            TransitionManager.go(scene, this)
        }
        if (posotion == scenes.size - 1) {
            posotion = 0
            if (count % 2 == 0) {
                Toast.makeText(this, "Чётный круг: №$count", Toast.LENGTH_SHORT).show()
            }
            count++
        } else {
            posotion++
        }
    }
}