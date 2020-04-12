package com.example.coro1

class Admin {
    private  var userid : String=""
    private var fname : String= ""
    private var lname : String= ""
    private var Adhar : String= ""
    private var members : String= ""
    private var address : String= ""
    private var pincode : String= ""
    private var adminid : String=""
    private var isStart : Boolean=false
    private var inProgress : Boolean=false
    private var isSuccessful : Boolean=false

    constructor()
    constructor(
        userid: String,
        fname: String,
        lname: String,
        Adhar: String,
        members: String,
        address: String,
        pincode: String,
        isStart : Boolean,
        inProgress: Boolean,
        isSuccessful:Boolean

    ) {
        this.userid = userid
        this.fname = fname
        this.lname = lname
        this.Adhar = Adhar
        this.members = members
        this.address = address
        this.pincode = pincode
        this.isStart=isStart
        this.isSuccessful =isSuccessful
        this.inProgress= inProgress

    }
    fun getUserId():String{
        return userid
    }
    fun getfname():String{
        return fname
    }
    fun getlname():String{
        return lname
    }
    fun getAdhar():String{
        return Adhar
    }
    fun getFamilymember():String{
        return members
    }
    fun getAddress():String{
        return address
    }
    fun getPincode():String{
        return pincode
    }
    fun getIsStart(): Boolean{
        return isStart
    }
    fun getIsSuccessful(): Boolean{
        return isSuccessful
    }
    fun getInProgress(): Boolean{
        return inProgress
    }

}