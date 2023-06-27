package CSCT321.ProjectAqua.API;

import CSCT321.ProjectAqua.Model.Student;
import CSCT321.ProjectAqua.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

// REST Controller for Student-related operations
@RestController // Marks this class as a controller where every method returns a domain object instead of a view
@RequestMapping("/student") // Mapping for the URL path
public class StudentController {

    // Dependency injection for StudentService
    @Autowired // Tells Spring to automatically wire the StudentService bean into this class
    private StudentService studentService;

    // HTTP POST method for creating a new student
    @PostMapping("/create") // Mapping for POST request
    public Student createStudent(@RequestBody Student student) { // @RequestBody annotation binds the HTTPRequest body to the domain object
        // Call the createStudent method of StudentService
        return studentService.createStudent(student);
    }

    // HTTP GET method for retrieving a student by ID
    @GetMapping("/getByID/{id}") // Mapping for GET request, {id} is a path variable
    public Student getStudentByID(@PathVariable Long id) { // @PathVariable indicates that a method parameter should be bound to a URI template variable
        // Call the getStudentByID method of StudentService
        return studentService.getStudentByID(id);
    }

    // HTTP GET method for retrieving all students
    @GetMapping("/findAll") // Mapping for GET request
    public List<Student> getAllStudents() {
        // Call the getAllStudents method of StudentService
        return studentService.getAllStudents();
    }

    // HTTP POST method for updating an existing student
    @PostMapping("/update/{id}") // Mapping for POST request, {id} is a path variable
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        // Call the updateStudent method of StudentService
        return studentService.updateStudent(id,student);
    }
}

