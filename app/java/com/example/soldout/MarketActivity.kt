package com.example.soldout

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text


class MarketActivity : AppCompatActivity() {

    // var storeSoleOut=HashMap<Int,SoldOut>()

    private val storeList = ArrayList<Store>()
    private val commodityList=ArrayList<SoldCommodity>()
    private val marketList=ArrayList<SoldMarket>()

    //   private val storeMap = HashMap<String,Store>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.market)

        //加网络权限
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        //接收username
        val intent: Intent = getIntent()
        val username= intent.getStringExtra("key").toString()

        //对菜单栏的各个按钮进行监听   页面的跳转
        val button_workshop=findViewById<Button>(R.id.button_workshop_market)
        val button_market=findViewById<Button>(R.id.button_market_market)
        val button_sell=findViewById<Button>(R.id.button_sell_market)
        button_workshop.setOnClickListener{
            val intent = Intent(this, WorkshopActivity::class.java)
            intent.putExtra("key",username);
            startActivity(intent)
            finish()
        }
        button_sell.setOnClickListener{
            val intent = Intent(this, OwnshopActivity::class.java)
            intent.putExtra("key",username);
            startActivity(intent)
            finish()
        }



        val money_ownshop=findViewById<TextView>(R.id.money_market)
        val money_buyText=findViewById<TextView>(R.id.buyText)
        initMoney(money_ownshop,username)
        initMoney(money_buyText,username)

        val recyclerView=findViewById<RecyclerView>(R.id.market)
        //initCommodity();
        // initMarket(15) // 初始化仓库数据
        //7查询市场内容（商品名 卖家用户名 订单ID 商品价格 商品数量 第几页 共几页)  (买）
        var receive7List: ArrayList<Receive7>
        val dbMarketHelper=MarketDatabaseHelper(this,"Market.db",1)
        receive7List=initTableMarket(dbMarketHelper,username,"1")
        initMarketList(dbMarketHelper,marketList)



        val test="grass#王1#001#15#1#0#01#water#王2#002#12#2#02#03"
        //    var receive7List=ArrayList<Receive7>()
        //  receive7List= receive_7(test)
        //页
        var currentPage=receive7List.get(0).currentPage
        var totalPage=receive7List.get(0).totalPages

        val marketPages=findViewById<TextView>(R.id.marketPages)
        marketPages.text="第"+currentPage+"页/共"+totalPage+"页"
        recyclerView.setLayoutManager(GridLayoutManager(this, 5))//设置网格布局
        val adapter = MarketAdapter(marketList,username)
        recyclerView.adapter = adapter

        //换页监听
        val buttonLastPage=findViewById<Button>(R.id.lastPage)
        buttonLastPage.setOnClickListener {
            val recyclerView=findViewById<RecyclerView>(R.id.market)
            //initCommodity();
            // initMarket(15) // 初始化仓库数据
            //7查询市场内容（商品名 卖家用户名 订单ID 商品价格 商品数量 第几页 共几页)  (买）
            var receive7List: ArrayList<Receive7>
            val dbMarketHelper=MarketDatabaseHelper(this,"Market.db",1)
            val sendPage=Integer.parseInt(currentPage)-1
            if(sendPage>=1) {
                receive7List = initTableMarket(dbMarketHelper, username, sendPage.toString())
                initMarketList(dbMarketHelper, marketList)
                currentPage = receive7List.get(0).currentPage
                totalPage = receive7List.get(0).currentPage

                val marketPages = findViewById<TextView>(R.id.marketPages)
                marketPages.text = "第" + currentPage + "页/共" + totalPage + "页"
                recyclerView.setLayoutManager(GridLayoutManager(this, 5))//设置网格布局
                val adapter = MarketAdapter(marketList, username)
                recyclerView.adapter = adapter
            }else{
                Toast.makeText(this,"已达到第一页",Toast.LENGTH_SHORT)
            }
        }

        val buttonNextPage=findViewById<Button>(R.id.nextPage)
        buttonNextPage.setOnClickListener {
            val recyclerView=findViewById<RecyclerView>(R.id.market)
            //initCommodity();
            // initMarket(15) // 初始化仓库数据
            //7查询市场内容（商品名 卖家用户名 订单ID 商品价格 商品数量 第几页 共几页)  (买）
            var receive7List: ArrayList<Receive7>
            val dbMarketHelper=MarketDatabaseHelper(this,"Market.db",1)
            val sendPage=Integer.parseInt(currentPage)+1
            if(sendPage<=Integer.parseInt(totalPage)) {
                receive7List = initTableMarket(dbMarketHelper, username, sendPage.toString())
                initMarketList(dbMarketHelper, marketList)
                currentPage = receive7List.get(0).currentPage
                totalPage = receive7List.get(0).currentPage

                val marketPages = findViewById<TextView>(R.id.marketPages)
                marketPages.text = "第" + currentPage + "页/共" + totalPage + "页"
                recyclerView.setLayoutManager(GridLayoutManager(this, 5))//设置网格布局
                val adapter = MarketAdapter(marketList, username)
                recyclerView.adapter = adapter
            }else{
                Toast.makeText(this,"已达到最后一页",Toast.LENGTH_SHORT)
            }
        }

//        val buttonLastPage=findViewById<Button>(R.id.lastPage)
//        buttonLastPage.setOnClickListener {
//            val recyclerView=findViewById<RecyclerView>(R.id.market)
//            //initCommodity();
//            // initMarket(15) // 初始化仓库数据
//            //7查询市场内容（商品名 卖家用户名 订单ID 商品价格 商品数量 第几页 共几页)  (买）
//            var receive7List: ArrayList<Receive7>
//            val dbMarketHelper=MarketDatabaseHelper(this,"Market.db",1)
//            val sendPage=Integer.parseInt(currentPage)-1
//            if(sendPage>=1) {
//                receive7List = initTableMarket(dbMarketHelper, username, sendPage.toString())
//                initMarketList(dbMarketHelper, marketList)
//                var currentPage = receive7List.get(0).currentPage
//                var totalPage = receive7List.get(0).currentPage
//
//                val marketPages = findViewById<TextView>(R.id.marketPages)
//                marketPages.text = "第" + currentPage + "页/共" + totalPage + "页"
//                recyclerView.setLayoutManager(GridLayoutManager(this, 5))//设置网格布局
//                val adapter = MarketAdapter(marketList, username)
//                recyclerView.adapter = adapter
//            }else{
//                Toast.makeText(this,"已达到第一页",Toast.LENGTH_SHORT)
//            }
//        }
    }


    private fun initCommodity(){
        //val newCommodity=SoldCommodity("1","grass","王大马","36","5")
        commodityList.add(SoldCommodity("1","grass","王大1","36","1"))
        commodityList.add(SoldCommodity("2","map","王大2","37","2"))
        commodityList.add(SoldCommodity("3","paper","王大3","38","3"))
        commodityList.add(SoldCommodity("4","pen","王大4","39","4"))
        commodityList.add(SoldCommodity("5","pigment","王大5","40","5"))
        commodityList.add(SoldCommodity("6","stone","王大6","41","6"))
        commodityList.add(SoldCommodity("7","water","王大7","42","7"))
        commodityList.add(SoldCommodity("8","branch","王大8","43","8"))
        commodityList.add(SoldCommodity("9","feather","王大9","44","9"))
        commodityList.add(SoldCommodity("10","gold","王大10","45","10"))
        commodityList.add(SoldCommodity("11","grass","王大11","46","11"))
        commodityList.add(SoldCommodity("12","grass","王大12","47","12"))
        commodityList.add(SoldCommodity("13","grass","王大13","48","13"))
        commodityList.add(SoldCommodity("14","grass","王大14","49","14"))
        commodityList.add(SoldCommodity("15","grass","王大15","50","15"))
    }

    private fun initMarket(count:Int){
        for(i in 0..count-1){
            setMarketImage(commodityList[i],marketList)
        }
    }


}


fun setMarketImage(commodity: SoldCommodity,marketList:ArrayList<SoldMarket>){
    if(commodity.name=="water"){
        marketList.add(SoldMarket(commodity, R.drawable.pic_water))
        println("add water")
    }else if(commodity.name=="branch"){
        marketList.add(SoldMarket(commodity, R.drawable.pic_branch))
        println("add branch")
    }else if(commodity.name=="feather"){
        marketList.add(SoldMarket(commodity, R.drawable.pic_feather))
        println("add feather")
    }else if(commodity.name=="gold"){
        marketList.add(SoldMarket(commodity, R.drawable.pic_gold))
        println("add gold")
    }else if(commodity.name=="grass"){
        marketList.add(SoldMarket(commodity, R.drawable.pic_grass))
        println("add grass")
    }else if(commodity.name=="map"){
        marketList.add(SoldMarket(commodity, R.drawable.pic_map))
        println("add map")
    }else if(commodity.name=="paper"){
        marketList.add(SoldMarket(commodity, R.drawable.pic_paper))
        println("add paper")
    }else if(commodity.name=="pen"){
        marketList.add(SoldMarket(commodity, R.drawable.pic_pen))
        println("add pen")
    }else if(commodity.name=="stone"){
        marketList.add(SoldMarket(commodity,  R.drawable.pic_stone))
        println("add stone")
    }else if(commodity.name=="pigment"){
        marketList.add(SoldMarket(commodity, R.drawable.pic_pigment))
        println("add pigment")
    }else{
        println("add NULL!")
    }
}



class SoldCommodity(val id:String, val name:String, val solderName:String, val price:String, val count:String)
class SoldMarket(val commodity: SoldCommodity,val imageId: Int)

//class Store(val name:String, val imageId: Int)


class MarketAdapter(val marketList: ArrayList<SoldMarket>,val username:String) : RecyclerView.Adapter<MarketAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fruitImage: ImageView = view.findViewById(R.id.fruitImage)
        val fruitName: TextView = view.findViewById(R.id.fruitName)
        val fruitPrice: TextView = view.findViewById(R.id.fruitPrice)
        val fruitAmount: TextView = view.findViewById(R.id.fruitAmount)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.market_item, parent, false)

        val viewHolder = ViewHolder(view)




        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // val store = marketList[position]
        val store=marketList.get(position)

        holder.fruitImage.setImageResource(store.imageId)
        holder.fruitName.text = store.commodity.name
        holder.fruitPrice.text = "单价:"+store.commodity.price+"元"
        holder.fruitAmount.text = "数量:"+store.commodity.count+"个"

        holder.itemView.setOnClickListener{
            val position=holder.adapterPosition
            val market=marketList[position]

            //   val inputServer=EditText(holder.itemView.context)
            val dialog=AlertDialog.Builder(holder.itemView.context)
            val viewInput: View=LayoutInflater.from(holder.itemView.context).inflate(R.layout.dialog_view, null)
            dialog.setView(viewInput)
            val inputNumber=viewInput.findViewById(R.id.dialog) as EditText
            // val sendNumber=inputNumber.text.toString()
            dialog.setIcon(store.imageId)
            dialog.setTitle("请输入商品数")
            //      dialog.setView(inputServer)

            dialog.setNegativeButton("取消",null)
            dialog.setPositiveButton(("确认"),
                DialogInterface.OnClickListener{dialogText,which ->
                    val sendNumber=inputNumber.text.toString()
                    println("inputNumber"+sendNumber)
                    //发送 number
                    val message=send_9(username,market.commodity.id,sendNumber)
                    //接受 true /false
                    if(receive_9(message)==true) {
                        //更新金币
                        Toast.makeText(holder.itemView.context, "购买成功", Toast.LENGTH_SHORT).show()
                    }
                })



            dialog.show()
            Toast.makeText(holder.itemView.context,"youclicked view ${market.commodity.name}",Toast.LENGTH_SHORT).show()
        }
    }
    override fun getItemCount() = marketList.size
}
