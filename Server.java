import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	private static ServerSocket server;
	public static Vector<Player> playerList = new Vector<Player>();
	public static Vector<Room> roomList = new Vector<Room>();

	public static void main(String[] args) {
		try {
			server = new ServerSocket(Integer.parseInt(args[0]));
        	while (true) {
        		Socket clientSocket = server.accept();
        		Player p = new Player(clientSocket);
                playerList.add(p);
        		p.start();
        	}
        }
    	catch (IOException e) {
    		return;
    	}
	}
}