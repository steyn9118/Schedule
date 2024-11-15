package steyn91.Schedule.repos;

import org.springframework.data.repository.CrudRepository;
import steyn91.Schedule.models.Group;

public interface GroupsRepo extends CrudRepository<Group, Integer> {
}
