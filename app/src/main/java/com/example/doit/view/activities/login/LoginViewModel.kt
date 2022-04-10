package com.example.doit.view.activities.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doit.model.firebase.Firebase

class LoginViewModel : ViewModel() {

    fun getAllTasksFromFirebase() {
        Firebase.getAllTasks(viewModelScope)
    }

}