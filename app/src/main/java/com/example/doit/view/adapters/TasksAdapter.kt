package com.example.doit.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.doit.R
import com.example.doit.model.roomDB.TaskRdb
import kotlinx.android.synthetic.main.item_note.view.*

class TasksAdapter : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    private var taskList = emptyList<TaskRdb>()
    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = taskList[position]
        val view = holder.itemView

        view.tv_title.text = task.title
        view.tv_body.text = task.desc

        view.iv_delete.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClickDelete(task)
            }
        }

        view.iv_edit.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClickEdit(task)
            }
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    interface OnClickListener {
        fun onClickDelete(task: TaskRdb)
        fun onClickEdit(task: TaskRdb)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun setData(tasks: List<TaskRdb>) {
        this.taskList = tasks
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}