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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        assertNull(employees.get(0).getSalaryDetails());
        assertThat(employees.get(0).getName()).isEqualTo(name);
        assertThat(employees.get(0).getPosition()).isEqualTo(position);
        assertThat(employees.get(0).getDepartment()).isEqualTo(department);
    }
}