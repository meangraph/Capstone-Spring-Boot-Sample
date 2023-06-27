package CSCT321.ProjectAqua.API;

import CSCT321.ProjectAqua.Model.Subject;
import CSCT321.ProjectAqua.Service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

// REST Controller for Subject-related operations
@RestController // Marks this class as a controller where every method returns a domain object instead of a view
@RequestMapping("/subject") // Mapping for the URL path
public class SubjectController {

    // Dependency injection for SubjectService
    @Autowired // Tells Spring to automatically wire the SubjectService bean into this class
    private SubjectService subjectService;

    // HTTP POST method for creating a new subject
    @PostMapping("/create") // Mapping for POST request
    public Subject createSubject(@RequestBody Subject subject) { // @RequestBody annotation binds the HTTPRequest body to the domain object
        // Call the createSubject method of SubjectService
        return subjectService.createSubject(subject);
    }

    // HTTP GET method for retrieving a subject by ID
    @GetMapping("/getByID/{id}") // Mapping for GET request, {id} is a path variable
    public Subject getSubjectByID (@PathVariable Long id) { // @PathVariable indicates that a method parameter should be bound to a URI template variable
        // Call the getSubjectByID method of SubjectService
        return subjectService.getSubjectByID(id);
    }

    // HTTP GET method for retrieving all subjects
    @GetMapping("/findAll") // Mapping for GET request
    public List<Subject> getAllSubjects() {
        // Call the getAllSubjects method of SubjectService
        return subjectService.getAllSubjects();
    }

    // HTTP POST method for updating an existing subject
    @PostMapping("/update/{id}") // Mapping for POST request, {id} is a path variable
    public Subject editSubject(@PathVariable Long id, @RequestBody Subject subject) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        // Call the editSubject method of SubjectService
        return subjectService.editSubject(id, subject);
    }
}