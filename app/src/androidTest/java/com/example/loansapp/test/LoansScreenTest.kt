package com.example.loansapp.test

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.loansapp.KTestCase
import com.example.loansapp.R
import com.example.loansapp.screen.CreateLoanScreen
import com.example.loansapp.screen.EnterScreen
import com.example.loansapp.screen.LoansScreen
import com.example.loansapp.ui.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoansScreenTest : KTestCase() {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun before() {
        EnterScreen {
            loginNameEditText {
                isDisplayed()
                typeText("this Name Only For test")
            }

            loginPasswordEditText {
                isDisplayed()
                typeText("password")
            }

            loginSubmitButton {
                isDisplayed()
                click()
            }
        }
    }

    @Test
    fun checkConditionsCardSwipe() {
        run {
            step("Check card is displayed") {
                LoansScreen {
                    loansConditionsCard {
                        isDisplayed()
                    }
                }
            }

            step("Swipe card") {
                LoansScreen {
                    loansConditionsCard {
                        act {
                            ViewActions.swipeLeft()
                        }
                    }
                }
            }

            step("Check that card return back") {
                LoansScreen {
                    loansConditionsCard {
                        isDisplayed()
                    }
                }
            }
        }
    }

    @Test
    fun checkChangeThemeWorkCorrectly() {
        run {
            step("Open toolbar menu") {
                LoansScreen {
                    toolbar.isDisplayed()

                    Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
                }
            }

            step("Change theme if it light") {
                LoansScreen {
                    changeThemeMenuItem {
                        isDisplayed()
                        click()
                    }
                }
            }

            step("Check is theme changed") {
                LoansScreen {
                    toolbar {
                        hasBackgroundColor(R.color.grey_800)
                    }
                }
            }

            step("Change theme back") {
                LoansScreen {
                    toolbar.isDisplayed()

                    Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)

                    changeThemeMenuItem {
                        isDisplayed()
                        click()
                    }
                }
            }
        }
    }

    @Test
    fun checkChangeLanguageWorkCorrectly() {
        run {
            step("Open choose language dialog") {
                LoansScreen {
                    toolbar.isDisplayed()

                    Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)

                    changeLanguageMenuItem {
                        isDisplayed()
                        click()
                    }

                    chooseLanguageDialog {
                        isDisplayed()
                    }
                }
            }

            step("Choose russian language") {
                EnterScreen {
                    chooseLanguageDialog {
                        isDisplayed()

                        //Для случая когда начальный язык НЕ английский
                        try {
                            russianItemInEnglish.click()
                        } catch (e: Exception) {
                            russianItemInRussian.click()
                        }


                        positiveButton.click()
                    }
                }
            }

            step("Check that toolbar title write in russian") {
                LoansScreen {
                    toolbar.hasTitle("Ваши займы")
                }
            }

            step("Back to english") {
                LoansScreen {
                    Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)

                    changeLanguageMenuItemInRussian {
                        isDisplayed()
                        click()
                    }

                    chooseLanguageDialog {
                        englishItemInRussian.click()

                        positiveButton.click()
                    }
                }
            }

            step("Check that toolbar title in english") {
                LoansScreen {
                    toolbar {
                        isDisplayed()
                        hasTitle("Your loans")
                    }
                }
            }
        }
    }

    @Test
    fun checkLoansListShowsCorrectly() {
        run {
            step("Check loans list is displayed and has 3 items") {
                LoansScreen {
                    loansList {
                        isDisplayed()
                        hasSize(3)
                    }
                }
            }

            step("Check list item have correct information") {
                LoansScreen {
                    loansList {
                        firstChild<LoansScreen.LoanItem> {
                            loanAmount.hasText("Amount: 10 rub")
                            loanState.hasText("State: approved")
                        }
                    }
                }
            }
        }
    }

    @Test
    fun checkTransitionToCreationScreenWorkCorrectly() {
        run {
            step("Check creation loan card is displayed data") {
                LoansScreen {
                    createLoanCardTitle.isDisplayed()
                }
            }
            step("Click on loan creation card") {
                LoansScreen {
                    loansConditionsCard {
                        click()
                    }
                }
            }

            step("Check than creation screen is appear") {
                CreateLoanScreen {
                    createLoanTitle.isDisplayed()
                }
            }
        }
    }
}