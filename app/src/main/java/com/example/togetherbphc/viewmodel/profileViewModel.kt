package com.example.togetherbphc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.togetherbphc.model.UserModel
import com.example.togetherbphc.model.repository.RegRepo
import com.example.togetherbphc.model.repository.RegRepo.StaticFunction.getInstance

class profileViewModel: ViewModel() {
    var reg:RegRepo =RegRepo()
    fun getUser(): LiveData<UserModel> {
        return reg.getUser()
    }
    fun updateName(userName: String?) {
        reg.updateName(userName!!)
    }

    fun updateImage(imagePath: String) {
        reg.updateImage(imagePath)
    }
    fun updateGender(gender: String)
    {
        reg.updateGender(gender)
    }

    fun updateClgID(userCLgID:String)
    {
        reg.updateClgID(userCLgID)
    }
    fun updateDOB(userDOB:String)
    {
        reg.updateDOB(userDOB)
    }
}