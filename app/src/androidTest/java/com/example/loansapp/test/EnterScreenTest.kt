package com.example.loansapp.test

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.loansapp.KTestCase
import com.example.loansapp.screen.EnterScreen
import com.example.loansapp.screen.LoansScreen
import com.example.loansapp.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EnterScreenTest : KTestCase() {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun checkTabLayoutWorkCorrectly() {
        run {
            step("Check logo is visible") {
                EnterScreen {
                    logoIcon {
                        isDisplayed()
                    }
                }
            }

            step("Click on registration in tab layout") {
                EnterScreen {
                    optionsTab {
                        selectTab(1)
                    }
                }
            }

            step("Check that one of the registration fragment fields visible") {
                EnterScreen {
                    registerNameEditText {
                        isDisplayed()
                    }
                }
            }

            step("Click on login in tab layout") {
                EnterScreen {
                    optionsTab {
                        selectTab(0)
                    }
                }
            }

            step("Check that one of the login fragment fields visible") {
                EnterScreen {
                    loginNameEditText {
                        isDisplayed()
                    }
                }
            }
        }
    }

    @Test
    fun checkLanguageChangeWorkCorrectly() {
        run {
            step("Open choose language dialog") {
                EnterScreen {
                    changeLanguageCard {
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

            step("Check that bottom text write in russian") {
                EnterScreen {
                    bottomText.hasText("Ещё нет аккаунта? Зарегестируйтесь!")
                }
            }

            step("Back to english") {
                EnterScreen {
                    changeLanguageCard.click()

                    chooseLanguageDialog {
                        englishItemInRussian.click()

                        positiveButton.click()
                    }
                }
            }

            step("Check that bottom test write in english") {
                EnterScreen {
                    bottomText.hasText("You don\'t have account yet? Go to Register page!")
                }
            }
        }
    }

    @Test
    fun registrationTest() {
        run {
            step("Choose registration in tab options") {
                EnterScreen {
                    optionsTab {
                        isDisplayed()
                        selectTab(1)
                    }
                }
            }

            step("Fill name and password fields and click submit button") {
                EnterScreen {
                    registerNameEditText {
                        isDisplayed()
                        typeText("name")
                    }

                    registerPasswordEditText {
                        isDisplayed()
                        typeText("password")
                    }

                    registerSubmitButton {
                        isDisplayed()
                        click()
                    }
                }
            }

            step("Check success card is displayed") {
                EnterScreen {
                    registerSuccessCard {
                        isVisible()
                    }
                }
            }

            step("Check submit button is not enable") {
                EnterScreen {
                    registerSubmitButton {
                        isDisabled()
                    }
                }
            }

        }
    }

    @Test
    fun checkLoginWorkCorrectly() {
        run {
            step("Fill login fields and click submit button") {
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

            step("Check that loans screen appear") {
                LoansScreen {
                    toolbar {
                        isDisplayed()
                    }
                }
            }
        }
    }

}