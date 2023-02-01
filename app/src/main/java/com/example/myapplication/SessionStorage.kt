package com.example.myapplication

import android.util.Log
import java.util.*

class SessionStorage {
    companion object {
        private val sessionStorage: SessionStorage = SessionStorage()
        fun getInstance(): SessionStorage {
            return sessionStorage
        }
    }

    private var sessions: MutableList<Session> = mutableListOf()

    fun getSessions(): List<Session> {
        return sessions
    }
    fun getSession(taskID: UUID?): Session {
        var taskFound = Session()
        for (task in sessions) {
            if (task.id == taskID) {
                taskFound = task
                Log.d("TASK_KEY", "Found task: " + taskFound.Name)
            }
        }
        return taskFound

    }

    fun addSession(session: Session) {
        sessions.add(session)
    }
}