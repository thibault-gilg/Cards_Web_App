package play.service;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

import play.model.CardPublic;
import play.model.Player;
import play.model.PvpGame;

public class PlayService {
//CONSTRUCTOR
	public PlayService() {
	}
	
//Creation des joueurs, initialisation du pvpGame
	public void loadPlayers(ArrayList<PvpGame> listPvP, String roomId, String userlist) throws IOException {
		// Create output file (to visualize the simualtion process)
	    //FileOutputStream f = new FileOutputStream("file.txt"); //a modifier par l'utilisateur
	    //System.setOut(new PrintStream(f));
		//on recupere les id des joueurs et on creer des nouveaux Players
		StringTokenizer ST = new StringTokenizer(userlist, "_");
		String A = "" + ST.nextToken();
		String B = "" + ST.nextToken(); 
		Player playerA = new Player(A);	// on crée deux nouveaux joueurs selon le modele Player
		Player playerB = new Player(B);	// ces joueurs sont initialisés avec 5 cartes choisies au hasard dans leur collection
		// on selection la -PvpGame- associé a la -Room- selectionnée
		for (PvpGame pvpGame: listPvP) {
			if (pvpGame.getRoom().contentEquals(roomId)) {
				pvpGame.addPlayer(playerA);	//on ajoute les deux joueurs créés a liste de joueur de pvpGame
				pvpGame.addPlayer(playerB);
				this.playGame(listPvP, roomId); //on lance la partie
			}
		}
	}
	
	//lancement de la partie (boucle jusqu'a ce qu'un joueur obtienne la majorité des points
	public void playGame(ArrayList<PvpGame> listPvP, String roomId) {
		//System.out.println("##### SARTING NEW PVP GAME #####");
		for (PvpGame pvpGame: listPvP) {
			if (pvpGame.getRoom().contentEquals(roomId)) {
				for (Player player: pvpGame.getPlayerList()) {
					if (player.getCardList().size()!=0) {
						this.playRound(pvpGame);
					}
					if  (player.getCardList().size()==0) {
						if (player.getScore() >= 3) {
							//System.out.println("Player : " + player.getPlayerId() + " won the game !");
							pvpGame.setUpdateResults(player.getPlayerId());
							//System.out.println(this.returnResults(listPvP, roomId));
							return;
						}
					}
				}
				this.playGame(listPvP, roomId);
			}
		}
	}

	
	public void playRound(PvpGame pvpGame) {
		//System.out.println("##### SELECTING HEROS FOR ROUND #####");
				for (Player player: pvpGame.getPlayerList()) {
					//System.out.println("Selecting card for player : " + player.getPlayerId() + "\n");
					if (player.getSelectedCard() == null){
						player.setSelectedCard(player.getCardList().get(0));
						player.setSelectedCardId(player.getCardList().get(0).getId());
						//System.out.println("Selected card : " + player.getSelectedCard().toString());
					}
				}
				//System.out.println("Players ready." + "\n");
				this.roundProcess(pvpGame);
			}

		

	public void roundProcess(PvpGame pvpGame) {
		//System.out.println("##### FIGHT PROCESS #####");
		/*A chaque nouveau round (= appel de roundProcess), on renverse la liste pour inverser la priorité d'attaque 
		cad, pour eviter que ce soit toujours le joueur 1 qui effectue la premiere attaque
		*/
		Collections.reverse(pvpGame.getPlayerList());
			for (Player player: pvpGame.getPlayerList()) {
				ListIterator<Player> it = pvpGame.getPlayerList().listIterator();
				
				if (it.hasNext()) {
					// Definitoin des roles
					//System.out.println("---Nouvelle attaque---");
					CardPublic attaquant = it.next().getSelectedCard();
					CardPublic defenseur = it.next().getSelectedCard();
					//System.out.println("Attaquant : " + attaquant.toString());
					//System.out.println("Defenseur : " + defenseur.toString());
					// Simulation de l'attaque
					defenseur.setDefence(-attaquant.getAttack());
					//System.out.println(attaquant.getName() + "a infligé " + attaquant.getAttack() + " degats a : " + defenseur.getName() + "\n");
					// Si on a un vainqueur ...
					if (defenseur.getDefence() <= 0) {
						it.previous();
						it.previous().addScore(1);
						pvpGame.setUpdateResults(attaquant.getUserId());
						//System.out.println("winner : " + attaquant.getName()); 
						this.endRound(pvpGame);
						return;
					}
				}
				if (it.hasPrevious()) {
					// Definitoin des roles
					//System.out.println("---Nouvelle attaque---");
					CardPublic attaquant = it.previous().getSelectedCard();
					CardPublic defenseur = it.previous().getSelectedCard();
					//System.out.println("Attaquant : " + attaquant.toString());
					//System.out.println("Defenseur : " + defenseur.toString());
					// Simulation de l'attaque
					defenseur.setDefence(-attaquant.getAttack());
					//System.out.println(attaquant.getName() + "a infligé " + attaquant.getAttack() + " degats a : " + defenseur.getName() + "\n");
					// Si on a un vainqueur ...
					if (defenseur.getDefence() <= 0) {
						it.next();
						it.next().addScore(1);
						pvpGame.setUpdateResults(attaquant.getUserId());
						//System.out.println("winner : " + attaquant.getName()); 
						this.endRound(pvpGame);
						return;
					}
				}
			}
	}

	
	public void endRound(PvpGame pvpGame) {
		//System.out.println("##### ROUND RESULTS #####");
		for (Player player: pvpGame.getPlayerList()) {
			//System.out.println("Player " + player.getPlayerId() + " has " + player.getScore() + " points !");
			//System.out.println("Player " + player.getPlayerId() + " played " + player.getSelectedCard().toString() );
			player.removeCard();
			player.setSelectedCard(null);
			//System.out.println("Player " + player.getPlayerId() + " has " + player.getCardList().size() + " cards !" + "\n");
		}
		return;
	}
			

	public List<CardPublic> getUserCards(ArrayList<PvpGame> listPvP, String roomId, String userId) {
		//retourne la liste des id des cartes pour un user 
		for (PvpGame pvpGame: listPvP) {
			if (pvpGame.getRoom().contentEquals(roomId)) {
				return pvpGame.getPlayerById(userId).getStaticCardList();
			}
		}
		return null;
	}
	
	public List<CardPublic> getUserCardsResult(ArrayList<PvpGame> listPvP, String roomId, String userId) {
		//retourne la liste des id des cartes pour un user 
		for (PvpGame pvpGame: listPvP) {
			if (pvpGame.getRoom().contentEquals(roomId)) {
				return pvpGame.getPlayerById(userId).getCardListResult();
			}
		}
		return null;
	}

	
	public String returnResults(ArrayList<PvpGame> listPvP, String roomId) {
		for (PvpGame pvpGame: listPvP) {
			if (pvpGame.getRoom().contentEquals(roomId)) {
					return pvpGame.getRoomResults();
			}
		}
		return null;
	}
	
	public String returnUserCards(ArrayList<PvpGame> listPvP, String roomId, String userId) {
		String cardIds = "";
		for (PvpGame pvpGame: listPvP) {
			if (pvpGame.getRoom().contentEquals(roomId)) {
				for (Player player: pvpGame.getPlayerList()) {
					if (player.getPlayerId().contentEquals(userId)) {
						for (CardPublic card: player.getStaticCardList()){
							cardIds += card.getId() + "/";
						}
						return cardIds;
					}
				}
			}
		}
		return null;
	}

}
