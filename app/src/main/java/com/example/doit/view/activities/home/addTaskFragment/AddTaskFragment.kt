package com.example.doit.view.activities.home.addTaskFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.doit.R
import kotlinx.android.synthetic.main.add_task_fragment.*

class AddTaskFragment : Fragment() {

    private lateinit var viewModel: AddTaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_task_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[AddTaskViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()


        btn_save.setOnClickListener {
            val title = ev_title.text.toString()
            val desc = ev_desc.text.toString()

            viewModel.addTask(title, desc)
        }
    }

}