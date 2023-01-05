package com.example.quero_anime_app.ui.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import com.example.quero_anime_app.R
import com.example.quero_anime_app.ui.model.remote.FirebaseClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainAuthenticationViewModel(application: Application) : AndroidViewModel(application) {
}