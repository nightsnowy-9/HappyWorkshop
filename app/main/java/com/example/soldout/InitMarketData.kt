package com.example.soldout

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
//val id:String, val name:String, val solderName:String, val price:String, val count:String
//对Ownshop界面通过 Store Selling 表进行初始化

fun initMarketList(dbMarketHelper: MarketDatabaseHelper,marketList: ArrayList<SoldMarket>) {
    //清空storelist
    marketList.clear()

    // 查询Store表中所有的数据
    val db = dbMarketHelper.writableDatabase
    val cursor = db.query("Market", null, null, null, null, null, null)
    var i=0

    if (cursor.moveToFirst()) {
        do {
            i=cursor.getColumnIndex("id")
            var id = cursor.getString(i)
            i=cursor.getColumnIndex("name")
            var name = cursor.getString(i)
            i=cursor.getColumnIndex("solderName")
            var solderName = cursor.getString(i)
            i=cursor.getColumnIndex("price")
            var price = cursor.getString(i)
            i=cursor.getColumnIndex("count")
            var count=cursor.getString(i)


            setMarketImage(SoldCommodity(id,name,solderName,price,count),marketList)
      //      addStoreList(name,amount,storeList)

       //     println("WorkshopActivity "+ "Store name is $name amount is $amount")

        } while (cursor.moveToNext())
    }
    cursor.close()

}


