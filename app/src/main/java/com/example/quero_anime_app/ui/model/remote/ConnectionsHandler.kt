package com.example.quero_anime_app.ui.model.remote

import com.example.quero_anime_app.util.listeners.RetrofitListener
import com.example.quero_anime_app.util.models.SignInModel
import com.example.quero_anime_app.util.models.SignUpModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class ConnectionsHandler {

    private val auth: FirebaseAuth = FirebaseClient.getFirebaseClient()

    fun loginHandler(loginData: SignInModel, listener: RetrofitListener<Task<AuthResult>>) {
        auth.signInWithEmailAndPassword(loginData.email, loginData.password).addOnCompleteListener { login ->
            if(login.isSuccessful){
                listener.onSuccess(login)
            }else{
                listener.onError(login.exception?.message ?: "")
            }
        }
    }

    fun signupHandler(signUpData: SignUpModel, listener: RetrofitListener<Task<AuthResult>>){
        auth.createUserWithEmailAndPassword(signUpData.email, signUpData.password).addOnCompleteListener { signup ->
            if(signup.isSuccessful){
                listener.onSuccess(signup)
            }else{
                listener.onError(signup.exception?.message ?: "")
            }
        }
    }

    fun sendResetPasswordEmail(email: String, listener: RetrofitListener<Task<Void>>){
        auth.sendPasswordResetEmail(email).addOnCompleteListener { resetPassword ->
            if(resetPassword.isSuccessful){
                listener.onSuccess(resetPassword)
            }else{
                listener.onError(resetPassword.exception?.message ?: "")
            }
        }
    }

}