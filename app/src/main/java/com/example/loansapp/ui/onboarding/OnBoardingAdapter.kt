package com.example.loansapp.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loansapp.databinding.OnBoardingItemBinding

class OnBoardingAdapter : RecyclerView.Adapter<OnBoardingViewHolder>() {

    var data: List<OnBoardingItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = OnBoardingItemBinding.inflate(layoutInflater, parent, false)
        return OnBoardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.count()
}

class OnBoardingViewHolder(
    private val binding: OnBoardingItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(onBoardingItem: OnBoardingItem) {
        with(binding) {
            imageOnBoarding.setImageResource(onBoardingItem.image)
            textDescription.text = onBoardingItem.description
            textTitle.text = onBoardingItem.title
        }
    }
}