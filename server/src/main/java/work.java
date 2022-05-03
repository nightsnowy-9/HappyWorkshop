import java.util.Date;

public class work {//工坊用户名、窗口号、作业次数、结束时间、公式号
	int windownum;//窗口号
	int workid;//公式号
	Date endtime;//结束时间
	int worknum;//作业次数
	int userid;//用户id
	public String getwindownum() {
		return String.valueOf(windownum);
	}
	public int getworkid() {
		return workid;
	}
	public int getworknum() {
		return worknum;
	}
	public Date getendtime() {
		return endtime;
	}
	public void setwindownum(int n) {
		windownum=n;
	}
	public void setworkid(int m) {
		workid=m;
	}public void setworknum(int p) {
		worknum=p;
	}
	public void setendtime(Date t) {
		endtime=t;
	}
	public void setuserid(int id) {
		userid=id;
	}
}
