import java.net.*;
import java.util.*;

public class Room {
	private Vector<Player> playerList;
	private String name;

	public Room(String name) {
		this.name = name;
		playerList = new Vector<Player>();
	}

	public String getName() {
		return name;
	}

	public Vector<Player> getPlayerList() {
		return playerList;
	}

	public void addPlayer(Player p) {
		playerList.add(p);
	}

	public void removePlayer(Player p) {
		playerList.remove(p);
	}
}