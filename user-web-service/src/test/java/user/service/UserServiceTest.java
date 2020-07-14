package user.service;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import user.model.UserEntity;
import user.repository.UserRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserService.class)
public class UserServiceTest {

	@Autowired
	private UserService uService;

	@MockBean
	private UserRepository uRepo;
	
	UserEntity tmpUser = new UserEntity("jean", "Dupont", "azerazer66");
	
	@Test
	public void getCard() {
		Mockito.when(
				uRepo.findById(Mockito.anyInt())
				).thenReturn(tmpUser);
		UserEntity UserInfo = uService.getUserById("12");
		assertTrue(UserInfo.toString().equals(tmpUser.toString()));
	}
}
