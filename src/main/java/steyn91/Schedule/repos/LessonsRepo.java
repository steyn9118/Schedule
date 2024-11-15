package steyn91.Schedule.repos;

import org.springframework.data.repository.CrudRepository;
import steyn91.Schedule.models.Lesson;

public interface LessonsRepo extends CrudRepository<Lesson, Integer> {
}
