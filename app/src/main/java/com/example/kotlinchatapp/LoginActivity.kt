package com.example.kotlinchatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth=FirebaseAuth.getInstance()
        initViews()
    }
    fun initViews(){
        signup.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
        login.setOnClickListener {
            var email=email.text.toString()
            var password=password.text.toString()
            loginFunctionality(email,password)
        }

    }

    private fun loginFunctionality(email: String, password: String) {
        //logic for login
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this,MainActivity::class.java))
                } else {
                    Toast.makeText(this, "user does not exit", Toast.LENGTH_SHORT).show()
                }
            }
    }


}