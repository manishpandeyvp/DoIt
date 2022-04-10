package com.example.doit.view.activities.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.doit.R
import com.example.doit.view.activities.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()

        login.setOnClickListener {
            val email = username.text.toString()
            val password = password.text.toString()

//            Firebase.signInUsingEmailAndPassword(email, password, LoginActivity())

//            TODO : how to remove this block of code from here and move to the Firebase Object

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModel.getAllTasksFromFirebase().also {
                        startActivity(Intent(this, HomeActivity::class.java))
//                        TODO : If I finish activity here, then data is not saved in RoomDB
//                        finish()
                    }
                } else {
                    Toast.makeText(this, "Login Failed!!", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

//    fun postLogin() {
//        startActivity(Intent(MyApplication.getContext(), HomeActivity::class.java))
//    }
}
