package com.conygre.spring.boot.services;

import com.conygre.spring.boot.entities.CompactDisc;
import com.conygre.spring.boot.repos.CompactDiscRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CompactDiscServiceImplTest {

    @Mock
    private CompactDiscRepository repository;

    @InjectMocks
    private CompactDiscServiceImpl service;

    private CompactDisc testDisc;

    @Before
    public void setUp() {
        testDisc = new CompactDisc("Test Album", 9.99, "Test Artist", 12);
        testDisc.setId(1);
    }

    @Test
    public void testGetCatalog() {
        CompactDisc disc1 = new CompactDisc("Album 1", 10.0, "Artist 1", 10);
        CompactDisc disc2 = new CompactDisc("Album 2", 15.0, "Artist 2", 12);
        List<CompactDisc> expectedDiscs = Arrays.asList(disc1, disc2);

        when(repository.findAll()).thenReturn(expectedDiscs);

        Iterable<CompactDisc> result = service.getCatalog();

        assertNotNull(result);
        assertEquals(expectedDiscs, result);
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetCatalogReturnsEmptyList() {
        when(repository.findAll()).thenReturn(Arrays.asList());

        Iterable<CompactDisc> result = service.getCatalog();

        assertNotNull(result);
        assertEquals(Arrays.asList(), result);
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetCompactDiscById() {
        when(repository.findById(1)).thenReturn(Optional.of(testDisc));

        CompactDisc result = service.getCompactDiscById(1);

        assertNotNull(result);
        assertEquals(testDisc, result);
        assertEquals("Test Album", result.getTitle());
        verify(repository, times(1)).findById(1);
    }

    @Test
    public void testGetCompactDiscByIdNotFound() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        CompactDisc result = service.getCompactDiscById(999);

        assertNull(result);
        verify(repository, times(1)).findById(999);
    }

    @Test
    public void testAddNewCompactDisc() {
        CompactDisc newDisc = new CompactDisc("New Album", 12.99, "New Artist", 15);
        newDisc.setId(5); // Set initial ID
        
        CompactDisc savedDisc = new CompactDisc("New Album", 12.99, "New Artist", 15);
        savedDisc.setId(1); // ID assigned by database

        when(repository.save(any(CompactDisc.class))).thenReturn(savedDisc);

        CompactDisc result = service.addNewCompactDisc(newDisc);

        assertNotNull(result);
        assertEquals(0, newDisc.getId()); // Original disc ID should be set to 0
        verify(repository, times(1)).save(newDisc);
    }

    @Test
    public void testUpdateCompactDisc() {
        testDisc.setTitle("Updated Title");
        
        when(repository.save(testDisc)).thenReturn(testDisc);

        CompactDisc result = service.updateCompactDisc(testDisc);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        verify(repository, times(1)).save(testDisc);
    }

    @Test
    public void testDeleteCompactDiscById() {
        when(repository.findById(1)).thenReturn(Optional.of(testDisc));

        service.deleteCompactDisc(1);

        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).delete(testDisc);
    }

    @Test
    public void testDeleteCompactDiscByObject() {
        service.deleteCompactDisc(testDisc);

        verify(repository, times(1)).delete(testDisc);
    }

    @Test
    public void testGetCompactDiscByIdWithMultipleCalls() {
        CompactDisc disc1 = new CompactDisc("Album 1", 10.0, "Artist 1", 10);
        disc1.setId(1);
        CompactDisc disc2 = new CompactDisc("Album 2", 15.0, "Artist 2", 12);
        disc2.setId(2);

        when(repository.findById(1)).thenReturn(Optional.of(disc1));
        when(repository.findById(2)).thenReturn(Optional.of(disc2));

        CompactDisc result1 = service.getCompactDiscById(1);
        CompactDisc result2 = service.getCompactDiscById(2);

        assertEquals(disc1, result1);
        assertEquals(disc2, result2);
        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).findById(2);
    }

    @Test
    public void testAddNewCompactDiscSetsIdToZero() {
        CompactDisc newDisc = new CompactDisc("Album", 10.0, "Artist", 10);
        newDisc.setId(100); // Set a non-zero ID

        when(repository.save(any(CompactDisc.class))).thenReturn(newDisc);

        service.addNewCompactDisc(newDisc);

        // Verify that the ID was set to 0 before saving
        assertEquals(0, newDisc.getId());
    }

    @Test
    public void testUpdateCompactDiscWithPriceChange() {
        CompactDisc discToUpdate = new CompactDisc("Album", 10.0, "Artist", 10);
        discToUpdate.setId(1);
        discToUpdate.setPrice(15.99);

        when(repository.save(discToUpdate)).thenReturn(discToUpdate);

        CompactDisc result = service.updateCompactDisc(discToUpdate);

        assertEquals(Double.valueOf(15.99), result.getPrice());
        verify(repository, times(1)).save(discToUpdate);
    }

    @Test
    public void testUpdateCompactDiscWithTitleChange() {
        CompactDisc discToUpdate = new CompactDisc("Original Title", 10.0, "Artist", 10);
        discToUpdate.setId(1);
        discToUpdate.setTitle("New Title");

        when(repository.save(discToUpdate)).thenReturn(discToUpdate);

        CompactDisc result = service.updateCompactDisc(discToUpdate);

        assertEquals("New Title", result.getTitle());
        verify(repository, times(1)).save(discToUpdate);
    }

    @Test
    public void testDeleteCompactDiscByIdThenVerify() {
        CompactDisc discToDelete = new CompactDisc("Album", 10.0, "Artist", 10);
        discToDelete.setId(5);
        
        when(repository.findById(5)).thenReturn(Optional.of(discToDelete));

        service.deleteCompactDisc(5);

        verify(repository, times(1)).delete(discToDelete);
    }

    @Test
    public void testGetCatalogWithMultipleDiscs() {
        List<CompactDisc> discs = Arrays.asList(
            new CompactDisc("Album 1", 10.0, "Artist 1", 10),
            new CompactDisc("Album 2", 15.0, "Artist 2", 12),
            new CompactDisc("Album 3", 12.0, "Artist 3", 11)
        );

        when(repository.findAll()).thenReturn(discs);

        Iterable<CompactDisc> result = service.getCatalog();

        assertNotNull(result);
        verify(repository, times(1)).findAll();
    }
}
