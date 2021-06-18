package com.example.loansapp.ui.authorization

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.loansapp.MyApplication
import com.example.loansapp.R
import com.example.loansapp.databinding.RegisterFragmentBinding
import com.example.loansapp.domain.entity.ErrorType
import com.example.loansapp.presentation.authorization.registration.RegistrationViewModel
import com.example.loansapp.presentation.authorization.registration.RegistrationViewState
import com.example.loansapp.utils.anim.fadeInAndFadeOutOverTime
import com.example.loansapp.utils.anim.fadeReplaceWithView
import com.example.loansapp.utils.anim.shake
import com.example.loansapp.utils.closeKeyboard
import javax.inject.Inject

class RegistrationFragment : Fragment() {

    companion object {
        fun newInstance() = RegistrationFragment()
    }

    @Inject
    lateinit var viewModel: RegistrationViewModel

    private lateinit var binding: RegisterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RegisterFragmentBinding.inflate(layoutInflater, container, false)

        viewModel.state.observe(viewLifecycleOwner) {
            renderState(it)
        }

        binding.registerButton.setOnClickListener {
            it.closeKeyboard()

            viewModel.register(
                binding.registerNameEditText.text.toString(),
                binding.registerPasswordEditText.text.toString()
            )
        }

        return binding.root
    }

    private fun renderState(state: RegistrationViewState?) {
        when (state) {
            is RegistrationViewState.SuccessRegistered -> {
                with(binding) {
                    registerSuccessText.text =
                        resources.getString(R.string.you_successfully_registered)

                    registerProgressBar.fadeReplaceWithView(binding.registerButton)

                    registerButton.isEnabled = false

                    registerSuccessCard.fadeInAndFadeOutOverTime(4000L)
                }
            }

            is RegistrationViewState.Error -> {
                handleError(state.reason)

                binding.registerProgressBar.fadeReplaceWithView(binding.registerButton)

                if (!binding.registerErrorCard.isVisible) {
                    binding.registerErrorCard.fadeInAndFadeOutOverTime(4000L)
                }
            }

            is RegistrationViewState.Loading -> {
                with(binding) {
                    registerNameField.isErrorEnabled = false
                    registerPasswordField.isErrorEnabled = false
                    registerButton.fadeReplaceWithView(binding.registerProgressBar)
                }
            }
        }
    }

    private fun handleError(errorType: ErrorType) {
        when (errorType) {
            ErrorType.Connection -> binding.registerErrorText.text =
                resources.getString(R.string.check_your_internet_connection)

            ErrorType.InvalidData -> {
                binding.registerNameField.apply {
                    shake()
                    error = "Incorrect"
                }
                binding.registerPasswordField.apply {
                    shake()
                    error = "Incorrect"
                }
                binding.registerErrorText.text =
                    resources.getString(R.string.name_or_password_is_invalid)
            }

            ErrorType.Network -> {
                binding.registerNameField.apply {
                    shake()
                    error = "Already exist"
                }
                binding.registerErrorText.text =
                    resources.getString(R.string.user_with_the_same_data_already_exist)
            }

            else -> binding.registerErrorText.text =
                resources.getString(R.string.something_went_wrong_try_later)
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as MyApplication).appComponent.authorizationComponent()
            .create().inject(this)
    }
}