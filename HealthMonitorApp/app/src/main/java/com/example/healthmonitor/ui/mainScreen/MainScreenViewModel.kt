package com.example.healthmonitor.ui.mainScreen


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.healthmonitor.network.Response.ResponseData
import kotlinx.coroutines.*
import shubhamji.com.newspaper.network.Api

class MainScreenViewModel  : ViewModel() {
    private var viewModelJob = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val _response=MutableLiveData<ResponseData?>()
    val response
    get() = _response
    fun getSensorData(){
        uiScope.launch(Dispatchers.IO) {
            try{
                val resp= Api.retrifitService.getData().await()
                withContext(Dispatchers.Main){
                    _response.value=resp
                }
            }catch (e: Exception){

            }

        }
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}