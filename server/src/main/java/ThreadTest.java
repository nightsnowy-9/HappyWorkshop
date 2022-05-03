public class ThreadTest extends Thread{
    //线程体,启动线程时会运行run()方法中的代码
    npc t=new npc();
    public void run() {
        //输出100以内的偶数
        t.start();

        }
    }