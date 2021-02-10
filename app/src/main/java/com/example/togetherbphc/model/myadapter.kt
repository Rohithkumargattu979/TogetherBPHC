package com.example.togetherbphc.model

import com.example.togetherbphc.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import de.hdodenhof.circleimageview.CircleImageView


class myadapter(options: FirebaseRecyclerOptions<searchmodel?>) :
    FirebaseRecyclerAdapter<searchmodel?, myadapter.myviewholder?>(options) {
    override fun onBindViewHolder(holder: myviewholder, position: Int, model: searchmodel) {
        holder.name.text = model.getName()
        holder.gender.text = model.getGender()
        holder.Id.text = model.getID()
        Glide.with(holder.img.context).load(model.getPurl()).into(holder.img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.single_row_item, parent, false)
        return myviewholder(view)
    }

    inner class myviewholder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var img: CircleImageView
        var name: TextView
        var gender: TextView
        var Id: TextView

        init {
            img = itemView.findViewById<View>(R.id.img1) as CircleImageView
            name = itemView.findViewById<View>(R.id.nametext) as TextView
            gender = itemView.findViewById<View>(R.id.gendertext) as TextView
            Id= itemView.findViewById<View>(R.id.idtext) as TextView
        }
    }
}
