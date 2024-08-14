package com.example.kotlinchatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var mAuth:FirebaseAuth
    private lateinit var mDataRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        mAuth=FirebaseAuth.getInstance()
        initviews()
    }
    
    fun initviews(){
        signup.setOnClickListener {
            var email = emails.text.toString()
            var password = passwords.text.toString()
            var name = name.text.toString()
            signUpFunctionality(name,email,password)
        }

    }

    private fun signUpFunctionality(name:String,email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDataBase(name,email,password,mAuth.currentUser?.uid!!)
                    //code for jumping to home
                    startActivity(Intent(this,MainActivity::class.java))
                    Toast.makeText(this, "Register successful", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "some error occurred", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun addUserToDataBase(name: String, email: String,password: String, uid: String) {

        mDataRef= FirebaseDatabase.getInstance().getReference()
        mDataRef.child("user").child(uid).setValue(User(name,email,password,uid))
    }

}