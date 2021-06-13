package com.example.loansapp.ui.loans

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.loansapp.R
import com.example.loansapp.data.network.Loan
import com.example.loansapp.databinding.LoanItemBinding
import com.example.loansapp.utils.getColor
import com.example.loansapp.utils.isEven

class LoansAdapter(private val onClick: (Loan) -> Unit) :
    ListAdapter<Loan, LoanViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LoanItemBinding.inflate(layoutInflater, parent, false)
        return LoanViewHolder(binding, parent.context, onClick)
    }

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DiffCallback : DiffUtil.ItemCallback<Loan>() {
        override fun areItemsTheSame(oldItem: Loan, newItem: Loan): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Loan, newItem: Loan): Boolean =
            oldItem == newItem
    }
}

class LoanViewHolder(
    private val binding: LoanItemBinding,
    private val context: Context,
    private val onClick: (Loan) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("ResourceType")
    fun bind(loan: Loan) {
        binding.amountText.text = context.resources.getString(R.string.amount_template, loan.amount)
        binding.stateText.text = context.resources.getString(R.string.state_template, loan.state)

        binding.root.setOnClickListener {
            onClick(loan)
        }


        if (layoutPosition.isEven()) {
            binding.root.setCardBackgroundColor(context.theme.getColor(R.attr.colorPrimaryVariant))
        } else {
            binding.root.setCardBackgroundColor(context.theme.getColor(R.attr.colorPrimary))
        }
    }
}