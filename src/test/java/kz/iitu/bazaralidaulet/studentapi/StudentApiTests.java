package kz.iitu.bazaralidaulet.studentapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class StudentApiTests {
    @Autowired MockMvc mvc;
    @Autowired StudentRepository repository;

    private static final String STUDENT = """
        {"fullName":"Daulet Bazarali","email":"daulet@example.com", 
         "groupCode":"IT2-2308","studentNumber":"37392"}
        """;

    @BeforeEach
    void clearStudents() { repository.deleteAll(); }

    @Test
    void createStudentReturns201() throws Exception {
        mvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON).content(STUDENT))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName").value("Daulet Bazarali"));
    }

    @Test
    void listStudentsReturnsSavedRecord() throws Exception {
        repository.save(new Student("Daulet Bazarali", "daulet@example.com", "IT2-2308", "37392"));
        mvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].studentNumber").value("37392"));
    }

    @Test
    void getStudentByIdReturns200() throws Exception {
        var saved = repository.save(new Student("Daulet Bazarali", "daulet@example.com", "IT2-2308", "37392"));
        mvc.perform(get("/api/students/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()));
    }

    @Test
    void updateStudentChangesName() throws Exception {
        var saved = repository.save(new Student("Daulet Bazarali", "daulet@example.com", "IT2-2308", "37392"));
        mvc.perform(put("/api/students/{id}", saved.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(STUDENT.replace("Daulet Bazarali", "Bazarali Daulet")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Bazarali Daulet"));
    }

    @Test
    void deleteStudentReturns204() throws Exception {
        var saved = repository.save(new Student("Daulet Bazarali", "daulet@example.com", "IT2-2308", "37392"));
        mvc.perform(delete("/api/students/{id}", saved.getId()))
                .andExpect(status().isNoContent());
        mvc.perform(get("/api/students/{id}", saved.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void missingStudentReturns404() throws Exception {
        mvc.perform(get("/api/students/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void invalidEmailReturns400() throws Exception {
        mvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON)
                .content(STUDENT.replace("daulet@example.com", "invalid-address")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void healthEndpointIsAvailable() throws Exception {
        mvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void aboutShowsStudent() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value("37392"));
    }
    @Test
    void countStudentsReturnsCorrectTotal() throws Exception {
        repository.save(new Student(
                "Daulet Bazarali",
                "daulet@example.com",
                "IT2-2308",
                "37392"
        ));

        mvc.perform(get("/api/students/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("999"));
    }
}
