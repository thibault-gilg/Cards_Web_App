package user.restcontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import user.model.UserEntity;
import user.service.UserService;
import user.util.Tools;


@RestController
public class UserRestController {
	
	
	@Autowired
	private UserService userService;
	
	/**
	 * Get user information from name
	 * @param name
	 * @return UserEntity
	 */
	@GetMapping("UserService/user/{name}")
	public UserEntity getUser(@PathVariable String name) {
		return userService.getUserByName(name);
	}
	
	/**
	 * Get user id information from name
	 * @param name
	 * @return UserEntity
	 */
	@GetMapping("UserService/userId/{name}")
	public String getUserId(@PathVariable String name) {
		return userService.getUserByName(name).getId().toString();
	}
	
	/**
	 * Get user name information from id
	 * @param name
	 * @return UserEntity
	 */
	@GetMapping("UserService/getName/{id}")
	public String getName(@PathVariable String id) {
		return userService.getUserById(id).getName();
	}
	
	/**
	 * Get the money of the user
	 * @param name
	 * @return String the money of the user
	 */
	@GetMapping("UserService/money/{id}")
	public String getMoney(@PathVariable String id) {
		return userService.getUserById(id).getMoney().toString();
	}
	
	/**
	 * Update the money of the user
	 * @param name
	 * @return String the money of the user
	 */
	@GetMapping("UserService/{type}/{id}/{money}")
	public void updateMoney(@PathVariable String type, @PathVariable String id, @PathVariable String money) {
		UserEntity user =  userService.getUserById(id);
		if (type.contentEquals("sell")) {
			user.setMoney(user.getMoney() + Integer.parseInt(money));
		} else {
			user.setMoney(user.getMoney() - Integer.parseInt(money));
		}
		userService.updateUser(user);
	}
	
	
	/**
	 * Return information of user if name and password match, otherwise return void string
	 * @param name
	 * @param pswd
	 * @return String 
	 */
	@GetMapping("UserService/user/{name}/{pswd}")
	public String checkUser(@PathVariable String name, @PathVariable String pswd){
		UserEntity user = userService.getUserByName(name);
		if(user == null) {
			return "";
		}
		else if(!user.getPassword().contentEquals(pswd)) {
			return "";
		}
		return Tools.toJsonString(user);
	}
	
	/**
	 * Remove the money to the id specified
	 * @param userId
	 * @param money
	 */
	@GetMapping("UserService/removeMoney/{userId}/{money}")
	public void removeMoney(@PathVariable String userId, @PathVariable String money) {
		UserEntity user = userService.getUserById(userId);
		user.setMoney(user.getMoney() - Integer.parseInt(money));
		userService.updateUser(user);
		
	}
	
	/**
	 * Add the money to the id specified
	 * @param userId
	 * @param money
	 */
	@GetMapping("UserService/addMoney/{userId}/{money}")
	public void addMoney(@PathVariable String userId, @PathVariable String money) {
		UserEntity user = userService.getUserById(userId);
		user.setMoney(user.getMoney() + Integer.parseInt(money));
		userService.updateUser(user);
		
	}
	
	
	/**
	 * Add user in the database
	 * @param user
	 * @return boolean true if no errors
	 * @throws IOException
	 */
	@PostMapping(value="UserService/addUser", consumes=MediaType.APPLICATION_JSON_VALUE)
	public boolean addUser(@RequestBody UserEntity user) throws IOException {
		//Add 5 new cards to the user 
		//Call to CardWebService
		boolean bool = userService.addUser(user);
		if(!bool) {
			return false;
		}
		URL obj = new URL("http://localhost/CardService/initUser/" + user.getId());
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
        
		userService.updateUser(user);
		return true;
	}
	

}
