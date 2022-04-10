package com.example.doit.model.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskRdb::class], version = 1)
abstract class TaskDB : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDB? = null

        fun getDatabase(context: Context): TaskDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, TaskDB::class.java, "task_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}