package market.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MarketService {

	public int getUserMoney(String id) throws IOException {
		URL obj = new URL("http://localhost/UserService/money/" + id);
        HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setRequestMethod("GET"); 
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine = "";
                StringBuffer money = new StringBuffer();
                 while ((inputLine = in.readLine()) != null) {
                   money.append(inputLine);
                 } in .close();
        return Integer.parseInt(money.toString());
	}
	
	public void updateUserMoney(String type, String user, String money) throws IOException {
		URL obj = new URL("http://localhost/UserService/" + type + "/" + user + "/" + money);
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

	public int getCardPrice(String imgId) throws IOException {
		URL obj = new URL("http://localhost/CardService/money/" + imgId);
		HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setRequestMethod("GET"); 
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine = "";
                StringBuffer cardPrice = new StringBuffer();
                 while ((inputLine = in.readLine()) != null) {
                	 cardPrice.append(inputLine);
                 } in .close();
                 
        return Integer.parseInt(cardPrice.toString());
	}

	public void buyCard(String id, String imgId) throws IOException {
		URL obj = new URL("http://localhost/CardService/buy/" + id + "/" + imgId);
		HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setRequestMethod("GET"); 
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine = "";
                StringBuffer response = new StringBuffer();
                 while ((inputLine = in.readLine()) != null) {
                   response.append(inputLine);
                 } in .close();
		
	}

	public void sellCard(String imgId) throws IOException {
		URL obj = new URL("http://localhost/CardService/sell/" + imgId);
		HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setRequestMethod("GET"); 
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine = "";
                StringBuffer response1 = new StringBuffer();
                 while ((inputLine = in.readLine()) != null) {
                   response1.append(inputLine);
                 } in .close();	
	}
	
	public String getUserId(String name) throws IOException {
		URL obj = new URL("http://localhost/UserService/userId/" + name);
		HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setRequestMethod("GET"); 
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine = "";
                StringBuffer id = new StringBuffer();
                 while ((inputLine = in.readLine()) != null) {
                	 id.append(inputLine);
                 } in .close();
        return id.toString();
	}


}
