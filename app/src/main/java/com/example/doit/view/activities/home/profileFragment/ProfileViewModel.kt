package com.example.doit.view.activities.home.profileFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doit.model.repository.TaskRepository

class ProfileViewModel : ViewModel() {
    private val taskRepository = TaskRepository()

    fun deleteAllTasks(){
        taskRepository.deleteAllData(viewModelScope)
    }
}