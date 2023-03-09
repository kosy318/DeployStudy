package com.example.deploytest.springboot.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class) // @WebMVCTest를 이용할 수도 있지만 속도가 느리다
public class HelloControllerTest {

        @InjectMocks
        private HelloController target;

        private MockMvc mockMvc;

        @BeforeEach // 각각의 테스트가 실행되기 전에 초기화함
        public void init() {
            mockMvc = MockMvcBuilders.standaloneSetup(target)
                    .build();
        }

        final String hello = "hello";

        @Test
        public void hello를_리턴함() throws Exception {
            // given
            final String url = "/hello";

            // when
            final ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders.get(url)
            );

            // then
            // HTTP Status가 OK인지 확인
            MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
            System.out.println(mvcResult.getResponse().getContentAsString());
        }

}
