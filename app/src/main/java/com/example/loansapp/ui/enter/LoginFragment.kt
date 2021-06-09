package com.example.loansapp.ui.enter

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.loansapp.MyApplication
import com.example.loansapp.databinding.LoginFragmentBinding
import com.example.loansapp.presentation.enter.login.LoginViewModel
import com.example.loansapp.presentation.enter.login.LoginViewState
import com.example.loansapp.utils.anim.changeButtonWithProgressBar
import java.lang.Exception
import javax.inject.Inject

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    @Inject
    lateinit var viewModel: LoginViewModel

    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(layoutInflater, container, false)

        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is LoginViewState.SuccessAuthorised -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                }
                is LoginViewState.Error -> {
                    Toast.makeText(requireContext(), it.reason.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.loginButton.setOnClickListener {
            viewModel.login("string", "string")
        }


        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as MyApplication).appComponent.inject(this)
    }
}