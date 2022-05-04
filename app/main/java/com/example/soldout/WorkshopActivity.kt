package com.example.soldout


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.StrictMode
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*


class WorkshopActivity : AppCompatActivity() {

    lateinit var timeChangeReceiver: TimeChangeReceiver //实例化时间监听
    //lateinit var timer: Timer
    //数据库表的创建 Store Working
    val dbstoreHelper = StoreDatabaseHelper(this, "Store.db", 1)
    val dbworkingHelper = WorkingDatabaseHelper(this, "Working.db", 1)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.workshop)

        //加网络权限
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        //接收username
        val intent: Intent = getIntent()
        val username= intent.getStringExtra("key").toString()

        //注册时间监听  每秒钟将前端数据库时间-1  每秒更新金币数
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.TIME_TICK")
        timeChangeReceiver = TimeChangeReceiver()
        registerReceiver(timeChangeReceiver, intentFilter)

        //对菜单栏的各个按钮进行监听   页面的跳转
        val button_workshop=findViewById<Button>(R.id.button_workshop_workshop)
        val button_market=findViewById<Button>(R.id.button_market_workshop)
        val button_sell=findViewById<Button>(R.id.button_sell_workshop)
        button_sell.setOnClickListener{
            val intent = Intent(this, OwnshopActivity::class.java)
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


        //定义变量工坊 金钱 三个window 三个窗口下的个数时间Text
        val storeList = ArrayList<Store>()//仓库列表
        val window1_workshop=findViewById<ImageView>(R.id.window1_workshop)
        val window2_workshop=findViewById<ImageView>(R.id.window2_workshop)
        val window3_workshop=findViewById<ImageView>(R.id.window3_workshop)
        val textwindow1=findViewById<TextView>(R.id.Textwindow1_workshop)
        val textwindow2=findViewById<TextView>(R.id.Textwindow2_workshop)
        val textwindow3=findViewById<TextView>(R.id.Textwindow3_workshop)
        val money_workshop=findViewById<TextView>(R.id.money_workshop)

       //界面的刷新方法
      /*  val myHandler= Handler(){
            fun handleMessage(msg:Message){
                when(msg.what){
                    0->{
                        //根据Working Store表 初始化界面
                        initInterfaceWorkshop(dbstoreHelper,dbworkingHelper,
                            window1_workshop,window2_workshop,window3_workshop,textwindow1,
                            textwindow2,textwindow3,storeList)
                    }
                    break
                }
            }
        }*/


        //初始化金钱数量并在界面设置
        initMoney(money_workshop,username)
        //对数据库 Workshop  和 Store 表初始化
        initTableStore(dbstoreHelper,username)
        initTableWorking(dbworkingHelper,username)

        //根据Working Store表 初始化界面
        initInterfaceWorkshop(dbstoreHelper,dbworkingHelper,
            window1_workshop,window2_workshop,window3_workshop,textwindow1,
            textwindow2,textwindow3,storeList)


        //对仓库的布局 根据StoreList 进行初始化
        val recyclerView=findViewById<RecyclerView>(R.id.store_workshop)
        recyclerView.setLayoutManager(GridLayoutManager(this, 6))//设置网格布局
        val adapter = StoreAdapter(storeList)
        recyclerView.adapter = adapter

        //工坊窗口监听  弹出选择框 选择操作
        window1_workshop.setOnClickListener{
            workwindowListener(this,window1_workshop,"1",username,dbstoreHelper,dbworkingHelper,
                window1_workshop,window2_workshop,window3_workshop,textwindow1, textwindow2,textwindow3,storeList)
        }
        window2_workshop.setOnClickListener{
            workwindowListener(this,window2_workshop,"2",username,dbstoreHelper,dbworkingHelper,
                window1_workshop,window2_workshop,window3_workshop,textwindow1, textwindow2,textwindow3,storeList)
        }
        window3_workshop.setOnClickListener{
            workwindowListener(this,window3_workshop,"3",username,dbstoreHelper,dbworkingHelper,
                window1_workshop,window2_workshop,window3_workshop,textwindow1, textwindow2,textwindow3,storeList)
        }

        //


    }//onCreate方法结束



    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(timeChangeReceiver)//消除时间监听
    }

    //界面的刷新的方法
    open fun flush_workshop(view: View?) {
        finish()
        val intent = Intent(this, WorkshopActivity::class.java)
        startActivity(intent)
    }


    //时间监听
    inner class TimeChangeReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Toast.makeText(context, "Time has changed", Toast.LENGTH_SHORT).show()
            //获取时间
            //val c = Calendar.getInstance()
            val df = SimpleDateFormat("HH:mm")
            val formattedDate = df.format(System.currentTimeMillis())
            //数据库的结束时间-1
            updateTimeWorking(dbworkingHelper)

            //初始化时间

            //根据Working Store表 初始化界面




        }
    }


}//Mainactivity结束


