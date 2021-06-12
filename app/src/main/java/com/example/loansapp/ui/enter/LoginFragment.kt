package com.example.loansapp.ui.enter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.loansapp.MyApplication
import com.example.loansapp.R
import com.example.loansapp.databinding.LoginFragmentBinding
import com.example.loansapp.domain.entity.ErrorType
import com.example.loansapp.presentation.enter.login.LoginViewModel
import com.example.loansapp.presentation.enter.login.LoginViewState
import com.example.loansapp.ui.loans.LoansFragment
import com.example.loansapp.utils.anim.fadeInAndFadeOutOverTime
import com.example.loansapp.utils.anim.fadeReplaceWithView
import com.example.loansapp.utils.anim.shake
import com.example.loansapp.utils.closeKeyboard
import javax.inject.Inject

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    @Inject
    lateinit var viewModel: LoginViewModel

    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(layoutInflater, container, false)

        viewModel.state.observe(viewLifecycleOwner) {
            renderState(it)
        }

        //TODO: DELETE THIS TWO STRINGS
        binding.loginNameEditText.setText("string")
        binding.loginPasswordEditText.setText("string")

        binding.loginButton.setOnClickListener {
            it.closeKeyboard()

            viewModel.login(
                binding.loginNameEditText.text.toString(),
                binding.loginPasswordEditText.text.toString()
            )
        }

        return binding.root
    }


    private fun renderState(state: LoginViewState?) {
        when (state) {
            is LoginViewState.SuccessAuthorised -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LoansFragment.newInstance())
                    .commit()
            }
            is LoginViewState.Error -> {
                handleError(state.reason)

                binding.loginProgressBar.fadeReplaceWithView(binding.loginButton)

                if (!binding.loginErrorCard.isVisible) {
                    binding.loginErrorCard.fadeInAndFadeOutOverTime(4000L)
                }
            }

            is LoginViewState.Loading -> {
                binding.loginButton.fadeReplaceWithView(binding.loginProgressBar)
                binding.loginNameField.isErrorEnabled = false
                binding.loginPasswordField.isErrorEnabled = false
            }
        }
    }

    private fun handleError(errorType: ErrorType) {
        when (errorType) {
            ErrorType.NotFound -> {
                binding.loginNameField.apply {
                    shake()
                    error = "Incorrect"
                }
                binding.loginPasswordField.apply {
                    shake()
                    error = "Incorrect"
                }
                binding.loginErrorText.text =
                    resources.getString(R.string.you_entered_your_username_or_password_incorrectly)
            }

            ErrorType.Connection -> binding.loginErrorText.text =
                resources.getString(R.string.check_your_internet_connection)

            ErrorType.InvalidData -> {
                binding.loginNameField.apply {
                    shake()
                    error = "Invalid"
                }
                binding.loginPasswordField.apply {
                    shake()
                    error = "Invalid"
                }
                binding.loginErrorText.text =
                    resources.getString(R.string.name_or_password_is_invalid)
            }

            else -> binding.loginErrorText.text =
                resources.getString(R.string.something_went_wrong_try_later)
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as MyApplication).appComponent.authorizationComponent()
            .create().inject(this)
    }
}