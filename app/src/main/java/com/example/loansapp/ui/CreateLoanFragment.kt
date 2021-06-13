package com.example.loansapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.loansapp.MyApplication
import com.example.loansapp.R
import com.example.loansapp.databinding.CreateLoanFragmentBinding
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

        binding.amountSlider.valueFrom = 1.0f
        binding.amountSlider.valueTo = maxAmount.toFloat()
        binding.amountSlider.value = maxAmount / 2.toFloat()

        binding.maxAmountText.text = resources.getString(R.string.max_amount_template, maxAmount)

        binding.conditionsText.text =
            resources.getString(R.string.conditions_template, percent, period)

        binding.backArrowImage.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as MyApplication).appComponent.loansComponent().create()
            .inject(this)
    }
}