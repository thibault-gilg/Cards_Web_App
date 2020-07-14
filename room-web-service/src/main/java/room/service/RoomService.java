package room.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import room.model.Room;
import room.repository.RoomRepository;

@Service
public class RoomService {

	@Autowired
	private RoomRepository RoomRepository;
	
	public Room getRoomByName(String name) {
		return RoomRepository.findByName(name);
	}
	
	public List<Room> getAll() {
		return RoomRepository.findAll();
	}
	
	public long getNumber() {
		return RoomRepository.count();
	}
	
	public void addRoom(Room room) {
		RoomRepository.save(room);
	}
	
	public void updateCard(Room room) {
		RoomRepository.save(room);
	}
	
	public void deleteRoom(String id) {
		RoomRepository.delete(RoomRepository.findById(Integer.parseInt(id)));
	}
	
	public Room getRoomById(String id) {
		return RoomRepository.findById(Integer.parseInt(id));
	}

	public void save(Room room) {
		RoomRepository.save(room);
		
	}

	public void startGame(int id, int id_joueur1, int id_joueur2) throws IOException {
		URL obj = new URL("http://localhost/PlayService/New/" + id + "/" + id_joueur1 + "_" + id_joueur2);
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

	public List<Room> getAvailableRooms() {
		List<Room> roomlist = RoomRepository.findAll();
		List<Room> availableRooms = new ArrayList<Room>();
		for(Room room : roomlist) {
			if(!(room.getId_joueur1() != 0 && room.getId_joueur2() != 0)) {
				availableRooms.add(room);
			}
		}
		return availableRooms;
	}
}
