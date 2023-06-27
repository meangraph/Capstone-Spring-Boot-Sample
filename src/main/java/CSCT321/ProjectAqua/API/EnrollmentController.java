package CSCT321.ProjectAqua.API;

import CSCT321.ProjectAqua.Model.Enrollment;
import CSCT321.ProjectAqua.Service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

// Controller for Enrollment-related requests
@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {

    // Automatically inject an instance of EnrollmentService
    @Autowired
    private EnrollmentService enrollmentService;

    // Endpoint for creating an enrollment
    @PostMapping("/create")
    public Enrollment createEnrollment(@RequestBody Enrollment enrollment) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        return enrollmentService.createEnrollment(enrollment);
    }

    // Endpoint for deferring an enrollment
    @PostMapping("/defer/{enrollmentID}")
    public Enrollment deferEnrollment(@PathVariable Long enrollmentID) {
        return enrollmentService.deferEnrollment(enrollmentID);
    }

    // Endpoint for retrieving all enrollments
    @GetMapping("/findAll")
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    // Endpoint for retrieving enrollments by subject
    @GetMapping("/findBySubject/{subjectID}")
    public List<Enrollment> getEnrollmentsBySubject(@PathVariable Long subjectID) {
        return enrollmentService.getEnrollmentsBySubject(subjectID);
    }

    // Endpoint for finalizing a subject
    @PostMapping("/finishSubject/{enrollmentID}/{mark}")
    public Enrollment finaliseSubject(@PathVariable Long enrollmentID, @PathVariable double mark) {
        return  enrollmentService.finaliseSubject(enrollmentID,mark);
    }
}
