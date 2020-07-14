package play.restcontroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import play.util.Tools;
import play.model.CardPublic;
import play.model.Player;
import play.model.PvpGame;
import play.service.PlayService;

@RestController
public class PlayRestController {
	private ArrayList<PvpGame> listPvP =new ArrayList<PvpGame>();
	
	
	private PlayService playService = new PlayService();
	
	public PlayRestController() {
	}
	
	@GetMapping("PlayService/New/{roomId}/{userlist}")
	public void newPvpGame(@PathVariable String roomId, @PathVariable String userlist) throws IOException {
		//System.out.println("joueurs selctionnés : " + userlist);
		PvpGame pvpGame =new PvpGame();
		pvpGame.setRoom(roomId);
		listPvP.add(pvpGame);
		playService.loadPlayers(listPvP, roomId, userlist);
	}
	
	
	@GetMapping("PlayService/GetResults/{roomId}")
	public String getRoomResults(@PathVariable String roomId) throws IOException {
		return playService.returnResults(this.listPvP, roomId);
	}
	
	
	@GetMapping("PlayService/GetCardsIds/{roomId}/{userId}")
	public String getCardsIds(@PathVariable String roomId, @PathVariable String userId) {
		return playService.returnUserCards(this.listPvP, roomId,userId);
	}
	
	@GetMapping("PlayService/GetCards/{roomId}/{userId}")
	public String getCards(@PathVariable String roomId, @PathVariable String userId) {
		return Tools.toJsonString(playService.getUserCards(this.listPvP, roomId,userId));
		
	}
	
	@GetMapping("PlayService/GetCardsResult/{roomId}/{userId}")
	public String getCardsResult(@PathVariable String roomId, @PathVariable String userId) {
		return Tools.toJsonString(playService.getUserCardsResult(this.listPvP, roomId,userId));
		
	}

	

}
