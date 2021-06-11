package com.example.loansapp.ui.loans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.loansapp.databinding.LoansFragmentBinding

class LoansFragment : Fragment() {

    companion object {
        fun newInstance() = LoansFragment()
    }

    private lateinit var viewModel: LoansViewModel

    private lateinit var binding: LoansFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoansFragmentBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoansViewModel::class.java)
        // TODO: Use the ViewModel
    }

}