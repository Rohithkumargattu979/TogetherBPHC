    package com.example.togetherbphc.model

    import com.bumptech.glide.Glide
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.storage.FirebaseStorage
    import com.google.firebase.storage.StorageReference
    import com.squareup.picasso.Picasso

    class searchmodel {
    var UserName: String? = null
    var Gender: String? = null
    var prof_img: String? = null
    var BITS_ID: String? = null
        private var storageReference: StorageReference? = null
        private var firebaseAuth: FirebaseAuth? = null


    internal constructor() {}
    constructor(UserName: String?, Gender: String?, BITS_ID: String?, prof_img: String?) {
        this.UserName = UserName
        this.Gender = Gender
        this.BITS_ID = BITS_ID
        this.prof_img = prof_img
    }

    fun getName(): String? {
        return UserName
    }
    fun setName(name: String) {
        UserName = name
    }

    @JvmName("getGender1")
    fun getGender(): String?{
        return Gender
    }

    @JvmName("setGender1")
    fun setGender(Gen: String) {
        Gender = Gen
    }
    fun getID(): String?{
        return BITS_ID
    }
    fun setID(ID: String){
        BITS_ID = ID
    }
    @JvmName("getPurl1")
    fun getPurl(): String? {
        return prof_img
    }

    @JvmName("setPurl1")
    fun setPurl(prof_img: String?) {
        this.prof_img = prof_img
    }
}