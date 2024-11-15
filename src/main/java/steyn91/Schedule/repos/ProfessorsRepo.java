package steyn91.Schedule.repos;

import org.springframework.data.repository.CrudRepository;
import steyn91.Schedule.models.Professor;

public interface ProfessorsRepo extends CrudRepository<Professor, Integer> {
}
