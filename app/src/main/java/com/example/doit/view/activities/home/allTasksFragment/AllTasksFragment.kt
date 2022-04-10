package com.example.doit.view.activities.home.allTasksFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doit.R
import com.example.doit.model.roomDB.TaskRdb
import com.example.doit.utils.Constants
import com.example.doit.view.activities.update.UpdateTaskActivity
import com.example.doit.view.adapters.TasksAdapter
import kotlinx.android.synthetic.main.all_tasks_fragment.*

class AllTasksFragment : Fragment() {

    private lateinit var viewModel: AllTasksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.all_tasks_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[AllTasksViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()

        syncAllDataOfRoomDbWithFirebase()

        rv_notes.layoutManager = LinearLayoutManager(context)
        val adapter = TasksAdapter()
        rv_notes.adapter = adapter

        setAdapterClickListener(adapter)
        getAndSetTasksList(adapter)
    }

    private fun getAndSetTasksList(adapter: TasksAdapter) {
        viewModel.getAllTask().observe(this) { tasks ->
            adapter.setData(tasks)
        }
    }

    private fun setAdapterClickListener(adapter: TasksAdapter) {
        adapter.setOnClickListener(object : TasksAdapter.OnClickListener {
            override fun onClickDelete(task: TaskRdb) {
                viewModel.deleteTask(task)
                getAndSetTasksList(adapter)
            }

            override fun onClickEdit(task: TaskRdb) {
                val intent = Intent(context, UpdateTaskActivity::class.java)
                intent.putExtra(Constants.TASKS, task)
                startActivity(intent)
            }
        })
    }

    private fun syncAllDataOfRoomDbWithFirebase() {
        viewModel.getAllTaskToBeSynced().observe(this) { tasks ->
            if (tasks.isNotEmpty()) {
                viewModel.syncAllTasks(tasks)
            }
        }

        viewModel.getAllTasksToBeDeleted().observe(this) { tasks ->
            if (tasks.isNotEmpty()) {
                viewModel.syncAllTasksToBeDeleted(tasks)
            }
        }

        viewModel.getAllTasksToBeUpdated().observe(this) { tasks ->
            if (tasks.isNotEmpty()) {
                viewModel.syncAllTaskToBeUpdated(tasks)
            }
        }
    }

}