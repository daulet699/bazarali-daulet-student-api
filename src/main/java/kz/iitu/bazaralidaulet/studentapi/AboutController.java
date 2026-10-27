package kz.iitu.bazaralidaulet.studentapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class AboutController {
    @GetMapping("/")
    public Map<String, String> about() {
        return Map.of(
            "project", "Bazarali Daulet Student API",
            "student", "Daulet Bazarali",
            "group", "IT2-2308",
            "studentId", "37392",
            "endpoints", "/api/students"
        );
    }
}
