package com.conygre.spring.boot.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrackTest {

    private Track track;

    @Before
    public void setUp() {
        track = new Track();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(track);
        assertNull(track.getId());
        assertNull(track.getTitle());
    }

    @Test
    public void testConstructorWithTitle() {
        Track t = new Track("Come Together");
        assertEquals("Come Together", t.getTitle());
    }

    @Test
    public void testConstructorWithIdTitleAndCdId() {
        Track t = new Track(1, "Let It Be", 5);
        assertEquals(Integer.valueOf(1), t.getId());
        assertEquals("Let It Be", t.getTitle());
        assertEquals(5, t.getCdId());
    }

    @Test
    public void testSetAndGetId() {
        track.setId(1);
        assertEquals(Integer.valueOf(1), track.getId());
    }

    @Test
    public void testSetAndGetTitle() {
        String title = "Here Comes the Sun";
        track.setTitle(title);
        assertEquals(title, track.getTitle());
    }

    @Test
    public void testSetAndGetCdId() {
        track.setCdId(10);
        assertEquals(10, track.getCdId());
    }

    @Test
    public void testMultipleSettersAndGetters() {
        track.setId(3);
        track.setTitle("Something");
        track.setCdId(2);

        assertEquals(Integer.valueOf(3), track.getId());
        assertEquals("Something", track.getTitle());
        assertEquals(2, track.getCdId());
    }

    @Test
    public void testSetIdToNull() {
        track.setId(5);
        track.setId(null);
        assertNull(track.getId());
    }

    @Test
    public void testSetTitleToNull() {
        track.setTitle("Test Track");
        track.setTitle(null);
        assertNull(track.getTitle());
    }

    @Test
    public void testEmptyStringTitle() {
        track.setTitle("");
        assertEquals("", track.getTitle());
    }

    @Test
    public void testLongTitle() {
        String longTitle = "A Very Long Track Title That Contains Multiple Words For Testing";
        track.setTitle(longTitle);
        assertEquals(longTitle, track.getTitle());
    }

    @Test
    public void testIdWithDifferentValues() {
        track.setId(0);
        assertEquals(Integer.valueOf(0), track.getId());

        track.setId(999);
        assertEquals(Integer.valueOf(999), track.getId());

        track.setId(1);
        assertEquals(Integer.valueOf(1), track.getId());
    }

    @Test
    public void testCdIdWithDifferentValues() {
        track.setCdId(0);
        assertEquals(0, track.getCdId());

        track.setCdId(100);
        assertEquals(100, track.getCdId());

        track.setCdId(1);
        assertEquals(1, track.getCdId());
    }

    @Test
    public void testMultipleTitleUpdates() {
        track.setTitle("First Track");
        assertEquals("First Track", track.getTitle());

        track.setTitle("Second Track");
        assertEquals("Second Track", track.getTitle());

        track.setTitle("Third Track");
        assertEquals("Third Track", track.getTitle());
    }

    @Test
    public void testTrackWithAllFieldsSet() {
        track.setId(7);
        track.setTitle("Something Else");
        track.setCdId(3);

        assertEquals(Integer.valueOf(7), track.getId());
        assertEquals("Something Else", track.getTitle());
        assertEquals(3, track.getCdId());
    }

    @Test
    public void testConstructorPreservesValues() {
        Track t = new Track(42, "Bohemian Rhapsody", 15);
        
        assertEquals(Integer.valueOf(42), t.getId());
        assertEquals("Bohemian Rhapsody", t.getTitle());
        assertEquals(15, t.getCdId());
        
        // Verify we can still modify them
        t.setTitle("Another Song");
        assertEquals("Another Song", t.getTitle());
    }
}
