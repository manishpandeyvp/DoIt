package com.example.doit.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.doit.R
import com.example.doit.view.activities.home.HomeActivity
import com.example.doit.view.activities.login.LoginActivity
import com.example.doit.view.activities.signup.SignupActivity
import com.example.doit.model.firebase.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        if(Firebase.getCurrentUserId() != ""){
            startActivity(Intent(this, HomeActivity::class.java))
        }
        btn_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        btn_signup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}