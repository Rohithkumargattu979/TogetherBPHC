package com.example.togetherbphc.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.togetherbphc.R
import com.example.togetherbphc.view.Otherprofile
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import de.hdodenhof.circleimageview.CircleImageView


class myadapter(options: FirebaseRecyclerOptions<ProfileModel?>) :
    FirebaseRecyclerAdapter<ProfileModel?, myadapter.myviewholder?>(options) {
    private var databaseReference: DatabaseReference? = null
    private var firebaseAuth: FirebaseAuth? = null
    var map2 : HashMap<String, Any>
            = HashMap()
    override fun onBindViewHolder(holder: myviewholder, position: Int, model: ProfileModel) {
        holder.name.text = model.getUserName()
        holder.gender.text = model.getGender()
        holder.Id.text = model.getID()
        map2["UserName"] = model.getUserName().toString()
        map2["Gender"] = model.getGender().toString()
        map2["BITS_ID"] = model.getID().toString()
        Glide.with(holder.img.context).load(model.getPurl()).into(holder.img)
        holder.img.setOnClickListener {
            Otherprofile(
                model.getUserName(),
                model.getGender(),
                model.getID(),
                model.getDOB(),
                model.getInsta(),
                model.getFB(),
                model.getPurl(),
                model.getHobby1(),
                model.getHobby2(),
                model.getHobby3(),
                model.getHobby4(),
                model.getHobby5(),
                model.getHobbyOther())
            val activity  = it.context as? AppCompatActivity
            activity?.supportFragmentManager?.commit {
                addToBackStack(Otherprofile::class.toString())
                setReorderingAllowed(true)
                replace<Otherprofile>(R.id.home)
            }

        }
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
        var check: CheckBox

        init {
            firebaseAuth = FirebaseAuth.getInstance()
            databaseReference = FirebaseDatabase.getInstance().getReference("users")
            img = itemView.findViewById<View>(R.id.img1) as CircleImageView
            name = itemView.findViewById<View>(R.id.nametext) as TextView
            gender = itemView.findViewById<View>(R.id.gendertext) as TextView
            Id= itemView.findViewById<View>(R.id.idtext) as TextView
            check = itemView.findViewById<View>(R.id.fav) as CheckBox
            if(check.isChecked)
            {
                databaseReference!!.child(firebaseAuth!!.uid!!).child("Favourites").updateChildren(
                    map2)
            }
        }
    }
}