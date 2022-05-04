package com.example.soldout

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.*
import android.content.IntentFilter
import android.os.StrictMode
import android.widget.EditText
import java.sql.Time


class two : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.two)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val button1=findViewById<Button>(R.id.button)
        val s1=findViewById<EditText>(R.id.editTextTextPersonName)

        val s2=findViewById<EditText>(R.id.editTextTextPersonName2)
        var text2:String=s2.text.toString();
        val s3=findViewById<EditText>(R.id.editTextTextPersonName3)
        var text3:String=s3.text.toString();
        val regex = "^[a-z0-9A-Z]+$";
        button1.setOnClickListener(){
            //Toast.makeText(this,s2.text.toString()+s3.text.toString(),Toast.LENGTH_LONG).show()

            if(s2.text.toString()!=s3.text.toString())
                 Toast.makeText(this,"两次输入的密码不相同"+text2+text3,Toast.LENGTH_LONG).show()
             else if(!s2.text.toString().matches(Regex(regex)))
                 Toast.makeText(this,"密码只允许数字和字母",Toast.LENGTH_LONG).show()
            else if(!s1.text.toString().matches(Regex(regex)))
                Toast.makeText(this,"用户名只允许数字和字母",Toast.LENGTH_LONG).show()
             else
            {
                val a:Client=Client()

//                val answer= a.start(s1.text.toString()+"#1#"+s2.text.toString())
                val answer= a.start(s1.text.toString()+"#1#"+s2.text.toString())
                if(answer=="true#")
                    Toast.makeText(this,"注册成功",Toast.LENGTH_LONG).show()
                else if(answer=="false#")
                    Toast.makeText(this,"该用户名已被注册",Toast.LENGTH_LONG).show()
                else
                    Toast.makeText(this,"bug"+answer,Toast.LENGTH_LONG).show()


            }


            //将密码传输到后台服务器
        }

        val button=findViewById<Button>(R.id.button2)
        button.setOnClickListener(){
            finish()

        }

    }


}

