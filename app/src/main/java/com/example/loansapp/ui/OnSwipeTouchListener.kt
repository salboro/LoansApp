package com.example.loansapp.ui

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class OnSwipeTouchListener(
    context: Context,
    private val onSwipeRight: () -> Unit = {},
    private val onSwipeLeft: () -> Unit = {},
    private val onClick: () -> Unit = {}
) : View.OnTouchListener {
    companion object {
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    private val gestureDetector = GestureDetector(context, GestureListener())


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        v?.performClick()
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var result = false
            val diffY = e2!!.y - e1!!.y
            val diffX = e2.x - e1.x
            if (abs(diffX) > abs(diffY)) {
                if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight()
                    } else {
                        onSwipeLeft()
                    }
                    result = true
                }
            }
            return result
        }

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            onClick()
            return true
        }
    }
}