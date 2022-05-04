package com.example.soldout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.EditTextPreference
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import com.example.soldout.Client
import com.example.soldout.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val button = findViewById<Button>(R.id.button2)
        val user = findViewById<EditText>(R.id.editTextTextPersonName)
        val serect = findViewById<EditText>(R.id.editTextTextPersonName2)
        val regex = "^[a-z0-9A-Z]+$";
        button.setOnClickListener {
            //send(user,sercet)
            if(!user.text.toString().matches(Regex(regex)))
                Toast.makeText(this,"密码只允许数字和字母",Toast.LENGTH_LONG).show()
            else if(!serect.text.toString().matches(Regex(regex)))
                Toast.makeText(this,"用户名只允许数字和字母",Toast.LENGTH_LONG).show()
            else
            {
                val a:Client= Client()
                val answer= a.start(user.text.toString()+"#2#"+serect.text.toString())


                if(answer=="true#")
                {
                    Toast.makeText(this,"登录成功",Toast.LENGTH_LONG).show()
                    val intent = Intent(this, WorkshopActivity::class.java)
                    intent.putExtra("key",user.text.toString());
                    startActivity(intent)
                }
                else if(answer=="false1#")
                    Toast.makeText(this,"不存在该用户名",Toast.LENGTH_LONG).show()
                else if(answer=="false2#")
                    Toast.makeText(this,"密码不正确",Toast.LENGTH_LONG).show()
                else
                    Toast.makeText(this,"bug"+answer,Toast.LENGTH_LONG).show()


            }

        }
        val button2 = findViewById<Button>(R.id.button)
        button2.setOnClickListener()
        {
            val intent=Intent(this,two::class.java)
            startActivity(intent)
        }
    }

}
