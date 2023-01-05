package com.example.quero_anime_app.ui.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quero_anime_app.R
import com.example.quero_anime_app.databinding.FragmentMainAuthenticationBinding
import com.example.quero_anime_app.ui.model.remote.FirebaseClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainAuthenticationFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentMainAuthenticationBinding? = null
    private val binding get() = _binding!!

    private val firebaseAuth: FirebaseAuth = FirebaseClient.getFirebaseClient()

    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var openActivity: ActivityResultLauncher<Intent>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainAuthenticationBinding.inflate(inflater, container, false)

        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)
        openActivity = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val intent = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                try {
                    val account = task.getResult(ApiException::class.java)
                    loginWithGoogle(account.idToken ?: "")
                } catch (e: ApiException) {
                    Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.emailAuthentication.id -> {
                findNavController().navigate(R.id.mainAuthenticationFragmentToSignInFragment)
            }
            binding.buttonSignupHere.id -> {
                findNavController().navigate(R.id.mainAuthenticationFragmentToSignUpFragment)
            }
            binding.googleAuthentication.id -> {
                googleSignIn()
            }
            binding.buttonSkip.id -> {
                openMainActivity()
            }
        }
    }

    private fun initClicks() {
        binding.emailAuthentication.setOnClickListener(this)
        binding.buttonSignupHere.setOnClickListener(this)
        binding.googleAuthentication.setOnClickListener(this)
        binding.buttonSkip.setOnClickListener(this)
    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        openActivity.launch(signInIntent)
    }

    private fun loginWithGoogle(accountToken: String) {
        val credential = GoogleAuthProvider.getCredential(accountToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "Autenticação efetuada com sucesso! ${firebaseAuth.currentUser}",
                        Toast.LENGTH_SHORT
                    ).show()
                    openMainActivity()
                } else {
                    Toast.makeText(requireContext(), "Erro de autenticação.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun openMainActivity() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }
}