package com.example.togetherbphc.model

class ProfileModel {
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

    constructor()
    constructor(UserName: String?, Gender: String?, prof_img: String?, FB: String,Insta: String,DOB: String,Hobby1: String,
                Hobby2: String,Hobby3: String,Hobby4: String,Hobby5: String,HobbyOther: String,BITS_ID: String) {
        this.UserName = UserName
        this.Gender = Gender
        this.prof_img = prof_img
        this.FB = FB
        this.Insta = Insta
        this.DOB = DOB
        this.Hobby1 = Hobby1
        this.Hobby2 = Hobby2
        this.Hobby3 = Hobby3
        this.Hobby4 = Hobby4
        this.Hobby5 = Hobby5
        this.HobbyOther = HobbyOther
        this.BITS_ID = BITS_ID
    }

    @JvmName("getUserName1")
    fun getUserName(): String?{
        return UserName
    }

    @JvmName("getGender1")
    fun getGender(): String?{
        return Gender
    }

    fun getPurl(): String?{
        return prof_img
    }

    @JvmName("getFB1")
    fun getFB(): String?{
        return FB
    }

    @JvmName("getInsta1")
    fun getInsta(): String?{
        return Insta
    }

    @JvmName("getDOB1")
    fun getDOB(): String?{
        return DOB
    }

    @JvmName("getHobby11")
    fun getHobby1(): String?{
        return Hobby1
    }

    @JvmName("getHobby21")
    fun getHobby2(): String?{
        return Hobby2
    }

    @JvmName("getHobby31")
    fun getHobby3(): String?{
        return Hobby3
    }

    @JvmName("getHobby41")
    fun getHobby4():String?{
        return Hobby4
    }

    @JvmName("getHobby51")
    fun getHobby5(): String?{
        return Hobby5
    }

    @JvmName("getHobbyOther1")
    fun getHobbyOther(): String?{
        return HobbyOther
    }

    @JvmName("getBITS_ID1")
    fun getID(): String?{
        return BITS_ID
    }



}