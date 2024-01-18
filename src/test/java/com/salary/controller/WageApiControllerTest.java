package com.salary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salary.domain.Employee;
import com.salary.domain.Wage;
import com.salary.dto.EmployeeRequest;
import com.salary.dto.WageRequest;
import com.salary.repository.EmployeeRepository;
import com.salary.repository.WageRepository;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class WageApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    WageRepository wageRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @DisplayName("createWorkDetail: 근무표 생성에 성공한다.")
    @Test
    public void createWorkDetail() throws Exception {
        //given
        final String url = "/api/employees/{id}/work-detail";

        final String name = "홍길동";
        final String position = "부장";
        final String department = "영업부";

        EmployeeRequest employeeRequest = new EmployeeRequest(name, position, department);
        Employee savedEmployee = employeeRepository.save(employeeRequest.toEntity());

        final Long hourlyRate = 9830L;

        WageRequest wageRequest = new WageRequest(hourlyRate);

        // 객체 JSON으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(wageRequest);

        //when
        //설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url, savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.hourlyRate").value(hourlyRate));

        List<Wage> wages = wageRepository.findAll();

        assertThat(wages.size()).isEqualTo(1);
        assertNotNull(wages.get(0).getEmployee());
        assertThat(wages.get(0).getHourlyRate()).isEqualTo(hourlyRate);
    }

    @DisplayName("createWorkDetail_AlreadyExists: 근무표 생성 실패시 예외를 발생한다.")
    @Test
    public void createWorkDetail_AlreadyExists() throws Exception {
        //given
        final String url = "/api/employees/{id}/work-detail";

        final String name = "홍길동";
        final String position = "부장";
        final String department = "영업부";

        EmployeeRequest employeeRequest = new EmployeeRequest(name, position, department);
        Employee savedEmployee = employeeRepository.save(employeeRequest.toEntity());

        final Long hourlyRate = 9830L;

        Wage wage = new Wage();
        wage.setHourlyRate(10000L);
        wage.setEmployee(savedEmployee);
        wageRepository.save(wage);

        WageRequest wageRequest = new WageRequest(hourlyRate);

        // 객체 JSON으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(wageRequest);

        //when
        //설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url, savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("이미 근무표가 생성된 상태입니다.[무결성 위반]"));
    }

    @DisplayName("findWorkDetailByEmployeeId: 특정 직원의 근무표 조회에 성공한다.")
    @Test
    public void findWorkDetailByEmployeeId() throws Exception {
        //given
        final String url = "/api/employees/{id}/work-detail";

        final String name = "홍길동";
        final String position = "부장";
        final String department = "영업부";

        EmployeeRequest employeeRequest = new EmployeeRequest(name, position, department);
        Employee savedEmployee = employeeRepository.save(employeeRequest.toEntity());


        final Long hourlyRate = 9830L;

        Wage wage = new Wage();
        wage.setHourlyRate(hourlyRate);
        wage.setEmployee(savedEmployee);
        wageRepository.save(wage);

        //when
        //설정한 내용을 바탕으로 요청 전송
        final ResultActions resultActions = mockMvc.perform(get(url, savedEmployee.getId()));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hourlyRate").value(hourlyRate));
    }

    @DisplayName("updateWorkDetail: 특정 직원의 근무표 수정에 성공한다.")
    @Test
    public void updateWorkDetail() throws Exception {
        //given
        final String url = "/api/employees/{id}/work-detail";

        final String name = "홍길동";
        final String position = "부장";
        final String department = "영업부";

        final Long hourlyRate = 9830L;

        Employee savedEmployee = employeeRepository.save(new EmployeeRequest(name, position, department).toEntity());

        Wage wage = new Wage();
        wage.setHourlyRate(hourlyRate);
        wage.setEmployee(savedEmployee);
        wageRepository.save(wage);

        final Long newHourlyRate = 9010L;

        WageRequest wageRequest = new WageRequest(newHourlyRate);

        // 객체 JSON으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(wageRequest);

        //when
        //설정한 내용을 바탕으로 요청 전송
        final ResultActions resultActions = mockMvc.perform(put(url, savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        resultActions
                .andExpect(status().isOk());

        List<Wage> wages = wageRepository.findAll();

        assertThat(wages.size()).isEqualTo(1);
        assertNotNull(wages.get(0).getEmployee());
        assertNull(savedEmployee.getWage());
        assertThat(wages.get(0).getHourlyRate()).isEqualTo(newHourlyRate);
    }

    @DisplayName("deleteWorkDetail: 특정 직원의 근무표 삭제에 성공한다.")
    @Test
    public void deleteWorkDetail() throws Exception {
        //given
        final String url = "/api/employees/{id}/work-detail";

        final String name = "홍길동";
        final String position = "부장";
        final String department = "영업부";

        final Long hourlyRate = 9830L;

        EmployeeRequest employeeRequest = new EmployeeRequest(name, position, department);
        Employee savedEmployee = employeeRepository.save(employeeRequest.toEntity());

        Wage wage = new Wage();
        wage.setHourlyRate(hourlyRate);
        wage.setEmployee(savedEmployee);
        wageRepository.save(wage);

        //when
        //설정한 내용을 바탕으로 요청 전송
        mockMvc.perform(delete(url, savedEmployee.getId()))
                .andExpect(status().isOk());

        //then
        List<Wage> wages = wageRepository.findAll();

        assertThat(wages).isEmpty();
    }
}