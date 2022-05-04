package com.example.soldout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


//仓库的 RecyclerView的相关方法和类
class Store(val name:String, val amount:String, val imageId: Int)
class StoreAdapter(val storeList: List<Store>) : RecyclerView.Adapter<StoreAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fruitImage: ImageView = view.findViewById(R.id.fruitImage)
        val fruitAmount: TextView = view.findViewById(R.id.fruitAmount)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.store_item, parent, false)
        val viewHolder = ViewHolder(view)
        //RecyclerView（仓库）中的物品的监听
        /*viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = storeList[position]
            Toast.makeText(parent.context, "you clicked view ${fruit.name}", Toast.LENGTH_SHORT).show()
            if(fruit.name=="map"){//点击地图时可以选择是否使用
                val lesson = arrayOf("确认", "取消" )
                var builder = AlertDialog.Builder(parent.context)
                var alert = builder.setIcon(R.mipmap.ic_launcher)
                    .setTitle("是否使用该道具")
                    .setItems(lesson,
                        DialogInterface.OnClickListener { dialog , which->
                            when(which){
                                //使用地图道具的逻辑
                                0-> Toast.makeText(parent.context, "确认使用地图" , Toast.LENGTH_SHORT).show()
                            }
                        }).create()
                alert.show()
            }else{
                Toast.makeText(parent.context, "该道具无法被使用", Toast.LENGTH_SHORT).show()
            }
        }*/

        return viewHolder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val store = storeList[position]
        holder.fruitImage.setImageResource(store.imageId)
        holder.fruitAmount.setText(store.amount)
    }
    override fun getItemCount() = storeList.size
}
