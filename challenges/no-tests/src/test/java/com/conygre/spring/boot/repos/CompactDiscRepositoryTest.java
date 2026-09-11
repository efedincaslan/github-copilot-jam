package com.conygre.spring.boot.repos;

import com.conygre.spring.boot.entities.CompactDisc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CompactDiscRepositoryTest {

    @Autowired
    private CompactDiscRepository repository;

    private CompactDisc testDisc;

    @Before
    public void setUp() {
        testDisc = new CompactDisc("Test Album", 9.99, "Test Artist", 12);
    }

    @Test
    public void testSaveCompactDisc() {
        CompactDisc saved = repository.save(testDisc);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("Test Album", saved.getTitle());
        assertEquals("Test Artist", saved.getArtist());
    }

    @Test
    public void testFindById() {
        CompactDisc saved = repository.save(testDisc);
        
        Optional<CompactDisc> found = repository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Test Album", found.get().getTitle());
    }

    @Test
    public void testFindByIdNotFound() {
        Optional<CompactDisc> found = repository.findById(99999);

        assertFalse(found.isPresent());
    }

    @Test
    public void testFindAll() {
        CompactDisc disc1 = new CompactDisc("Album 1", 10.0, "Artist 1", 10);
        CompactDisc disc2 = new CompactDisc("Album 2", 15.0, "Artist 2", 12);
        
        repository.save(disc1);
        repository.save(disc2);

        Iterable<CompactDisc> all = repository.findAll();

        assertNotNull(all);
        int count = 0;
        for (CompactDisc disc : all) {
            count++;
        }
        assertTrue(count >= 2);
    }

    @Test
    public void testFindByArtist() {
        CompactDisc disc1 = new CompactDisc("Album 1", 10.0, "Pink Floyd", 10);
        CompactDisc disc2 = new CompactDisc("Album 2", 15.0, "Pink Floyd", 12);
        CompactDisc disc3 = new CompactDisc("Album 3", 12.0, "The Beatles", 11);

        repository.save(disc1);
        repository.save(disc2);
        repository.save(disc3);

        Iterable<CompactDisc> result = repository.findByArtist("Pink Floyd");

        assertNotNull(result);
        int count = 0;
        for (CompactDisc disc : result) {
            assertEquals("Pink Floyd", disc.getArtist());
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    public void testFindByArtistNotFound() {
        Iterable<CompactDisc> result = repository.findByArtist("Non Existent Artist");

        assertNotNull(result);
        int count = 0;
        for (CompactDisc disc : result) {
            count++;
        }
        assertEquals(0, count);
    }

    @Test
    public void testDeleteCompactDisc() {
        CompactDisc saved = repository.save(testDisc);
        int id = saved.getId();

        repository.delete(saved);

        Optional<CompactDisc> found = repository.findById(id);
        assertFalse(found.isPresent());
    }

    @Test
    public void testDeleteById() {
        CompactDisc saved = repository.save(testDisc);
        int id = saved.getId();

        repository.deleteById(id);

        Optional<CompactDisc> found = repository.findById(id);
        assertFalse(found.isPresent());
    }

    @Test
    public void testUpdateCompactDisc() {
        CompactDisc saved = repository.save(testDisc);
        
        saved.setTitle("Updated Title");
        saved.setPrice(19.99);
        CompactDisc updated = repository.save(saved);

        assertEquals("Updated Title", updated.getTitle());
        assertEquals(Double.valueOf(19.99), updated.getPrice());
    }

    @Test
    public void testSaveMultipleDiscs() {
        repository.save(new CompactDisc("Album 1", 10.0, "Artist 1", 10));
        repository.save(new CompactDisc("Album 2", 15.0, "Artist 2", 12));
        repository.save(new CompactDisc("Album 3", 12.0, "Artist 3", 11));

        Iterable<CompactDisc> all = repository.findAll();
        int count = 0;
        for (CompactDisc disc : all) {
            count++;
        }
        assertTrue(count >= 3);
    }

    @Test
    public void testFindByArtistWithMultipleResults() {
        repository.save(new CompactDisc("Album 1", 10.0, "Beatles", 10));
        repository.save(new CompactDisc("Album 2", 15.0, "Beatles", 12));
        repository.save(new CompactDisc("Album 3", 12.0, "Beatles", 11));

        Iterable<CompactDisc> result = repository.findByArtist("Beatles");

        int count = 0;
        for (CompactDisc disc : result) {
            count++;
        }
        assertEquals(3, count);
    }

    @Test
    public void testCompactDiscWithDifferentPrices() {
        repository.save(new CompactDisc("Album 1", 0.99, "Artist", 10));
        repository.save(new CompactDisc("Album 2", 19.99, "Artist", 12));
        repository.save(new CompactDisc("Album 3", 99.99, "Artist", 11));

        Iterable<CompactDisc> all = repository.findAll();
        int count = 0;
        for (CompactDisc disc : all) {
            count++;
        }
        assertTrue(count >= 3);
    }

    @Test
    public void testCompactDiscWithDifferentTrackCounts() {
        repository.save(new CompactDisc("Album 1", 10.0, "Artist", 1));
        repository.save(new CompactDisc("Album 2", 15.0, "Artist", 50));
        repository.save(new CompactDisc("Album 3", 12.0, "Artist", 100));

        Iterable<CompactDisc> all = repository.findAll();
        int count = 0;
        for (CompactDisc disc : all) {
            count++;
        }
        assertTrue(count >= 3);
    }
}
