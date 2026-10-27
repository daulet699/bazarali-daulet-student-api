package kz.iitu.bazaralidaulet.studentapi;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Student> create(@Valid @RequestBody StudentRequest input) {
        Student student = new Student(input.fullName().trim(), input.email().trim(),
                input.groupCode().trim(), input.studentNumber().trim());
        Student saved = repository.save(student);
        return ResponseEntity.created(URI.create("/api/students/" + saved.getId())).body(saved);
    }

    @GetMapping
    public List<Student> all() { return repository.findAll(); }

    @GetMapping("/{id}")
    public Student one(@PathVariable long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable long id, @Valid @RequestBody StudentRequest input) {
        Student student = one(id);
        student.update(input);
        return repository.save(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        Student student = one(id);
        repository.delete(student);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/count")
    public long count() {
        return repository.count();
    }
}
