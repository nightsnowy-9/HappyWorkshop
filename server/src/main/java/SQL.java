import java.sql.*;
import java.util.*;
import java.util.Date;

public class SQL {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/happyworkshop";
    private static final String JDBC_USER = "nightsnowy";
    private static final String JDBC_PASSWORD = "123456";

    private static String addQuotes(String s) {
        return "'" + s + "'";
    }

    public static int getUid(String username) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT uid FROM user where username=" + addQuotes(username))) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    } else return 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getUsername(int uid) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT username FROM user where uid=" + uid)) {
                    if (rs.next()) {
                        return rs.getString(1);
                    } else return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean SignUp(String username, String password) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO user (uid, username, password, gold) VALUES (?,?,?,?)")) {
                ps.setObject(1, null);
                ps.setObject(2, username);
                ps.setObject(3, password);
                ps.setObject(4, 0);
                int n = ps.executeUpdate();
                return (n == 1);
            }

        } catch (SQLException e) {
            return false;
        }
    }

    public static String getPassword(String username) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT password FROM user where username=" + addQuotes(username))) {
                    if (rs.next()) {
                        return rs.getString(1);
                    } else return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getItemName(int iid) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT itemname FROM item where iid=" + iid)) {
                    if (rs.next()) {
                        return rs.getString(1);
                    } else return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getItemID(String itemName) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT iid FROM item where itemname=" + addQuotes(itemName))) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    } else return 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getItemValue(String itemName) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT ivalue FROM item where itemname=" + addQuotes(itemName))) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    } else return 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static stock[] getMaterials(int fid) {
        List<stock> mt = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT iid, lnumber, ltype FROM lineitems where fid=" + fid)) {
                    while (rs.next()) {
                        if (rs.getInt(3) == 1) continue;
                        stock x = new stock();
                        x.setgoodsname(getItemName(rs.getInt(1)));
                        x.setgoodsnum(rs.getInt(2));
                        mt.add(x);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mt.toArray(new stock[0]);
    }

    public static stock[] getProducts(int fid) {
        List<stock> mt = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT iid, lnumber, ltype FROM lineitems where fid=" + fid)) {
                    while (rs.next()) {
                        if (rs.getInt(3) == 0) continue;
                        stock x = new stock();
                        x.setgoodsname(getItemName(rs.getInt(1)));
                        x.setgoodsnum(rs.getInt(2));
                        mt.add(x);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mt.toArray(new stock[0]);
    }

    public static stock[] getStock(int uid) {
        List<stock> mt = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT iid, wnumber FROM store where uid=" + uid)) {
                    while (rs.next()) {
                        stock x = new stock();
                        x.setgoodsname(getItemName(rs.getInt(1)));
                        x.setgoodsnum(rs.getInt(2));
                        mt.add(x);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mt.toArray(new stock[0]);
    }

    public static market[] getShop(int uid) {
        List<market> mt = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM market where uid=" + uid)) {
                    while (rs.next()) {
                        market x = new market();
                        x.setmarketid(rs.getInt(1));
                        x.setuserid(rs.getInt(2));
                        x.setgoodsname(getItemName(rs.getInt(3)));
                        x.setgoodsnum(rs.getInt(4));
                        x.setprice(rs.getInt(5));
                        x.setwindowid(rs.getInt(6));
                        mt.add(x);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mt.toArray(new market[0]);
    }

    public static market[] getMarket() {
        List<market> mt = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM market")) {
                    while (rs.next()) {
                        market x = new market();
                        x.setmarketid(rs.getInt(1));
                        x.setuserid(rs.getInt(2));
                        x.setgoodsname(getItemName(rs.getInt(3)));
                        x.setgoodsnum(rs.getInt(4));
                        x.setprice(rs.getInt(5));
                        x.setwindowid(rs.getInt(6));
                        mt.add(x);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mt.toArray(new market[0]);
    }

    public static market getOneMarket(int mid) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM market where mid = " + mid)) {
                    if (rs.next()) {
                        market x = new market();
                        x.setmarketid(rs.getInt(1));
                        x.setuserid(rs.getInt(2));
                        x.setgoodsname(getItemName(rs.getInt(3)));
                        x.setgoodsnum(rs.getInt(4));
                        x.setprice(rs.getInt(5));
                        x.setwindowid(rs.getInt(6));
                        return x;
                    } else return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static market getOneMarketWindow(int uid, int window) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM market where uid = " + uid + " and mwindow = " + window)) {
                    if (rs.next()) {
                        market x = new market();
                        x.setmarketid(rs.getInt(1));
                        x.setuserid(rs.getInt(2));
                        x.setgoodsname(getItemName(rs.getInt(3)));
                        x.setgoodsnum(rs.getInt(4));
                        x.setprice(rs.getInt(5));
                        x.setwindowid(rs.getInt(6));
                        return x;
                    } else return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean getOneWorkshopWindow(int uid, int window) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM workshop where uid = " + uid + " and wwindow = " + window)) {
                    if (rs.next()) {
                        return true;
                    } else return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static work[] getWork(int uid) {
        List<work> mt = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT wwindow, fid, endtime, times, uid FROM workshop where uid=" + uid)) {
                    while (rs.next()) {
                        work x = new work();
                        x.setwindownum(rs.getInt(1));
                        x.setworkid(rs.getInt(2));
                        x.setendtime(rs.getTimestamp(3));
                        x.setworknum(rs.getInt(4));
                        x.setuserid(rs.getInt(5));
                        mt.add(x);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mt.toArray(new work[0]);
    }

    public static work getOneWork(int uid, int window) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT wwindow, fid, endtime, times, uid FROM workshop where uid=" + uid + " and wwindow = " + window)) {
                    if (rs.next()) {
                        work x = new work();
                        x.setwindownum(rs.getInt(1));
                        x.setworkid(rs.getInt(2));
                        x.setendtime(rs.getTimestamp(3));
                        x.setworknum(rs.getInt(4));
                        x.setuserid(rs.getInt(5));
                        return x;
                    } else return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean setWork(int uid, int window, int times, Date endtime, int fid) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO workshop VALUES (null,?,?,?,?,?)")) {
                ps.setObject(1, uid);
                ps.setObject(2, fid);
                ps.setObject(3, times);
                ps.setObject(4, endtime);
                ps.setObject(5, window);
                int n = ps.executeUpdate();
                return (n == 1);
            }

        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean deleteFromMarket(int mid) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM market where mid = " + mid)) {
                int n = ps.executeUpdate();
                return (n == 1);
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean If_Item_Property(String iname) {  //查询待查物品的种类（true：道具 false：原料）
        boolean itype = false;
        int type = -1;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cne) {
            cne.printStackTrace();
        }
        String sql = "select itemname,type from item";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rst = stmt.executeQuery(sql)) {
            while (rst.next()) {
                if (rst.getString(1).equals(iname))
                    type = rst.getInt(2);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }
        if (type == -1) System.out.print("该物品不存在！！！\n");
        else itype = type == 1;
        return itype;
    }

    public static int Find_Store_Stock(int uid, String itemname) {  //查询待查物品在仓库中的数量
        int wnumber = 0;
        int iid = -1;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cne) {
            cne.printStackTrace();
        }
        String sql = "select itemname,iid from item ";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rst = stmt.executeQuery(sql)) {
            while (rst.next()) {
                if (rst.getString(1).equals(itemname))
                    iid = rst.getInt(2);
            }
            String sql0 = "select uid,iid,wnumber from store ";
            try (Connection conn0 = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                 Statement stmt0 = conn0.createStatement();
                 ResultSet rst0 = stmt0.executeQuery(sql0)) {
                while (rst0.next()) {
                    if (rst0.getInt(1) == uid && rst0.getInt(2) == iid)
                        wnumber = rst0.getInt(3);
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return wnumber;
    }

    public static boolean If_Modify_Store_Successful(int uid, String itemName, int wnumber){  //查询对库存表的修改是否成功
        int iid = getItemID(itemName);

        boolean successful = false;
        int result = 0;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException cne){
            cne.printStackTrace();
        }
        String sql = "select uid, iid from store";
        try(    Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                Statement stmt = conn.createStatement();
                ResultSet rst = stmt.executeQuery(sql))

        {
            while (rst.next())
            {

                if(rst.getInt(1)==uid && rst.getInt(2)==iid)
                {
                    if(wnumber != 0)
                    {
                        String sql2 = "update store set wnumber = " + wnumber + " where uid = " + uid + " and iid = " + iid;
                        try
                        {
                            Connection conn1 = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                            Statement stmt1 = conn1.createStatement();
                            result = stmt1.executeUpdate(sql2);
                        }
                        catch (SQLException se)
                        {
                            se.printStackTrace();
                        }
                    }
                    else
                    {
                        String sql4 = "delete from store where uid = " + uid + " and iid = " + iid;
                        try
                        {
                            Connection conn1 = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                            Statement stmt1 = conn1.createStatement();
                            result = stmt1.executeUpdate(sql4);
                        }
                        catch (SQLException se)
                        {
                            se.printStackTrace();
                        }
                    }
                }
            }
            if(result == 0)
            {
                String sql3 = "Insert into store values(null, " + uid +","+ iid + ","+ wnumber +")";
                try
                {
                    Connection conn2 = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                    Statement stmt2 = conn2.createStatement();
                    result = stmt2.executeUpdate(sql3);
                }
                catch (SQLException se)
                {
                    se.printStackTrace();
                }
            }
        }
        catch (SQLException se)
        {
            se.printStackTrace();
        }
        if(result == 1) successful = true;
        return successful;
    }
//
//    public static boolean If_Modify_Workshop_Successful(int uid, int fid, int times, String endtime, int wwindow) {
//        //对工坊表的修改并返回是否成功成功
//        boolean successful = false;
//        int result = 0;
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException cne) {
//            cne.printStackTrace();
//        }
//        String sql = "update workshop set fid=" + Integer.toString(fid) + "," + "times=" + Integer.toString(times) + " "
//                + "where uid=" + Integer.toString(uid) + " AND wwindow=" + Integer.toString(wwindow);
//        System.out.println(sql);
//        try {
//            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
//            Statement stmt = conn.createStatement();
//            result = stmt.executeUpdate(sql);
//        } catch (SQLException se) {
//            se.printStackTrace();
//        }
//        if (result == 1) successful = true;
//        return successful;
//    }

    public static int Find_JDBC_USER_Gold(int uid) {  //查询用户的金币数量
        int gold = -1;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cne) {
            cne.printStackTrace();
        }
        String sql = "select uid, gold from user";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rst = stmt.executeQuery(sql)) {
            while (rst.next()) {
                if (rst.getInt(1) == uid)
                    gold = rst.getInt(2);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }
        return gold;
    }

    public static String Find_Formula_Needtime(int fid) {  //查询待查物品的种类（true：道具 false：原料）
        String ftime = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cne) {
            cne.printStackTrace();
        }
        String sql = "select fid,spendtime from formula";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rst = stmt.executeQuery(sql)) {
            while (rst.next()) {
                if (rst.getInt(1) == fid)
                    ftime = rst.getString(2);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }
        return ftime;
    }

    public static boolean Modify_JDBC_USER_gold(int uid, int gold) {  //查询对库存表的修改是否成功
        boolean successful = false;
        int result = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cne) {
            cne.printStackTrace();
        }
        String sql = "update user set gold = " + gold + " where uid = " + uid;
        try {
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            Statement stmt = conn.createStatement();
            result = stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        }
        if (result == 1) successful = true;
        return successful;
    }

    public static boolean Modify_Market_mnumber(int mid, int mnumber) {  //查询对市场表的修改是否成功
        boolean successful = false;
        int result = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cne) {
            cne.printStackTrace();
        }
        String sql = "update market set mnumber = " + mnumber + " where mid = " +
                mid;
        try {
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            Statement stmt = conn.createStatement();
            result = stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        }
        if (result == 1) successful = true;
        return successful;
    }

    public static boolean Delete_Market(int mid) {  //查询对市场表的删除是否成功
        boolean successful = false;
        int result = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cne) {
            cne.printStackTrace();
        }
        String sql = "delete from market where mid = " + mid;
        try {
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            Statement stmt = conn.createStatement();
            result = stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        }
        if (result == 1) successful = true;
        return successful;
    }

    public static boolean Delete_Workshop(int uid, int wwindow) {  //查询对市场表的删除是否成功
        boolean successful = false;
        int result = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cne) {
            cne.printStackTrace();
        }
        String sql = "delete from workshop where uid = " + uid + " and wwindow = " +
                wwindow;
        try {
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            Statement stmt = conn.createStatement();
            result = stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        }
        if (result == 1) successful = true;
        return successful;
    }

    public static void Insert_Market(int uid, String itemname, int mnumber, int price, int mwindow) {  //添加市场订单
//        int result = 0;
        int iid = 0;
//        int mid = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cne) {
            cne.printStackTrace();
        }
        String sql1 = "select iid, itemname from item";
        try (Connection conn1 = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement stmt1 = conn1.createStatement();
             ResultSet rst1 = stmt1.executeQuery(sql1)) {
            while (rst1.next()) {
                if (rst1.getString(2).equals(itemname))
                    iid = rst1.getInt(1);
            }
            String sql2 = "Insert into market values(null, " + uid + "," + iid + "," + mnumber + "," + price + "," + mwindow + ")";
            try {
                Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql2);
            } catch (SQLException se) {
                se.printStackTrace();
            }
//            String sql3 = "select mid, uid, mwindow from market";
//            try (Connection conn = DriverManager.getConnection(dbur3, JDBC_USER, JDBC_PASSWORD);
//                 Statement stmt = conn.createStatement();
//                 ResultSet rst = stmt.executeQuery(sql3)) {
//                while (rst.next()) {
//                    if (rst.getInt(2) == uid && rst.getInt(3) == mwindow)
//                        mid = rst.getInt(1);
//                }
//
//            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

//    public static boolean Delete_Market(int uid, int mwindow) {  //查询对商店表的删除是否成功
//        boolean successful = false;
//        int result = 0;
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException cne) {
//            cne.printStackTrace();
//        }
//        String sql = "delete from market where uid = " + Integer.toString(uid) + " and mwindow = " +
//                Integer.toString(mwindow);
//        try {
//            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
//            Statement stmt = conn.createStatement();
//            result = stmt.executeUpdate(sql);
//        } catch (SQLException se) {
//            se.printStackTrace();
//        }
//        if (result == 1) successful = true;
//        return successful;
//    }

    public static void main(String[] args) {
//        System.out.println(SignUp("nightsnowy", "123456"));
//        System.out.println(getUid("nightsnowy"));
//        stock[] mt = getProducts(0);
//        for (stock stock : mt) {
//            System.out.println(stock.getgoodsname() + stock.getgoodsnum());
//        }
//        Date t = new Date();
//        setWork(1,1,1,1,t);
//        System.out.println(t);
//        Date c = new Date(t.getTime() + 30 * 60 * 1000);
//        System.out.println(c);
    }
}
