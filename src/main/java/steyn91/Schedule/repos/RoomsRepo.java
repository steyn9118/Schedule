package steyn91.Schedule.repos;

import org.springframework.data.repository.CrudRepository;
import steyn91.Schedule.models.Room;

public interface RoomsRepo extends CrudRepository<Room, Integer> {
}
