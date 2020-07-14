package card.repository;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import card.model.CardEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CardRepositoryTest {

    @Autowired
    CardRepository cRepo;

    @Before
    public void setUp() {
    	Random rand = new Random();
		String name = "Trundamere";
		String imgUrl = "/images/" + name + ".png";
		String description = "meilleur perso";
		int attack = rand.nextInt(100) + 30;
		int defence = rand.nextInt(100) + 30;
		CardEntity x = new CardEntity(name, imgUrl, description, attack, defence);
    	cRepo.save(x);
    }

    @After
    public void cleanUp() {
    	cRepo.deleteAll();
    }

    @Test
    public void finds() {
        List<CardEntity> CardList = cRepo.findAll();
        assertTrue(CardList.size() == 1);
        assertTrue(CardList.get(0).getName().equals("Trundamere"));
        assertTrue(CardList.get(0).getDescription().equals("meilleur perso"));
        assertTrue(CardList.get(0).getImgUrl().equals("/images/Trundamere.png"));
        CardEntity objTrynda = new CardEntity("trynda", "adTrynda.png", "dps", 10, 1);
    	cRepo.save(objTrynda);
    	cRepo.save(new CardEntity("kassa", "kassadin.jpeg", "that's a lot of damage", 50, 0));
    	assertTrue(cRepo.findAll().size() == 3);
    	assertTrue(cRepo.findByName("trynda").getName() == "trynda");
    	assertTrue(cRepo.findById(1).getId() == 1);
    	assertTrue(cRepo.findByUserId("14").isEmpty());
    	assertTrue(cRepo.findByUserIdNull().size() == 3);
    }
}
