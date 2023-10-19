package com.nuvalence.rectangles.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RectangleControllerTest {

    public static final String INTERCEPTION_INPUTS_JSON = "{\n" +
            "  \"rectangleA\": {\n" +
            "    \"x\": 0, \n" +
            "    \"y\": 0, \n" +
            "    \"width\": 5, \n" +
            "    \"height\": 5 \n" +
            "  },\n" +
            "  \"rectangleB\": {\n" +
            "    \"x\": 2, \n" +
            "    \"y\": 3, \n" +
            "    \"width\": 10, \n" +
            "    \"height\": 5 \n" +
            "  }\n" +
            "}";

    public static final String CONTAINMENT_INPUTS_JSON = "{\n" +
            "  \"rectangleA\": {\n" +
            "    \"x\": 0, \n" +
            "    \"y\": 0, \n" +
            "    \"width\": 5, \n" +
            "    \"height\": 5 \n" +
            "  },\n" +
            "  \"rectangleB\": {\n" +
            "    \"x\": 2, \n" +
            "    \"y\": 3, \n" +
            "    \"width\": 1, \n" +
            "    \"height\": 1 \n" +
            "  }\n" +
            "}";

    public static final String ADJACENCY_INPUTS_JSON = "{\n" +
            "  \"rectangleA\": {\n" +
            "    \"x\": 0, \n" +
            "    \"y\": 0, \n" +
            "    \"width\": 5, \n" +
            "    \"height\": 5 \n" +
            "  },\n" +
            "  \"rectangleB\": {\n" +
            "    \"x\": 5, \n" +
            "    \"y\": 0, \n" +
            "    \"width\": 5, \n" +
            "    \"height\": 5 \n" +
            "  }\n" +
            "}";
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testIntersection() throws Exception {
        this.mockMvc.perform(post("/api/intersection").contentType(MediaType.APPLICATION_JSON)
                        .content(INTERCEPTION_INPUTS_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bottomLeft.x", equalTo(2)))
                .andExpect(jsonPath("$.bottomLeft.y", equalTo(3)))
                .andExpect(jsonPath("$.topRight.x", equalTo(5)))
                .andExpect(jsonPath("$.topRight.y", equalTo(5)))
        ;
    }

    @Test
    void testContainment() throws Exception {
        this.mockMvc.perform(post("/api/containment").contentType(MediaType.APPLICATION_JSON)
                        .content(CONTAINMENT_INPUTS_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("\"CONTAINMENT\""));
        ;
    }

    @Test
    void testAdjacency() throws Exception {
        this.mockMvc.perform(post("/api/adjacency").contentType(MediaType.APPLICATION_JSON)
                        .content(ADJACENCY_INPUTS_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("\"PROPER\""));
        ;
    }

}