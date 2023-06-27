package CSCT321.ProjectAqua.Repository;

import CSCT321.ProjectAqua.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//Believe it or not this is all we need to communicate with the database
//This class may look like it doesn't do much, but it's the workhorse of the persistent storage
//We set this file as an interface, so we don't have to implement all the default JpaRepository functions (there are a lot)
//We can also define SQL queries with natural language and the SQL commands will be run automatically (This is finicky)
@Repository // Tells spring boot this a repository class in the repository layer
public interface StudentRepository extends JpaRepository<Student, Long> {

}
