package com.example.soldout

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception


class OwnshopActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ownshop)

        //加网络权限
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        //接收username
        val intent: Intent = getIntent()
        val username= intent.getStringExtra("key").toString()

        //对菜单栏的各个按钮进行监听   页面的跳转
        val button_workshop=findViewById<Button>(R.id.button_workshop_ownshop)
        val button_market=findViewById<Button>(R.id.button_market_ownshop)
        val button_sell=findViewById<Button>(R.id.button_sell_ownshop)
        button_workshop.setOnClickListener{
            val intent = Intent(this, WorkshopActivity::class.java)
            intent.putExtra("key",username);
            startActivity(intent)
            finish()
        }
        button_market.setOnClickListener{
            val intent = Intent(this, MarketActivity::class.java)
            intent.putExtra("key",username);
            startActivity(intent)
            finish()
        }



        //数据库表的创建 Store Selling
        val dbstoreHelper = StoreDatabaseHelper(this, "Store.db", 1)
        val dbsellingHelper = SellingDatabaseHelper(this, "Selling.db", 1)
        //定义变量小店 金钱 仓库列表 三个window  三个Window Text
        val money_ownshop=findViewById<TextView>(R.id.money_ownshop)
        val storeList = ArrayList<Store>()//仓库列表
        val window1=findViewById<ImageView>(R.id.window1_ownshop)
        val window2=findViewById<ImageView>(R.id.window2_ownshop)
        val window3=findViewById<ImageView>(R.id.window3_ownshop)
        val textwindow1=findViewById<TextView>(R.id.Textwindow1_ownshop)
        val textwindow2=findViewById<TextView>(R.id.Textwindow2_ownshop)
        val textwindow3=findViewById<TextView>(R.id.Textwindow3_ownshop)


        //初始化金钱数量并在界面设置
        initMoney(money_ownshop,username)
        //对 Selling 和 Store 表进行初始化
        initTableStore(dbstoreHelper,username)
        initTableSelling(dbsellingHelper,username)

        //根据Selling Store数据库初始化页面
        initInterfaceOwnshop(this,dbstoreHelper,dbsellingHelper,
            window1,window2,window3,textwindow1,textwindow2,textwindow3,storeList)


        //对仓库的布局 根据StoreList 进行初始化
        val recyclerView=findViewById<RecyclerView>(R.id.store_ownshop)
        recyclerView.setLayoutManager(GridLayoutManager(this, 6))//设置网格布局
        val adapter = StoreAdapter(storeList)
        recyclerView.adapter = adapter

        //贩卖窗口监听  弹出选择框 选择操作
        window1.setOnClickListener{
            sellwindowListener(this,window1,"1",username,dbstoreHelper,dbsellingHelper,
                window1,window2,window3,textwindow1,textwindow2,textwindow3,storeList)
        }
        window2.setOnClickListener{
            sellwindowListener(this,window2,"2",username,dbstoreHelper,dbsellingHelper,
                window1,window2,window3,textwindow1,textwindow2,textwindow3,storeList)
        }
        window3.setOnClickListener{
            sellwindowListener(this,window3,"3",username,dbstoreHelper,dbsellingHelper,
                window1,window2,window3,textwindow1,textwindow2,textwindow3,storeList)
        }



    }//onCreate方法结束






}//Mainactivity结束






