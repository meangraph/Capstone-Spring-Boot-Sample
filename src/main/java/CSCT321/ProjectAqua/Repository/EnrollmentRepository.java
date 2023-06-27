package CSCT321.ProjectAqua.Repository;

import CSCT321.ProjectAqua.Model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    //Usage of a custom Jpa Query using natural language [ no SQL :) ]
    //find (the return object type) by x (what we are passing into the function)
    List<Enrollment> findBySubjectId(Long id);
}
