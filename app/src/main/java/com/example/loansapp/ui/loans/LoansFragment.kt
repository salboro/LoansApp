package com.example.loansapp.ui.loans

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.loansapp.MyApplication
import com.example.loansapp.R
import com.example.loansapp.data.network.Loan
import com.example.loansapp.databinding.LoansFragmentBinding
import com.example.loansapp.domain.entity.ErrorType
import com.example.loansapp.presentation.loans.LoansConditionsViewState
import com.example.loansapp.presentation.loans.LoansViewModel
import com.example.loansapp.presentation.loans.LoansViewState
import com.example.loansapp.ui.OnSwipeTouchListener
import com.example.loansapp.ui.authorization.EnterFragment
import com.example.loansapp.ui.createloan.CreateLoanFragment
import com.example.loansapp.utils.anim.disappearInLeftComeFromRight
import com.example.loansapp.utils.anim.disappearInRightComeFromLeft
import com.example.loansapp.utils.anim.yScaleInAndFadeIn
import com.example.loansapp.utils.anim.yScaleOutAndFadeOut
import com.example.loansapp.utils.getColor
import com.example.loansapp.utils.getResourcesLoanState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
            renderConditionsState(state)
        }

        viewModel.loansState.observe(viewLifecycleOwner) { state ->
            renderLoansState(state, adapter)
        }

        setRecyclerViewScrollListener()
        handleToolbarMenuActions()
        loadLoansData()

        return binding.root
    }

    private fun loadLoansData() {
        viewModel.getLoans()
        viewModel.getLoansConditions()
    }

    private fun handleToolbarMenuActions() {
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_refresh -> loadLoansData()

                R.id.action_logout -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.container, EnterFragment.newInstance())
                        .commit()
                }

                R.id.action_change_theme -> changeTheme()
            }
            true
        }
    }

    private fun renderLoansState(state: LoansViewState?, adapter: LoansAdapter) {
        when (state) {
            is LoansViewState.Success -> {
                if (state.loans.isEmpty()) {
                    binding.onEmptyListCard.isVisible = true
                    binding.loansList.isVisible = false
                } else {
                    binding.onEmptyListCard.isVisible = false
                    binding.loansList.isVisible = true
                }

                binding.onErrorListCard.isVisible = false
                binding.loansListProgressBar.isVisible = false
                adapter.submitList(state.loans)
            }

            is LoansViewState.Error -> {
                handleLoansError(state.reason)

                binding.loansListProgressBar.isVisible = false
                binding.onErrorListCard.isVisible = true
            }

            is LoansViewState.Loading -> {
                binding.loansList.isInvisible = true
                binding.loansListProgressBar.isVisible = true
            }
        }
    }

    private fun handleLoansError(reason: ErrorType) {
        when (reason) {
            ErrorType.Connection -> binding.onErrorListText.text =
                resources.getString(R.string.check_your_internet_connection)

            ErrorType.AccessDenied -> binding.onErrorListText.text =
                resources.getString(R.string.trouble_with_authentication_try_to_reenter)

            else -> binding.onErrorListText.text =
                resources.getString(R.string.something_went_wrong_try_later)
        }
    }

    private fun renderConditionsState(state: LoansConditionsViewState?) {
        when (state) {
            is LoansConditionsViewState.Success -> {
                binding.loansConditionsCard.setCardBackgroundColor(requireContext().theme.getColor(R.attr.colorSurface))
                binding.conditionsErrorText.isVisible = false
                binding.conditionsLayout.isVisible = true
                binding.loansConditionsProgressBar.isVisible = false
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

            is LoansConditionsViewState.Error -> {
                handleLoansConditionsError(state.reason)

                binding.loansConditionsCard.setCardBackgroundColor(requireContext().theme.getColor(R.attr.colorError))
                binding.loansConditionsProgressBar.isVisible = false
                binding.conditionsErrorText.isVisible = true
            }

            is LoansConditionsViewState.Loading -> {
                binding.conditionsErrorText.isVisible = false
                binding.conditionsLayout.isInvisible = true
                binding.loansConditionsProgressBar.isVisible = true
            }
        }
    }

    private fun handleLoansConditionsError(reason: ErrorType) {
        when (reason) {
            ErrorType.Connection -> binding.conditionsErrorText.text =
                resources.getString(R.string.check_your_internet_connection)

            ErrorType.AccessDenied -> binding.conditionsErrorText.text =
                resources.getString(R.string.trouble_with_authentication_try_to_reenter)

            else -> binding.conditionsErrorText.text =
                resources.getString(R.string.something_went_wrong_try_later)
        }

    }

    private fun changeTheme() {
        if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
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
                binding.floatingActionButton.setOnClickListener {
                    recyclerView.smoothScrollToPosition(0)
                }

                if (RecyclerView.SCROLL_STATE_SETTLING == newState) {
                    if (y >= 1) {
                        binding.loansConditionsCard.yScaleOutAndFadeOut(300L)
                        binding.floatingActionButton.isVisible = true
                        y = 0
                    } else if (recyclerView.layoutManager?.findViewByPosition(0) != null) {
                        binding.loansConditionsCard.yScaleInAndFadeIn(300L)
                        binding.floatingActionButton.isVisible = false
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                y = dy
            }
        })
    }

    private fun onLoanClick(loan: Loan) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.loan))
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
                    loan.state.getResourcesLoanState(requireContext())
                )
            )
            .setPositiveButton(resources.getString(R.string.i_see)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as MyApplication).appComponent.loansComponent().create()
            .inject(this)
    }
}