package com.example.loansapp.ui.enter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.loansapp.R
import com.example.loansapp.databinding.EnterFragmentBinding
import com.example.loansapp.presentation.main.EnterViewModel
import com.example.loansapp.utils.transformer.ZoomOutPageTransformer
import com.google.android.material.tabs.TabLayoutMediator


class EnterFragment : Fragment() {

    companion object {
        fun newInstance() = EnterFragment()
        private const val NUM_PAGES = 2
    }

    private lateinit var viewModel: EnterViewModel

    private lateinit var binding: EnterFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = EnterFragmentBinding.inflate(layoutInflater, container, false)

        binding.pager.adapter = ScreenSlidePagerAdapter(this)
        binding.pager.isUserInputEnabled = false
        binding.pager.setPageTransformer(ZoomOutPageTransformer())

        TabLayoutMediator(binding.enterOptionsTabLayout, binding.pager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.login)
                1 -> tab.text = resources.getString(R.string.register)
            }
        }.attach()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EnterViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private inner class ScreenSlidePagerAdapter(f: Fragment) : FragmentStateAdapter(f) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> LoginFragment.newInstance()
                1 -> RegisterFragment.newInstance()
                else -> throw ExceptionInInitializerError("There's no page with that num!")
            }
        }

    }

}