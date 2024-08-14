package com.example.kotlinchatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth:FirebaseAuth
    private lateinit var userList:ArrayList<User>
    private lateinit var adapter:UserAdapter
    private lateinit var mDataRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth=FirebaseAuth.getInstance()
        mDataRef= FirebaseDatabase.getInstance().getReference()

        initviews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.logout){
            //write the logic for logout
            mAuth.signOut()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
            return true
        }
        return true
    }


    fun initviews(){
        userList= ArrayList()
        adapter= UserAdapter(this,userList)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=adapter
        mDataRef.child("user").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postsnapshot in snapshot.children){
                    val currentuser = postsnapshot.getValue(User::class.java)
                    if (mAuth.currentUser!!.uid!=currentuser!!.uid){
                        userList.add(currentuser!!)
                    }

                }
                    adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}