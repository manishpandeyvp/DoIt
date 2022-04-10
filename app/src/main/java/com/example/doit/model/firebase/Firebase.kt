package com.example.doit.model.firebase

import com.example.doit.model.repository.TaskRepository
import com.example.doit.model.roomDB.TaskRdb
import com.example.doit.utils.Constants
import com.example.doit.utils.Mapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope

object Firebase {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db = FirebaseFirestore.getInstance()
    private val taskRepository = TaskRepository()

    fun getCurrentUserId(): String {
        val currentUser = firebaseAuth.currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

    fun addTaskToFirestore(title: String, desc: String, scope: CoroutineScope) {
        var taskId: String
        var isSynced: Boolean
        val taskFb = TaskFb(title, desc)
        db.collection(Constants.TASKS).document(getCurrentUserId()).collection(Constants.TASKS)
            .add(taskFb).addOnCompleteListener {
                if (it.isSuccessful) {
                    taskId = it.result.id
                    isSynced = true
                    val taskRdb = TaskRdb(0, taskId, title, desc, isSynced)
                    taskRepository.addTaskToRoomDb(taskRdb, scope)
                } else {
                    val taskRdb = TaskRdb(0, "", title, desc, false)
                    taskRepository.addTaskToRoomDb(taskRdb, scope)
                }
            }
    }

    fun syncAllTaskToFirestore(task: TaskRdb, scope: CoroutineScope) {
        val taskFb = TaskFb(task.title, task.desc)
        db.collection(Constants.TASKS).document(getCurrentUserId()).collection(Constants.TASKS)
            .add(taskFb).addOnCompleteListener {
                if (it.isSuccessful) {
                    val taskRdb = TaskRdb(task.id, it.result.id, task.title, task.desc, true)
                    taskRepository.update(taskRdb, scope)
                }
            }
    }

    fun getAllTasks(scope: CoroutineScope) {
        db.collection(Constants.TASKS).document(getCurrentUserId()).collection(Constants.TASKS)
            .get().addOnCompleteListener { res ->
                if (res.isSuccessful) {
                    for (doc in res.result.documents) {
                        val t = doc.toObject(TaskFb::class.java)
                        taskRepository.addTaskToRoomDb(
                            Mapper.firebaseToRoomDbTaskMapper(t!!, doc.id),
                            scope
                        )
                    }
                }
            }
    }

    fun deleteTask(task: TaskRdb, scope: CoroutineScope) {
        db.collection(Constants.TASKS).document(getCurrentUserId()).collection(Constants.TASKS)
            .document(task.taskId).delete().addOnCompleteListener { res ->
                if (res.isSuccessful) {
                    taskRepository.delete(task, scope)
                } else {
                    val mTask =
                        TaskRdb(task.id, task.taskId, task.title, task.desc, task.isSynced, true)
                    taskRepository.update(mTask, scope)
                }
            }
    }

    fun updateTask(task: TaskRdb, scope: CoroutineScope) {
        val tempTask = TaskFb(task.title, task.desc)
        db.collection(Constants.TASKS).document(getCurrentUserId()).collection(Constants.TASKS)
            .document(task.taskId).set(tempTask).addOnCompleteListener { res ->
                if (res.isSuccessful) {
                    val mTask = TaskRdb(
                        task.id,
                        task.taskId,
                        task.title,
                        task.desc,
                        task.isSynced,
                        task.toBeDeleted,
                        false
                    )
                    taskRepository.update(mTask, scope)
                } else {
                    val mTask = TaskRdb(
                        task.id,
                        task.taskId,
                        task.title,
                        task.desc,
                        task.isSynced,
                        task.toBeDeleted,
                        true
                    )
                    taskRepository.update(mTask, scope)
                }
            }
    }

//    fun signInUsingEmailAndPassword(email: String, password: String, activity: LoginActivity) {
//        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { res ->
//            if (res.isSuccessful) activity.postLogin()
//            else Toast.makeText(MyApplication.getContext(), "Login Failed", Toast.LENGTH_LONG)
//                .show()
//        }
//    }
}