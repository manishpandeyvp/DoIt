package com.example.doit.view.activities.home.profileFragment

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doit.MyApplication
import com.example.doit.R
import com.example.doit.utils.Communicator
import com.example.doit.view.activities.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        communicator = activity as Communicator
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        btn_log_out.setOnClickListener {
            firebaseAuth.signOut()
            viewModel.deleteAllTasks()
            communicator.signOut()
        }
    }

}