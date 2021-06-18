package com.example.loansapp.test

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.loansapp.KTestCase
import com.example.loansapp.screen.CreateLoanScreen
import com.example.loansapp.screen.EnterScreen
import com.example.loansapp.screen.LoansScreen
import com.example.loansapp.ui.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreateLoanScreenTest : KTestCase() {

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

        LoansScreen {
            createLoanCardTitle.isDisplayed()

            loansConditionsCard {
                click()
            }
        }
    }

    @Test
    fun checkFieldsShowsCorrectly() {
        run {
            step("Check back button displayed and clickable") {
                CreateLoanScreen {
                    backButton {
                        isDisplayed()
                        isClickable()
                    }
                }
            }

            step("Check form for loan creation shows right") {
                CreateLoanScreen {
                    createLoanTitle {
                        isDisplayed()
                        hasText("Create loan")
                    }

                    nameEditText {
                        isDisplayed()
                        hasHint("Name")
                    }

                    lastNameEditText {
                        isDisplayed()
                        hasHint("Last name")
                    }

                    amountSliderTitleText {
                        isDisplayed()
                        hasText("Choose amount")
                    }

                    amountSlider {
                        isDisplayed()
                        SliderUtil.withValue(5000f)
                    }

                    amountText {
                        isDisplayed()
                        hasText("Max 10000 rub")
                    }

                    conditionsText {
                        isDisplayed()
                        hasText("With 10.00% and 15 months")
                    }

                    createLoanButton {
                        isDisplayed()
                        isClickable()
                        hasText("Create loan")
                    }
                }
            }
        }
    }

    @Test
    fun checkSliderWorkCorrectly() {
        run {
            step("Change slider value") {
                CreateLoanScreen {
                    amountSlider {
                        act {
                            SliderUtil.setValue(7000f)
                        }
                    }
                }
            }

            step("Check slider value") {
                CreateLoanScreen {
                    amountSlider {
                        SliderUtil.withValue(7000f)
                    }
                }
            }

            step("Check amount text") {
                CreateLoanScreen {
                    amountText.hasText("Amount: 7000 rub")
                }
            }
        }
    }

    @Test
    fun checkCreateLoanWorkCorrectly() {
        run {
            step("Fill all fields") {
                CreateLoanScreen {
                    nameEditText.typeText("test")

                    lastNameEditText.typeText("test")

                    phoneNumberEditText.typeText("123")

                    amountSlider {
                        SliderUtil.setValue(4257f)
                    }
                }
            }

            step("Click on create button") {
                CreateLoanScreen {
                    createLoanButton.click()
                }
            }

            step("Click positive button in dialogues") {
                CreateLoanScreen {
                    confirmationDialog {
                        isDisplayed()
                        positiveButton.click()
                    }

                    resultDialog {
                        isDisplayed()

                        positiveButton.click()
                    }
                }
            }

            step("Check transition to loans screen") {
                LoansScreen {
                    toolbar.isDisplayed()
                }
            }
        }
    }

    @Test
    fun checkBackButtonWorkCorrectly() {
        run {
            step("Click on back button") {
                CreateLoanScreen {
                    backButton.click()
                }
            }

            step("Check transition to loans fragment") {
                LoansScreen {
                    toolbar.isDisplayed()
                }
            }
        }
    }
}