package com.example.coro1

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_admin_login.*

class AdminLogin : AppCompatActivity() {
    var firebaseAuth: FirebaseAuth?=null
    lateinit var loadingBar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)
        var login_btn = findViewById<Button>(R.id.login_btn)
        var phone_login_btn = findViewById<Button>(R.id.login_btn)
        var login_email = findViewById<EditText>(R.id.login_email)
        var login_password = findViewById<EditText>(R.id.login_password)
        var forget_password_link = findViewById<TextView>(R.id.forget_password_link)
        need_new_account_link?.setOnClickListener(){
            startActivity(Intent(this@AdminLogin,AdminRegister::class.java))
            finish()
        }
        firebaseAuth = FirebaseAuth.getInstance()
        var firebaseuser= FirebaseAuth.getInstance().currentUser
        if(firebaseuser!=null){
            finish()
            startActivity(Intent(this,MainActivity::class.java))
        }
        loadingBar = ProgressDialog(this)

        login_btn.setOnClickListener {

            var email_text = login_email?.text.toString().trim()
            var password_text = login_password?.text.toString().trim()

            if (TextUtils.isEmpty(email_text)) {
                Toast.makeText(applicationContext, "Email field can't be empty", Toast.LENGTH_SHORT)
                    .show()
            } else if (TextUtils.isEmpty(password_text)) {
                Toast.makeText(
                    applicationContext,
                    "password field can't be empty",
                    Toast.LENGTH_SHORT
                ).show()
            } else {


                loadingBar.setTitle("Logging You In")
                loadingBar.setMessage("Please wait , While we are Logging You In")
                loadingBar.setCanceledOnTouchOutside(true)
                loadingBar.show()


                firebaseAuth?.signInWithEmailAndPassword(email_text, password_text)
                    ?.addOnCompleteListener(object :
                        OnCompleteListener<AuthResult> {

                        override fun onComplete(task: Task<AuthResult>) {
                            if (task.isSuccessful()) {


                                Toast.makeText(
                                    applicationContext,
                                    "Logged In Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                loadingBar.dismiss()
                                val intent = Intent(this@AdminLogin, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                            } else {
                                var error = task.exception?.message
                                Toast.makeText(
                                    applicationContext,
                                    "Error -> " + error,
                                    Toast.LENGTH_SHORT
                                ).show()
                                loadingBar.dismiss()
                            }
                        }

                    })
            }


        }
    }
    private fun sendUserToMainActivity(){
        var loginIntent : Intent = Intent(this@AdminLogin , MainActivity::class.java)
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(loginIntent)
        finish()

    }
    fun goToMainActivity() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}
