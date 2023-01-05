package com.example.quero_anime_app.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.quero_anime_app.R
import com.example.quero_anime_app.databinding.FragmentSignInBinding
import com.example.quero_anime_app.ui.viewmodel.HomeViewModel
import com.example.quero_anime_app.ui.viewmodel.SignInViewModel
import com.example.quero_anime_app.util.models.SignInModel

class SignInFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSignInBinding? = null
    private val binding: FragmentSignInBinding get() = _binding!!
    private lateinit var viewmodel: SignInViewModel
    private lateinit var email: EditText
    private lateinit var password: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        viewmodel = ViewModelProvider(this).get(SignInViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
        observers()
        email = binding.inputEmail
        password = binding.inputPassword
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.buttonBack.id -> {
                findNavController().navigate(R.id.signInFragmentToMainAuthenticationFragment)
            }
            binding.buttonSignupHere.id -> {
                findNavController().navigate(R.id.signInFragmentToSignUpFragment)
            }
            binding.buttonForgotPassword.id -> {
                findNavController().navigate(R.id.signInFragmentToForgotPasswordFragment)
            }
            binding.buttonForgotPassword.id -> {
                findNavController().navigate(R.id.signInFragmentToForgotPasswordFragment)
            }
            binding.buttonLogin.id -> {
                if (email.text.toString().isNotEmpty()) {
                    if (password.text.toString().isNotEmpty()) {
                        binding.inputEmail.isEnabled = false
                        binding.inputPassword.isEnabled = false
                        binding.progressBar.isVisible = true
                        viewmodel.login(
                            SignInModel(
                                email.text.toString(),
                                password.text.toString()
                            )
                        )
                    } else {
                        Toast.makeText(context, getString(R.string.insert_password), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, getString(R.string.hint_email), Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun initClicks() {
        binding.buttonBack.setOnClickListener(this)
        binding.buttonSignupHere.setOnClickListener(this)
        binding.buttonForgotPassword.setOnClickListener(this)
        binding.buttonLogin.setOnClickListener(this)
    }

    private fun observers() {
        viewmodel.loginResult.observe(viewLifecycleOwner) { loginResult ->
            email.isEnabled = true
            password.isEnabled = true
            binding.progressBar.isVisible = false

            if (loginResult.result) {
                val intent = Intent(context, MainActivity::class.java)
                requireActivity().finish()
                startActivity(intent)
            } else {
                Toast.makeText(context, loginResult.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun makeToast(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}