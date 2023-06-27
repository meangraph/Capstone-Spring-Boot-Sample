package CSCT321.ProjectAqua.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

// Entity class for Student
@Entity // This annotation tells spring boot that this class is an entity and will have persistence storage in a database,
@Data // This annotation generates the getters, setters, RequiredArgsConstructor, toString and Hashcode automatically
@EqualsAndHashCode(exclude = {"enrollments"})
public class Student {

    @Id // This annotation marks the attribute as a primary key.
    @GeneratedValue (strategy = GenerationType.SEQUENCE) // This annotation controls the generation of the ID. Currently, a sequence. 1,2,3 etc.
    private Long studentID;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private double wam;

    @Lob //Not needed here but this tells spring boot that this string will be larger than 255 chars
    private String description;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value="student-enroll")
    private Set<Enrollment> enrollments = new HashSet<>();

    private LocalDate dob;
    private int age;

    // Method for setting date of birth and calculating age
    public void setDob(LocalDate dob) {
        this.dob = dob;
        calculateAge();
    }

    // Method for calculating age
    @PrePersist
    @PreUpdate
    public void calculateAge() {
        if (dob != null) {
            LocalDate now = LocalDate.now();
            this.age = Period.between(dob, now).getYears();
        }
    }

    // Method for calculating Weighted Average Mark (WAM)
    public void calculateWAM() {
        double total = 0.0;
        double sumOfWeightedCreditPoints = 0.0;

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getEnrollmentStatus() == EnrollmentStatus.COMPLETED || enrollment.getEnrollmentStatus() == EnrollmentStatus.FAIL) {
                total += enrollment.getGrade() * enrollment.getSubject().getCredits();
                sumOfWeightedCreditPoints += enrollment.getSubject().getCredits();
            }
        }

        if (sumOfWeightedCreditPoints == 0) {
            throw new IllegalArgumentException("Sum of weighted credit points cannot be zero");
        }

        this.wam = total / sumOfWeightedCreditPoints;
    }
}


