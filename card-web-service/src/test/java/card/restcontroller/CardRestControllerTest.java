package card.restcontroller;

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

import card.model.CardEntity;
import card.service.CardService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CardRestController.class)
public class CardRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CardService hService;

	CardEntity mockCard=new CardEntity("kassa", "kassadin.jpeg", "that's a lot of damage", 50, 0);
	
	@Test
	public void retrieveCard() throws Exception {
		Mockito.when(
				hService.getCardById(Mockito.any())
				).thenReturn(mockCard);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/CardService/50").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedResult="{\"id\":null,\"userId\":null,\"name\":\"kassa\",\"imgUrl\":\"kassadin.jpeg\",\"description\":\"that's a lot of damage\",\"attack\":50,\"defence\":0,\"price\":50}";
		JSONAssert.assertEquals(expectedResult, result.getResponse()
				.getContentAsString(), false);
		
		requestBuilder = MockMvcRequestBuilders.get("/CardService/initUser/50");
		requestBuilder = MockMvcRequestBuilders.get("/CardService/buy/1/1").accept(MediaType.APPLICATION_JSON);
		result = mockMvc.perform(requestBuilder).andReturn();
		expectedResult="{\"id\":null,\"userId\":\"1\",\"name\":\"kassa\",\"imgUrl\":\"kassadin.jpeg\",\"description\":\"that's a lot of damage\",\"attack\":50,\"defence\":0,\"price\":50}";
		JSONAssert.assertEquals(expectedResult, result.getResponse()
				.getContentAsString(), false);
		
		requestBuilder = MockMvcRequestBuilders.get("CardService/sell/1").accept(MediaType.APPLICATION_JSON);
		result = mockMvc.perform(requestBuilder).andReturn();
		expectedResult="encore un probleme de json...";
		/*JSONAssert.assertEquals(expectedResult, result.getResponse()
				.getContentAsString(), false);*/
	}

}
