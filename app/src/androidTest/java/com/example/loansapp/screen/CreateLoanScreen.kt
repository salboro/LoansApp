package com.example.loansapp.screen

import com.agoda.kakao.common.views.KView
import com.agoda.kakao.dialog.KAlertDialog
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.example.loansapp.R

object CreateLoanScreen : Screen<CreateLoanScreen>() {
    val createLoanTitle = KTextView { withId(R.id.createLoanTitle) }

    val nameEditText = KEditText { withId(R.id.nameEditText) }

    val lastNameEditText = KEditText { withId(R.id.lastNameEditText) }

    val phoneNumberEditText = KEditText { withId(R.id.phoneNumberEditText) }

    val amountSlider = KView { withId(R.id.amountSlider) }

    val amountSliderTitleText = KTextView { withId(R.id.amountSliderTitle) }

    val amountText = KTextView { withId(R.id.amountText) }

    val conditionsText = KTextView { withId(R.id.conditionsText) }

    val createLoanButton = KButton { withId(R.id.createLoanButton) }

    val backButton = KButton { withId(R.id.backArrowImage) }

    val confirmationDialog = KAlertDialog()

    val resultDialog = KAlertDialog()
}