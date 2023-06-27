package CSCT321.ProjectAqua.API;

import CSCT321.ProjectAqua.Model.Enrollment;
import CSCT321.ProjectAqua.Model.EnrollmentStatus;
import CSCT321.ProjectAqua.Model.Student;
import CSCT321.ProjectAqua.Model.Subject;
import CSCT321.ProjectAqua.Repository.EnrollmentRepository;
import CSCT321.ProjectAqua.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class TestAPI {

    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    private String testString = "Hello, world!";

    // Create
    @PostMapping("/create/{newString}")
    public String createString(@PathVariable String newString) {
        testString = newString;
        return "Created new string: " + testString;
    }

    // Read
    @GetMapping("/read")
    public String readString() {
        if (testString == null || testString.equals(""))
            return "String is null. Create a new string or update the string";
        return testString;
    }

    // Update
    @PutMapping("/update/{updatedString}")
    public String updateString(@PathVariable String updatedString) {
        testString = updatedString;
        return "Updated string to: " + testString;
    }

    // Delete
    @DeleteMapping("/delete")
    public String deleteString() {
        String oldString = testString;
        testString = null;
        return "Deleted string: " + oldString;
    }

    @PostMapping("/generate")
    public String generateData() {
        Random rand = new Random();

        // Create 4 Subjects
        for (int i = 0; i < 4; i++) {
            Subject subject = new Subject();
            subject.setSubjectCode("SUB" + (i + 1));
            subject.setCredits(6);
            subjectRepository.save(subject);
        }

        // Create 30 Students for each Subject and Enrollments
        for (int i = 0; i < 30; i++) {
            Student student = new Student();
            student.setFirstName("Student" + (i + 1));
            student.setLastName("LastName" + (i + 1));
            student.setPhoneNumber("1234567890");
            student.setDob(LocalDate.now().minusYears(20 + rand.nextInt(10)));
            student.setDescription("A test student.");

            // Enroll the student in all 4 Subjects
            for(int j=0; j<4; j++) {
                Subject subject = subjectRepository.findById((long) j+1).get();
                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(student);
                enrollment.setSubject(subject);

                double grade = 1 + rand.nextDouble() * 100; // Random grade between 1 and 100
                enrollment.setGrade(grade);

                if (grade < 50) {
                    enrollment.setEnrollmentStatus(EnrollmentStatus.FAIL);
                } else {
                    enrollment.setEnrollmentStatus(EnrollmentStatus.COMPLETED);
                }

                enrollmentRepository.save(enrollment);

                // add enrollment to student's enrollments set
                student.getEnrollments().add(enrollment);
            }

            // call the calculateWAM method to update the WAM before saving the student
            student.calculateWAM();
        }
        return "Test Data Generated";
    }
}
