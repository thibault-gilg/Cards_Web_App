package room.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import room.model.Room;

public interface RoomRepository extends CrudRepository<Room, Integer> {
		
		public Room findByName(String nom);

		public Room findById(int id);
		
		public List<Room> findAll();
		
		public long count();

}