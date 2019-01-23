package com.qfxl.splash

import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.transition.TransitionManager

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashConstraint = ConstraintSet()
        splashConstraint.clone(this, R.layout.activity_splash)
        setContentView(R.layout.activity_splash_preview)

        val constraintLayout = findViewById<ConstraintLayout>(R.id.ctl_splash)

        Handler().postDelayed({
            TransitionManager.beginDelayedTransition(constraintLayout)
            splashConstraint.applyTo(constraintLayout)
        }, 3000)
    }
}
