import java.io.*;
import java.net.*;
import java.util.*;

public class Player extends Thread {
    private Socket clientSocket;
    private String playerName;
    private Room room = null;
    private PrintStream os = null;

    public Player(Socket socket) {
      clientSocket = socket;
    }

    public void run() {
      String line;
      DataInputStream is;
      try {
        is = new DataInputStream(clientSocket.getInputStream());
        while (true) {
          line = is.readLine(); 
          process(line);
        }
      }   
      catch (IOException e) {
        System.out.println(e);
      }
    }

    public void send(String message) {
      try {
        os = new PrintStream(clientSocket.getOutputStream());
        os.println(message);
      }
      catch (IOException e) {
        System.out.println(e);
      }
    }

    public void sendToAll(String message) {
      for (Player p : Server.playerList) {
        p.send(message);
      }
    }

    public void sendToRoom(String message) {
      for (Player p : room.getPlayerList()) {
        p.send(message);
      }
    }    

    public void process(String command) {
      if (command.contains("create room")) {
        String roomName = command.replace("create room ", "");
        Room room = new Room(roomName);
        Server.roomList.add(room);
        send("room created: " + roomName);
      }
      else if (command.contains("get room list")) {
        String rooms = "";
        for (Room room : Server.roomList) {
          rooms = rooms + "'" + room.getName() + "' ";
        }
        send(rooms);
      }
      else if (command.contains("join")) {
        String roomName = command.replace("join ", "");
        for (Room r : Server.roomList) {
          if (roomName.equals(r.getName())) {
            r.addPlayer(this);
            room = r;
            sendToAll("player " + playerName + " has joined room " + room.getName());
            break;
          }
        }
      }
      else if (command.contains("leave")) {
        String roomName = command.replace("leave ", "");
        for (Room r : Server.roomList) {
          if (roomName.equals(r.getName())) {
            r.removePlayer(this);
            room = null;
            sendToAll("player " + playerName + " has left room " + roomName);
            break;
          }
        }
      }
      else if (command.contains("register")) {
        playerName = command.replace("register ", "");
        send("registered as " + playerName);
      }
      else if (command.contains("get room players")) {
        String players = "";
        for (Player p : room.getPlayerList()) {
          players = players + p.playerName + " ";
        }
        send(players);
      }
      else if (command.contains("start game")) {
        sendToRoom("starting game...");
      }
    }
}