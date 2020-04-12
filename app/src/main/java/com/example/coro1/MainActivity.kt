package com.example.coro1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var admin: Admin?=null
    private var adminadapter: AdminAdapter?=null
    private var adminMutableList: MutableList<Admin>?=null
    private var firebaseUser: FirebaseUser?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var recyclerView : RecyclerView?=null
        recyclerView = findViewById(R.id.recyclerview_admin)
        recyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.reverseLayout=false
        linearLayoutManager.stackFromEnd=true
        recyclerView.layoutManager=linearLayoutManager
        adminMutableList= ArrayList()
adminadapter= AdminAdapter(this,adminMutableList!!)
        recyclerView.adapter=adminadapter


        firebaseUser = FirebaseAuth.getInstance().currentUser

       showrequest()
        logout_btn?.setOnClickListener(){
            FirebaseAuth.getInstance().signOut()
                Toast.makeText(applicationContext,"You are Successfully Logout..",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity, AdminRegister::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

    }

    private fun showrequest() {
        val ref = FirebaseDatabase.getInstance().reference.child("Users")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                adminMutableList?.clear()
for(p0 in snapshot.children)
{  if(p0.exists()){
                    val user = p0.getValue<Admin>(Admin::class.java)
                    val fname = p0.child("fname").value.toString()
                    val lname = p0.child("lname").value.toString()
                    val adhar = p0.child("Adhar").value.toString()
                    val member = p0.child("members").value.toString()
                    val address = p0.child("address").value.toString()
                    val pincode = p0.child("pincode").value.toString()
                    val userid = p0.child("userid").value.toString()
                    val adminid = p0.child("adminid").value.toString()
                    val isstart = p0.child("isStart").value.toString()
                    val inprogress = p0.child("inProgress").value.toString()
                    val issuccessful = p0.child("isSuccessful").value.toString()

                     adminMutableList!!.add(Admin(p0.child("userid").value.toString(),
                        p0.child("fname").value.toString(),
                        p0.child("lname").value.toString(),
                        p0.child("Adhar").value.toString(),
                        p0.child("members").value.toString(),
                        p0.child("address").value.toString(),
                        p0.child("pincode").value.toString(),

                       isstart.toBoolean(),inprogress.toBoolean(),issuccessful.toBoolean()))

                  adminadapter!!.notifyDataSetChanged()
                }}
                // adminMutableList?.clear()


            }

        })

    }
}
