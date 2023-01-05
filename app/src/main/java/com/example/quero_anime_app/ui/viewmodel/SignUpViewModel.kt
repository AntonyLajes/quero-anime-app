package com.example.quero_anime_app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quero_anime_app.ui.model.remote.ConnectionsHandler
import com.example.quero_anime_app.util.listeners.RetrofitListener
import com.example.quero_anime_app.util.models.ResultModel
import com.example.quero_anime_app.util.models.SignUpModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class SignUpViewModel(application: Application): AndroidViewModel(application){

    private val connectionsHandler = ConnectionsHandler()
    private var _signUpResult: MutableLiveData<ResultModel> = MutableLiveData()
    val signUpResult: LiveData<ResultModel> get() = _signUpResult


    fun signup(signupData: SignUpModel){
        connectionsHandler.signupHandler(signupData, object : RetrofitListener<Task<AuthResult>>{
            override fun onSuccess(response: Task<AuthResult>) {
                _signUpResult.value = ResultModel(true, "")
            }

            override fun onError(error: String) {
                _signUpResult.value = ResultModel(false, error)
            }
        })
    }

}