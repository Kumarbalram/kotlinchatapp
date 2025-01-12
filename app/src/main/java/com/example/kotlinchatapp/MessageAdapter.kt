package com.example.kotlinchatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context,val list:ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE=1
    val ITEM_SENT=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       if (viewType==1){
           val view = LayoutInflater.from(context).inflate(R.layout.receiver,parent,false)
           return ReceiveViewHolder(view)
       }else{
           val view = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
           return SentViewHolder(view)
       }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = list[position]
        if (holder.javaClass==SentViewHolder::class.java){
            val viewHolder = holder as SentViewHolder
                holder.messagesent.text=currentMessage.message
        }else{
            val viewHolder = holder as ReceiveViewHolder
            holder.receiveMessage.text=currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = list[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }

    class  SentViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val messagesent=itemView.findViewById<TextView>(R.id.tv_sent)
    }

    class  ReceiveViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val receiveMessage=itemView.findViewById<TextView>(R.id.tv_receive)
    }

}