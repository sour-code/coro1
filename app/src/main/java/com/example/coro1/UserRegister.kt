package com.example.coro1

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_admin_register.*
import kotlinx.android.synthetic.main.activity_admin_register.register_email
import kotlinx.android.synthetic.main.activity_admin_register.register_password
import kotlinx.android.synthetic.main.activity_user_register.*

class UserRegister : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    var RootRef : DatabaseReference?= null
    lateinit var loadingBar: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_register)
        var already_have_account_link = findViewById<TextView>(R.id.already_have_account_link)
        var register_email = findViewById<EditText>(R.id.register_email)
        var register_password = findViewById<EditText>(R.id.register_password)
        var register_btn = findViewById<Button>(R.id.register_btn)
        if(FirebaseAuth.getInstance().currentUser!=null){
            finish()
            startActivity(Intent(this,user_main::class.java))
        }
        firebaseAuth = FirebaseAuth.getInstance()
        RootRef = FirebaseDatabase.getInstance().getReference()
        loadingBar = ProgressDialog(this)

        already_have_account_link.setOnClickListener {
            startActivity(Intent(this@UserRegister , UserLogin::class.java))
        }

        register_btn.setOnClickListener {
            CreateNewAccount()
        }

    }

    private fun CreateNewAccount(){
        var email_text = register_email!!.text.toString().trim()
        var password_text = register_password!!.text.toString().trim()

        if(TextUtils.isEmpty(email_text)){
            Toast.makeText(applicationContext,"Email field can't be empty", Toast.LENGTH_SHORT).show()
        }
        else if(TextUtils.isEmpty(password_text)){
            Toast.makeText(applicationContext,"password field can't be empty", Toast.LENGTH_SHORT).show()
        }
        else{

            loadingBar.setTitle("Creating New Account")
            loadingBar.setMessage("Please wait , While we are creating your account")
            loadingBar.setCanceledOnTouchOutside(true)
            loadingBar.show()


            firebaseAuth?.createUserWithEmailAndPassword(email_text,password_text)?.addOnCompleteListener(object :
                OnCompleteListener<AuthResult> {

                override fun onComplete(task: Task<AuthResult>) {
                    if(task.isSuccessful()){


                        Toast.makeText(applicationContext,"Account Created Successfully" , Toast.LENGTH_SHORT).show()
                        loadingBar.dismiss()
                        startActivity(Intent(this@UserRegister , user_main::class.java))
                    }
                    else{
                        var error = task.exception?.message
                        Toast.makeText(applicationContext,"Error -> "+ error , Toast.LENGTH_SHORT).show()
                        loadingBar.dismiss()
                    }
                }

            })
        }
    }

}