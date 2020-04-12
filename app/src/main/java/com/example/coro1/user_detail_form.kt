package com.example.coro1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_user_detail_form.*

class user_detail_form : AppCompatActivity() {
    var mAuth : FirebaseUser?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail_form)
        mAuth= FirebaseAuth.getInstance().currentUser
        var fname : EditText = findViewById(R.id.firstname)
        var lnmae : EditText = findViewById(R.id.lastname)
        var adhar : EditText = findViewById(R.id.adhaar)
        var mobile : EditText = findViewById(R.id.mobile)
        var address : EditText = findViewById(R.id.address)
        var pincode : EditText = findViewById(R.id.pincode)

        submit_req_btn.setOnClickListener(){
            val ref = FirebaseDatabase.getInstance().reference.child("Users").child(mAuth!!.uid)
            var user = HashMap<String,Any>()
            user["fname"]= fname.text.toString()
            user["lname"]= lnmae.text.toString()
            user["Adhar"]= adhar.text.toString()
            user["members"]= mobile.text.toString()
            user["address"]= address.text.toString()
            user["pincode"]= pincode.text.toString()
            user["userid"]= mAuth!!.uid
            user["adminid"]= mAuth!!.uid
            user["isStart"]= false
            user["inProgress"]=false
            user["isSuccessful"]= false

            ref.setValue(user)
            Toast.makeText(applicationContext,"Request is Submitted Successfully",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@user_detail_form,user_main::class.java))
            finish()
        }



    }
}
