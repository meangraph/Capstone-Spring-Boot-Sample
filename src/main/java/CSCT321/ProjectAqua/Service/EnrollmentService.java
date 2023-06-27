package CSCT321.ProjectAqua.Service;

import CSCT321.ProjectAqua.Model.Enrollment;
import CSCT321.ProjectAqua.Model.EnrollmentStatus;
import CSCT321.ProjectAqua.Model.Student;
import CSCT321.ProjectAqua.Model.Subject;
import CSCT321.ProjectAqua.Repository.EnrollmentRepository;
import CSCT321.ProjectAqua.Repository.StudentRepository;
import CSCT321.ProjectAqua.Repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional //Since this class isn't the owning side of the relationship we need to mark this class as transactional
              //This applies ACID (either it all commits or nothing does) required for the IoC to pass checks
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private EntityUpdateService entityUpdateService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    public Enrollment createEnrollment(Enrollment enrollment) {

        Student student = studentRepository.findById(enrollment.getStudent().getStudentID())
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + enrollment.getStudent().getStudentID()));
        Subject subject = subjectRepository.findById(enrollment.getSubject().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid subject Id:" + enrollment.getSubject().getId()));

        //Set the student and subject into the enrollment and save the enrollment
        enrollment.setStudent(student);
        enrollment.setSubject(subject);
        enrollment.setEnrollmentStatus(EnrollmentStatus.ENROLLED);

        // Manually add the enrollment to the student/subject sets
        student.getEnrollments().add(enrollment);
        subject.getEnrollments().add(enrollment);

        return ResponseEntity.ok(enrollmentRepository.save(enrollment)).getBody();
    }

    public Enrollment deferEnrollment(Long enrollmentID) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentID).orElseThrow(() -> new EntityNotFoundException("Enrollment with ID: " + enrollmentID
                + " not found"));

        enrollment.setEnrollmentStatus(EnrollmentStatus.DEFERRED);

        return ResponseEntity.ok(enrollmentRepository.save(enrollment)).getBody();
    }

    public List<Enrollment> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentRepository.findAll()).getBody();
    }

    public List<Enrollment> getEnrollmentsBySubject(Long id) {
        return ResponseEntity.ok(enrollmentRepository.findBySubjectId(id)).getBody();
    }

    @Transactional
    public Enrollment finaliseSubject(Long enrollmentID, double mark) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentID).orElseThrow(() -> new EntityNotFoundException("Enrollment with ID: " + enrollmentID
                + " not found"));

        enrollment.setGrade(mark);

        Student student = studentRepository.findById(enrollment.getStudent().getStudentID())
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + enrollment.getStudent().getStudentID()));

        if (mark < 50) {
            enrollment.setEnrollmentStatus(EnrollmentStatus.FAIL);
        } else {
            enrollment.setEnrollmentStatus(EnrollmentStatus.COMPLETED);
        }

        // Save the enrollment first so that it has the updated status
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        // Update WAM after the enrollment status is updated
        student.calculateWAM();

        // Save the student to persist the new WAM
        studentRepository.save(student);

        // Return the updated enrollment
        return ResponseEntity.ok(savedEnrollment).getBody();
    }
}
