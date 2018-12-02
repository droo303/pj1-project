package game;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {
    public boolean createNewCoin = false;
    public boolean otherPlayerCreatedCoin = false;
    public BufferedReader br;
    public Socket socket;
    public PrintWriter out;
    public Controller controller;

    Client (Controller controller) {
        this.controller = controller;
    }

    public void init() throws IOException {
        final String host = "localhost";
        final int portNumber = 21000;
        System.out.println("Creating socket to '" + host + "' on port " + portNumber);

        socket = new Socket(host, portNumber);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void run() {
        try {
            this.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true) {
            String userInput;
            String server;
            if (this.controller.createTreesElsewhere) {
                out.println("addcoin");
                this.controller.createTreesElsewhere = false;
            }
            try {
                if (br.ready()) {
                    server = br.readLine();
                    if (server.equals("addcoin")) {
                        this.controller.createTreesHere = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}