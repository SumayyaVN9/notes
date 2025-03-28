package com.example.notes

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList

class Adapter(private val notelist: ArrayList<Notes>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notes = itemView.findViewById<EditText>(R.id.viewnote)
        //val enable = itemView.findViewById<Button>(R.id.enable)
        val update = itemView.findViewById<Button>(R.id.update)
        val delete = itemView.findViewById<Button>(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.listnote, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return notelist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notelist[position]

        holder.notes.setText(note.Note)

        val Id = note.document

        holder.update.setOnClickListener{
            val text = holder.notes.text.toString().trim()
            val washingtonRef = FirebaseFirestore.getInstance().collection("Notes").document(Id)

            washingtonRef
                .update("Note", text)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
        }

        holder.delete.setOnClickListener{
            FirebaseFirestore.getInstance().collection("Notes").document(Id).delete()

        }
    }
}