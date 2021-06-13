package com.example.loansapp.ui.createloan

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.loansapp.MyApplication
import com.example.loansapp.R
import com.example.loansapp.data.network.Loan
import com.example.loansapp.databinding.CreateLoanFragmentBinding
import com.example.loansapp.domain.entity.ErrorType
import com.example.loansapp.domain.entity.NewLoan
import com.example.loansapp.presentation.createloan.CreateLoanViewModel
import com.example.loansapp.presentation.createloan.CreateLoanViewState
import com.example.loansapp.utils.anim.fadeInAndFadeOutOverTime
import com.example.loansapp.utils.anim.fadeReplaceWithView
import com.example.loansapp.utils.anim.shake
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject
import kotlin.properties.Delegates


class CreateLoanFragment : Fragment() {

    companion object {
        private const val PERCENT_PARAM = "param1"
        private const val PERIOD_PARAM = "param2"
        private const val MAX_AMOUNT_PARAM = "param3"

        fun newInstance(percent: Double, period: Int, maxAmount: Int) =
            CreateLoanFragment().apply {
                arguments = Bundle().apply {
                    putDouble(PERCENT_PARAM, percent)
                    putInt(PERIOD_PARAM, period)
                    putInt(MAX_AMOUNT_PARAM, maxAmount)
                }
            }
    }

    @Inject
    lateinit var viewModel: CreateLoanViewModel

    lateinit var binding: CreateLoanFragmentBinding

    private var percent by Delegates.notNull<Double>()
    private var period by Delegates.notNull<Int>()
    private var maxAmount by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            percent = it.getDouble(PERCENT_PARAM)
            period = it.getInt(PERIOD_PARAM)
            maxAmount = it.getInt(MAX_AMOUNT_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CreateLoanFragmentBinding.inflate(layoutInflater, container, false)
        setViewProperties()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            renderState(state)
        }

        return binding.root
    }

    private fun renderState(state: CreateLoanViewState) {
        when (state) {
            is CreateLoanViewState.Loading -> {
                binding.createLoanButton.fadeReplaceWithView(binding.progressBar)
            }

            is CreateLoanViewState.Success -> {
                binding.createLoanButton.isEnabled = false
                binding.progressBar.fadeReplaceWithView(binding.createLoanButton)
                createLoanInformationDialog(state.loan)
            }
            is CreateLoanViewState.Error -> {
                handleError(state.reason)

                binding.progressBar.fadeReplaceWithView(binding.createLoanButton)

                if (!binding.createLoanErrorCard.isVisible) {
                    binding.createLoanErrorCard.fadeInAndFadeOutOverTime(4000L)
                }
            }
        }
    }

    private fun handleError(reason: ErrorType) {
        when (reason) {
            is ErrorType.Connection -> binding.createLoanErrorText.text =
                resources.getString(R.string.check_your_internet_connection)

            is ErrorType.InvalidData -> {
                binding.nameField.apply {
                    shake()
                    error = "Invalid"
                }
                binding.lastNameField.apply {
                    shake()
                    error = "Invalid"
                }
                binding.phoneNumberField.apply {
                    shake()
                    error = "Invalid"
                }

                binding.createLoanErrorText.text =
                    resources.getString(R.string.name_or_last_name_or_phone_number_is_invalid)
            }

            else -> binding.createLoanErrorText.text =
                resources.getString(R.string.something_went_wrong_try_later)
        }
    }

    private fun setViewProperties() {
        binding.amountSlider.valueFrom = 1.0f
        binding.amountSlider.valueTo = maxAmount.toFloat()
        binding.amountSlider.value = maxAmount / 2.toFloat()

        binding.maxAmountText.text = resources.getString(R.string.max_amount_template, maxAmount)

        binding.conditionsText.text =
            resources.getString(R.string.conditions_template, percent, period)

        binding.backArrowImage.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.createLoanButton.setOnClickListener {
            createAcceptDialog()
        }
    }

    private fun createLoanInformationDialog(loan: Loan) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.your_loan_created))
            .setMessage(
                resources.getString(
                    R.string.loan_created_information_template,
                    loan.id,
                    loan.firstName,
                    loan.lastName,
                    loan.phoneNumber,
                    loan.amount.toInt(),
                    loan.percent,
                    loan.period,
                    loan.date,
                    loan.state
                )
            )
            .setPositiveButton(resources.getString(R.string.excellent)) { dialog, _ ->
                dialog.cancel()
            }
            .setOnCancelListener {
                requireActivity().onBackPressed()
            }
            .show()
    }

    private fun createAcceptDialog() {
        val newLoan = buildNewLoan()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.are_you_sure))
            .setMessage(
                resources.getString(
                    R.string.accept_loan_creation_information_template,
                    newLoan.firstName,
                    newLoan.lastName,
                    newLoan.phoneNumber,
                    newLoan.amount,
                    newLoan.percent,
                    newLoan.period
                )
            )
            .setPositiveButton(resources.getString(R.string.yes)) { dialog, _ ->
                viewModel.createLoan(newLoan)
                dialog.cancel()
            }
            .setNegativeButton(resources.getString(R.string.no)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun buildNewLoan(): NewLoan =
        NewLoan(
            binding.nameEditText.text.toString(),
            binding.lastNameEditText.text.toString(),
            binding.amountSlider.value.toInt(),
            percent,
            binding.phoneNumberEditText.text.toString(),
            period
        )

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as MyApplication).appComponent.loansComponent().create()
            .inject(this)
    }
}