package com.lkww.codo.codo.presentation.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkww.codo.codo.domain.Project;
import com.lkww.codo.codo.service.ProjectService;
import com.lkww.codo.codo.util.DemoObjects;
import org.hamcrest.Matchers;
import org.json.simple.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ProjectRestControllerTest {
    String HOST = "http://localhost:8090/";
    String PATH = HOST + ProjectRestController.BASE_URL + "/";
    MockMvc mockMvc;
    @MockBean
    private ProjectService service;

    private final String notExists = "DemoID";
    private Project pr;
    private String prID;

    private List<Project> list;
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ProjectRestController(service)).build();
        pr = DemoObjects.demoProject1();
        prID = pr.getProjectID();
        list = Arrays.asList(DemoObjects.demoProject1(), DemoObjects.demoProject2());
    }

    @Test
    void ensureGetReturnsOkResponse() throws Exception {
        when(service.getById(prID)).thenReturn(Optional.of(pr));
        mockMvc.perform(get(PATH + prID)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andDo(print())
                .andExpect(jsonPath("$.projectID").value(pr.getProjectID()));
    }

    @Test
    void ensureGetNoContentReturnsNotFoundResponse() throws Exception {
        when(service.getById(notExists)).thenReturn(Optional.empty());
        mockMvc.perform(get(PATH + notExists)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void ensureGetAllReturnsOkResponse() throws Exception {
        when(service.getAll()).thenReturn(list);
        mockMvc.perform(get(PATH)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andDo(print())
                .andExpect(jsonPath("$[0].projectID").value(list.get(0).getProjectID()))
                .andExpect(jsonPath("$[1].projectID").value(list.get(1).getProjectID()));
    }

    @Test
    void ensureGetAllNoContentReturnsNoContentResponse() throws Exception {
        when(service.getAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get(PATH)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }


    @Test
    void ensurePostReturnsOkResponse() throws Exception {
        when(service.create(pr)).thenReturn(pr);
        mockMvc.perform(post(PATH)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(pr)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }
    @Test
    void ensurePostNonsenseReturnsBadRequestResponse() throws Exception {
        when(service.create(pr)).thenReturn(null);
        mockMvc.perform(post(PATH)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(pr)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void ensureDeleteReturnsOkResponse() throws Exception {
        when(service.delete(prID)).thenReturn(1L);
        mockMvc.perform(delete(PATH + prID)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void ensureDeleteNotFoundReturnsNotFoundResponse() throws Exception {
        when(service.delete(notExists)).thenReturn(0L);
        mockMvc.perform(delete(PATH + notExists)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
