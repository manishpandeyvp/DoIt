package com.example.doit.view.activities.update

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.doit.R
import com.example.doit.model.roomDB.TaskRdb
import com.example.doit.utils.Constants
import kotlinx.android.synthetic.main.activity_update_task.*

class UpdateTaskActivity : AppCompatActivity() {
    private lateinit var viewModel : UpdateTaskViewModel
    private lateinit var task: TaskRdb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_task)
        viewModel = ViewModelProvider(this)[UpdateTaskViewModel::class.java]

        task = intent.getParcelableExtra(Constants.TASKS)!!
    }

    override fun onResume() {
        super.onResume()

        ev_title.setText(task.title)
        ev_desc.setText(task.desc)

        btn_update.setOnClickListener {
            val title = ev_title.text.toString()
            val desc = ev_desc.text.toString()

            val mTask = TaskRdb(task.id, task.taskId, title, desc, task.isSynced, task.toBeDeleted, true)
            viewModel.updateTask(mTask)
            finish()
        }
    }
}