package com.example.loansapp.utils.anim

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible

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

fun View.shake() {
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

fun View.yScaleOutAndFadeOut(time: Long) {
    val scaleOut = ObjectAnimator.ofFloat(this, View.SCALE_Y, 0.1f)
        .setDuration(time)

    val fadeOut = ObjectAnimator.ofFloat(this, View.ALPHA, 0f)
        .setDuration(time)

    val animatorSet = AnimatorSet()
    animatorSet.playTogether(scaleOut, fadeOut)
    animatorSet.doOnEnd {
        this.isVisible = false
    }
    animatorSet.start()
}

fun View.yScaleInAndFadeIn(time: Long) {
    val scaleIn = ObjectAnimator.ofFloat(this, View.SCALE_Y, 1f)
        .setDuration(time)

    val fadeIn = ObjectAnimator.ofFloat(this, View.ALPHA, 1f)
        .setDuration(time)

    val animatorSet = AnimatorSet()
    animatorSet.playTogether(scaleIn, fadeIn)
    animatorSet.doOnStart {
        this.isVisible = true
    }
    animatorSet.start()
}

fun View.disappearInLeftComeFromRight() {
    val transitionToLeft =
        ObjectAnimator.ofFloat(this, View.TRANSLATION_X, -this.width * 2.toFloat())
            .apply {
                duration = 400L
                doOnEnd {
                    translationX = this@disappearInLeftComeFromRight.width * 2.toFloat()
                }
            }

    val transitionFromRight = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, 0f)
        .apply {
            duration = 400L
            startDelay = 700L
        }

    val animatorSet = AnimatorSet()
    animatorSet.playSequentially(transitionToLeft, transitionFromRight)
    animatorSet.start()
}

fun View.disappearInRightComeFromLeft() {
    val transitionToLeft =
        ObjectAnimator.ofFloat(this, View.TRANSLATION_X, this.width * 2.toFloat())
            .apply {
                duration = 400L
                doOnEnd {
                    translationX = -this@disappearInRightComeFromLeft.width * 2.toFloat()
                }
            }

    val transitionFromRight = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, 0f)
        .apply {
            duration = 400L
            startDelay = 700L
        }

    val animatorSet = AnimatorSet()
    animatorSet.playSequentially(transitionToLeft, transitionFromRight)
    animatorSet.start()
}
