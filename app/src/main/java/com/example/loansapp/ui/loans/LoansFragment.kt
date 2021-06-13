package com.example.loansapp.ui.loans

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.loansapp.MyApplication
import com.example.loansapp.R
import com.example.loansapp.data.network.Loan
import com.example.loansapp.databinding.LoansFragmentBinding
import com.example.loansapp.presentation.loans.LoansConditionsViewState
import com.example.loansapp.presentation.loans.LoansViewModel
import com.example.loansapp.presentation.loans.LoansViewState
import com.example.loansapp.ui.CreateLoanFragment
import com.example.loansapp.ui.OnSwipeTouchListener
import com.example.loansapp.utils.anim.disappearInLeftComeFromRight
import com.example.loansapp.utils.anim.disappearInRightComeFromLeft
import com.example.loansapp.utils.anim.yScaleInAndFadeIn
import com.example.loansapp.utils.anim.yScaleOutAndFadeOut
import javax.inject.Inject


class LoansFragment : Fragment() {

    companion object {
        fun newInstance() = LoansFragment()
    }

    @Inject
    lateinit var viewModel: LoansViewModel

    private lateinit var binding: LoansFragmentBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoansFragmentBinding.inflate(layoutInflater, container, false)

        val adapter = LoansAdapter(::onLoanClick)

        binding.loansList.adapter = adapter
        binding.loansConditionsCard.setOnTouchListener(
            OnSwipeTouchListener(
                requireContext(),
                ::loansConditionsOnRightSwipe,
                ::loansConditionsOnLeftSwipe,
                ::loansConditionsOnClick
            )
        )


        viewModel.loansConditionsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoansConditionsViewState.Success -> {
                    binding.creatLoanProgressBar.isVisible = false
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

                is LoansConditionsViewState.Loading -> {
                    binding.creatLoanProgressBar.isVisible = true
                }
            }
        }

        viewModel.loansState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoansViewState.Success -> {
                    binding.loansListProgressBar.isVisible = false
                    adapter.submitList(state.loans)
                }

                is LoansViewState.Loading -> {
                    binding.loansListProgressBar.isVisible = true
                }
            }
        }

        setRecyclerViewScrollListener()
        viewModel.getLoans()
        viewModel.getLoansConditions()

        return binding.root
    }

    private fun loansConditionsOnLeftSwipe() {
        binding.loansConditionsCard.disappearInLeftComeFromRight()
        viewModel.getLoansConditions()
    }

    private fun loansConditionsOnRightSwipe() {
        binding.loansConditionsCard.disappearInRightComeFromLeft()
        viewModel.getLoansConditions()
    }

    private fun loansConditionsOnClick() {
        if (viewModel.loansConditionsState.value is LoansConditionsViewState.Success) {
            val state = viewModel.loansConditionsState.value as LoansConditionsViewState.Success

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    CreateLoanFragment.newInstance(
                        state.loansConditions.percent,
                        state.loansConditions.period,
                        state.loansConditions.maxAmount
                    )
                )
                .addToBackStack(this.toString())
                .commit()
        }
    }

    private fun setRecyclerViewScrollListener() {
        var y = 0
        binding.loansList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (RecyclerView.SCROLL_STATE_SETTLING == newState) {
                    if (y >= 0) {
                        binding.loansConditionsCard.yScaleOutAndFadeOut(300L)
                    } else {
                        y = 0
                        binding.loansConditionsCard.yScaleInAndFadeIn(300L)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                y = dy
            }
        })
    }

    private fun onLoanClick(loan: Loan) {
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as MyApplication).appComponent.loansComponent().create()
            .inject(this)
    }
}