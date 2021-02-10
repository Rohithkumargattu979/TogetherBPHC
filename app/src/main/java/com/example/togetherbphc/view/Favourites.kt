package com.example.togetherbphc.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.togetherbphc.R
import com.example.togetherbphc.model.ProfileModel
import com.example.togetherbphc.model.myadapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


// TODO: Rename parameter arguments, choose names that match
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Favourites : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var database = FirebaseDatabase.getInstance()
    var fvrtref:DatabaseReference? = null
    var fvrt_listRef:DatabaseReference? = null
    var recview: RecyclerView? = null
    var adapter: myadapter? = null
    var fvrtChecker = false
    var check: CheckBox? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        recview = view.findViewById(R.id.recview) as RecyclerView
        recview!!.layoutManager = LinearLayoutManager(activity)

        val options: FirebaseRecyclerOptions<ProfileModel?> = FirebaseRecyclerOptions.Builder<ProfileModel>()
            .setQuery(FirebaseDatabase.getInstance().reference.child("users"),
                ProfileModel::class.java)
            .build()

        adapter = myadapter(options)
        recview!!.adapter = adapter
    }




    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Favourites().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}