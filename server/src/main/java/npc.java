import java.util.Random;

import static java.lang.Math.min;

public class npc {

    public static void start() {
        //npc实现
        npc npc = new npc();
        npc.presell();
        while (true) {
            try {
                for (int i = 0; i < 10; ++i) {
                    npc.npcbuy();
                    Thread.sleep(1000L * 60 * 10);  //间隔10分钟执行一次
                }
                npc.npcsell();// 需要执行的程序

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }

    public void presell() {
        int[] wantsellnum = new int[]{10, 20, 20, 20, 20, 10, 10, 20, 10};//预定的数量之后需要修改
        int[] wantsellprice = new int[]{1000, 100, 100, 100, 100, 300, 500, 100, 250};//预定的价格之后需要修改
        String[] wantsellgoods = new String[]{"map", "water", "branch", "feather", "grass", "paper", "pen", "stone", "pigment"};
        //加市场订单
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                SQL.Insert_Market(i + 1, wantsellgoods[i * 3 + j], wantsellnum[i * 3 + j], wantsellprice[i * 3 + j], 0);
            }
        }
    }

    public void npcsell() {
        //修改市场表，删除原有订单
        for (int i = 0; i < 3; i++) {
            market[] mar = SQL.getShop(i + 1);//根据用户id 返回市场类实例化对象数组
            for (market market : mar) {
                SQL.Delete_Market(market.getmarketid());//删除现有订单;
            }
        }
        presell();
//            int[] wantsellnum = new int[3];
//            int[] wantsellprice = new int[3];
//            String[] wantsellgoods = new String[3];
//            market[] mar2 = SQL.getMarket();
//            Random r = new Random();
//            int mid = mar2[r.nextInt(mar2.length)].getmarketid();
//            if (mid >= mar2.length - 2) mid = mar2.length - 3;
//            for (int k = 0, l = mid; k < 3; k++, l++) {
//                wantsellnum[k] = mar2[l].getgoodsnum() * 2;
//                wantsellprice[k] = mar2[l].getprice();
//                wantsellgoods[k] = mar2[l].getgoodsname();
//                SQL.Insert_Market(i + 1, wantsellgoods[k], wantsellprice[k], wantsellnum[k], 0);//添加新订单
//            }
    }

    public void npcbuy()//用户名 订单id 物品数量
    {
        market[] mar2 = SQL.getMarket();
        int totalnum = mar2.length;
        Random r = new Random();
        int mid = mar2[r.nextInt(totalnum)].getmarketid();//生成随机数 获取对应的订单id
        market mar = SQL.getOneMarket(mid);//用订单号查市场的某一条
        int mprice = mar.getprice();//商品价格
        int wantnum = min(mar.getgoodsnum(), (r.nextInt(10)+1)*SQL.getItemValue(mar.getgoodsname())/mprice);//欲购买量
        int sumprice = mprice * wantnum;
        if (mar.getuserid() > 3) {
            //修改卖家金币
            int sellerid = mar.getuserid();
            int sellermoney = SQL.Find_JDBC_USER_Gold(sellerid);
            boolean flag;
            while (true) {
                flag = SQL.Modify_JDBC_USER_gold(sellerid, sellermoney + sumprice);//给用户id和金币数修改金币数
                if (flag) break;
            }
        }
        //减卖家订单上物品数
        while (true) {
            boolean flag = SQL.Modify_Market_mnumber(mid, mar.getgoodsnum() - wantnum);//修改订单，给订单id 现在数目
            if (flag) break;
        }
        //如果全被买走删除该订单
        if (mar.getgoodsnum() == wantnum) {
            while (true) {
                boolean flag = SQL.Delete_Market(mid);//删除订单，给订单号返回true\false
                if (flag) break;
            }
        }
    }
}