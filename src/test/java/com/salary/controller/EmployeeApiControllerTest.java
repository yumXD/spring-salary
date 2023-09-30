package com.salary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salary.domain.Employee;
import com.salary.dto.EmployeeRequest;
import com.salary.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class EmployeeApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @DisplayName("createEmployee: 새로운 직원 생성에 성공한다.")
    @Test
    public void createEmployee() throws Exception {
        //given
        final String url = "/api/employees";

        final String name = "홍길동";
        final String position = "부장";
        final String department = "영업부";

        EmployeeRequest employeeRequest = new EmployeeRequest(name, position, department);

        // 객체 JSON으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(employeeRequest);

        //when
        //설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());

        List<Employee> employees = employeeRepository.findAll();

        assertThat(employees.size()).isEqualTo(1);
        assertThat(employees.get(0).getName()).isEqualTo(name);
        assertThat(employees.get(0).getPosition()).isEqualTo(position);
        assertThat(employees.get(0).getDepartment()).isEqualTo(department);
    }

    @DisplayName("findAllEmployees: 모든 직원 조회에 성공한다.")
    @Test
    public void findAllEmployees() throws Exception {
        //given
        final String url = "/api/employees";

        final String name = "홍길동";
        final String position = "부장";
        final String department = "영업부";

        EmployeeRequest employeeRequest = new EmployeeRequest(name, position, department);
        employeeRepository.save(employeeRequest.toEntity());

        //when
        //설정한 내용을 바탕으로 요청 전송
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].position").value(position))
                .andExpect(jsonPath("$[0].department").value(department));
    }

    @DisplayName("findEmployee: 특정 직원 조회에 성공한다.")
    @Test
    public void findEmployee() throws Exception {
        //given
        final String url = "/api/employees/{id}";

        final String name = "홍길동";
        final String position = "부장";
        final String department = "영업부";

        EmployeeRequest employeeRequest = new EmployeeRequest(name, position, department);
        Employee savedEmployee = employeeRepository.save(employeeRequest.toEntity());

        //when
        //설정한 내용을 바탕으로 요청 전송
        final ResultActions resultActions = mockMvc.perform(get(url, savedEmployee.getId()));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.position").value(position))
                .andExpect(jsonPath("$.department").value(department));
    }

    @DisplayName("updateEmployee: 특정 직원 수정에 성공한다.")
    @Test
    public void updateEmployee() throws Exception {
        //given
        final String url = "/api/employees/{id}";

        final String name = "홍길동";
        final String position = "부장";
        final String department = "영업부";

        Employee savedEmployee = employeeRepository.save(new EmployeeRequest(name, position, department).toEntity());


        final String newName = "임꺽정";
        final String newPosition = "임원";
        final String newDepartment = "마케팅부";

        EmployeeRequest employeeRequest = new EmployeeRequest(newName, newPosition, newDepartment);

        // 객체 JSON으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(employeeRequest);

        //when
        //설정한 내용을 바탕으로 요청 전송
        final ResultActions resultActions = mockMvc.perform(put(url, savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        resultActions
                .andExpect(status().isOk());

        List<Employee> employees = employeeRepository.findAll();

        assertThat(employees.size()).isEqualTo(1);
        assertThat(employees.get(0).getName()).isEqualTo(newName);
        assertThat(employees.get(0).getPosition()).isEqualTo(newPosition);
        assertThat(employees.get(0).getDepartment()).isEqualTo(newDepartment);
    }

    @DisplayName("deleteEmployee: 특정 직원 삭제에 성공한다.")
    @Test
    public void deleteEmployee() throws Exception {
        //given
        final String url = "/api/employees/{id}";

        final String name = "홍길동";
        final String position = "부장";
        final String department = "영업부";

        EmployeeRequest employeeRequest = new EmployeeRequest(name, position, department);
        Employee savedEmployee = employeeRepository.save(employeeRequest.toEntity());

        //when
        //설정한 내용을 바탕으로 요청 전송
        mockMvc.perform(delete(url, savedEmployee.getId()))
                .andExpect(status().isOk());

        //then
        List<Employee> employees = employeeRepository.findAll();

        assertThat(employees).isEmpty();
    }
}