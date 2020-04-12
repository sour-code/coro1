package com.example.coro1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class user_main : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_main)

        var food_prblm: TextView = findViewById(R.id.food_problem)

        food_prblm.setOnClickListener(){
            startActivity(Intent(this@user_main,user_detail_form::class.java))
            finish()
        }
    }
}
