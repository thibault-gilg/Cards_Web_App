package card.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import card.model.CardEntity;
import card.repository.CardRepository;

@Service
public class CardService {
	
	@Autowired
	private CardRepository cardRepository;
	
	public CardEntity getCardByName(String Name) {
		return cardRepository.findByName(Name);
	}
	
	public long getNumber() {
		return cardRepository.count();
	}
	
	public List<CardEntity> getAll(){
		return cardRepository.findAll();
	}
	
	
	
	public void addCard(CardEntity card) {
		cardRepository.save(card);
	}
	
	public void updateCard(CardEntity card) {
		cardRepository.save(card);
	}
	
	public void deleteCard(String id) {
		cardRepository.delete(cardRepository.findById(Integer.parseInt(id)));
	}

	public CardEntity getCardById(String id) {
		return cardRepository.findById(Integer.parseInt(id));
	}

	public String getUserCards(String userid) {
		List<CardEntity> cardList =  cardRepository.findByUserId(userid);
		String idList = "";
		for (CardEntity card: cardList) {
			idList += card.getId() + "/";
		}
		
		return idList.substring(0, idList.length() - 1 );
	}

	public String getBuyableCards() {
		List<CardEntity> cardList =  cardRepository.findByUserIdNull();
		String idList = "";
		for (CardEntity card: cardList) {
			idList += card.getId() + "/";
		}
		
		return idList.substring(0, idList.length() - 1 );
	}

	public List<CardEntity> pickCards(String id) {
		List<CardEntity> cardList =  cardRepository.findByUserId(id);
		List<CardEntity> pickedList = new ArrayList<CardEntity>();
		Random rand = new Random();
		for (int i = 0; i < 5; i++) {
	        int randomIndex = rand.nextInt(cardList.size());
	        CardEntity randomCard = cardList.get(randomIndex);
	        pickedList.add(randomCard);
	        cardList.remove(randomIndex);
	    }
		return pickedList;
		
	}
	
	public void updateUserMoney(int price, String user) throws IOException {
		URL obj = new URL("http://localhost/UserService/buy/" + user + "/" + price);
        HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setRequestMethod("GET"); 
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
     
        BufferedReader in = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer response1 = new StringBuffer();
                 while ((inputLine = in.readLine()) != null) {
                   response1.append(inputLine);
                 } in .close();
	}
}
	
