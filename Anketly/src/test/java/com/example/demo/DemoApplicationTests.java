package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.example.demo.AssignmentResultHandler.assignTo;
import static org.hamcrest.Matchers.hasLength;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DemoApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void testGetServiceTypes() throws Exception {
        String[] services = readServices();
        String[] services2 = readServices();
        Assert.isTrue(Arrays.equals(services, services2));
    }

    @Test
    void testAuthenticate() throws Exception {
        String token = getJwt("user", "password");

        Assert.notNull(token);
    }

    @Test
    void testEnrollWithAuthority() throws Exception {
        String[] services = readServices();
        String token = getJwt("user", "password");

        Assert.isTrue(assertEnroll(services[0], 1, true, token) == 1);
        Assert.isTrue(assertEnroll(services[1], 1, true, token) == 2);
        Assert.isTrue(assertEnroll(services[0], 2, true, token) == 3);
        assertEnroll(UUID.randomUUID().toString(), -1, false, token);
        Assert.isTrue(assertEnroll(services[0], 3, true, token) == 4);
        Assert.isTrue(assertEnroll(services[2], 1, true, token) == 5);
        Assert.isTrue(assertEnroll(services[1], 2, true, token) == 6);
    }

    @Test
    void testCall() throws Exception {
        String[] services = readServices();
        String token = getJwt("user", "password");

        assertEnroll(services[0], 1, true, token); //1
        assertEnroll(services[1], 1, true, token); //2
        assertEnroll(services[0], 2, true, token); //3
        assertEnroll(services[0], 3, true, token); //4
        assertEnroll(services[2], 1, true, token); //5
        assertEnroll(services[1], 2, true, token); //6

        String[] onlyTwo = new String[] {services[0], services[2]};
        String tokenDesk = getJwt("desk", "123");

        assertCall(onlyTwo, 2,services[0], 1, true, tokenDesk);
        assertCall(new String[0], 2, null, -1, false, tokenDesk);
        assertCall(new String[] {UUID.randomUUID().toString()}, 2,null, -1, false, tokenDesk);
        assertCall(new String[] {services[1]}, 3, services[1],2, true, tokenDesk);
        assertEnroll(services[1], 2, true, token); //7
        assertCall(new String[] {services[1], services[2]}, 4, services[2], 5, true, tokenDesk);
    }

    @Test
    void testCallFails() throws Exception {
        String[] services = readServices();
        String token = getJwt("user", "password");

        assertEnroll(services[0], 1, true, token); //1
        assertEnroll(services[1], 1, true, token); //2
        assertEnroll(services[0], 2, true, token); //3
        assertEnroll(services[0], 3, true, token); //4
        assertEnroll(services[2], 1, true, token); //5
        assertEnroll(services[1], 2, true, token); //6

        assertCall(new String[] {services[1]}, 3, services[1],2, false, token)
        .andExpect(status().isForbidden());
    }

    @Test
    void testBoard() throws Exception {
        String[] services = readServices();
        String token = getJwt("user", "password");

        assertEnroll(services[0], 1, true, token); //1
        assertEnroll(services[1], 1, true, token); //2
        assertEnroll(services[0], 2, true, token); //3
        assertEnroll(services[0], 3, true, token); //4
        assertEnroll(services[2], 1, true, token); //5
        assertEnroll(services[1], 2, true, token); //6

        String[] onlyTwo = new String[] {services[0], services[2]};
        String tokenDesk = getJwt("desk", "123");

        assertCall(onlyTwo, 2,services[0], 1, true, tokenDesk);
        assertCall(new String[] {services[1]}, 3, services[1],2, true, tokenDesk);
        assertCall(new String[] {services[1], services[2]}, 4, services[2], 5, true, tokenDesk);
        assertCall(onlyTwo, 2, services[0], 3, true, tokenDesk);

        List<DeskCustomer> deskCustomerList = new ArrayList<>();
        deskCustomerList.add(new DeskCustomer(2, 3));
        deskCustomerList.add(new DeskCustomer(4, 5));
        deskCustomerList.add(new DeskCustomer(3, 2));

        assertBoard(3, deskCustomerList, token);
    }

    private String[] readServices() throws Exception {
        AssignmentResult[] services = new AssignmentResult[3];
        services[0] = new AssignmentResult();
        services[1] = new AssignmentResult();
        services[2] = new AssignmentResult();

        mvc.perform(get("/db/services")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Плащане на такси")))
                .andExpect(jsonPath("$[0].description", is("Ще можете да си платите таксите към държавата.")))
                .andExpect(jsonPath("$[0].id", hasLength(36)))
                .andExpect(jsonPath("$[1].name", is("Подаване на декларация")))
                .andExpect(jsonPath("$[1].description", is("Подаване на различни декларации.")))
                .andExpect(jsonPath("$[1].id", hasLength(36)))
                .andExpect(jsonPath("$[2].name", is("Получаване на АКТ 16")))
                .andExpect(jsonPath("$[2].description", is("Трябва да носите вносната бележка със себе си.")))
                .andExpect(jsonPath("$[2].id", hasLength(36)))
                .andDo(assignTo("$[0].id", services[0]))
                .andDo(assignTo("$[1].id", services[1]))
                .andDo(assignTo("$[2].id", services[2]));

        String value1 = (String) services[0].getValue();
        String value2 = (String) services[1].getValue();
        String value3 = (String) services[2].getValue();

        return new String[] {value1, value2,value3};
    }

    private String getJwt(String username, String password) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TestUser testUser = new TestUser(username, password);
        AssignmentResult assignmentResult = new AssignmentResult();

        mvc.perform(post("/authenticate")
                .content(objectMapper.writeValueAsString(testUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(AssignmentResultHandler.assignTo("$.token", assignmentResult));

        return (String) assignmentResult.getValue();
    }

    private int assertEnroll(String serviceId, int queueCount, boolean isSuccess, String jwt) throws Exception {
        if (!isSuccess) {
            mvc.perform(post("/db/enroll").header("Authorization", "Bearer " + jwt)
                    .content(serviceId)
                    .contentType(MediaType.TEXT_PLAIN))
                    .andExpect(status().isNotFound());
            return -1;
        } else {
            AssignmentResult result = new AssignmentResult();
            mvc.perform(post("/db/enroll").header("Authorization", "Bearer "+jwt)
                    .content(serviceId)
                    .contentType(MediaType.TEXT_PLAIN))
                    .andExpect(status().isCreated())
                    .andExpect(content()
                            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.serviceId", is(serviceId)))
                    .andExpect(jsonPath("$.queueCount", is(queueCount)))
                    .andDo(assignTo("$.customerId", result));
            return (int) result.getValue();
        }
    }

    private ResultActions assertCall(String[] serviceIds, int deskNumber, String serviceId, int customerId, boolean isSuccess, String jwt) throws Exception {
        ObjectMapper om = new ObjectMapper();

        if (!isSuccess) {
            return mvc.perform(put("/db/desk/" + deskNumber).header("Authorization", "Bearer " + jwt)
                    .content(om.writeValueAsBytes(serviceIds))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError());
        } else {
            return mvc.perform(put("/db/desk/" + deskNumber).header("Authorization", "Bearer "+jwt)
                    .content(om.writeValueAsBytes(serviceIds))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content()
                            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.serviceId", is(serviceId)))
                    .andExpect(jsonPath("$.customerId", is(customerId)));
        }
    }

    private void assertBoard(int top, List<DeskCustomer> deskCustomerList, String jwt) throws Exception {
        ResultActions resultActions = mvc.perform(get("/db/board?top=" + top).header("Authorization", "Bearer "+jwt))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]", hasSize(top)));

        int counter = 0;
        for (DeskCustomer deskCustomer : deskCustomerList) {
            resultActions = resultActions
                    .andExpect(jsonPath("$["+counter+"].deskNumber", is(deskCustomer.desk)))
                    .andExpect(jsonPath("$["+counter+"].customerId", is(deskCustomer.customer)));
            counter += 1;
        }
    }

    private static class DeskCustomer {
        public int desk;
        public int customer;

        public DeskCustomer(int desk, int customer) {
            this.desk = desk;
            this.customer = customer;
        }
    }

}
