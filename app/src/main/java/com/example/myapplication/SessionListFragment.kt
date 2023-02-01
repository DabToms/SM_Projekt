package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SessionListFragment : Fragment() {
    companion object {
        val KEY_EXTRA_TASK_ID: String = "KEY_EXTRA_TASK_ID"
    }

    private var recyclerView: RecyclerView? = null
    private var adapter: TaskAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.recycler, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        updateView()
        return view
    }

    inner class TaskHolder(inflater: LayoutInflater, parent: ViewGroup?) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)),
        View.OnClickListener {
        private var task: Session? = null
        private val nameTextView: TextView
        private val dateTextView: TextView

        init {
            itemView.setOnClickListener(this)
            nameTextView = itemView.findViewById(R.id.task_item_name)
            dateTextView = itemView.findViewById(R.id.task_item_date)
        }

        fun bind(task: Session) {
            this.task = task
            nameTextView.text = task.Name
            dateTextView.text = task.Date.toString()
        }

        override fun onClick(view: View?) {
            val intent = Intent(activity, StatisticsActivity::class.java)
            intent.putExtra(KEY_EXTRA_TASK_ID, task?.id)
            startActivity(intent)
        }
    }

    inner class TaskAdapter() :
        RecyclerView.Adapter<TaskHolder>() {

        private lateinit var tasks: List<Session>

        constructor(tasks: List<Session>) : this() {
            this.tasks = tasks
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
            val layoutInflater: LayoutInflater = LayoutInflater.from(activity)
            return TaskHolder(layoutInflater, parent)
        }

        override fun onBindViewHolder(holder: TaskHolder, position: Int) {
            val task = tasks[position]
            holder.bind(task)
        }

        override fun getItemCount(): Int {
            return tasks.size
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateView() {
        val taskStorage = SessionStorage.getInstance()
        val tasks: List<Session> = taskStorage.getSessions()

        if (adapter == null) {
            adapter = TaskAdapter(tasks)
            recyclerView?.adapter = adapter
        } else {
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        updateView()
    }
}