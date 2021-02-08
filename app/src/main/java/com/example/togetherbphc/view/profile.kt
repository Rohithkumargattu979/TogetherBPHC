package com.example.togetherbphc.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.togetherbphc.R
import com.example.togetherbphc.constants.Constants.Companion.TAG
import com.example.togetherbphc.databinding.FragmentProfileBinding
import com.example.togetherbphc.viewmodel.profileViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class profile : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var userID: String? = null
    private var fbID: String? = null
    private var instaID: String? = null
    val btn : Button? = null
    val vm :MainActivity = MainActivity()
    val vm1: profileViewModel
        get() = profileViewModel()
    private var databaseReference: DatabaseReference? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var storageReference: StorageReference? = null
    private var fstore: FirebaseFirestore? = null
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
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_profile, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth!!.currentUser!!.uid
        fstore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().reference
        storageReference = FirebaseStorage.getInstance().reference


        // photo upload
        val profileRef = storageReference!!.child("users/" + firebaseAuth!!.currentUser!!.uid + "/profile.jpg")
        profileRef.downloadUrl.addOnSuccessListener { uri ->
            //Picasso.get().load(uri).into(profileImage)
            Glide.with(this)
                .load(uri)
                .into(binding.profileImage)
        }
      /*  val uidRef: DatabaseReference = databaseReference!!.child("users").child(userID!!)
        uidRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


            }

            override fun onCancelled(error: DatabaseError) {

            }
        })*/

        val docRef: DocumentReference = fstore!!.collection("users").document(userID!!)
        docRef.addSnapshotListener(EventListener<DocumentSnapshot?> { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@EventListener
            }
            if (snapshot!!.exists()) {
                binding.profileName.text = snapshot.getString("UserName")
                binding.profileEmail.text = firebaseAuth!!.currentUser!!.email.toString()
                binding.profileBITS.text = snapshot.getString("BITS_ID")
                binding.dob.text = snapshot.getString("DOB")
                binding.gender.text = snapshot.getString("Gender")
                fbID = snapshot.getString("FB")!!.trim()
                instaID = snapshot.getString("Insta")!!.trim()
                if (snapshot.getString("Hobby1") != null) {
                    binding.hob1.text = snapshot.getString("Hobby1")
                }
                if (snapshot.getString("Hobby2") != null) {
                    binding.hob2.text = snapshot.getString("Hobby2")
                }
                if (snapshot.getString("Hobby3")!= null) {
                    binding.hob3.text = snapshot.getString("Hobby3")
                }
                if (snapshot.getString("Hobby4") != null) {
                    binding.hob4.text = snapshot.getString("Hobby4")
                }
                if (snapshot.getString("Hobby5") != null) {
                    binding.hob5.text = snapshot.getString("Hobby5")
                }
                if (snapshot.getString("HobbyOther") != null) {
                    binding.otherhob.text = snapshot.getString("HobbyOther")
                }
            } else {
                Log.d(TAG, "Current data: null")
            }
        })
        binding.fb.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(fbID)
            startActivity(i)
        }
        binding.insta.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(instaID)
            startActivity(i)
        }

        binding.button.setOnClickListener {
            Logout()
        }
    }

    private fun Logout() {
        FirebaseAuth.getInstance().signOut()
        val i = Intent(activity,
            GoogleSignin::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                profile().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }


}


