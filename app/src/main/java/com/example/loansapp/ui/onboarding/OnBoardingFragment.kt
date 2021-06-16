package com.example.loansapp.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.loansapp.R
import com.example.loansapp.databinding.OnBoardingFragmentBinding
import com.example.loansapp.ui.loans.LoansFragment
import com.example.loansapp.utils.transformer.ZoomOutPageTransformer

class OnBoardingFragment : Fragment() {

    companion object {
        fun newInstance() = OnBoardingFragment()
    }

    private lateinit var binding: OnBoardingFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OnBoardingFragmentBinding.inflate(layoutInflater, container, false)

        val adapter = OnBoardingAdapter()
        binding.onBoardingViewPager.adapter = adapter
        binding.onBoardingViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.onBoardingViewPager.setPageTransformer(ZoomOutPageTransformer())

        adapter.data = getOnBoardingData()

        binding.onBoardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position + 1 == adapter.itemCount) {
                    binding.buttonOnBoardingAction.text = resources.getString(R.string.get_started)
                } else {
                    binding.buttonOnBoardingAction.text = resources.getString(R.string.next)
                }
            }
        })

        binding.buttonOnBoardingAction.setOnClickListener {
            if (binding.onBoardingViewPager.currentItem + 1 < adapter.itemCount) {
                binding.onBoardingViewPager.apply {
                    currentItem += 1
                }
            } else {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LoansFragment.newInstance())
                    .commit()
            }
        }

        return binding.root
    }

    private fun getOnBoardingData(): List<OnBoardingItem> = listOf(
        OnBoardingItem(
            R.drawable.on_boarding_1,
            resources.getString(R.string.on_boarding_title_1),
            resources.getString(R.string.on_boarding_description_1)
        ),
        OnBoardingItem(
            R.drawable.on_boarding_2,
            resources.getString(R.string.on_boarding_title_2),
            resources.getString(R.string.on_boarding_description_2)
        ),
        OnBoardingItem(
            R.drawable.on_boarding_3,
            resources.getString(R.string.on_boarding_title_3),
            resources.getString(R.string.on_boarding_description_3)
        ),
        OnBoardingItem(
            R.drawable.on_boarding_4,
            resources.getString(R.string.on_boarding_title_4),
            resources.getString(R.string.on_boarding_description_4)
        ),
    )

}