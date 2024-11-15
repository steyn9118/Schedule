package steyn91.Schedule.repos;

import org.springframework.data.repository.CrudRepository;
import steyn91.Schedule.models.Student;

public interface StudentsRepo extends CrudRepository<Student, Integer> {
}
