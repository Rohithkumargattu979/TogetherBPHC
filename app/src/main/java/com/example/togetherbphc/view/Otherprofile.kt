package com.example.togetherbphc.view

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.togetherbphc.databinding.FragmentOtherprofileBinding

class Otherprofile : Fragment {
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var fbID: String? = null
    private var instaID: String? = null
    var UserName: String? = null
    var Gender: String? = null
    var prof_img: String? = null
    var BITS_ID: String? = null
    var FB: String? = null
    var Insta: String? = null
    var DOB: String? = null
    var Hobby1: String? = null
    var Hobby2: String? = null
    var Hobby3: String? = null
    var Hobby4: String? = null
    var Hobby5: String? = null
    var HobbyOther: String? = null

    constructor() {}
    constructor(
        UserName: String?, Gender: String?,
        ID: String?,
        DOB: String?,
        Insta: String?, FB: String?, Purl: String?,
        Hobby1: String?,
        Hobby2: String?,
        Hobby3: String?,
        Hobby4: String?,
        Hobby5: String?,
        HobbyOther: String?
    ) {
        this.UserName = UserName
        this.Gender = Gender
        this.prof_img = Purl
        this.FB = FB
        this.Insta = Insta
        this.DOB = DOB
        this.Hobby1 = Hobby1
        this.Hobby2 = Hobby2
        this.Hobby3 = Hobby3
        this.Hobby4 = Hobby4
        this.Hobby5 = Hobby5
        this.HobbyOther = HobbyOther
        this.BITS_ID = ID
    }

    private var _binding: FragmentOtherprofileBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOtherprofileBinding.inflate(inflater, container, false)
        val view = binding.root

        return view


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        binding.profileName.text = UserName
        binding.profileBITS.text = BITS_ID
        fbID = FB.toString()
        instaID = Insta.toString()
        binding.dob.text = DOB
        binding.gender.text = Gender
        binding.hob1.text = Hobby1
        binding.hob2.text = Hobby2
        binding.hob3.text = Hobby3
        binding.hob4.text = Hobby4
        binding.hob5.text = Hobby5
        binding.otherhob.text = HobbyOther
        Glide.with(requireContext()).load(prof_img).into(binding.profileImage)
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
    }

    fun onBackPressed() {
        val activity = context as AppCompatActivity?
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.home, Home())
            .addToBackStack(null).commit()
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        fun newInstance(
            param1: String?,
            param2: String?): Otherprofile {
            val fragment = Otherprofile()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}