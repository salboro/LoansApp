package com.example.loansapp.screen

import com.agoda.kakao.common.views.KView
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.tabs.KTabLayout
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.example.loansapp.R
import com.example.loansapp.view.ChooseLanguageDialog

object EnterScreen : Screen<EnterScreen>() {

    val logoIcon = KImageView { withId(R.id.titleIconImage) }

    val optionsTab = KTabLayout { withId(R.id.enterOptionsTabLayout) }

    val loginNameEditText = KEditText { withId(R.id.loginNameEditText) }

    val loginPasswordEditText = KEditText { withId(R.id.loginPasswordEditText) }

    val loginSubmitButton = KButton { withId(R.id.loginButton) }

    val registerNameEditText = KEditText { withId(R.id.registerNameEditText) }

    val registerPasswordEditText = KEditText { withId(R.id.registerPasswordEditText) }

    val registerSubmitButton = KButton { withId(R.id.registerButton) }

    val changeLanguageCard = KView { withId(R.id.changeLanguageCard) }

    val chooseLanguageDialog = ChooseLanguageDialog()

    val bottomText = KTextView { withId(R.id.loginNoticeText) }

    val registerSuccessCard = KView { withId(R.id.registerSuccessCard) }
}