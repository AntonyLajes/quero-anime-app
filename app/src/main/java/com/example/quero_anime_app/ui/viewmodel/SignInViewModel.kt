package com.example.quero_anime_app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quero_anime_app.ui.model.remote.ConnectionsHandler
import com.example.quero_anime_app.util.listeners.RetrofitListener
import com.example.quero_anime_app.util.models.SignInModel
import com.example.quero_anime_app.util.models.ResultModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class SignInViewModel(application: Application): AndroidViewModel(application){

    private val connectionsHandler = ConnectionsHandler()
    private var _loginResult: MutableLiveData<ResultModel> = MutableLiveData()
    val loginResult: LiveData<ResultModel> get() = _loginResult


    fun login(loginData: SignInModel){
        connectionsHandler.loginHandler(loginData, object : RetrofitListener<Task<AuthResult>>{
            override fun onSuccess(response: Task<AuthResult>) {
                _loginResult.value = ResultModel(true, "")
            }

            override fun onError(error: String) {
                _loginResult.value = ResultModel(false, error)
            }

        })
    }

}