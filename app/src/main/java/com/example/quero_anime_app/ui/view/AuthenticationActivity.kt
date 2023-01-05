package com.example.quero_anime_app.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quero_anime_app.databinding.ActivityAuthenticationBinding
import com.example.quero_anime_app.ui.model.remote.FirebaseClient

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding
    private val firebaseClient = FirebaseClient.getFirebaseClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        if(firebaseClient.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        setContentView(binding.root)
    }

    override fun onStop() {
        super.onStop()
       //finish()
    }
}