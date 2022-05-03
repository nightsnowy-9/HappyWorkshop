import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientHandlerThread extends Thread{
    private Socket socket;

    public ClientHandlerThread(Socket socket) {
        super();
        this.socket = socket;
    }

    public void run(){

        try{
            //（1）获取输入流，用来接收该客户端发送给服务器的数据
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //（2）获取输出流，用来发送数据给该客户端
            PrintStream ps = new PrintStream(socket.getOutputStream());
            String str;
            // （3）接收数据
            while ((str = br.readLine()) != null) {
                //（4）str为接收到的数据，梳理好以后返还给客服端
                   System.out.println("接收到的数据为"+str);
                String word=soldout.separate(str);
//                    word="服务器已收到数据，数据为"+str;
                //（5）返回给客户端
                ps.println(word);
            }
        }catch(Exception  e){
            e.printStackTrace();
        }finally{
            try {
                //（6）断开连接
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}