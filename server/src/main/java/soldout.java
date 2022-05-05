import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import static java.lang.Long.max;

public class soldout {

    public static void main(String[] args)throws Exception {
        //soldout s1=new soldout();
        //run();
//        while(true){
//            Scanner input = new Scanner(System.in);
//            String s = input.nextLine();
//            separate(s);
//        }
        ServerSocket server = new ServerSocket(8888);
        System.out.println("等待连接...");
        ThreadTest t1 = new ThreadTest();
        //通过此对象调用start()启动一个线程
        t1.start();
        int count = 0;
        while(true){
            // 2、监听一个客户端的连接
            Socket socket = server.accept();
            System.out.println("第" + ++count + "个客户端连接成功！！");

            ClientHandlerThread ct = new ClientHandlerThread(socket);
            ct.start();
        }


    }

    public static String separate(String s) {
        String[] a = s.split("#");
		/*for(int i=0;i<a.length;i++)
			System.out.println(a[i]);*/

        switch (a[1]) {
            case "1":
                s=register(a[0], a[2]);
                break;//1.注册 用户名#1#密码
            case "2":
                s=login(a[0], a[2]);
                break;//2.登录 用户名#2#密码
            case "3":
                s=checkmoney(a[0]);
                break;//3.查询用户金币数 用户名#3
            case "4":
                s=checkstock(a[0]);
                break;//4.查询仓库 用户名#4
            case "5":
                s=checkshop(a[0]);
                break;//5.查询商店 用户名#5
            case "6":
                s=checkwork(a[0]);
                break;//6.查询工坊 用户名#6
            case "7":
                s=checkmarket(a[0], a[2]);
                break;//7.查询市场内容 用户名#7#页数
            case "8":
                s=compose(a[0], a[2], a[3], a[4]);
                break;//8.前端给欲合成的原料号(地图为0 水为1 随机2 纸3 颜料4 笔5 ) 和要完成的作业次数 用户名#8#窗口号#数1#个数2
            case "9":
                s=buy(a[0], a[2], a[3]);
                break;//9.购买物品 用户名#9#订单ID#数量
            case "10":
                s=sell(a[0], a[2], a[3], a[4], a[5]);
                break;//10.上架要卖的物品 用户名#10#窗口号#物品名#物品单价#物品数量
            case "11":
                s=checkend(a[0], a[2]);
                break;//11.結束加工 用户名#11#窗口号
            case "12":
                s=takeoff(a[0], a[2]);
                break;//12.下架 用户名#12#窗口号
            default:
                System.out.println("error");
        }
        return s;
    }

    public static String register(String id, String password) {
        String[] s = new String[3];
        s[0] = id;
        s[1] = "1";
        //s[2]="true";//调用函数
        //!!!!!!给数据库存入数据库返回true/false
        boolean m = SQL.SignUp(id, password);
        if (m) {
            s[2] = "true";
            int uid = SQL.getUid(id);
            SQL.If_Modify_Store_Successful(uid, "map", 5);
            SQL.Modify_JDBC_USER_gold(uid, 2000);
        }
        else s[2] = "false";
        return(arrtostr(s, 3));
    }

    public static String login(String id, String password) {//2.登录是否成功//true false1 false2 分别代表 用户不存在 密码不正确 用户名#2#false1/false2
        String[] s = new String[3];
        s[0] = id;
        s[1] = "2";
        String m = SQL.getPassword(id);//调数据库返回正确密码作比较
        if (m == null) s[2] = "false1";
        else if (m.equals(password)) s[2] = "true";
        else s[2] = "false2";
        return (arrtostr(s, 3));
    }

    public static String compose(String id, String windowid, String mid, String num)//用户名 窗口号 原料号 作业次数
    {
        String[] s = new String[3];
        s[0] = id;
        s[1] = "8";
        int k = SQL.getUid(id);//给用户名返回用户编号
        //判断该窗口是否存在正在加工的物品
        if (SQL.getOneWorkshopWindow(k,Integer.parseInt(windowid))) s[2] = "false";
        else{
            int nnum = Integer.parseInt(num);
            stock[] mt = SQL.getMaterials(Integer.parseInt(mid));//给目标原料公式号 返回需要原料名+消耗个数
            int[] store = new int[10];//剩余原料数
            int st = 0;//用于判断库存数是否足够
            int ff = 1;
            for (int i = 0; i < mt.length; i++) {
                store[i] = SQL.Find_Store_Stock(k, mt[i].getgoodsname());//保存对应原料库存数
                if (store[i] < mt[i].getgoodsnum() * nnum) {
                    ff = 0;
                    break;
                }
            }
            if (ff == 1) {
                for (int j = 0; j < nnum; j++) {
                    for (int i = 0; i < mt.length; i++) {
                        if (SQL.If_Item_Property(mt[i].getgoodsname())) {//如果为道具(1为道具)类型 以10%概率消耗库存 M函数根据原料名查对应类型
                            Random r = new Random();//生成随机数 0-9
                            int i1 = r.nextInt(10);
                            if (i1 == 1) {
                                store[i] = store[i] - mt[i].getgoodsnum();// I给用户名 原料名 返回库存数 ;库存数减去消耗数
                                if (store[i] < 0) {
                                    st = 1;
                                    break;
                                }//直接返回拒绝！
                            }
                            //else store[i]=I(k,mt[i].getgoodsname());
                        } else {
                            store[i] = store[i] - mt[i].getgoodsnum();// I给用户名 原料号 返回库存数
                            if (store[i] < 0) {
                                st = 1;
                                break;
                            }//直接返回拒绝！
                        }
                    }
                }
            } else st = 1;

            String needtime = SQL.Find_Formula_Needtime(Integer.parseInt(mid));//J	给公式号 返回一次需要时间
            String[] datestr = needtime.split(":");
            int[] dateint = Arrays.stream(datestr).mapToInt(Integer::parseInt).toArray();
            Date t = new Date();
            Date c = new Date(t.getTime() + (long) dateint[0] * nnum * 1000 * 60 * 60 + (long) dateint[1] * nnum * 1000 * 60 + (long) dateint[2] * nnum * 1000);
            if (st == 1) s[2] = "false";
            else {//确认作业 用户名、消耗后的原料数
                s[2] = "true";
                for (int i = 0; i < mt.length; i++) {
                    if (SQL.If_Modify_Store_Successful(k, mt[i].getgoodsname(), store[i]) == false)
                        i--;//K修改仓库表 用户id 物品名 现在数量 修改失败则继续修改
                }
                while (true) {
                    if (SQL.setWork(k, Integer.parseInt(windowid), Integer.valueOf(num), c, Integer.parseInt(mid)) == true)
                        break;//改工坊表 用户名、窗口号、作业次数、结束时间、公式号 返回true/false
                }
            }
        }
        return(arrtostr(s, 3));
    }

    public static String checkend(String id, String windowid) {
        String[] s = new String[3];
        s[0] = id;
        s[1] = "11";
        int k = SQL.getUid(id);//给用户名返回用户编号
        work wk2 = SQL.getOneWork(k, Integer.parseInt(windowid));//返回工坊类的实例化对象
        int worknum = wk2.getworknum();
        Date end = wk2.getendtime();
        //获取本地时间
        Date dtf = new Date();
        System.out.println(dtf);
        System.out.println(end);
        //本地时间与这个时间作比较
        if(end.before(dtf)) {
            //修改工坊表 仓库表加
            s[2] = "true";
            boolean tag = SQL.Delete_Workshop(k, Integer.parseInt(windowid));//删除工坊表中一项
            if (tag == false) s[2] = "false";
            else {
                if (wk2.getworkid() == 2) {
                    stock[] mt = SQL.getProducts(wk2.getworkid());//给生成公式 得到生成结果对象数组
					/*int n=mt.length;//因为只会生成四项所以直接限制个数，若有添加可用这段代码
					int[] lastnum=new int[];*/
                    int[] stk1 = new int[4];
                    for (int j = 0; j < 4; j++) {
                        stk1[j] = SQL.Find_Store_Stock(k, mt[j].getgoodsname());//查仓库表，给用户id和原料名返回原料数
                    }
                    for (int i = 0; i < 4; i++) {
                        int lastnum;
                        int producenum = mt[i].getgoodsnum();
                        Random r = new Random();
                        int i1 = r.nextInt(5);
                        if (i1 == 0) lastnum = producenum+1;
                        if (i1 == 1) lastnum = producenum+2;
                        if (i1 == 2) lastnum = producenum+3;
                        if (i1 == 3) lastnum = producenum-2;
                        else lastnum = producenum - 1;
                        boolean flag;
                        //给当前改动后的原料和个数，改仓库表
                        while (true) {
                            flag = SQL.If_Modify_Store_Successful(k, mt[i].getgoodsname(), stk1[i] + (lastnum * worknum));
                            if (flag == true) break;
                        }
                    }

                } else {
                    stock[] mt2 = SQL.getProducts(wk2.getworkid());//给合成公式号 得到生成结果对象，仅有一个
                    int stk2 = SQL.Find_Store_Stock(k, mt2[0].getgoodsname());//查仓库表，给用户id返回原料数
                    boolean flag;
                    while (true) {
                        flag = SQL.If_Modify_Store_Successful(k, mt2[0].getgoodsname(), stk2 + (mt2[0].getgoodsnum() * worknum));//给当前改动后的原料和个数，改仓库表
                        if (flag) break;
                    }
                }
            }
        }
		else s[2] = "false";
        return(arrtostr(s, 3));
    }

    public static String checkmoney(String id)//用户名
    {
        String[] s = new String[3];
        s[0] = id;
        s[1] = "3";
        int k = SQL.getUid(id);//给用户名返回用户编号
        int m = SQL.Find_JDBC_USER_Gold(k);//给用户id查金币数量
        s[2] = String.valueOf(m);
        return( arrtostr(s, 3));
    }

    public static String checkstock(String id) {
        int k = SQL.getUid(id);//给用户名返回用户编号
        stock[] stk = SQL.getStock(k);//根据用户id查询仓库内容 返回格式为 物品名 数量
        String[] s = new String[30];
        s[0] = id;
        s[1] = "4";
        for (int i = 0, j = 2; i < stk.length; i++, j = j + 2) {
            s[j] = stk[i].getgoodsname();
            s[j + 1] = String.valueOf(stk[i].getgoodsnum());
        }
        int n = 0;  //保存元素个数的变量
        for (String value : s) {
            if (null != value) n++;
        }
        //System.out.println(n);
        return(arrtostr(s, n));
    }

    public static String checkshop(String id) {
        String[] s = new String[15];
        s[0] = id;
        s[1] = "5";
        int k = SQL.getUid(id);//给用户名返回用户编号
        //窗口号及物品名 单价 数量
        market[] mrk = SQL.getShop(k);//传用户id 返回 对象数组market类
        for (int i = 0, j = 2; i < mrk.length; i++, j = j + 4) {
            s[j] = String.valueOf(mrk[i].getwindowid());
            s[j + 1] = mrk[i].getgoodsname();
            s[j + 2] = String.valueOf(mrk[i].getprice());
            s[j + 3] = String.valueOf(mrk[i].getgoodsnum());
        }
        return(arrtostr(s, mrk.length * 4 + 2));
    }

    public static String checkwork(String id) {//正在生产的东西（窗口号（1~3） 生成的物品名 作业次数 结束时间） // .#.#.#.#     	—前向后6
        int k = SQL.getUid(id);//给用户名返回用户编号
        work[] wrk = SQL.getWork(k);//传用户ID 返回工坊类的实例化对象
        String[] s = new String[30];
        s[0] = id;
        s[1] = "6";
        for (int i = 0, j = 2; i < wrk.length; i++, j = j + 4) {
            s[j] = wrk[i].getwindownum();//窗口号
            stock[] mt2 = SQL.getProducts(wrk[i].getworkid());//根据当前窗口公式号查询生产物品 返回合成类对象数组
            if (wrk[i].getworkid() == 2) s[j + 1] = "grass";//如果公式号为2 生产出4个物品 返回物品名为grass
            else s[j + 1] = mt2[0].getgoodsname();//物品名
            s[j + 2] = String.valueOf(wrk[i].getworknum());//作业次数

            s[j + 3] = String.valueOf(max(0,((wrk[i].getendtime().getTime() - new Date().getTime())/60000)));//结束时间
        }
        return (arrtostr(s, wrk.length * 4 + 2));
    }

    public static String checkmarket(String id, String pagenum)//用户名 页数
    {
		 
		 /*给信号调函数 无参数函数
		 给对象数组：调size查总数
		 订单ID、卖家名、物品名、单价、数量*/
        market[] mar = SQL.getMarket(); //返回所有订单
        int ordersum = mar.length;//总订单数
        int pagesum = (int) Math.ceil(ordersum * 1.0 / 15);//总页数
        String[] s = new String[120];
        s[0] = id;
        s[1] = "7";
        int page=Integer.valueOf(pagenum);//想要第几页
        int llength;
        int endpagenum;//尾页中订单数量
        if(page<pagesum) {llength=15*7+2;endpagenum=15;}
        else {llength=(ordersum-(page-1)*15)*7+2; endpagenum=ordersum-(page-1)*15;}
        for(int i=15*(page-1),j=2;i<15*(page-1)+endpagenum;i++){
            s[j]=String.valueOf(mar[i].getgoodsname());//商品名
            String fullname;
            fullname=SQL.getUsername(mar[i].getuserid());//给用户id返回用户名
            s[j+1]=fullname;//用户名
            s[j+2]=String.valueOf(mar[i].getmarketid());//订单id
            s[j+3]=String.valueOf(mar[i].getprice());//商品价格
            s[j+4]=String.valueOf(mar[i].getgoodsnum());//商品数量
            s[j+5]=pagenum;//第几页
            s[j+6]=String.valueOf(pagesum);//共几页
            j=j+7;
        }
        return (arrtostr(s, llength));
    }

    public static String buy(String id, String oid, String goodsnum)//用户名 订单id 物品数量
    {
        //给用户名 返回金币数
        //给订单ID 返回表的单行
        //计算金币够不够
        //够的话：给买家id 金币数；给买家id和原料号 返回原料个数 增加后给买家id和原料名和数
        //给卖家id 返回金币数 改后再给卖家id、金币数；原料修改
        //订单修改：订单id 新的物品数
        String[] s = new String[3];
        s[0] = id;
        s[1] = "9";
        int k = SQL.getUid(id);//给用户名返回用户编号
        int money = SQL.Find_JDBC_USER_Gold(k);//传用户id 返回金币数量（int)
        int mid = Integer.parseInt(oid);//订单id
        market mar = SQL.getOneMarket(mid);//用订单号查市场的某一条
        int mprice = mar.getprice();//商品价格
        int wantnum = Integer.parseInt(goodsnum);//欲购买量
        //没加欲购买量必须小于订单量的判断逻辑，前端如果没有需要重新加上
        if(wantnum>mar.getgoodsnum()) s[2] = "false";
        else
        {
            int sumprice = mprice * wantnum;
            if (money < sumprice) s[2] = "false";
            else {
                //修改卖家金币
                if (mar.getuserid() > 3) {
                    int sellerid = mar.getuserid();
                    int sellermoney = SQL.Find_JDBC_USER_Gold(sellerid);
                    boolean flag;
                    while (true) {
                        flag = SQL.Modify_JDBC_USER_gold(sellerid, sellermoney + sumprice);//给用户id和金币数修改金币数
                        if (flag) break;
                    }
                }
                //修改买家金币
                while (true) {
                    boolean flag = SQL.Modify_JDBC_USER_gold(k, money - sumprice);//给用户id和金币数修改金币数
                    if (flag) break;
                }
                //查买家买前库存
                int numm = SQL.Find_Store_Stock(k, mar.getgoodsname());//查仓库表
                while (true) {
                    boolean flag = SQL.If_Modify_Store_Successful(k, mar.getgoodsname(), numm + wantnum);//给买家id购买物品目前数量修改买家仓库
                    if (flag) break;
                }
                //减卖家订单上物品数
                while (true) {
                    boolean flag = SQL.Modify_Market_mnumber(mid, mar.getgoodsnum() - wantnum);//修改订单，给订单id 现在数目
                    //System.out.println(mar.getgoodsnum() - wantnum);
                    if (flag) break;
                }
                //如果全被买走删除该订单
                if (mar.getgoodsnum() == wantnum) {
                    while (true) {
                        boolean flag = SQL.Delete_Market(mid);//删除订单，给订单号返回true\false
                        if (flag) break;
                    }
                }
                s[2] = "true";
            }
        }
        return (arrtostr(s, 3));
    }

    public static String sell(String id, String windownum, String goodsname, String goodsprice, String goodsnum)//用户名窗口号#物品名#物品单价#物品数量
    {
        //给用户名查仓库数 够不够上架
        //计算好现在的仓库数 给用户名 物品名 物品数
        //市场表加 给用户名、物品名、数量、价格 返回订单id
        //商店加 给用户名、窗口号、订单id
        String[] s = new String[3];
        s[0] = id;
        s[1] = "10";
        int k = SQL.getUid(id);//给用户名返回用户编号
        if (SQL.getOneMarketWindow(k,Integer.parseInt(windownum)) != null) s[2] = "false";
        else {
            int nownum = SQL.Find_Store_Stock(k, goodsname);
            int wantsellnum = Integer.parseInt(goodsnum);
            int sellprice = Integer.parseInt(goodsprice);
            int windowid = Integer.parseInt(windownum);
            if (wantsellnum > nownum) s[2] = "false";
            else {
                boolean flag;
                //改卖家仓库
                while (true) {
                    flag = SQL.If_Modify_Store_Successful(k, goodsname, nownum - wantsellnum);//给卖家id、上架物品名、上架后仓库数量修改卖家仓库
                    if (flag) break;
                }
                //加市场订单
                SQL.Insert_Market(k, goodsname, wantsellnum, sellprice, windowid);//给用户id、物品名、单价、数量；返回mid
                //			 //改商店
                //			 while(true) {
                //				 flag=W(k,mid);//给用户id、订单号、窗口号返回T/F
                //			 	 if(flag==true) break;
                //			 }
                s[2] = "true";
            }
        }
        return (arrtostr(s, 3));
    }

    public static String takeoff(String id, String windownum)//用户名、窗口号
    {
        //给用户名、窗口号查订单物品数
        //给用户名、物品名查下架前物品数
        //计算好现在的仓库数 给用户名 物品名 物品数 改仓库表
        //市场表减 给用户名、窗口号
        String[] s = new String[3];
        s[0] = id;
        s[1] = "12";
        int k = SQL.getUid(id);//给用户名返回用户编号
        int windowid = Integer.parseInt(windownum);//获取窗口号
        market mar = SQL.getOneMarketWindow(k,windowid);//根据用户名和窗口号查询当前物品
        if (mar==null) s[2] = "false";//若为空 下架失败
        else {
            int num = SQL.Find_Store_Stock(k, mar.getgoodsname());//查仓库表
            //改卖家仓库
            while (true) {
                boolean flag;
                flag = SQL.If_Modify_Store_Successful(k, mar.getgoodsname(), num + mar.getgoodsnum());//给卖家id、下架物品名、下架后仓库数量修改卖家仓库
                if (flag) break;
            }
            //改市场表 给订单号删除对应物品
            while (true) {
                boolean flag;
                flag = SQL.deleteFromMarket(mar.getmarketid());//给卖家id、下架物品名、下架后仓库数量修改卖家仓库
                if (flag) break;
            }
            s[2] = "true";
        }
        return (arrtostr(s, 3));
    }

    public static String arrtostr(String[] content, int length) {
        String s = "";
        for (int i = 2; i < length; i++) {
            s += content[i] + "#";
        }
        return s;
//        System.out.println(s);
    }

//    public static void run() {
//        while (true) {
//            try {
//                System.out.println("hello");// 需要执行的程序
//                Thread.sleep(1000L);  //间隔1秒执行一次！
//            } catch (Exception e) {
//                System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            }
//        }
//    }
}
