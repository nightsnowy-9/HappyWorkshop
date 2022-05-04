package com.example.soldout

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception


//数据库的 相关操作
//仓库表Store数据库的创建
class StoreDatabaseHelper(val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {
    private val createStore = "create table Store (name text PRIMARY KEY, amount int) "

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createStore)
        println("Create StoreDB succeeded")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}
//向Store中添加的语句  一次+1  表中没有就insert   有就在原数量+1
fun addToStoreDB(dbHelper: StoreDatabaseHelper ,name :String){
    try {
        dbHelper.writableDatabase.execSQL("INSERT INTO Store VALUES(?,?)", arrayOf( name ,"1"))
        println("insert into Store " + name  )
    }catch ( e : Exception) {//Store中已经有了该物品  数量+1
        dbHelper.writableDatabase.execSQL("Update Store set amount=amount+1 where name= ? ", arrayOf( name))
        println("inserted  Store again" +name + "+1")
    }
}
//修改Store中物品的数量  当表中没有的时候insert 有的时候update
fun insertToStoreDB(dbHelper: StoreDatabaseHelper , name: String, amount:String){
    try {
        dbHelper.writableDatabase.execSQL("INSERT INTO Store VALUES(?,?)", arrayOf( name , amount))
        println("set " +name + " "+amount  )
    }catch ( e : Exception) {//Store中已经有了该物品  数量+1
        dbHelper.writableDatabase.execSQL("Update Store set amount=? where name= ? ", arrayOf(amount, name))
        println("set " +name + " "+amount )
    }
}
//清空Store
fun clearStoreDB(dbHelper: StoreDatabaseHelper){
    dbHelper.writableDatabase.execSQL("delete from  Store ")
    println("clear Store")
}
//根据后端传递的数据来对 Store 表进行初始话
fun initTableStore(dbHelper: StoreDatabaseHelper,username:String){
    /*//先清空Store表
    clearStoreDB(dbHelper)
    //再接受后端数据 添加到Store表中
    insertToStoreDB(dbHelper,"map","3")
    insertToStoreDB(dbHelper,"grass","2")
    insertToStoreDB(dbHelper,"feather","1")
    insertToStoreDB(dbHelper,"pigment","3")
    insertToStoreDB(dbHelper,"paper","5")
    insertToStoreDB(dbHelper,"pen","6")
    insertToStoreDB(dbHelper,"water","8")
    insertToStoreDB(dbHelper,"stone","9")
    insertToStoreDB(dbHelper,"branch","20")*/


    //向后端发送更新仓库表的请求   //接收后端的字符串
    val message=send_4(username)

    //处理字符串更新Store表
    receive_4(message,dbHelper)

}





//正在生产的表 Working
class WorkingDatabaseHelper(val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {
    private val createWorking = "create table Working (id int PRIMARY KEY, name text, amount int , time int ) "

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createWorking)
        println("Create WorkingDB succeeded")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}
//向Working中添加的语句
fun addToWorkingDB(dbHelper: WorkingDatabaseHelper ,id:String, name :String, amount:String,time:String){
    try {
        dbHelper.writableDatabase.execSQL("INSERT INTO Working VALUES(?,?,?,?)", arrayOf( id ,name ,amount, time))
        println("insert into Working " +" id: "+ id +" name: " + name +" amount: "+ amount + " time: " +time  )
    }catch ( e : Exception) {//Store中已经有了该物品  数量+1
        println("insert Working  error !!!")
    }
}
//清空Working
fun clearWorkingDB(dbHelper: WorkingDatabaseHelper){
    dbHelper.writableDatabase.execSQL("delete from  Working ")
    println("clear Working")
}
//根据后端传递的数据来对 working 表进行初始话
fun initTableWorking(dbworkingHelper: WorkingDatabaseHelper,username: String){
    /*//先清空Working表
    clearWorkingDB(dbworkingHelper)
    //再接收后端数据 插入Worling 表中   后端传送的是结束时间 需计算剩余时间（单位min）
    addToWorkingDB(dbworkingHelper,"1","map","3","40")
    addToWorkingDB(dbworkingHelper,"2","branch","4","30")
    addToWorkingDB(dbworkingHelper,"3","water","2","1")*/


    //向后端发送更新仓库表的请求 //接收后端的字符串
    val message=send_6(username)

    //处理字符串更新Store表
    receive_6(message,dbworkingHelper)
}
//对working表 中正在生产的物品时间-1
fun updateTimeWorking(dbworkingHelper: WorkingDatabaseHelper){
    //遍历working表
    val db = dbworkingHelper.writableDatabase
    // 查询Working表中所有的数据
    val cursor = db.query("Working", null, null, null, null, null, null)
    var i=0
    if (cursor.moveToFirst()) {
        do {
            // 遍历Cursor对象，取出数据并打印
            i = cursor.getColumnIndex("id")
            var id = cursor.getString(i)
            i = cursor.getColumnIndex("name")
            var name = cursor.getString(i)
            i = cursor.getColumnIndex("amount")
            var amount = cursor.getString(i)
            i = cursor.getColumnIndex("time")
            var time = cursor.getString(i)
            //设置工坊窗口的图片 时间 个数的显示
            when (id) {
                "1" -> {
                    if(time=="0"){
                        time="0"
                    }else{
                        dbworkingHelper.writableDatabase.execSQL("Update Working set time=time-1 where id= 1 ")

                    }
                }
                "2" -> {
                    if(time=="0"){
                        time="0"
                    }else{
                        dbworkingHelper.writableDatabase.execSQL("Update Working set time=time-1 where id= 2 ")

                    }
                }
                "3" -> {
                    if(time=="0"){
                        time="0"
                    }else{
                        dbworkingHelper.writableDatabase.execSQL("Update Working set time=time-1 where id= 3 ")

                    }
                }
            }
            println("change time " + "Working windowid is $id  name is $name amount is $amount time is $time")

        } while (cursor.moveToNext())
    }
    cursor.close()
}


//正在贩卖的表  Selling
class SellingDatabaseHelper(val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {
    private val createSelling = " create table Selling (id int PRIMARY KEY, name text, price text ,amount int) "

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createSelling)
        println("Create SellingDB succeeded")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}
//向Selling中添加的语句
fun addToSellingDB(dbHelper: SellingDatabaseHelper ,id:String, name :String ,price:String ,amount:String){
    try {
        dbHelper.writableDatabase.execSQL("INSERT INTO Selling VALUES(?,?,?,?)", arrayOf( id ,name,price,amount))
        println("insert into Selling " +" id: "+id +" name: "+  name +" price: "+ price+" amount: " + amount )
    }catch ( e : Exception) {//Store中已经有了该物品  数量+1
        println("insert Selling  error !!!")
    }
}
//清空Selling
fun clearSellingDB(dbHelper: SellingDatabaseHelper){
    dbHelper.writableDatabase.execSQL("delete from  Selling ")
    println("clear Selling")
}
//根据后端传递的数据来对 selling 表进行初始话
fun initTableSelling(dbsellingHelper:SellingDatabaseHelper,username: String){
    /*//先清空Selling表
    clearSellingDB(dbsellingHelper)
    //接受后端数据 加入表中
    addToSellingDB(dbsellingHelper,"1","feather","9000","10")
    addToSellingDB(dbsellingHelper,"2","paper","8000","20")
    addToSellingDB(dbsellingHelper,"3","pen","6000","30")*/

    //向后端发送更新Selling表的请求 //接收后端的字符串
    val message=send_5(username)


    //处理字符串更新Selling表
    receive_5(message,dbsellingHelper)

}


