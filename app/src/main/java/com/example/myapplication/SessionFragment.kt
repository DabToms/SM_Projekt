package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

import java.util.*

class SessionFragment : Fragment() {

    companion object {

        private val ARG_TASK_ID: String = "ARG_TASK_ID"

        fun newInstance(taskID: UUID?): SessionFragment {
            val bundle = Bundle()
            bundle.putSerializable(ARG_TASK_ID, taskID)
            val taskFragment = SessionFragment()
            taskFragment.arguments = bundle
            return taskFragment
        }
    }

    private lateinit var task: Session
    private lateinit var nameField: TextView
    private lateinit var dateButton: TextView
    private lateinit var doneCheckBox: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val taskId: UUID? = arguments?.getSerializable(ARG_TASK_ID) as UUID?
        task = SessionStorage.getInstance().getSession(taskId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view: View = inflater.inflate(R.layout.activity_statistics, container, false)

        dateButton = view.findViewById(R.id.distance_stat)
        dateButton.text = task.Distance.toString()



        return view
    }
}