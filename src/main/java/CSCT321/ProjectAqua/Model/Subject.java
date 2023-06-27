package CSCT321.ProjectAqua.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

// Entity class for Subject
@Data // This annotation generates the getters, setters, RequiredArgsConstructor, toString and Hashcode automatically
@Entity // This annotation tells spring boot that this class is an entity and will have persistence storage in a database
@EqualsAndHashCode(exclude = {"enrollments"})// Excludes the enrollments field from the auto-generated equals and hashcode methods
public class Subject {

    @Id // This annotation marks the attribute as a primary key
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // This annotation controls the generation of the ID. Currently, a sequence. 1,2,3 etc.
    private Long id;
    private String subjectCode;
    private int credits;

    // One to many relationship with Enrollment
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // mappedBy indicates this entity is not the owner of the relationship
    @JsonManagedReference(value="subject-enroll") // This annotation is used to handle circular reference
    private Set<Enrollment> enrollments = new HashSet<>();
}
