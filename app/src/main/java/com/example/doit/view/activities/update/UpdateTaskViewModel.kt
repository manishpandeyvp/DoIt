package com.example.doit.view.activities.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doit.model.repository.TaskRepository
import com.example.doit.model.roomDB.TaskRdb

class UpdateTaskViewModel : ViewModel() {
    private val taskRepository = TaskRepository()

    fun updateTask(task : TaskRdb){
        taskRepository.updateTask(task, viewModelScope)
    }
}