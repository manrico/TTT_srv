import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.ttt.Message.*;

public class SocketServer {
    public static void main(String args[]) throws Exception {
        ServerSocket serverSocket;
        Socket fromClientSocket;
        int cTosPortNumber = 5843;
        Message m;

        serverSocket = new ServerSocket(cTosPortNumber);
        System.out.println("Waiting for a connection on " + cTosPortNumber);
        fromClientSocket = serverSocket.accept();
        System.out.println("fromClientSocket accepted");

        ObjectOutputStream oos = new ObjectOutputStream(fromClientSocket.getOutputStream());

        ObjectInputStream ois = new ObjectInputStream(fromClientSocket.getInputStream());

        while ((m = (Message) ois.readObject()) != null) {
            System.out.println("The message from client:  " + m);

            if (m.cmd == Command.PING) {
                System.out.println("PONG");
                break;
            }
        }
        oos.close();
        fromClientSocket.close();
    }
}
