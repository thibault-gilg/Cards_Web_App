package play.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class PvpGame {
//PARAMETERS
	private String roomid;
	private List<Player> playerList = new ArrayList<Player>(); //list d'id de carte possédé par le joueur
	public String results = "";
	public boolean gameOver = false;
	//public boolean round = false;

//CONSTRUCTORS
	public PvpGame() {
	}
	
//METHODS
	//roomId methods
	public void setRoom(String roomId) {
		this.roomid = roomId;
	}
	
	public String getRoom() {
		return this.roomid;
	}
	
	//playerList methods
	public void addPlayer(Player player) {
		this.playerList.add(player);
	}
	
	public Player getPlayerById(String id) {
		for (Player player: this.getPlayerList()) {
	    	if (player.getPlayerId().contentEquals(id)) {
	    		return player;
	    	}
	    }
		return null;
	}
	
	public List<Player> getPlayerList(){
		return this.playerList;
	}
	
	//Room results methods
	public String getRoomResults() {
		return this.results.substring(0, this.results.length() -1);
	}
	
	public void setUpdateResults(String winnerId) {
		this.results = this.results + winnerId + "/";
	}
	
	//Game Over Methods
	public boolean getGameOver() {
		return this.gameOver;
	}
	
	public void setGameOver() {
		this.gameOver = true;
	}
	
}

