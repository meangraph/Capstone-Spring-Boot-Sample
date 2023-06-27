package CSCT321.ProjectAqua.Service;

import CSCT321.ProjectAqua.Model.Subject;
import CSCT321.ProjectAqua.Repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

// Service for Subject-related operations
@Service // Marks the class as a service provider for the application
public class SubjectService {

    // Dependency injection for SubjectRepository and EntityUpdateService
    @Autowired // Tells Spring to automatically wire the SubjectRepository and EntityUpdateService beans into this class
    private SubjectRepository subjectRepository;
    @Autowired
    private EntityUpdateService entityUpdateService;

    // Method for creating a new subject
    public Subject createSubject(Subject subject) {
        // Save the subject in the repository and return the result
        return ResponseEntity.ok(subjectRepository.save(subject)).getBody();
    }

    // Method for getting a subject by ID
    public Subject getSubjectByID(Long id) {
        // Find the subject in the repository by ID, throw an exception if not found
        return subjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    // Method for getting all subjects
    public List<Subject> getAllSubjects() {
        // Return all subjects from the repository
        return subjectRepository.findAll();
    }

    // Method for editing an existing subject
    @Transactional // Marks a method as requiring a database transaction
    public Subject editSubject(Long id, Subject editedSubject) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        // Find the old subject in the repository by ID, throw an exception if not found
        Subject oldSubject = subjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Subject not found with ID:" + id));
        // Call the partialUpdate method of EntityUpdateService to edit the old subject
        entityUpdateService.partialUpdate(oldSubject,editedSubject);
        // Save the edited subject in the repository and return the result
        return ResponseEntity.ok(subjectRepository.save(oldSubject)).getBody();
    }
}
