package steyn91.Schedule.repos;

import org.springframework.data.repository.CrudRepository;
import steyn91.Schedule.models.Subject;

public interface SubjectsRepo extends CrudRepository<Subject, Integer> {
}
