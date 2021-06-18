package com.example.loansapp.view

import android.R
import com.agoda.kakao.common.views.KBaseView
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView

class ChooseLanguageDialog : KBaseView<ChooseLanguageDialog>({ isRoot() }) {


    val englishItemInEnglish = KTextView { containsText("English") }
        .also { it.inRoot { isDialog() } }

    val russianItemInEnglish = KTextView { containsText("Russian") }
        .also { it.inRoot { isDialog() } }

    val englishItemInRussian = KTextView { containsText("Английский") }
        .also { it.inRoot { isDialog() } }

    val russianItemInRussian = KTextView { containsText("Русский") }
        .also { it.inRoot { isDialog() } }

    val positiveButton = KButton { withId(R.id.button1) }
        .also { it.inRoot { isDialog() } }

    val negativeButton = KButton { withId(R.id.button2) }
        .also { it.inRoot { isDialog() } }

    val neutralButton = KButton { withId(R.id.button3) }
        .also { it.inRoot { isDialog() } }

    val title = KTextView { withResourceName("alertTitle") }
        .also { it.inRoot { isDialog() } }

    val message = KTextView { withId(R.id.message) }
        .also { it.inRoot { isDialog() } }

    val icon = KImageView { withId(R.id.icon) }
        .also { it.inRoot { isDialog() } }
}