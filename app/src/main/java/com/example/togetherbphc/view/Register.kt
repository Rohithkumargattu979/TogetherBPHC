package com.example.togetherbphc.view

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.togetherbphc.R
import com.example.togetherbphc.R.array.Gender
import com.example.togetherbphc.constants.Constants
import com.example.togetherbphc.viewmodel.RegistrationViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_register.*


class Register : AppCompatActivity() {
    private val vm = RegistrationViewModel()
    private var image: Uri? = null
    private lateinit var username: String
    private lateinit var clgID: String
    private lateinit var gender: String
    private lateinit var date: String
    private lateinit var imageUrl: String
    private lateinit var hb1: String
    private lateinit var hb2: String
    private lateinit var hb3: String
    private lateinit var hb4: String
    private lateinit var hb5: String
    private lateinit var hbo: String
    private lateinit var userID: FirebaseUser

    private var databaseReference: DatabaseReference? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var storageReference: StorageReference? = null
    private var fstore: FirebaseFirestore? = null
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val myAdapter = ArrayAdapter<String>(
                this@Register,
                android.R.layout.simple_list_item_1, resources.getStringArray(Gender)
        )
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = myAdapter

        spinner1.onItemSelectedListener = object :
                AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                gender = spinner1.selectedItem.toString();
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                TODO("Not yet implemented")
            }
        }

        // date
        dobbtn.text = vm.dateFormat.format(vm.now.time)
        dobbtn.setOnClickListener {
            val datePicker = DatePickerDialog(
                    this,
                    { _, year, month, dayOfMonth ->
                        vm.now.set(Calendar.YEAR, year)
                        vm.now.set(Calendar.MONTH, month)
                        vm.now.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        dobbtn.text = vm.dateFormat.format(vm.now.time)
                        date = dobbtn.text.toString().trim()
                    },
                    vm.now.get(Calendar.YEAR),
                    vm.now.get(Calendar.MONTH),
                    vm.now.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        //firebase
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("users")
        storageReference = FirebaseStorage.getInstance().reference
        userID = firebaseAuth!!.currentUser!!



        profpic.setOnClickListener {
            val openGalleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(openGalleryIntent, 1000)
        }
        // sign up
        signup.setOnClickListener {
            /*if (checkData()) {
                uploadData(username, clgID)
            }*/

            //signing  up and uploading data

            fstore = FirebaseFirestore.getInstance()
            username = userName.text.toString().trim()
            clgID = idno.text.toString().trim()
            if (username.isEmpty()) {
                userName.error = "Username cannot be empty"
                return@setOnClickListener
            } else if (clgID.isEmpty()) {
                idno.error = "ID number cannot be empty"
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE
            val documentReference: DocumentReference = fstore!!.collection("users").document(userID.toString())
           /* storageReference!!.child(firebaseAuth!!.uid + Constants.PATH).putFile(image!!)
                    .addOnSuccessListener {
                        val task = it.storage.downloadUrl
                        task.addOnCompleteListener { uri ->
                            imageUrl = uri.result.toString()*/
                         var map1 : HashMap<String, Any>
                                                            = HashMap()
                            map1["UserName"] = username
                            map1["Gender"] = gender
                            map1["BITS_ID"] = clgID
                            map1["DOB"] = date
                            map1["prof_img"] = imageUrl

                            if(hobby1.isChecked)
                            {
                                hb1  = hobby1.text.toString().trim()
                                map1["Hobby1"] = hb1
                            }
                            if(hobby2.isChecked)
                            {
                                hb2  = hobby2.text.toString().trim()
                                map1["Hobby2"] = hb2

                            }
                            if(hobby3.isChecked)
                            {
                                hb3  = hobby3.text.toString().trim()
                                map1["Hobby3"] = hb3
                            }
                            if(hobby4.isChecked)
                            {
                                hb4  = hobby4.text.toString().trim()
                                map1["Hobby4"] = hb4

                            }
                            if(hobby5.isChecked)
                            {
                                hb5  = hobby5.text.toString().trim()
                                map1["Hobby5"] = hb5
                            }
                            hbo = hobbyother.text.toString().trim()
                            if(hbo.isNotEmpty())
                            {
                                map1["HobbyOther"] = hbo
                            }


                            databaseReference!!.child(firebaseAuth!!.uid!!).updateChildren(map1)

                            documentReference.set(map1).addOnSuccessListener {
                                Log.d(
                                        Constants.TAG,
                                        "onSuccess: user Profile is created for $userID"
                                )
                            }.addOnFailureListener { e ->
                                Log.d(
                                        Constants.TAG,
                                        "onFailure: $e"
                                )
                            }

                            val intent = Intent(this, MainActivity::class.java) // (1) (2)
                            startActivity(intent)
                            progressBar.visibility = View.GONE
                        }



                    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                image = data!!.data

                //profileImage.setImageURI(imageUri);
                uploadImageToFirebase(image)
            }
        }
    }

    private fun uploadImageToFirebase(imageUri: Uri?) {
        // upload image to firebase storage
        val fileRef = storageReference!!.child("users/" + firebaseAuth!!.currentUser!!.uid + "/profile.jpg")
        fileRef.putFile(imageUri!!).addOnSuccessListener {
            fileRef.downloadUrl.addOnSuccessListener { uri -> Picasso.get().load(uri)} }
                .addOnFailureListener { Toast.makeText(applicationContext, "Failed.", Toast.LENGTH_SHORT).show() }
        imageUrl = fileRef.downloadUrl.toString().trim()
    }

        }









