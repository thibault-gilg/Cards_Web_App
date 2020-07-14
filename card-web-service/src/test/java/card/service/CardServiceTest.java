package card.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import card.model.CardEntity;
import card.repository.CardRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CardService.class)
public class CardServiceTest {

	@Autowired
	private CardService cService;

	@MockBean
	private CardRepository cRepo;
	
	CardEntity tmpCard = new CardEntity("kassa", "kassadin.jpeg", "that's a lot of damage", 50, 0);
	
	@Test
	public void getCard() {
		Mockito.when(
				cRepo.findById(Mockito.anyInt())
				).thenReturn(tmpCard);
		CardEntity CardInfo = cService.getCardById("12");
		assertTrue(CardInfo.toString().equals(tmpCard.toString()));
		long n = cService.getNumber();
		List<CardEntity> lCard = cService.getAll();
		assertTrue(n == lCard.size());
		cService.addCard(new CardEntity("Trynda", "trynda.jpeg", "that's a lot of damage", 20, 10));
		cService.updateCard(new CardEntity("Trynda", "trynda.png", "that's a lot of damage", 20, 10));
		cService.deleteCard("1");
	}
	
	@Test
	public void usertests() {
		List<CardEntity> lCard = new ArrayList<CardEntity>();
		for (int i = 0; i < 5; i++) { 
			lCard.add(tmpCard);
		}
		Mockito.when(
				cRepo.findByUserId(Mockito.any())
				).thenReturn(lCard);
		assertTrue(cService.getUserCards("a").equals("null/null/null/null/null"));
		assertTrue(cService.pickCards("a").size() == 5);

	}
	
	@Test
	public void buyable() {
		List<CardEntity> lCard = new ArrayList<CardEntity>();
		for (int i = 0; i < 5; i++) { 
			lCard.add(tmpCard);
		}
		Mockito.when(cRepo.findByUserIdNull()).thenReturn(lCard);
		assertTrue(cService.getBuyableCards().equals("null/null/null/null/null"));
	}
}
