package com.example.doit.utils

import com.example.doit.model.firebase.TaskFb
import com.example.doit.model.roomDB.TaskRdb

object Mapper {
    fun firebaseToRoomDbTaskMapper(taskFb: TaskFb, taskId: String): TaskRdb {
        return TaskRdb(0, taskId, taskFb.title, taskFb.desc, isSynced = true, toBeDeleted = false)
    }
}