package com.example.loansapp.ui.loans

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.loansapp.MyApplication
import com.example.loansapp.R
import com.example.loansapp.data.network.Loan
import com.example.loansapp.databinding.LoansFragmentBinding
import com.example.loansapp.presentation.loans.LoansConditionsViewState
import com.example.loansapp.presentation.loans.LoansViewModel
import com.example.loansapp.presentation.loans.LoansViewState
import javax.inject.Inject

class LoansFragment : Fragment() {

    companion object {
        fun newInstance() = LoansFragment()
    }

    @Inject
    lateinit var viewModel: LoansViewModel

    private lateinit var binding: LoansFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoansFragmentBinding.inflate(layoutInflater, container, false)

        val adapter = LoansAdapter(::onLoanClick)

        binding.loansList.adapter = adapter

        viewModel.loansConditionsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoansConditionsViewState.Success -> {
                    binding.percentText.text = resources.getString(
                        R.string.percent_template,
                        state.loansConditions.percent
                    )
                    binding.periodText.text =
                        resources.getString(R.string.period_template, state.loansConditions.period)
                    binding.maxAmountText.text = resources.getString(
                        R.string.max_amount_template,
                        state.loansConditions.maxAmount
                    )
                }
            }
        }

        viewModel.loansState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoansViewState.Success -> {
                    adapter.submitList(state.loans)
                }
            }
        }

        viewModel.getLoans()
        viewModel.getLoansConditions()

        return binding.root
    }

    private fun onLoanClick(loan: Loan) {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as MyApplication).appComponent.loansComponent().create()
            .inject(this)
    }
}