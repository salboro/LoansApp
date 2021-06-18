package com.example.loansapp.screen

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.agoda.kakao.toolbar.KToolbar
import com.example.loansapp.R
import com.example.loansapp.view.ChooseLanguageDialog
import org.hamcrest.Matcher

object LoansScreen : Screen<LoansScreen>() {

    val toolbar = KToolbar { withId(R.id.toolbar) }

    val loansConditionsCard = KView { withId(R.id.loansConditionsCard) }

    val changeLanguageMenuItem = KTextView { withText("Change language") }

    val changeLanguageMenuItemInRussian = KTextView { withText("Сменить язык") }

    val changeThemeMenuItem = KTextView { withText("Change theme") }

    val createLoanCardTitle = KTextView { withId(R.id.loansConditionsTitleText) }

    val chooseLanguageDialog = ChooseLanguageDialog()

    val loansList = KRecyclerView(
        builder = { withId(R.id.loansList) },
        itemTypeBuilder = { itemType(::LoanItem) }
    )

    class LoanItem(parent: Matcher<View>) : KRecyclerItem<LoanItem>(parent) {
        val loanAmount = KTextView(parent) { withId(R.id.loanItemAmountText) }
        val loanState = KTextView(parent) { withId(R.id.stateText) }
    }
}