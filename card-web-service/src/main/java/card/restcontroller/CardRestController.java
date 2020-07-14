package card.restcontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import card.model.CardEntity;
import card.model.CardFactory;
import card.util.Tools;
import card.service.CardService;

@RestController
public class CardRestController {
	
	private CardFactory cardFactory = new CardFactory();
	
	@Autowired
	private CardService cardService;
	
	public CardRestController(CardService cardService) {
		this.cardService = cardService;
		List<CardEntity> cardlist = this.generateCard();
		for (int i = 0; i < cardlist.size(); i++) {
			CardEntity card = cardlist.get(i);
			cardService.addCard(card);
		}
	}
	
	/**
	 * Get all the cards of the user
	 * @param Userid
	 * @return String
	 * @throws IOException
	 */
	@GetMapping("CardService/cards/{Userid}")
	public String getcard(@PathVariable String Userid) throws IOException {
		String userCards = cardService.getUserCards(Userid);
		return userCards;
	}
	
	
	@GetMapping("CardService/initUser/{userid}")
	public void initUser(@PathVariable String userid) {
		for (int i = 0; i < 5; i++) {
			CardEntity card = cardFactory.createCard();
			card.setUserId(userid);
			cardService.addCard(card);
		}
	}
	
	@GetMapping("CardService/user/BuyCards")
	public String getBuyableCards() {
		return cardService.getBuyableCards();
	}
	
	
	/**
	 * Get number of cards in the database
	 * @return long
	 */
	@GetMapping("CardService/card/number")
	public long getNumber() {
		return cardService.getNumber();
	}
	
	/**
	 * Get the card with corresponding id
	 * @param id
	 * @return String Json of the card
	 */
	@GetMapping("CardService/{id}")
	public String getCardFeatures(@PathVariable String id) {
		CardEntity card = cardService.getCardById(id);
		return Tools.toJsonString(card);
	}
	
	/**
	 * Get the price of card with corresponding id
	 * @param id
	 * @return String price the card
	 */
	@GetMapping("CardService/money/{id}")
	public String getMoney(@PathVariable String id) {
		CardEntity card = cardService.getCardById(id);
		return card.getPrice().toString();
	}
	
	
	/**
	 * Add a card 
	 * @param card
	 */
	@PostMapping(value="CardService/addCard", consumes=MediaType.APPLICATION_JSON_VALUE)
	public void addCard(@RequestBody CardEntity card) {
		cardService.addCard(card);
	}
	
	/**
	 * Change property userId of the card specified
	 * @param String userId
	 * @param String imgId
	 * @return String the json format of the card changed
	 */
	@GetMapping("CardService/buy/{userId}/{imgId}")
	public String buyCard(@PathVariable String userId, @PathVariable String imgId) {
		CardEntity card = cardService.getCardById(imgId);
		card.setUserId(userId);
		cardService.updateCard(card);
		return Tools.toJsonString(card);
	}
	
	/**
	 * Sell the card specified
	 * @param imgId
	 * @return String the json format of the card changed
	 */
	@GetMapping("CardService/sell/{imgId}")
	public String sellCard(@PathVariable String imgId) {
		CardEntity card = cardService.getCardById(imgId);
		card.setUserId(null);
		cardService.updateCard(card);
		return Tools.toJsonString(card);
	}
	
	/**
	 * Pick 5 random cards to the player id specified
	 * @param String id
	 */
	@GetMapping("CardService/pickCards/{id}")
	public String pick(@PathVariable String id) {
		List<CardEntity> cardList = cardService.pickCards(id);
		return Tools.toJsonString(cardList);
	}
	
	
	
	/**
	 * Generation of initial cards
	 * @return List<CardEntity>
	 */
	private List<CardEntity> generateCard(){
		List<CardEntity> cardlist = new ArrayList<CardEntity>();
		for (int i = 0; i < 20; i++) {
			cardlist.add(this.cardFactory.createCard());
		}
		
		
		return cardlist;
	}
	
	
	@GetMapping("CardService/craft/{id}")
	public String craft(@PathVariable String id) throws IOException {
		CardFactory cardFactory = new CardFactory();
		CardEntity card = cardFactory.createCard();
		card.setUserId(id);
		cardService.addCard(card);
		cardService.updateUserMoney(card.getPrice(), id);
		return Tools.toJsonString(card);
	}


}

