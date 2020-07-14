package play.model;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


import org.springframework.boot.json.JsonParseException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class Player {
//PARAMETERS
	private String id = "";
	private List<CardPublic> cardList = new ArrayList<CardPublic>();
	private List<CardPublic> staticCardList = new ArrayList<CardPublic>();
	private List<CardPublic> CardListResult = new ArrayList<CardPublic>();



	private int selectedCardId = 0;
	private int score = 0;
	private CardPublic selectedCard = null;
	
//CONSTRUCTORS
	public Player() {	
	}
	
	public Player(String id) throws JsonParseException, JsonMappingException, IOException {
		this.id = id;
		//Get random cards from the CardService and store it in the cardList
		this.pickRandom(this.id);
		for (CardPublic card: this.cardList) {
			this.staticCardList.add(new CardPublic(card.getId(),
			card.getUserId(), card.getName(), card.getImgUrl(), card.getDescription(), 
			card.getAttack(), card.getDefence(), card.getPrice()));
		}
	}
	
//METHODS
	//playerId methods
	public void setPlayerId(String Id) {
		this.id = Id;
	}
	
	public String getPlayerId() {
		return this.id;
	}
	
	//selectedCard methods
	/*
	public void setSelectedCardId(int Id) {
		this.selectedCardId = Id;
		for (CardPublic card: this.getCardList()) {
			if (card.getId() == this.selectedCardId) {
					this.setSelectedCard(card);
			}
		}
	}
	*/
	

	public List<CardPublic> getCardListResult() {
		return CardListResult;
	}

	public void setCardListResult(List<CardPublic> cardListResult) {
		CardListResult = cardListResult;
	}
	
	public List<CardPublic> getStaticCardList() {
		return staticCardList;
	}

	
	public CardPublic getSelectedCard() {
		return this.selectedCard;
	}
	
	public void setSelectedCard(CardPublic selectedCard) {
		this.selectedCard = selectedCard;
	}

	
	public int getSelectedCardId() {
		return selectedCardId;
	}

	public void setSelectedCardId(int cardId) {
		this.selectedCardId = cardId;
	}
	
	
	//cardList methods
	public List<CardPublic> getCardList(){
		return this.cardList;
	}
	
	public void addCard(CardPublic card){
		this.cardList.add(card);
		this.CardListResult.add(card);
	}
	
	public void addStaticCard(CardPublic card) {
		this.staticCardList.add(card);
	}
	
	public void removeCard() {
		Iterator<CardPublic> itr = this.cardList.iterator();
		// remove selected card
		while (itr.hasNext()) {
		    CardPublic card = itr.next();
		       if (card.getId() == this.selectedCardId) {
		           itr.remove();
		        }
		    }
		/*
		for (CardPublic card: this.getCardList()) {
			if (card.getId() == this.selectedCardId) {
				System.out.println("card to remove : " + card.getName());
				this.getCardList().remove(card);
			}
		}
		*/
	}
	
	//Methods for Score
	public int getScore() {
		return this.score;
	}
	
	public void addScore(int point) {
		this.score += point;
	}
	
	//Randomly choose 5 cards from user's collection
	private void pickRandom(String id) throws JsonParseException, JsonMappingException, IOException {
		URL url = new URL("http://localhost/CardService/pickCards/" + id);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
        StringBuffer response1 = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
        	response1.append(inputLine);
		} in .close();
		
		
	    //TODO Conversion en objets Event 
		ObjectMapper mapper = new ObjectMapper();
		//System.out.println(response1.toString());
        List<CardPublic> cards= mapper.readValue(response1.toString(), new TypeReference<List<CardPublic>>(){});
        int i;
		for(i = 0; i< cards.size(); i++) {
			this.addCard(cards.get(i));
			//System.out.println(cards.get(i).toString() + "\n");
		}
		
	}

}
