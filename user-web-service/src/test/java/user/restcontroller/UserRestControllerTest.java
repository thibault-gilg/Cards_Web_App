package user.restcontroller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import user.model.UserEntity;
import user.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserRestController.class)
public class UserRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService hService;

	UserEntity mockUser = new UserEntity("jean", "Dupont", "azerazer66");
	
	@Test
	public void retrieveUser() throws Exception {
		Mockito.when(
				hService.getUserById(Mockito.any())
				).thenReturn(mockUser);
				

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/UserService/50").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.print("AAA\n" + result.getResponse().getContentAsString() + "\nAAA");

		String expectedResult="la réponse n'est pas un json ? à l'aide";

		JSONAssert.assertEquals(expectedResult, result.getResponse()
				.getContentAsString(), false);
	}

}
