
public class market {
	int marketid;//订单号
	int userid;//用户id
	String goodsname;//商品名
	int price;//价格
	int goodsnum;//数量
	int windowid;//窗口号
	public void setuserid(int i) {
		userid=i;
	}
	public void setwindowid(int j) {
		windowid=j;
	}
	public void setprice(int p) {
		price=p;
	}
	public void setgoodsnum(int n) {
		goodsnum=n;
	}
	public void setmarketid(int i) {
		marketid=i;
	}
	public void setgoodsname(String m) {
		goodsname=m;
	}
	public int getmarketid() {
		return marketid;
	}
	public int getuserid() {
		return userid;
	}
	public String getgoodsname() {
		return goodsname;
	}
	public int getprice() {
		return price;
	}
	public int getgoodsnum() {
		return goodsnum;
	}
	public int getwindowid() {
		return windowid;
	}
}
