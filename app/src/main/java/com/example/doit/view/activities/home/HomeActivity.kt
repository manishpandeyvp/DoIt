package com.example.doit.view.activities.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.doit.R
import com.example.doit.utils.Communicator
import com.example.doit.view.activities.MainActivity
import com.example.doit.view.activities.home.addTaskFragment.AddTaskFragment
import com.example.doit.view.activities.home.allTasksFragment.AllTasksFragment
import com.example.doit.view.activities.home.profileFragment.ProfileFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), Communicator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setFragment(AllTasksFragment())
        bottom_nav.selectedItemId = R.id.menu_all_tasks
    }

    override fun onResume() {
        super.onResume()
        bottom_nav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_all_tasks -> {
                    setFragment(AllTasksFragment())
                    true
                }
                R.id.menu_add_task -> {
                    setFragment(AddTaskFragment())
                    true
                }
                R.id.menu_profile -> {
                    setFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun signOut() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}