package com.example.doit.view.activities.home.allTasksFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doit.model.firebase.Firebase
import com.example.doit.model.repository.TaskRepository
import com.example.doit.model.roomDB.TaskRdb
import com.example.doit.utils.Internet

class AllTasksViewModel : ViewModel() {
    private val taskRepository = TaskRepository()

    fun getAllTask(): LiveData<List<TaskRdb>> {
        return taskRepository.getAllTaskFromRoomDB
    }

    fun getAllTaskToBeSynced() : LiveData<List<TaskRdb>>{
        return taskRepository.getAllTasksToBeSynced
    }

    fun getAllTasksToBeDeleted() : LiveData<List<TaskRdb>>{
        return taskRepository.getAllTasksToBeDeleted
    }

    fun getAllTasksToBeUpdated() : LiveData<List<TaskRdb>>{
        return taskRepository.getAllTasksToBeUpdated
    }

    fun syncAllTasks(tasks: List<TaskRdb>) {
        if (Internet.hasInternetAccess()) {
            for (task in tasks) {
                Firebase.syncAllTaskToFirestore(task, viewModelScope)
            }
        }
    }

    fun syncAllTasksToBeDeleted(tasks : List<TaskRdb>){
        if(Internet.hasInternetAccess()){
            for(task in tasks){
                Firebase.deleteTask(task, viewModelScope)
            }
        }
    }

    fun syncAllTaskToBeUpdated(tasks : List<TaskRdb>){
        if(Internet.hasInternetAccess()){
            for(task in tasks){
                Firebase.updateTask(task, viewModelScope)
            }
        }
    }

    fun deleteTask(task : TaskRdb){
        taskRepository.deleteTask(task, viewModelScope)
    }
}