package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class ListActivity : SingleStatFragment() {
    override fun createFragment(): Fragment {
        return SessionListFragment()
    }
}