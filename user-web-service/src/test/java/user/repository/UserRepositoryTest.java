package user.repository;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import user.model.UserEntity;

//Penser à bien enlever la base de donnée pour pouvoir lancer les tests

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository uRepo;

    @Before
    public void setUp() {
    	System.out.println("[BEFORE TEST]");
		String name = "Lucas";
		String surname = "Bérard";
		String password = "SaraydaryanMeilleurProf";
		UserEntity x = new UserEntity(name, surname, password);
    	uRepo.save(x);
    }

    @After
    public void cleanUp() {
    	System.out.println("[AFTER TEST]");
    	uRepo.deleteAll();
    }

    @Test
    public void finds() {
    	System.out.println("[TEST1]");
        List<UserEntity> UserList = uRepo.findAll();
        assertTrue(UserList.size() == 1);
        assertTrue(UserList.get(0).getName().equals("Lucas"));
        assertTrue(UserList.get(0).getSurname().equals("Bérard"));
        assertTrue(UserList.get(0).getPassword().equals("SaraydaryanMeilleurProf"));
        UserEntity objtemp = new UserEntity("jean", "Dupont", "azerazer66");
    	uRepo.save(objtemp);
    	uRepo.save(new UserEntity("Jacques", "Nayradyaras", "QE74/&x98Uv%?"));
    	assertTrue(uRepo.findAll().size() == 3);
    	assertTrue(uRepo.findOneByName("Jacques").getName() == "Jacques");
    	assertTrue(uRepo.findOneByName("jean").getName() == "jean");
    	assertTrue(uRepo.findOneByName("Lucas").getName() == "Lucas");
    	assertTrue(uRepo.findById(1).getId() == 1);
    }
}
