package kz.iitu.bazaralidaulet.studentapi;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String groupCode;

    @Column(nullable = false)
    private String studentNumber;

    protected Student() {}

    public Student(String fullName, String email, String groupCode, String studentNumber) {
        this.fullName = fullName;
        this.email = email;
        this.groupCode = groupCode;
        this.studentNumber = studentNumber;
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getGroupCode() { return groupCode; }
    public String getStudentNumber() { return studentNumber; }

    public void update(StudentRequest request) {
        this.fullName = request.fullName().trim();
        this.email = request.email().trim();
        this.groupCode = request.groupCode().trim();
        this.studentNumber = request.studentNumber().trim();
    }
}
