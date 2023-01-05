package com.example.quero_anime_app.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.example.quero_anime_app.R
import com.example.quero_anime_app.databinding.FragmentForgotPasswordBinding
import com.example.quero_anime_app.databinding.FragmentMainAuthenticationBinding
import com.example.quero_anime_app.ui.viewmodel.ForgotPasswordViewModel

class ForgotPasswordFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewmodel: ForgotPasswordViewModel
    private lateinit var email: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        viewmodel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
        observers()
        email = binding.inputEmail
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View) {
        when(view.id){
            binding.buttonBack.id -> {
                findNavController().navigate(R.id.forgotPasswordFragmentToSignInFragment)
            }
            binding.buttonSendResetPassword.id -> {
                if(email.text.toString().isNotEmpty()){
                    viewmodel.sendResetPasswordEmail(email.text.toString())
                }else{
                    makeToast(getString(R.string.hint_email))
                }
            }
        }
    }

    private fun initClicks(){
        binding.buttonBack.setOnClickListener(this)
        binding.buttonSendResetPassword.setOnClickListener(this)
    }

    private fun observers(){
        viewmodel.resetPasswordResult.observe(viewLifecycleOwner){ resetPasswordResult ->
            if(resetPasswordResult.result){
                makeToast(getString(R.string.send_email))
                findNavController().navigate(R.id.forgotPasswordFragmentToSignInFragment)
            }else{
                makeToast(resetPasswordResult.message)
            }
        }
    }

    private fun makeToast(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}