//package com.iase24.crazy_task_tracker_api.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.iase24.crazy_task_tracker_api.CrazyTaskTrackerApiApplication;
//import com.iase24.crazy_task_tracker_api.security.dto.request.SignUpRequest;
//import com.iase24.crazy_task_tracker_api.support.DataSourceStub;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles(profiles = "test")
//@ContextConfiguration(classes = CrazyTaskTrackerApiApplication.class)
//@Import(value = DataSourceStub.class)
//public class UserControllerTest {
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void createUser_when_Create_then_Success() throws Exception {
//        SignUpRequest createUser = SignUpRequest.builder()
//                .firstName("User")
//                .password("password")
//                .email("user@gmail.com")
//                .build();
//
//        String contentAsString = mockMvc.perform(
//                        MockMvcRequestBuilders
//                                .post("/api/user")
//                                .content(objectMapper.writeValueAsString(createUser))
//                                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andReturn().getResponse().getContentAsString();
//
//        SignUpRequest result = objectMapper.readValue(contentAsString, SignUpRequest.class);
//
//        Assertions.assertEquals("User", result.getFirstName());
//        Assertions.assertEquals("password", result.getPassword());
//        Assertions.assertEquals("email", result.getEmail());
//    }
//}
