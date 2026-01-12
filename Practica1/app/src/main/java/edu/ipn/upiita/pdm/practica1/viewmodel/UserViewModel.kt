package edu.ipn.upiita.pdm.practica1.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.ipn.upiita.pdm.practica1.model.UserModel

class UserViewModel : ViewModel(){
    companion object {
        var userModelMutableList = MutableLiveData<UserModel>()

    }
}