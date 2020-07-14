package user.model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserEntityTest {
	private List<String> stringList;


	@Before
	public void setUp() {
		System.out.println("[BEFORE TEST]");
		stringList = new ArrayList<String>();
		stringList.add("normalString1");
		stringList.add("NormalString2");
		stringList.add(";:!?!:/!;;<>");
	}

	@After
	public void tearDown() {
		System.out.println("[AFTER TEST]");
		stringList = null;
	}

	@Test
	public void createUser() {
		System.out.println("[TEST1]");
		for(String msg:stringList) {
			for(String msg2:stringList) {
				for(String msg3:stringList) {
					UserEntity u = new UserEntity(msg, msg2, msg3);
					assertTrue(u.getName() == msg);
					assertTrue(u.getSurname() == msg2);
					assertTrue(u.getPassword() == msg3);
					assertTrue(u.getMoney() == 5000);

				}	
			}
		}
	}
	
	@Test
	public void displayUser() {
		System.out.println("[TEST2]");
		String name = "Lucas";
		String surname = "Bérard";
		String password = "SaraydaryanMeilleurProf";
		UserEntity h = new UserEntity(name, surname, password);
		String expectedResult= "Name : " + name + "\n" + " Surname : " + surname + "\n \n";
		assertTrue(h.toString().equals(expectedResult));
	}
}
