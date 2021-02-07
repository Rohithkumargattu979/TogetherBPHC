package com.example.togetherbphc.viewmodel

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.activity_register.*

 class RegistrationViewModel: ViewModel() {
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)
    val now = Calendar.getInstance()
     private lateinit var spinner: String


    fun getSpinner() :String {
        return spinner
    }

     fun setSpinner(spinner :String){
         this.spinner = spinner
     }


}