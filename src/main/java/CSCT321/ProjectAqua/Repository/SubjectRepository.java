package CSCT321.ProjectAqua.Repository;

import CSCT321.ProjectAqua.Model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository interface for Subject-related database operations
@Repository // Marks this class as a Repository indicating that it's a data access object
public interface SubjectRepository extends JpaRepository<Subject,Long> {
}