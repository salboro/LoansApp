package com.example.loansapp.test

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import com.google.android.material.slider.Slider
import org.hamcrest.Description
import org.hamcrest.Matcher

class SliderUtil {
    companion object {
        fun withValue(expectedValue: Float): Matcher<View?> {
            return object : BoundedMatcher<View?, Slider>(Slider::class.java) {
                override fun describeTo(description: Description) {
                    description.appendText("expected: $expectedValue")
                }

                override fun matchesSafely(slider: Slider?): Boolean {
                    return slider?.value == expectedValue
                }
            }
        }

        fun setValue(value: Float): ViewAction {
            return object : ViewAction {
                override fun getDescription(): String {
                    return "Set Slider value to $value"
                }

                override fun getConstraints(): Matcher<View> {
                    return ViewMatchers.isAssignableFrom(Slider::class.java)
                }

                override fun perform(uiController: UiController?, view: View) {
                    val seekBar = view as Slider
                    seekBar.value = value
                }
            }
        }
    }
}

