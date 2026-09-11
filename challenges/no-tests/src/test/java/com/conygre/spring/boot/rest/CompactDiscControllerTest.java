package com.conygre.spring.boot.rest;

import com.conygre.spring.boot.entities.CompactDisc;
import com.conygre.spring.boot.services.CompactDiscService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class CompactDiscControllerTest {

    @Mock
    private CompactDiscService service;

    @InjectMocks
    private CompactDiscController controller;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private CompactDisc testDisc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        
        testDisc = new CompactDisc("Test Album", 9.99, "Test Artist", 12);
        testDisc.setId(1);
    }

    @Test
    public void testFindAll() throws Exception {
        CompactDisc disc1 = new CompactDisc("Album 1", 10.0, "Artist 1", 10);
        CompactDisc disc2 = new CompactDisc("Album 2", 15.0, "Artist 2", 12);
        List<CompactDisc> discs = Arrays.asList(disc1, disc2);

        when(service.getCatalog()).thenReturn(discs);

        mockMvc.perform(get("/api/compactdiscs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

        verify(service, times(1)).getCatalog();
    }

    @Test
    public void testFindAllEmpty() throws Exception {
        when(service.getCatalog()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/compactdiscs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(service, times(1)).getCatalog();
    }

    @Test
    public void testGetCdById() throws Exception {
        when(service.getCompactDiscById(1)).thenReturn(testDisc);

        mockMvc.perform(get("/api/compactdiscs/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Album")))
                .andExpect(jsonPath("$.artist", is("Test Artist")));

        verify(service, times(1)).getCompactDiscById(1);
    }

    @Test
    public void testGetCdByIdWithDifferentIds() throws Exception {
        CompactDisc disc1 = new CompactDisc("Album 1", 10.0, "Artist 1", 10);
        disc1.setId(1);
        CompactDisc disc2 = new CompactDisc("Album 2", 15.0, "Artist 2", 12);
        disc2.setId(2);

        when(service.getCompactDiscById(1)).thenReturn(disc1);
        when(service.getCompactDiscById(2)).thenReturn(disc2);

        mockMvc.perform(get("/api/compactdiscs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Album 1")));

        mockMvc.perform(get("/api/compactdiscs/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.title", is("Album 2")));

        verify(service, times(1)).getCompactDiscById(1);
        verify(service, times(1)).getCompactDiscById(2);
    }

    @Test
    public void testGetByIdWith404() throws Exception {
        when(service.getCompactDiscById(999)).thenReturn(null);

        mockMvc.perform(get("/api/compactdiscs/404/999"))
                .andExpect(status().isNotFound());

        verify(service, times(1)).getCompactDiscById(999);
    }

    @Test
    public void testGetByIdWith404Found() throws Exception {
        when(service.getCompactDiscById(1)).thenReturn(testDisc);

        mockMvc.perform(get("/api/compactdiscs/404/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Album")));

        verify(service, times(1)).getCompactDiscById(1);
    }

    @Test
    public void testDeleteCdById() throws Exception {
        mockMvc.perform(delete("/api/compactdiscs/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteCompactDisc(1);
    }

    @Test
    public void testDeleteCdByObject() throws Exception {
        String json = objectMapper.writeValueAsString(testDisc);

        mockMvc.perform(delete("/api/compactdiscs")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteCompactDisc(any());
    }

    @Test
    public void testAddCd() throws Exception {
        CompactDisc newDisc = new CompactDisc("New Album", 12.99, "New Artist", 15);
        String json = objectMapper.writeValueAsString(newDisc);

        mockMvc.perform(post("/api/compactdiscs")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).addNewCompactDisc(any());
    }

    @Test
    public void testGetCdByIdNotFound() throws Exception {
        when(service.getCompactDiscById(999)).thenReturn(null);

        mockMvc.perform(get("/api/compactdiscs/999"))
                .andExpect(status().isOk());

        verify(service, times(1)).getCompactDiscById(999);
    }

    @Test
    public void testFindAllWithMultipleDiscs() throws Exception {
        List<CompactDisc> discs = Arrays.asList(
            createDisc(1, "Album 1", 10.0, "Artist 1", 10),
            createDisc(2, "Album 2", 15.0, "Artist 2", 12),
            createDisc(3, "Album 3", 12.0, "Artist 3", 11)
        );

        when(service.getCatalog()).thenReturn(discs);

        mockMvc.perform(get("/api/compactdiscs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        verify(service, times(1)).getCatalog();
    }

    @Test
    public void testAddCdAndVerify() throws Exception {
        CompactDisc newDisc = new CompactDisc("Fresh Album", 14.99, "Fresh Artist", 20);
        String json = objectMapper.writeValueAsString(newDisc);

        mockMvc.perform(post("/api/compactdiscs")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).addNewCompactDisc(any());
    }

    @Test
    public void testDeleteMultipleDiscs() throws Exception {
        mockMvc.perform(delete("/api/compactdiscs/1"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/compactdiscs/2"))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteCompactDisc(1);
        verify(service, times(1)).deleteCompactDisc(2);
    }

    @Test
    public void testGetCdByIdWithVerification() throws Exception {
        CompactDisc disc = createDisc(5, "Specific Album", 11.99, "Specific Artist", 14);
        
        when(service.getCompactDiscById(5)).thenReturn(disc);

        mockMvc.perform(get("/api/compactdiscs/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Specific Album")))
                .andExpect(jsonPath("$.artist", is("Specific Artist")))
                .andExpect(jsonPath("$.price", is(11.99)))
                .andExpect(jsonPath("$.tracks", is(14)));

        verify(service, times(1)).getCompactDiscById(5);
    }

    @Test
    public void testGetByIdWith404Boundary() throws Exception {
        when(service.getCompactDiscById(0)).thenReturn(null);

        mockMvc.perform(get("/api/compactdiscs/404/0"))
                .andExpect(status().isNotFound());

        verify(service, times(1)).getCompactDiscById(0);
    }

    private CompactDisc createDisc(int id, String title, double price, String artist, int tracks) {
        CompactDisc disc = new CompactDisc(title, price, artist, tracks);
        disc.setId(id);
        return disc;
    }
}
