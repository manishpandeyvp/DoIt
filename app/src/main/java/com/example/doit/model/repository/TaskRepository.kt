package com.example.doit.model.repository

import androidx.lifecycle.LiveData
import com.example.doit.MyApplication
import com.example.doit.model.firebase.Firebase
import com.example.doit.model.roomDB.TaskDB
import com.example.doit.model.roomDB.TaskDao
import com.example.doit.model.roomDB.TaskRdb
import com.example.doit.utils.Internet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TaskRepository {
    private val taskDao: TaskDao = TaskDB.getDatabase(MyApplication.getContext()).taskDao()

    val getAllTaskFromRoomDB: LiveData<List<TaskRdb>> = taskDao.getAllTasks(false)
    val getAllTasksToBeSynced: LiveData<List<TaskRdb>> = taskDao.getTaskToBeSynced(false)
    val getAllTasksToBeDeleted: LiveData<List<TaskRdb>> = taskDao.getAllTasks(true)
    val getAllTasksToBeUpdated : LiveData<List<TaskRdb>> = taskDao.getTaskToBeUpdated(true)

    fun addTaskToRoomDb(task: TaskRdb, scope: CoroutineScope) {
        scope.launch {
            taskDao.addTask(task)
        }
    }

    fun updateTask(task: TaskRdb, scope: CoroutineScope) {
        scope.launch {
            taskDao.updateTask(task)
            if (task.isSynced && task.taskId != "") {
                if(Internet.hasInternetAccess()){
                    Firebase.updateTask(task, scope)
                }else{
                    val mTask = TaskRdb(task.id, task.taskId, task.title, task.desc, task.isSynced, task.toBeDeleted, toBeUpdated = true)
                    taskDao.updateTask(mTask)
                }
            } else {
                val mTask = TaskRdb(task.id, task.taskId, task.title, task.desc, task.isSynced, task.toBeDeleted, toBeUpdated = true)
                taskDao.updateTask(mTask)
            }
        }
    }

    fun deleteTask(task: TaskRdb, scope: CoroutineScope) {
        scope.launch {
            if (task.isSynced && task.taskId != "") {
                if(Internet.hasInternetAccess()){
                    Firebase.deleteTask(task, scope)
                }else{
                    val mTask = TaskRdb(task.id, task.taskId, task.title, task.desc, task.isSynced, true)
                    update(mTask, scope)
                }
            } else {
                taskDao.deleteTask(task)
            }
        }
    }

    fun update(task : TaskRdb, scope: CoroutineScope){
        scope.launch {
            taskDao.updateTask(task)
        }
    }

    fun delete(task: TaskRdb, scope: CoroutineScope){
        scope.launch {
            taskDao.deleteTask(task)
        }
    }

    fun deleteAllData(scope: CoroutineScope){
        scope.launch {
            taskDao.deleteAllData()
        }
    }

}