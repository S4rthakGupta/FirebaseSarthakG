package com.example.firebasesarthakg

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

// This adapter class connects the Firebase data to the RecyclerView on the screen.
class PersonAdapter(options: FirebaseRecyclerOptions<Person>)
    : FirebaseRecyclerAdapter<Person, PersonAdapter.MyViewHolder>(options)
{
    // This function is called when a new list item is created.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(inflater,parent)
    }
    
    // This function connects the data from the model to the list item.
    override fun onBindViewHolder(
        holder: PersonAdapter.MyViewHolder,
        position: Int,
        model: Person
    )
    {
    // Setting the text in the textViews to the data in the Person model.
        holder.txtName.text = model.name
        holder.txtRole.text = model.role
        holder.txtTeam.text = model.team
        val theImage : String = model.photo

        if (theImage.indexOf("gs://") > -1) {
            val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(theImage)
            Glide.with(holder.imgPhoto.context)
                .load(storageReference)
                .into(holder.imgPhoto)
        } else {
            Glide.with(holder.imgPhoto.context)
                .load(theImage)
                .into(holder.imgPhoto)
        }
    }

    // This below inner class holds the view elements.
    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.row_layout, parent, false))
    {
        // These below are the text view elements where the person's name, role, and team will be displayed.
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtRole: TextView = itemView.findViewById(R.id.txtRole)
        val txtTeam: TextView = itemView.findViewById(R.id.txtTeam)
        val imgPhoto: ImageView = itemView.findViewById<ImageView>(R.id.imgPhoto)
    }
}