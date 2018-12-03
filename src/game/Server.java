package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Thread {

    public ServerSocket s;
    public Socket p1, p2;
    public PrintWriter bis1, bis2;
    public BufferedReader bos1, bos2;

    public Server() {
        try {
            s = new ServerSocket(21000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            p1 = s.accept();
            p2 = s.accept();
        } catch (IOException e) {

            e.printStackTrace();
        }

        try {
            bis1 = new PrintWriter(p1.getOutputStream());
            bis2 = new PrintWriter(p2.getOutputStream());
            bos1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
            bos2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!p1.isClosed() && !p2.isClosed()) { // if one of the players disconnect, the match will end.
            try {
                String p1;
                String p2;
                    if (bos1.ready()) {
                        p1 = bos1.readLine(); // what p1 says
                        if (!p1.equalsIgnoreCase("")) { // if what p1 says is something
                            if (p1.startsWith("addcoin")) {
                                bis2.println("addcoin");
                            }
                            if (p1.startsWith("youwon")){
                                bis2.println("youwon");
                            }
                        }
                    }

                    if (bos2.ready()) {
                        p2 = bos2.readLine(); // what p1 says
                    if (!p2.equalsIgnoreCase("")) { // if what p1 says is something
                        if (p2.startsWith("addcoin")) {
                            bis1.println("addcoin");
                        }
                        if (p2.startsWith("youwon")){
                            bis1.println("youwon");
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            bis1.flush();
            bis2.flush();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}