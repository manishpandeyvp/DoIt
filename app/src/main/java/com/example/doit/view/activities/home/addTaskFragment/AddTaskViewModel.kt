package com.example.doit.view.activities.home.addTaskFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doit.model.firebase.Firebase
import com.example.doit.model.repository.TaskRepository
import com.example.doit.model.roomDB.TaskRdb
import com.example.doit.utils.Internet
import kotlinx.coroutines.launch

class AddTaskViewModel : ViewModel() {
    private val taskRepository = TaskRepository()

    fun addTask(title: String, desc: String) {
        viewModelScope.launch {
            if (Internet.hasInternetAccess()) {
                Firebase.addTaskToFirestore(title, desc, viewModelScope)
            } else {
                val taskRdb = TaskRdb(0, "", title, desc, false)
                taskRepository.addTaskToRoomDb(taskRdb, viewModelScope)
            }
        }
    }
}