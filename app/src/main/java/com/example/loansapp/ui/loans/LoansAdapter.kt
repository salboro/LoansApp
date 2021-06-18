package com.example.loansapp.ui.loans

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loansapp.R
import com.example.loansapp.data.network.Loan
import com.example.loansapp.databinding.LoanItemBinding
import com.example.loansapp.utils.getColor
import com.example.loansapp.utils.getResourcesLoanState
import com.example.loansapp.utils.isEven

class LoansAdapter(
    private val onClick: (Loan) -> Unit
) : RecyclerView.Adapter<LoanViewHolder>() {

    var data: List<Loan> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LoanItemBinding.inflate(layoutInflater, parent, false)
        return LoanViewHolder(binding, parent.context, onClick)
    }

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.count()
}

class LoanViewHolder(
    private val binding: LoanItemBinding,
    private val context: Context,
    private val onClick: (Loan) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("ResourceType")
    fun bind(loan: Loan) {
        with(binding) {
            loanItemAmountText.text =
                context.resources.getString(R.string.amount_template, loan.amount)
            stateText.text = loan.state.getResourcesLoanState(context)
            itemPositionText.text = layoutPosition.plus(1).toString()
        }

        binding.root.setOnClickListener {
            onClick(loan)
        }

        if (layoutPosition.isEven()) {
            binding.root.setCardBackgroundColor(context.theme.getColor(R.attr.colorPrimarySurface))
        } else {
            binding.root.setCardBackgroundColor(context.theme.getColor(R.attr.colorSurface))
        }
    }
}