package com.example.soldout

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception
//val id:String, val name:String, val solderName:String, val price:String, val count:String
//仓库表Store数据库的创建
class MarketDatabaseHelper(val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {
    private val createMarket = "create table Market (id text PRIMARY KEY, name text, solderName text,price text,count text) "

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(createMarket)
        println("Create MarketDB succeeded")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}

//修改Store中物品的数量  当表中没有的时候insert 有的时候update
fun insertToMarketDB(dbHelper: MarketDatabaseHelper , id:String ,name:String ,solderName:String ,price:String ,count:String){
    try {
        dbHelper.writableDatabase.execSQL("INSERT INTO Market VALUES(?,?,?,?,?)", arrayOf( id,name,solderName,price,count))
        println("set " +name + " "  )
    }catch ( e : Exception) {//Store中已经有了该物品  数量+1
        dbHelper.writableDatabase.execSQL("Update Market set name=?, solderName=?, price=?, count=?  where id= ? ", arrayOf(name,solderName,price,count,id))
        println("set " +name + " ")
    }
}
//清空Store
fun clearMarketDB(dbHelper: MarketDatabaseHelper){
    dbHelper.writableDatabase.execSQL("delete from  Market ")
    println("clear Market")
}
//根据后端传递的数据来对 Store 表进行初始话
fun initTableMarket(dbHelper: MarketDatabaseHelper,username:String,page:String):ArrayList<Receive7>{
    //先清空Store表
    clearMarketDB(dbHelper)
    //再接受后端数据 添加到Store表中
//    insertToMarketDB(dbHelper,"1","map","王一","3","1")
//    insertToMarketDB(dbHelper,"2","grass","王二","2","2")
//    insertToMarketDB(dbHelper,"3","feather","王三","1","3")
//    insertToMarketDB(dbHelper,"4","pigment","王四","3","4")
//    insertToMarketDB(dbHelper,"5","paper","王五","5","5")
//    insertToMarketDB(dbHelper,"6","pen","王六","6","6")
//    insertToMarketDB(dbHelper,"7","water","王七","8","7")
//    insertToMarketDB(dbHelper,"8","stone","王八","9","8")
//    insertToMarketDB(dbHelper,"9","branch","王九","20","9")
    val message=send_7(username,page)
    return receive_7(message,dbHelper)

//    val receiveTest=ArrayList<Receive7>()
//    receiveTest.add(Receive7(SoldCommodity("1","1","1","1","1"),"1","2"))
//    return receiveTest
}
