package com.jpaboard.controller;

import com.jpaboard.service.BoardService;
import com.jpaboard.service.AccountService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "user",username = "test@test.com", password = "!asdfasdf")
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private BoardService boardService;

//    @BeforeEach
//    public void setUpUser() {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }

    @Test
    @DisplayName("게시물을 작성한다")
    void postBoard() throws Exception {
        mockMvc.perform(post("/board/form")
                .param("title","테스트 제목")
                .param("content", "테스트 내용")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/board/list"));
    }

    @Test
    @DisplayName("게시물을 수정한다")
    void updateBoard() throws Exception {
        mockMvc.perform(post("/board/form")
                .param("title","테스트 제목2")
                .param("content", "테스트 내용2")
                .param("mode","U")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/board/list"));
    }

    @Test
    @DisplayName("게시물을 삭제한다")
    void deleteBoard() throws Exception {
    }

    @Test
    @DisplayName("특정 게시물을 가져온다")
    void getBoard() throws Exception {
    }

    @Test
    @DisplayName("특정 게시물 목록을 가져온다")
    void getBoards() throws Exception {
        mockMvc.perform(get("/board/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("board/list"));
    }
}