package com.example.quero_anime_app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quero_anime_app.ui.model.remote.ConnectionsHandler
import com.example.quero_anime_app.util.listeners.RetrofitListener
import com.example.quero_anime_app.util.models.ResultModel
import com.google.android.gms.tasks.Task

class ForgotPasswordViewModel(application: Application): AndroidViewModel(application) {

    private val connectionsHandler = ConnectionsHandler()
    private var _resetPasswordResult: MutableLiveData<ResultModel> = MutableLiveData()
    val resetPasswordResult: LiveData<ResultModel> get() = _resetPasswordResult

    fun sendResetPasswordEmail(email: String){
        connectionsHandler.sendResetPasswordEmail(email, object : RetrofitListener<Task<Void>>{
            override fun onSuccess(response: Task<Void>) {
                _resetPasswordResult.value = ResultModel(true, "")
            }

            override fun onError(error: String) {
                _resetPasswordResult.value = ResultModel(false, error)
            }

        })
    }

}