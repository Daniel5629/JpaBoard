package com.restboot.controller;

import com.restboot.model.Board;
import com.restboot.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("특정 게시물을 가져온다")
    public void getBoard() throws Exception {
    }

    @Test
    @DisplayName("특정 게시물 목록을 가져온다")
    public void getBoards() throws Exception {
        mockMvc.perform(get("/board/list"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시물을 작성한다")
    public void postBoard() throws Exception {
    }

    @Test
    @DisplayName("게시물을 수정한다")
    public void updateBoard() throws Exception {
    }

    @Test
    @DisplayName("게시물을 삭제한다")
    public void deleteBoard() throws Exception {
    }


}