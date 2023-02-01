package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import java.util.*

class StatisticsActivity : SingleStatFragment() {
    override fun createFragment(): Fragment {
        val taskId: UUID? = intent.getSerializableExtra(SessionListFragment.KEY_EXTRA_TASK_ID) as UUID?
        return SessionFragment.newInstance(taskId)
    }
}