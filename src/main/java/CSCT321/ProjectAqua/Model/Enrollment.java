package CSCT321.ProjectAqua.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

// Model class for Enrollment
@Data
@Entity
@EqualsAndHashCode(exclude = {"student", "subject"})
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Foreign key relation to Student
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "student_id")
    @JsonBackReference(value="student-enroll")
    private Student student;

    // Foreign key relation to Subject
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "subject_id")
    @JsonBackReference(value="subject-enroll")
    private Subject subject;

    private Double grade;
    private EnrollmentStatus enrollmentStatus;
    private LocalDate enrollmentDate = LocalDate.now();
}