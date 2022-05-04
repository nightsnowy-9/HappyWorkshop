package com.example.soldout

//交互3 查金币  参数为 通信的类 用户名
fun send_3( username:String):String{
    val message="$username#3"
    println("send_3: "+message)
    //发送message
    //发送
    val a:Client= Client()
    val answer= a.start(message)
    return answer

}
//参数为收到的String
fun receive_3 (input:String):String{
    println("receive3: "+ input)

    var isProduced=StringBuilder()
    val inputChars=input.toCharArray()
    for(i in 0..input.length-1){
        if(inputChars[i]!='#'){
            isProduced.append(inputChars[i])
        }
    }
    return isProduced.toString()


}


//交互4 查仓库
fun send_4( username:String):String{
    val message="$username#4"
    println("send_4: "+message)
    //发送message

    //发送
    val a:Client= Client()
    val answer= a.start(message)
    return answer
}
//查询仓库内容（物品名及数量） //一次性传完 物品名#数量#物品名#数量
fun receive_4(input:String,dbstoreHelper: StoreDatabaseHelper){
    //清空Store表
    clearStoreDB(dbstoreHelper)
    val inputChars=input.toCharArray()
    var name=StringBuilder()
    var amount=StringBuilder()
    var order=0
    for(i in 0..input.length-1){
        if(inputChars[i]=='#'){
            order++;
            if(order==2){
                //插入数据库
                insertToStoreDB(dbstoreHelper,name.toString(),amount.toString())
                order=0
                name=StringBuilder()
                amount=StringBuilder()
            }
            //grass#王1#001#15#1#water#王2#002#12#2#
        }else if(order==0){
            name.append(inputChars[i])
        }else{
            amount.append(inputChars[i])
        }
    }
    println("receive_4 is over")
}

//交互5 查卖的商店
fun send_5(username: String):String{
    val message="$username#5"
    println("send_5: "+message)
    //发送message
    //发送
    val a:Client= Client()
    val answer= a.start(message)
    return answer
}
//接收 商店内容（窗口号及物品名 单价 数量）  更新Selling表
fun receive_5(input:String,dbsellHelper: SellingDatabaseHelper){
    //更新Selling表
    clearSellingDB(dbsellHelper)
    val inputChars=input.toCharArray()
    var windowNumber=StringBuilder()
    var name=StringBuilder()
    var price=StringBuilder()
    var amount=StringBuilder()
    var order=0
    for(i in 0..input.length-1){
        if(inputChars[i]=='#'){
            order++;
            if(order==4){
                //插入Selling表
                addToSellingDB(dbsellHelper,windowNumber.toString(),name.toString(),price.toString(),amount.toString())

                order=0
                windowNumber=StringBuilder()
                name=StringBuilder()
                price=StringBuilder()
                amount=StringBuilder()
            }
            //grass#王1#001#15#1#water#王2#002#12#2#
        }else if(order==0){
            windowNumber.append(inputChars[i])
        }else if(order==1){
            name.append(inputChars[i])
        }else if(order==2){
            price.append(inputChars[i])
        }else{
            amount.append(inputChars[i])
        }
    }
    println("receive_5 is over")
}

//交互6 查工坊
fun send_6(username: String):String{

    val message="$username#6"
    println("send_6: "+message)
    //发送message
    //发送
    val a:Client= Client()
    val answer= a.start(message)
    return answer

}

//正在生产的东西（窗口号（1~3） 物品名 数量 结束时间
fun receive_6(input:String,dbworkHelper: WorkingDatabaseHelper){
    //清空working表
    clearWorkingDB(dbworkHelper)
    val inputChars=input.toCharArray()
    var windowNumber=StringBuilder()
    var name=StringBuilder()
    var amount=StringBuilder()
    var minutes=StringBuilder()
    var order=0
    for(i in 0..input.length-1){
        if(inputChars[i]=='#'){
            order++;
            if(order==4){
                //插入数据库
                addToWorkingDB(dbworkHelper,windowNumber.toString(),name.toString(),amount.toString(),minutes.toString())

                order=0
                windowNumber=StringBuilder()
                name=StringBuilder()
                amount=StringBuilder()
                minutes=StringBuilder()
            }
            //grass#王1#001#15#1#water#王2#002#12#2#
        }else if(order==0){
            windowNumber.append(inputChars[i])
        }else if(order==1){
            name.append(inputChars[i])
        }else if(order==2){
            amount.append(inputChars[i])
        }else{
            minutes.append(inputChars[i])
        }
    }

   println("receive_6 is over")
}

//交互7 查市场
fun send_7(username: String , pagenumber: String):String{
    // .#7#页数
    val message="$username#7#$pagenumber"
    println("send_7: "+message)
    //发送message
    //发送
    val a:Client= Client()
    val answer= a.start(message)
    return answer
}

//市场返回的
class Receive7(val commodity: SoldCommodity,val currentPage:String,val totalPages:String)
fun receive_7(input:String,dbworkHelper: MarketDatabaseHelper):ArrayList<Receive7>{
    clearMarketDB(dbworkHelper)
    val receive7List=ArrayList<Receive7>()
    val commodityList=ArrayList<SoldCommodity>()
    val inputChars=input.toCharArray()
    var name=StringBuilder()
    var solderName=StringBuilder()
    var id=StringBuilder()
    var price=StringBuilder()
    var count=StringBuilder()
    var currentPage=StringBuilder()
    var totalPages=StringBuilder()
    var order=0
    for(i in 0..input.length-1){
        if(inputChars[i]=='#'){
            order++;
            if(order==7){
                //更新list,清空var
                //  commodityList.add(SoldCommodity(id.toString(),name.toString(),solderName.toString(),
                //      price.toString(),count.toString()))
                insertToMarketDB(dbworkHelper,id.toString(),name.toString(),solderName.toString(),
                    price.toString(),count.toString())
                receive7List.add(
                    Receive7(
                        SoldCommodity(
                            id.toString(), name.toString(), solderName.toString(),
                            price.toString(), count.toString()
                        ), currentPage.toString(), totalPages.toString()
                    )
                )
                order=0
                name=StringBuilder()
                solderName=StringBuilder()
                id=StringBuilder()
                price=StringBuilder()
                count=StringBuilder()
                currentPage=StringBuilder()
                totalPages=StringBuilder()
                val r=receive7List.get(0)
            }
            //grass#王1#001#15#1#water#王2#002#12#2#
        }else if(order==0){
            name.append(inputChars[i])
        }else if(order==1){
            solderName.append(inputChars[i])
        }else if(order==2){
            id.append(inputChars[i])
        }else if(order==3){
            price.append(inputChars[i])
        }else if(order==4){
            count.append(inputChars[i])
        }else if(order==5){
            currentPage.append(inputChars[i])
        }else if(order==6){
            totalPages.append(inputChars[i])
        }
    }
    return receive7List
}

//交互8 工坊合成作业 用户名 窗口号 物品号 数量
fun send_8(username: String, windowid:String,goodid:String, amount:String):String{
    //前端给欲合成的原料号(地图为0 水为1 随机2 纸3 颜料4 笔5 ) 和要完成的作业次数
    // 用户名#8#窗口号（1~3）#原料号 # 生产个数
    val message="$username#8#$windowid#$goodid#$amount"
    println("send_8: "+message)
    //发送message
    //发送
    val a:Client= Client()
    val answer= a.start(message)
    return answer

}
fun receive_8(input: String):Boolean{
    var isProduced=StringBuilder()
    val inputChars=input.toCharArray()
    for(i in 0..input.length-1){
        if(inputChars[i]!='#'){
            isProduced.append(inputChars[i])
        }
    }
    if(isProduced.toString()=="true"){
        return true
    }
    return false
}

//交互9 购买物品
fun send_9(username: String,orderID:String,amount:String):String{
    //.#9#订单ID#数量
    val message="$username#9#$orderID#$amount"
    println("send_9: "+message)
    //发送message
    //发送
    val a:Client= Client()
    val answer= a.start(message)
    return answer

}
fun receive_9(input: String):Boolean{
    var isPurchased=StringBuilder()
    val inputChars=input.toCharArray()
    for(i in 0..input.length-1){
        if(inputChars[i]!='#'){
            isPurchased.append(inputChars[i])
        }
    }
    if(isPurchased.toString()=="true"){
        return true
    }
    return false
}

//交互10 上架卖的物品
fun send_10(username: String,windowid:String,name:String,price:String,amount:String):String{
    //.#10#窗口号#物品名#物品单价#物品数量
    val message="$username#10#$windowid#$name#$price#$amount"
    println("send_10: "+message)
    //发送message
    //发送
    val a:Client= Client()
    val answer= a.start(message)
    return answer
}
fun receive_10(input: String):Boolean{
    var isSold=StringBuilder()
    val inputChars=input.toCharArray()
    for(i in 0..input.length-1){
        if(inputChars[i]!='#'){
            isSold.append(inputChars[i])
        }
    }
    if(isSold.toString()=="true"){
        return true
    }
    return false
}

//交互11 结束作业
fun send_11(username: String,windowid: String):String{
    //..#11#winid
    val message="$username#11#$windowid"
    println("send_11: "+message)
    //发送message
    //发送
    val a:Client= Client()
    val answer= a.start(message)
    return answer
}
fun receive_11(input: String):Boolean{
    var isFinished=StringBuilder()
    val inputChars=input.toCharArray()
    for(i in 0..input.length-1){
        if(inputChars[i]!='#'){
            isFinished.append(inputChars[i])
        }
    }
    if(isFinished.toString()=="true"){
        return true
    }
    return false
}

//交互12  下架商品
fun send_12(username: String,windowid: String):String{
    //..#12#winid
    val message="$username#12#$windowid"
    println("send_11: "+message)
    //发送message
    //发送
    val a:Client= Client()
    val answer= a.start(message)
    return answer
}
fun receive_12(input: String):Boolean{
    var isFinished=StringBuilder()
    val inputChars=input.toCharArray()
    for(i in 0..input.length-1){
        if(inputChars[i]!='#'){
            isFinished.append(inputChars[i])
        }
    }
    if(isFinished.toString()=="true"){
        return true
    }
    return false
}




