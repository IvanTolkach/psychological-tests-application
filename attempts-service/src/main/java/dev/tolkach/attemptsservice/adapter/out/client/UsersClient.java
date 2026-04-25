package dev.tolkach.attemptsservice.adapter.out.client;

import common.dto.FacultyDto;
import common.dto.StudentDto;
import dev.tolkach.attemptsservice.adapter.out.security.JwtFeignInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "users-service", configuration = JwtFeignInterceptor.class)
public interface UsersClient {

    @GetMapping("/api/students/{id}")
    Object getStudent(@PathVariable UUID id);

    @PostMapping("/api/students/search")
    List<StudentDto> searchStudents(@RequestBody StudentDto filter);

    @PostMapping("/api/faculties/search")
    List<FacultyDto> searchFaculties(@RequestBody Object filter);
}
