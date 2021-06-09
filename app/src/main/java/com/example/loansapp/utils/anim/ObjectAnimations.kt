package com.example.loansapp.utils.anim

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible

fun View.changeButtonWithProgressBar(progressBar: View) {
    val fadeOut = ObjectAnimator.ofFloat(this, View.ALPHA, 1f)
        .setDuration(500L)
        .apply {
            doOnEnd {
                isVisible = false
            }
        }

    val scaleOut = ObjectAnimator.ofFloat(this, View.SCALE_X, 0.3f)
        .setDuration(500L)
    val visibleOut = AnimatorSet()
    visibleOut.playTogether(fadeOut, scaleOut)

    val fadeIn = ObjectAnimator.ofFloat(progressBar, View.ALPHA, 1f)
        .setDuration(400L)
        .apply {
            doOnStart {
                progressBar.isVisible = true
            }
        }

    val animatorSet = AnimatorSet()
    animatorSet.playSequentially(visibleOut, fadeIn)
    animatorSet.start()
}