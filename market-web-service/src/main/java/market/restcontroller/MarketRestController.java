package market.restcontroller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import market.service.MarketService;

@RestController
public class MarketRestController {
	

	private MarketService marketService = new MarketService();
	
	

	/**
	 * Buy the imgId card to the user corresponding to the name given
	 * @param name
	 * @param imgId
	 * @return
	 * @throws IOException 
	 */
	@GetMapping("MarketService/Buy/{id}/{imgId}")
	public boolean buyCard(@PathVariable String id, @PathVariable String imgId) throws IOException {
		
		int userMoney = marketService.getUserMoney(id);        
        int cardPrice = marketService.getCardPrice(imgId);        
                  
		if(userMoney < cardPrice) {
			return false;
		}
		
		marketService.buyCard(id, imgId);
		marketService.updateUserMoney("buy", id,  String.valueOf(marketService.getCardPrice(imgId)));
		return true;
		
		
	}

	
	/**
	 * Sell the imgId card to the user corresponding to the name given
	 * @param name
	 * @param imgId
	 * @throws IOException 
	 */
	@GetMapping("MarketService/Sell/{name}/{imgId}")
	public boolean sellCard(@PathVariable String name, @PathVariable String imgId) throws IOException {
		marketService.sellCard(imgId);   
		marketService.updateUserMoney("sell", name, String.valueOf(marketService.getCardPrice(imgId)));
		return true;
	}
	
	
	
	/**
	 * Erase the last character of the string
	 * @param String
	 * @return String
	 */
	private String CardStringConversion(String str) {
	        return str = str.substring(0, str.length() - 1);
	}
}
