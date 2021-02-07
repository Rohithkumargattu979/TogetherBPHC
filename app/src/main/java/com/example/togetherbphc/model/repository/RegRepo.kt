package com.example.togetherbphc.model.repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.togetherbphc.model.UserModel
import com.example.togetherbphc.model.utils.AppUtil
import com.google.firebase.database.*

class RegRepo {
    private var liveData: MutableLiveData<UserModel>? = null
    private lateinit var databaseReference: DatabaseReference
    private val appUtil = AppUtil()

    object StaticFunction {
        private var instance: RegRepo? = null
        fun getInstance(): RegRepo {
            if (instance == null)
                instance = RegRepo()

            return instance!!
        }
    }

    fun getUser(): LiveData<UserModel> {

        if (liveData == null)
            liveData = MutableLiveData()
        databaseReference =
                FirebaseDatabase.getInstance().getReference("Users").child(appUtil.getUID()!!)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userModel = snapshot.getValue(UserModel::class.java)
                    liveData!!.postValue(userModel)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        return liveData!!
    }

    // name
    fun updateName(userName: String?) {


        val databaseReference: DatabaseReference =
                FirebaseDatabase.getInstance().getReference("Users").child(appUtil.getUID()!!)

        val map = mapOf<String, Any>("name" to userName!!)
        databaseReference.updateChildren(map)

    }

    fun updateImage(imagePath: String) {
        val databaseReference =
                FirebaseDatabase.getInstance().getReference("Users").child(appUtil.getUID()!!)

        val map = mapOf<String, Any>("image" to imagePath)
        databaseReference.updateChildren(map)
    }

    fun updateClgID(userClgID: String?) {


        val databaseReference: DatabaseReference =
                FirebaseDatabase.getInstance().getReference("Users").child(appUtil.getUID()!!)

        val map = mapOf<String, Any>("BITSid" to userClgID!!)
        databaseReference.updateChildren(map)

    }

    fun updateGender(userGender: String?) {


        val databaseReference: DatabaseReference =
                FirebaseDatabase.getInstance().getReference("Users").child(appUtil.getUID()!!)

        val map = mapOf<String, Any>("Gender" to userGender!!)
        databaseReference.updateChildren(map)

    }
    fun updateDOB(userDOB: String?) {


        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Users").child(appUtil.getUID()!!)

        val map = mapOf<String, Any>("DOB" to userDOB!!)
        databaseReference.updateChildren(map)

    }

}