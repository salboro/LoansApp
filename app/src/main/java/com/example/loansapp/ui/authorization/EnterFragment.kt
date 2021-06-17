package com.example.loansapp.ui.authorization

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.loansapp.MyApplication
import com.example.loansapp.R
import com.example.loansapp.databinding.EnterFragmentBinding
import com.example.loansapp.presentation.authorization.EnterViewModel
import com.example.loansapp.utils.LocaleManager
import com.example.loansapp.utils.transformer.ZoomOutPageTransformer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject


class EnterFragment : Fragment() {

    companion object {
        fun newInstance() = EnterFragment()
        private const val NUM_PAGES = 2
    }

    @Inject
    lateinit var viewModel: EnterViewModel

    private lateinit var binding: EnterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EnterFragmentBinding.inflate(layoutInflater, container, false)

        binding.pager.adapter = ScreenSlidePagerAdapter(this)
        binding.pager.isUserInputEnabled = false
        binding.pager.setPageTransformer(ZoomOutPageTransformer())

        binding.changeLanguageCard.setOnClickListener {
            createSelectLanguageDialog()
        }

        TabLayoutMediator(binding.enterOptionsTabLayout, binding.pager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.login)
                1 -> tab.text = resources.getString(R.string.register)
            }
        }.attach()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as MyApplication).appComponent.authorizationComponent()
            .create().inject(this)
    }

    private inner class ScreenSlidePagerAdapter(f: Fragment) : FragmentStateAdapter(f) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> LoginFragment.newInstance()
                1 -> RegistrationFragment.newInstance()
                else -> throw ExceptionInInitializerError("There's no page with that num!")
            }
        }

    }

    private fun createSelectLanguageDialog() {
        val checkedItem = 0
        val dialogItems = arrayOf(
            resources.getString(R.string.english),
            resources.getString(R.string.russian)
        )
        var langCode = ""

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.change_language)
            .setNeutralButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(R.string.change) { dialog, _ ->
                viewModel.setUserLocale(langCode)
                dialog.cancel()
                requireActivity().recreate()
            }
            .setSingleChoiceItems(dialogItems, checkedItem) { _, which ->
                langCode = if (which == 0) {
                    LocaleManager.ENGLISH_LANGUAGE_CODE
                } else {
                    LocaleManager.RUSSIAN_LANGUAGE_CODE
                }
            }
            .show()
    }

}