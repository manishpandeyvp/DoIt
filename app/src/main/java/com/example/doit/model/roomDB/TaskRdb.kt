package com.example.doit.model.roomDB

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "task_list")
data class TaskRdb(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val taskId : String,
    val title: String,
    val desc: String,
    val isSynced: Boolean = false,
    val toBeDeleted: Boolean = false,
    val toBeUpdated : Boolean = false
) : Parcelable
