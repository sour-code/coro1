package com.example.coro1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_show_status.*

class show_status : AppCompatActivity() {
        private var userid = ""

    private var adminList: MutableList<Admin>?=null
    private var firebaseUser: FirebaseUser?=null
    private var admin: Admin?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_status)

        val intent = intent
        userid = intent.getStringExtra("userId")
        firebaseUser =FirebaseAuth.getInstance().currentUser
        var fname: TextView = findViewById(R.id.firstname_show)
       // var lname: TextView = findViewById(R.id.lastname_show)
        var adhar: TextView = findViewById(R.id.adhar_no)
        var familymember: TextView = findViewById(R.id.members)
        var address: TextView = findViewById(R.id.address_show)
    //    var pincode: TextView = findViewById(R.id.pincode_show)
        var startproces: Button = findViewById(R.id.start_process)
        var completed: Button = findViewById(R.id.successful_btn)
        var dot : ImageView = findViewById(R.id.status_status_show)
        retrieveAllinfo(fname,adhar,familymember,address,startproces,completed,dot)
        back_btn?.setOnClickListener(){
            finish()
        }
        start_process?.setOnClickListener(){
            val ref = FirebaseDatabase.getInstance().reference.child("Users").child(userid)
            var usermap = HashMap<String, Any>()
            usermap["isStart"] =true
            ref.updateChildren(usermap)
            Toast.makeText(applicationContext,"You Accepted the Problem..",Toast.LENGTH_SHORT).show()
            retrieveAllinfo(fname,adhar,familymember,address,startproces,completed,dot)
        }
        completed?.setOnClickListener(){
            val ref = FirebaseDatabase.getInstance().reference.child("Users").child(userid)
            var usermap = HashMap<String, Any>()
            usermap["isSuccessful"] =true
            ref.updateChildren(usermap)
            Toast.makeText(applicationContext,"Problem is resolved Successfully..",Toast.LENGTH_SHORT).show()
            retrieveAllinfo(fname,adhar,familymember,address,startproces,completed,dot)
        }


    }

    private fun retrieveAllinfo(
        fname: TextView,

        adhar: TextView,
        familymember: TextView,
        address: TextView,

        startproces: Button,
        completed: Button,
        dot: ImageView
    ) {
        val ref = FirebaseDatabase.getInstance().reference.child("Users").child(userid)
        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists())
                {var details = p0.getValue<Admin>(Admin::class.java)
                    fname.text = p0.child("fname").value.toString() + " "+p0.child("lname").value.toString()

                    adhar.text = p0.child("Adhar").value.toString()
                    address.text = p0.child("address").value.toString() +", "+ p0.child("pincode").value.toString()
                    familymember.text = p0.child("members").value.toString()

                    if(p0.child("isSuccessful").value ==true){
                        startproces.visibility = View.GONE
                        completed.visibility = View.GONE
                        dot.setImageResource(R.drawable.green)
                    }
                 else  if(p0.child("isStart").value ==true){
                        startproces.visibility = View.GONE
                        completed.visibility = View.VISIBLE
                        dot.setImageResource(R.drawable.orange)
                    }
                    else{startproces.visibility = View.VISIBLE
                       completed.visibility = View.GONE
                       dot.setImageResource(R.drawable.red)
                    }


                }

            }

        })
    }







}


