package card.model;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CardEntityTest {
	private List<String> stringList;
	private List<Integer> intList;


	@Before
	public void setUp() {
		System.out.println("[BEFORE TEST]");
		stringList = new ArrayList<String>();
		intList = new ArrayList<Integer>();
		stringList.add("normalString1");
		stringList.add("normalString2");
		stringList.add(";:!?!:/!;;<>");
		intList.add(5);
		intList.add(500);
		intList.add(-1);

	}

	@After
	public void tearDown() {
		System.out.println("[AFTER TEST]");
		stringList = null;
		intList = null;
	}

	@Test
	public void createCard() {
		System.out.println("[TEST1]");
		for(String msg:stringList) {
			for(String msg2:stringList) {
				for(String msg3:stringList) {
					for(Integer msg4:intList) {
						for(Integer msg5:intList) {
							CardEntity h=new CardEntity(msg, msg2, msg3, msg4, msg5);
							//System.out.println("msg:"+msg+", msg2:"+msg2+", msg3:"+msg3+", msg4:"+msg4);
							assertTrue(h.getName() == msg);
							assertTrue(h.getImgUrl() == msg2);
							assertTrue(h.getDescription() == msg3);
							assertTrue(h.getAttack() == msg4);
							assertTrue(h.getDefence() == msg5);
							h.setAttack(5);
							h.setDefence(5);
							h.setDescription("description");
							h.setId(3);
							h.setImgUrl("image.png");
							h.setName("Trynda");
							h.setPrice(500);
							h.setUserId("3");
							assertTrue(h.getName() == "Trynda");
							assertTrue(h.getImgUrl() == "image.png");
							assertTrue(h.getDescription() == "description");
							assertTrue(h.getAttack() == 5);
							assertTrue(h.getDefence() == 5);	
							assertTrue(h.getId() == 3);	
							assertTrue(h.getPrice() == 500);	
							assertTrue(h.getUserId() == "3");	
							}
					}	
				}	
			}
		}
	}
	
	@Test
	public void displayCard() {
		System.out.println("[TEST2]");
		Random rand = new Random();
		String name = "Trundamere";
		String imgUrl = "/images/" + name + ".png";
		String description = "meilleur perso";
		int attack = rand.nextInt(100) + 30;
		int defence = rand.nextInt(100) + 30;
		CardEntity h = new CardEntity(name, imgUrl, description, attack, defence);
		String expectedResult="Card Name : " + name + "\n " + "Card Description : " + description + "\n \n";;
		assertTrue(h.toString().equals(expectedResult));
	}
	
	@Test
	public void listCard() {
		System.out.println("[TEST3]");
		CardFactory cf = new CardFactory();
		List<CardEntity> cardlist = cf.testCards();
		assertTrue(cf.getnames().length == cardlist.size());
		String[] str = {"Trynda"};
		cf.setnames(str);
		assertTrue(cf.getnames() == str);
	}
}

	
