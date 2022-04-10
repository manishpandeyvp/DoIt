package com.example.doit.model.roomDB

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE

@Dao
interface TaskDao {

    @Insert(onConflict = IGNORE)
    suspend fun addTask(task: TaskRdb)

    @Query("SELECT * FROM TASK_LIST WHERE toBeDeleted = :toBeDeleted ORDER BY id ASC")
    fun getAllTasks(toBeDeleted: Boolean): LiveData<List<TaskRdb>>

    @Query("SELECT * FROM task_list WHERE isSynced = :isSynced ORDER BY id ASC")
    fun getTaskToBeSynced(isSynced: Boolean): LiveData<List<TaskRdb>>

    @Query("SELECT * FROM task_list WHERE toBeUpdated = :toBeUpdated ORDER BY id ASC")
    fun getTaskToBeUpdated(toBeUpdated: Boolean): LiveData<List<TaskRdb>>

    @Update
    suspend fun updateTask(task: TaskRdb)

    @Delete
    suspend fun deleteTask(task: TaskRdb)

    @Query("DELETE FROM task_list")
    suspend fun deleteAllData()
}