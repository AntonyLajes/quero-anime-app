package com.example.quero_anime_app.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.quero_anime_app.R
import com.example.quero_anime_app.databinding.FragmentSignUpBinding
import com.example.quero_anime_app.ui.viewmodel.SignUpViewModel
import com.example.quero_anime_app.util.models.SignUpModel

class SignUpFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding get() = _binding!!
    private lateinit var viewmodel: SignUpViewModel

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        viewmodel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
        initInputs()
        observers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.buttonBack.id -> {
                findNavController().navigate(R.id.signUpFragmentToSignInFragment)
            }
            binding.buttonSignup.id -> {
                email.isEnabled = false
                password.isEnabled = false
                confirmPassword.isEnabled = false
                if (email.text.toString().isNotEmpty()) {
                    if (password.text.toString().isNotEmpty()) {
                        if (confirmPassword.text.toString().isNotEmpty()) {
                            if (password.text.toString() == confirmPassword.text.toString()) {
                                binding.tvPasswordDontMatch.isVisible = false
                                binding.progressBar.isVisible = true
                                viewmodel.signup(
                                    SignUpModel(
                                        email.text.toString(),
                                        password.text.toString(),
                                        confirmPassword.text.toString()
                                    )
                                )
                            } else {
                                handleInputs()
                                binding.tvPasswordDontMatch.isVisible = true
                            }
                        } else {
                            handleInputs()
                            makeToast(getString(R.string.insert_confirmation_password))
                        }
                    } else {
                        handleInputs()
                        makeToast(getString(R.string.insert_password))
                    }
                } else {
                    handleInputs()
                    makeToast(getString(R.string.hint_email))
                }
            }
        }
    }

    private fun initInputs() {
        email = binding.inputEmail
        password = binding.inputPassword
        confirmPassword = binding.inputConfirmPassword
    }

    private fun initClicks() {
        binding.buttonBack.setOnClickListener(this)
        binding.buttonSignup.setOnClickListener(this)
    }

    private fun observers() {
        viewmodel.signUpResult.observe(viewLifecycleOwner) { signUpResult ->
            binding.progressBar.isVisible = false
            handleInputs()
            if (signUpResult.result) {
                makeToast(getString(R.string.account_created))
                findNavController().navigate(R.id.signUpFragmentToSignInFragment)
            } else {
                makeToast(signUpResult.message)
            }
        }
    }

    private fun makeToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun handleInputs(){
        email.isEnabled = true
        password.isEnabled = true
        confirmPassword.isEnabled = true
    }
}