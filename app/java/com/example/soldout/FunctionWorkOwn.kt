package com.example.soldout

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

//Workshop和 Ownshop 共用的方法

//

//初始化金钱数量并在界面显示
fun initMoney(money_workshop:TextView , username:String){
    //发送金币请求
    val message=send_3(username)
    //接收金币数量
    var count=receive_3(message)

    //界面设置
    val money="金   $count G"
    money_workshop.setText(money)
}
//向仓库的StoreList添加物品 数量
fun addStoreList(name: String,amount: String,storeList: ArrayList<Store>){
    if(name=="water"){
        storeList.add(Store("water", amount, R.drawable.pic_water))
        println("add water")
    }else if(name=="branch"){
        storeList.add(Store("branch",amount, R.drawable.pic_branch))
        println("add branch")
    }else if(name=="feather"){
        storeList.add(Store("feather",amount, R.drawable.pic_feather))
        println("add feather")
    }else if(name=="gold"){
        storeList.add(Store("gold",amount, R.drawable.pic_gold))
        println("add gold")
    }else if(name=="grass"){
        storeList.add(Store("grass",amount, R.drawable.pic_grass))
        println("add grass")
    }else if(name=="map"){
        storeList.add(Store("map",amount, R.drawable.pic_map))
        println("add map")
    }else if(name=="paper"){
        storeList.add(Store("paper", amount, R.drawable.pic_paper))
        println("add paper")
    }else if(name=="pen"){
        storeList.add(Store("pen",amount, R.drawable.pic_pen))
        println("add pen")
    }else if(name=="stone"){
        storeList.add(Store("stone",amount, R.drawable.pic_stone))
        println("add stone")
    }else if(name=="pigment"){
        storeList.add(Store("pigment",amount, R.drawable.pic_pigment))
        println("add pigment")
    }else{
        println("add NULL!")
    }
}
//对工坊window窗口ImageView进行设置图片（工坊 和 贩卖 共用）
fun setImage(name:String,imageview: ImageView){
    if(name=="water"){
        imageview.setImageResource(R.drawable.pic_water)
        println(imageview.toString()+"set water")
    }else if(name=="branch"){
        imageview.setImageResource(R.drawable.pic_branch)
        println(imageview.toString()+"set branch")
    }else if(name=="feather"){
        imageview.setImageResource(R.drawable.pic_feather)
        println(imageview.toString()+"set feather")
    }else if(name=="gold"){
        imageview.setImageResource(R.drawable.pic_gold)
        println(imageview.toString()+"set gold")
    }else if(name=="grass"){
        imageview.setImageResource(R.drawable.pic_grass)
        println(imageview.toString()+"set grass")
    }else if(name=="map"){
        imageview.setImageResource(R.drawable.pic_map)
        println(imageview.toString()+"set map")
    }else if(name=="paper"){
        imageview.setImageResource(R.drawable.pic_paper)
        println(imageview.toString()+"set paper")
    }else if(name=="pen"){
        imageview.setImageResource(R.drawable.pic_pen)
        println(imageview.toString()+"set pen")
    }else if(name=="stone"){
        imageview.setImageResource(R.drawable.pic_stone)
        println(imageview.toString()+"set stone")
    }else if(name=="pigment"){
        imageview.setImageResource(R.drawable.pic_pigment)
        println(imageview.toString()+"set pigment")
    }else{
        println(imageview.toString()+"set NULL!!")
    }
}
//初始化仓库的arraylist通过Store表
fun initStore(dbstoreHelper: StoreDatabaseHelper,storeList: ArrayList<Store>) {
    //清空storelist
    storeList.clear()

    // 查询Store表中所有的数据
    val db = dbstoreHelper.writableDatabase
    val cursor = db.query("Store", null, null, null, null, null, null)
    var i=0
    if (cursor.moveToFirst()) {
        do {
            i=cursor.getColumnIndex("name")
            // 遍历Cursor对象，取出数据并打印
            val name = cursor.getString(i)
            i=cursor.getColumnIndex("amount")
            val amount = cursor.getString(i)
            //加入仓库的界面recyclerview  storelist
            addStoreList(name,amount,storeList)

            println("WorkshopActivity "+ "Store name is $name amount is $amount")

        } while (cursor.moveToNext())
    }
    cursor.close()

}



//Workshop 用的方法

//设置对工坊窗口  对话框  的监听
fun workwindowListener(mContext: Context, view: ImageView , id: String,username: String,
                       dbstoreHelper: StoreDatabaseHelper,dbworkHelper: WorkingDatabaseHelper,
                       window1: ImageView, window2: ImageView, window3: ImageView, text1:TextView, text2:TextView,
                       text3:TextView, storeList: ArrayList<Store>) {
    val lesson = arrayOf("生产地图","汲水", "探寻原料", "生产纸", "生产笔", "生产颜料",  "结束作业", "取消操作" )
    var builder = AlertDialog.Builder(mContext)
    var alert = builder.setIcon(R.mipmap.ic_launcher)
        .setTitle("选择想要进行的操作")
        .setItems(lesson,
            /*DialogInterface.OnClickListener { dialog, which -> Toast.makeText(mContext,
                    "你选择了" + lesson[which],
                    Toast.LENGTH_SHORT
                ).show()
            }*/
            DialogInterface.OnClickListener { dialog,
                                              which->
                when(which){
                    //生产地图
                    0->{
                        chooseWorkamount(mContext,view,id,"0",username,dbstoreHelper,dbworkHelper,
                            window1,window2,window3,text1,text2,text3,storeList)
                    }
                    //汲水
                    1-> {
                        chooseWorkamount(mContext,view,id,"1",username,dbstoreHelper,dbworkHelper,
                            window1,window2,window3,text1,text2,text3,storeList)
                    }
                    //搜寻原料
                    2->{
                        chooseWorkamount(mContext,view,id,"2",username,dbstoreHelper,dbworkHelper,
                            window1,window2,window3,text1,text2,text3,storeList)
                    }
                    //生产纸
                    3->{
                        chooseWorkamount(mContext,view,id,"3",username,dbstoreHelper,dbworkHelper,
                            window1,window2,window3,text1,text2,text3,storeList)
                    }
                    //生产笔
                    4->{
                        chooseWorkamount(mContext,view,id,"4",username,dbstoreHelper,dbworkHelper,
                            window1,window2,window3,text1,text2,text3,storeList)
                    }
                    //生产颜料
                    5->{
                        chooseWorkamount(mContext,view,id,"5",username,dbstoreHelper,dbworkHelper,
                            window1,window2,window3,text1,text2,text3,storeList)
                    }
                    //结束作业
                    6->{
                        //请求结束作业
                        val message=send_11(username,id)

                        val isOK=receive_11(message)
                        //判断是否为true

                        if(isOK){
                            //更新仓库
                            //更新工坊
                            initTableStore(dbstoreHelper,username)
                            initTableWorking(dbworkHelper,username)

                            /*//刷新workshop 界面
                            initInterfaceWorkshop(dbstoreHelper,dbworkHelper,
                                window1,window2,window3,text1,text2,text3,storeList)*/

                            //刷新workshop 界面
                            initInterfaceWorkshop(dbstoreHelper,dbworkHelper,
                                window1,window2,window3,text1,text2,text3,storeList)
                            Toast.makeText(mContext, "结束成功", Toast.LENGTH_SHORT).show()

                        }else{
                            Toast.makeText(mContext, "结束失败", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
                println(view.toString() + " selected " + lesson[which])
                Toast.makeText(mContext, "你选择了" + lesson[which], Toast.LENGTH_SHORT).show()

            }).create()
    alert.show()
}
//选择生产个数的 弹出输入框 监听  参数包括  窗口号  合成号  在该方法中向后端传递生产物品的信息 输入对话框
fun chooseWorkamount(mContext:Context , view: ImageView ,id: String, name:String,username: String ,
                     dbstoreHelper: StoreDatabaseHelper,dbworkHelper: WorkingDatabaseHelper,
                     window1: ImageView, window2: ImageView, window3: ImageView, text1:TextView, text2:TextView,
                     text3:TextView, storeList: ArrayList<Store>) {//name 是合成号地图为0 水为1 随机2 纸3 颜料4 笔5
    val inputamount = EditText(mContext)
    inputamount.isFocusable = true
    inputamount.setHint("请输入作业次数")
    val builder = AlertDialog.Builder(mContext)
    builder.setView(inputamount)

    when(name){
        "0"->{//map
            builder.setTitle("生产地图所需原料 笔:5 纸:5 ")
        }
        "1"->{//water
            builder.setTitle("汲水所需原料 地图:1")
        }
        "2"->{//原料
            builder.setTitle("随机生产所需原料 地图:1")
        }
        "3"->{//paper
            builder.setTitle("纸所需原料 水:10 树枝:10")
        }
        "4"->{//pen
            builder.setTitle("笔所需原料 羽毛:10 颜料:10")
        }
        "5"->{//颜料
            builder.setTitle("颜料所需原料 七色草:10 石块:5")
        }
    }

    /*builder.setTitle("输入生产数量")*/
    builder.setIcon(R.drawable.pic_gold)
    builder.setNegativeButton("取消", null)
    builder.setPositiveButton(("确认"),
        DialogInterface.OnClickListener { dialog, which ->
            val amount = inputamount.text.toString()
            println("windowid: "+id+ " name: "+ name + " amount: "+amount)

            //向后端建立通信 发8 收8 //接收字符串
            val message=send_8(username,id,name,amount)

            //收到8 true 时 对
            val istrue= receive_8(message)
            if(istrue){//更新数据库Working Store 表更新  并刷新界面
                initTableStore(dbstoreHelper,username)
                initTableWorking(dbworkHelper,username)

                //刷新workshop 界面
                initInterfaceWorkshop(dbstoreHelper,dbworkHelper,
                    window1,window2,window3,text1,text2,text3,storeList)
                Toast.makeText(mContext, "物品生产成功", Toast.LENGTH_SHORT).show()


            }else{//flase 时 通知生成失败
                Toast.makeText(mContext, "物品生产失败", Toast.LENGTH_SHORT).show()
            }


        })
    builder.show()
}
//对Workshop界面通过 Store Working 表进行初始化
fun initInterfaceWorkshop(
    dbstoreHelper: StoreDatabaseHelper, dbworkHelper: WorkingDatabaseHelper,
    window1: ImageView, window2: ImageView, window3: ImageView, text1:TextView, text2:TextView,
    text3:TextView, storeList: ArrayList<Store>){

    //初始化工坊界面
    val db = dbworkHelper.writableDatabase
    //将照片设置空 和文字
    window1.setImageDrawable(null)
    text1.setText("")
    window2.setImageDrawable(null)
    text2.setText("")
    window3.setImageDrawable(null)
    text3.setText("")

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
                    setImage(name, window1)
                    text1.setText(amount+"/"+time+"m")
                }
                "2" -> {
                    setImage(name, window2)
                    text2.setText(amount+"/"+time+"m")
                }
                "3" -> {
                    setImage(name, window3)
                    text3.setText(amount+"/"+time+"m")
                }
            }
            println("WorkshopActivity " + "Working windowid is $id name is $name amount is $amount time is $time")

        } while (cursor.moveToNext())
    }
    cursor.close()

    // 向仓库界面添加图片
    initStore(dbstoreHelper,storeList)
}

//对Workshop界面通过 Store Working 表进行初始化
fun updatetimeTextWorkshop(dbworkHelper: WorkingDatabaseHelper, text1:Int, text2:Int, text3:Int){
    //初始化工坊界面
    val db = dbworkHelper.writableDatabase
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

                    //text1.
                }
                "2" -> {

                    //text2.setText(amount+"/"+time+"m")
                }
                "3" -> {

                    //text3.setText(amount+"/"+time+"m")
                }
            }
            println("WorkshopActivity " + "Working windowid is $id name is $name amount is $amount time is $time")

        } while (cursor.moveToNext())
    }
    cursor.close()


}




//Ownshop用的方法

//设置对 贩卖窗口 的监听   对话框  winid为窗口号
fun sellwindowListener(mContext:Context , view: ImageView, winid:String,username: String,
                       dbstoreHelper: StoreDatabaseHelper,dbsellHelper: SellingDatabaseHelper,
                       window1: ImageView, window2: ImageView, window3: ImageView, text1:TextView, text2:TextView,
                       text3:TextView, storeList: ArrayList<Store>) {
    val lesson = arrayOf("地图","水", "树枝", "纸", "笔", "颜料", "羽毛","七色草","石块", "取消贩卖", "取消操作" )
    var builder = AlertDialog.Builder(mContext)
    var alert = builder.setIcon(R.mipmap.ic_launcher)
        .setTitle("选择想要贩卖的物品")
        .setItems(lesson,
            /*DialogInterface.OnClickListener { dialog, which -> Toast.makeText(mContext,
                    "你选择了" + lesson[which],
                    Toast.LENGTH_SHORT
                ).show()
            }*/
            DialogInterface.OnClickListener { dialog ,
                                              which->
                when(which){
                    //地图
                    0->{
                        chooseSellway(mContext,view,winid,"map",username,dbstoreHelper,dbsellHelper,
                            window1,window2,window3,text1,text2,text3,storeList)
                        //view.setImageResource(R.drawable.pic_map)
                    }
                    //水
                    1-> {
                        chooseSellway(mContext,view,winid,"water",username,dbstoreHelper,dbsellHelper,
                            window1,window2,window3,text1,text2,text3,storeList)
                        //view.setImageResource(R.drawable.pic_water)
                    }
                    //树枝
                    2->{
                        chooseSellway(mContext,view,winid,"branch",username,dbstoreHelper,dbsellHelper,
                            window1,window2,window3,text1,text2,text3,storeList)
                        //view.setImageResource(R.drawable.pic_branch)
                    }
                    //纸
                    3->{
                        chooseSellway(mContext,view,winid,"paper",username,dbstoreHelper,dbsellHelper,
                            window1,window2,window3,text1,text2,text3,storeList)
                        //view.setImageResource(R.drawable.pic_paper)
                    }
                    //笔
                    4->{
                        chooseSellway(mContext,view,winid,"pen",username,dbstoreHelper,dbsellHelper,
                            window1,window2,window3,text1,text2,text3,storeList)
                        //view.setImageResource(R.drawable.pic_pen)
                    }
                    //颜料
                    5->{
                        chooseSellway(mContext,view,winid,"pigment",username,dbstoreHelper,dbsellHelper,
                            window1,window2,window3,text1,text2,text3,storeList)
                        //view.setImageResource(R.drawable.pic_pigment)
                    }
                    //羽毛
                    6->{
                        chooseSellway(mContext,view,winid,"feather",username,dbstoreHelper,dbsellHelper,
                            window1,window2,window3,text1,text2,text3,storeList)
                        //view.setImageResource(R.drawable.pic_feather)
                    }
                    //七色草
                    7->{
                        chooseSellway(mContext,view,winid,"grass",username,dbstoreHelper,dbsellHelper,
                            window1,window2,window3,text1,text2,text3,storeList)
                        //view.setImageResource(R.drawable.pic_grass)
                    }
                    //石块
                    8->{
                        chooseSellway(mContext,view,winid,"stone",username,dbstoreHelper,dbsellHelper,
                            window1,window2,window3,text1,text2,text3,storeList)
                        //view.setImageResource(R.drawable.pic_stone)
                    }
                    //停止贩卖
                    9->{
                        //请求下架商品
                        val message=send_12(username,winid)

                        val isOK=receive_12(message)
                        //判断是否为true

                        if(isOK){
                            //更新仓库
                            //更新工坊
                            initTableStore(dbstoreHelper,username)
                            initTableSelling(dbsellHelper,username)

                            /*//刷新workshop 界面
                            initInterfaceWorkshop(dbstoreHelper,dbworkHelper,
                                window1,window2,window3,text1,text2,text3,storeList)*/

                            //刷新Ownshop 界面
                            //根据Selling Store数据库初始化页面
                            initInterfaceOwnshop(mContext,dbstoreHelper,dbsellHelper,
                                window1,window2,window3,text1,text2,text3,storeList)

                            Toast.makeText(mContext, "下架成功", Toast.LENGTH_SHORT).show()

                        }else{
                            Toast.makeText(mContext, "下架失败", Toast.LENGTH_SHORT).show()
                        }

                        /*view.setImageDrawable(null)*/
                    }
                }
                println(view.toString() + " selected " + lesson[which])
                Toast.makeText(mContext, "你选择了" + lesson[which], Toast.LENGTH_SHORT).show()

            }).create()
    alert.show()
}
//选择出售个数，价格的弹出输入框监听  参数包括 窗口号 物品名称  在该方法中向后端传递贩卖物品的信息 输入对话框
fun chooseSellway(mContext:Context , view: ImageView ,id: String, name:String,username: String,
                  dbstoreHelper: StoreDatabaseHelper,dbsellHelper: SellingDatabaseHelper,
                  window1: ImageView, window2: ImageView, window3: ImageView, text1:TextView, text2:TextView,
                  text3:TextView, storeList: ArrayList<Store>) {
    /*val inputamount = EditText(mContext)
   inputamount.isFocusable = true
   inputamount.setHint("出售数量")*/
    val builder = AlertDialog.Builder(mContext)
    val viewinput: View = LayoutInflater.from(mContext).inflate(R.layout.inputsellway, null)
    builder.setView(viewinput)
    val inputprice = viewinput.findViewById(R.id.input_sellprice) as EditText
    val inputamount = viewinput.findViewById(R.id.input_sellamount) as EditText
    builder.setTitle("输入出售方式")
    builder.setIcon(R.drawable.pic_gold)
    builder.setNegativeButton("取消", null)
    builder.setPositiveButton(("确认"),
        DialogInterface.OnClickListener { dialog, which ->
            val price = inputprice.text.toString()
            val amount = inputamount.text.toString()
            println("windowid: "+id+" name: "+ name +" price: "+price+" amount: "+amount)

            //向后端建立通信 发10 收 10  //接收字符串
            val message=send_10(username,id,name,price,amount)

            //收到10
            val istrue= receive_10(message)
            if(istrue){//true 时 对Selling Store 表更新  并刷新界面
                //初始化数据库
                initTableSelling(dbsellHelper,username)
                initTableStore(dbstoreHelper,username)
                //刷新界面
                //根据Selling Store数据库初始化页面
                initInterfaceOwnshop(mContext,dbstoreHelper,dbsellHelper,
                    window1,window2,window3,text1,text2,text3,storeList)

            }else{//flase 时 通知卖出失败
                Toast.makeText(mContext, "上架物品失败" , Toast.LENGTH_SHORT).show()

            }
        })

    builder.show()
}
//对Ownshop界面通过 Store Selling 表进行初始化
fun initInterfaceOwnshop(mContext: Context,dbstoreHelper: StoreDatabaseHelper,dbsellHelper: SellingDatabaseHelper,
                          window1: ImageView,window2: ImageView,window3: ImageView ,text1:TextView,text2:TextView ,
                         text3:TextView,storeList: ArrayList<Store>){

    //初始化工坊界面
    val db = dbsellHelper.writableDatabase
    //将照片设置空 和文字
    window1.setImageDrawable(null)
    text1.setText("")
    window2.setImageDrawable(null)
    text2.setText("")
    window3.setImageDrawable(null)
    text3.setText("")

    // 查询Working表中所有的数据
    val cursor = db.query("Selling", null, null, null, null, null, null)
    var i=0
    if (cursor.moveToFirst()) {
        do {
            // 遍历Cursor对象，取出数据并打印
            i=cursor.getColumnIndex("id")
            var id = cursor.getString(i)
            i=cursor.getColumnIndex("name")
            var name = cursor.getString(i)
            i=cursor.getColumnIndex("price")
            var price = cursor.getString(i)
            i=cursor.getColumnIndex("amount")
            var amount = cursor.getString(i)
            //设置工坊窗口的图片 金额 个数的显示
            when (id) {
                "1" -> {
                    setImage(name, window1)
                    text1.setText(amount+"/"+price+"g")
                }
                "2" -> {
                    setImage(name, window2)
                    text2.setText(amount+"/"+price+"g")
                }
                "3" -> {
                    setImage(name, window3)
                    text3.setText(amount+"/"+price+"g")
                }
            }
            println("WorkshopActivity "+ "Selling windowid is $id name is $name price is $price amount is $amount ")

        } while (cursor.moveToNext())

     }
    cursor.close()

    // 向仓库界面添加图片
    initStore(dbstoreHelper,storeList)
}


