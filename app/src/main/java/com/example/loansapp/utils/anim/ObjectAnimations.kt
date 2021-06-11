package com.example.loansapp.utils.anim

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout

fun View.fadeReplaceWithView(view: View) {
    val fadeOut = ObjectAnimator.ofFloat(this, View.ALPHA, 0f)
        .setDuration(400L)

    val fadeIn = ObjectAnimator.ofFloat(view, View.ALPHA, 1f)
        .apply {
            duration = 200L
            doOnStart {
                this@fadeReplaceWithView.isVisible = false
                view.isVisible = true
            }
        }

    val animatorSet = AnimatorSet()
    animatorSet.playSequentially(fadeOut, fadeIn)
    animatorSet.start()
}

fun View.fadeInAndFadeOutOverTime(time: Long) {
    val fadeIn = ObjectAnimator.ofFloat(this, View.ALPHA, 1f)
        .apply {
            startDelay = 500L
            duration = 800L
            doOnStart {
                isVisible = true
            }
        }

    val fadeOut = ObjectAnimator.ofFloat(this, View.ALPHA, 0f)
        .apply {
            startDelay = time
            duration = 500L
            doOnEnd {
                isVisible = false
            }
        }

    val animatorSet = AnimatorSet()
    animatorSet.playSequentially(fadeIn, fadeOut)
    animatorSet.start()
}

fun TextInputLayout.shake() {
    val leftShake = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, 20f)
        .setDuration(50L)

    val rightShake = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, -20f)
        .setDuration(100L)

    val back = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, 0f)
        .setDuration(50L)

    val animatorSet = AnimatorSet()
    animatorSet.playSequentially(leftShake, rightShake, back)
    animatorSet.start()
}