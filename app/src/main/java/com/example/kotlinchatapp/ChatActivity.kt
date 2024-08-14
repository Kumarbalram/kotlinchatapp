package com.example.kotlinchatapp

import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.devlomi.record_view.OnRecordListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import java.io.File
import java.io.IOException


class ChatActivity : AppCompatActivity() {
    private var mediaRecorder: MediaRecorder? = null
    private lateinit var messageAdapter:MessageAdapter
    private lateinit var audioPath:String
    private lateinit var messageList:ArrayList<Message>
    private lateinit var mDataRef:DatabaseReference
    var receiverRoom:String? = null
    var senderRoom:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        mDataRef = FirebaseDatabase.getInstance().getReference()
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid +  receiverUid
        supportActionBar?.title = name
        send.setOnClickListener {
            val mesage_box = et_message.text.toString()
            val messageObject=Message(mesage_box,senderUid)
            mDataRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDataRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            et_message.setText("")
        }


        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)
        chat_recycler.layoutManager = LinearLayoutManager(this)
        chat_recycler.adapter = messageAdapter

        //logic for show data in recyclerview
        mDataRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children){

                        var message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)

                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


    }


}