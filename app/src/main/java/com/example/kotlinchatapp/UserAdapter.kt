package com.example.kotlinchatapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.user_layout.view.*

class UserAdapter(val conxtact:Context,val userList:ArrayList<User>):RecyclerView.Adapter<UserAdapter.ViewHolder>(){

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.user_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.apply {
            val userlist=userList[position]
            user_name.text=userlist.name
            holder.itemView.setOnClickListener {
                context.startActivity(Intent(context,ChatActivity::class.java)
                    .putExtra("name",userlist.name)
                    .putExtra("uid",userlist.uid))
            }

        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}