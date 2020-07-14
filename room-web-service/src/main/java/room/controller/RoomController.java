package room.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import room.model.Room;
import room.service.RoomService;
import room.utils.Tools;

@RestController
public class RoomController {

	@Autowired
	private RoomService service;
	
	
	/**
	 * Get all the rooms
	 */
	@GetMapping("RoomService/rooms") 
	public List<Room> getrooms() throws IOException{
		return this.service.getAvailableRooms();
	}
	
	@GetMapping("RoomService/rooms/number")
	public long getNumberOfRooms() {
		return service.getNumber();
	}
	
	/**
	 * Get the room corresponding with the id
	 * @param id
	 */
	@GetMapping("RoomService/{id}")
	public String getRoomFeatures(@PathVariable String id) {
		Room room = service.getRoomById(id);
		return Tools.toJsonString(room);
	}
	
	@GetMapping("RoomService/joueurs/{idroom}")
	public String getPlayersByRoom(@PathVariable String idroom) {
		Room room = service.getRoomById(idroom);
		return room.getId_joueur1() + "/" + room.getId_joueur2();
	}
	
	@GetMapping("RoomService/addPlayer/{idroom}/{idplayer}")
	public void addPlayer(@PathVariable String idroom, @PathVariable String idplayer) throws IOException {
		Room room = service.getRoomById(idroom);
		room.setId_joueur2(Integer.parseInt(idplayer));
		service.save(room);
		//start the game
		service.startGame(room.getId(), room.getId_joueur1(), room.getId_joueur2());
	}
	
	/**
	 * Add a room
	 * @param room
	 */
	@PostMapping(value="RoomService/addRoom", consumes=MediaType.APPLICATION_JSON_VALUE)
	public int addRoom(@RequestBody Room room) {
		service.addRoom(room);
		return room.getId();
	}
	
	@GetMapping("RoomService/isRoomOk/{id}")
	public boolean isRoomOk(@PathVariable String id) {
		Room room = service.getRoomById(id);
		if(room.getId_joueur1() != 0 && room.getId_joueur2() != 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@GetMapping("RoomService/getBet/{id}")
	public int getBet(@PathVariable String id) {
		Room room = service.getRoomById(id);
		return room.getMise();
	}
	
	@DeleteMapping(value="RoomService/deleteRoom/{id}")
	public void deleteRoom(@PathVariable String id) {
		service.deleteRoom(id);
	}
}
