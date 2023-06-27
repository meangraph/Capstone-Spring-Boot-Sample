package CSCT321.ProjectAqua.Service;

import CSCT321.ProjectAqua.Model.Student;
import CSCT321.ProjectAqua.Repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

// Service class for managing Student-related operations
@Service // Marks this class as a service class in the service layer. In Spring, services are generally the classes that contain the business logic
public class StudentService {

    // Dependency injection for StudentRepository and EntityUpdateService
    @Autowired // This is used for automatic dependency injection. Spring Boot will automatically create and inject instances of StudentRepository and EntityUpdateService
    private StudentRepository studentRepository;
    @Autowired
    private EntityUpdateService entityUpdateService;

    // Method to create a new student record
    public Student createStudent(Student student) {
        // Save the Student instance to the database using the StudentRepository
        // ResponseEntity.ok() returns a response with a status code of 200 (OK)
        // getBody() extracts the body from the response
        return ResponseEntity.ok(studentRepository.save(student)).getBody();
    }

    // Method to retrieve a student record by ID
    public Student getStudentByID(Long id) {
        // Call the findById method of StudentRepository, which returns an Optional
        // If the ID is not found in the database, throw an exception
        return studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    // Method to retrieve all student records
    public List<Student> getAllStudents() {
        // Call the findAll method of StudentRepository
        return studentRepository.findAll();
    }

    // Method to update a student record
    @Transactional // This annotation is used to ensure that this method is executed within a transaction context
    public Student updateStudent(Long id,Student student) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        // Retrieve the existing Student record from the database
        // If the ID is not found in the database, throw an exception
        Student oldStudent = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Student not found with ID:" + id));
        // Use the EntityUpdateService to partially update the old Student record with data from the new one
        entityUpdateService.partialUpdate(oldStudent,student);
        // Save the updated Student instance to the database
        return ResponseEntity.ok(studentRepository.save(oldStudent)).getBody();
    }
}