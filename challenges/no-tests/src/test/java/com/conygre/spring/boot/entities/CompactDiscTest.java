package com.conygre.spring.boot.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CompactDiscTest {

    private CompactDisc compactDisc;

    @Before
    public void setUp() {
        compactDisc = new CompactDisc();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(compactDisc);
        assertEquals(0, compactDisc.getId());
        assertNull(compactDisc.getTitle());
        assertNull(compactDisc.getArtist());
        assertNull(compactDisc.getPrice());
        assertNull(compactDisc.getTracks());
    }

    @Test
    public void testParameterizedConstructor() {
        CompactDisc cd = new CompactDisc("Dark Side of the Moon", 12.99, "Pink Floyd", 10);
        assertEquals("Dark Side of the Moon", cd.getTitle());
        assertEquals("Pink Floyd", cd.getArtist());
        assertEquals(Double.valueOf(12.99), cd.getPrice());
        assertEquals(Integer.valueOf(10), cd.getTracks());
    }

    @Test
    public void testSetAndGetId() {
        compactDisc.setId(1);
        assertEquals(1, compactDisc.getId());
    }

    @Test
    public void testSetAndGetTitle() {
        String title = "Abbey Road";
        compactDisc.setTitle(title);
        assertEquals(title, compactDisc.getTitle());
    }

    @Test
    public void testSetAndGetArtist() {
        String artist = "The Beatles";
        compactDisc.setArtist(artist);
        assertEquals(artist, compactDisc.getArtist());
    }

    @Test
    public void testSetAndGetPrice() {
        Double price = 9.99;
        compactDisc.setPrice(price);
        assertEquals(price, compactDisc.getPrice());
    }

    @Test
    public void testSetAndGetTracks() {
        Integer tracks = 17;
        compactDisc.setTracks(tracks);
        assertEquals(tracks, compactDisc.getTracks());
    }

    @Test
    public void testMultipleSettersAndGetters() {
        compactDisc.setId(5);
        compactDisc.setTitle("Rumours");
        compactDisc.setArtist("Fleetwood Mac");
        compactDisc.setPrice(11.99);
        compactDisc.setTracks(40);

        assertEquals(5, compactDisc.getId());
        assertEquals("Rumours", compactDisc.getTitle());
        assertEquals("Fleetwood Mac", compactDisc.getArtist());
        assertEquals(Double.valueOf(11.99), compactDisc.getPrice());
        assertEquals(Integer.valueOf(40), compactDisc.getTracks());
    }

    @Test
    public void testSetTitleToNull() {
        compactDisc.setTitle("Test");
        compactDisc.setTitle(null);
        assertNull(compactDisc.getTitle());
    }

    @Test
    public void testSetArtistToNull() {
        compactDisc.setArtist("Artist");
        compactDisc.setArtist(null);
        assertNull(compactDisc.getArtist());
    }

    @Test
    public void testSetPriceToNull() {
        compactDisc.setPrice(10.0);
        compactDisc.setPrice(null);
        assertNull(compactDisc.getPrice());
    }

    @Test
    public void testSetTracksToNull() {
        compactDisc.setTracks(10);
        compactDisc.setTracks(null);
        assertNull(compactDisc.getTracks());
    }

    @Test
    public void testPriceWithDifferentValues() {
        compactDisc.setPrice(0.0);
        assertEquals(Double.valueOf(0.0), compactDisc.getPrice());

        compactDisc.setPrice(99.99);
        assertEquals(Double.valueOf(99.99), compactDisc.getPrice());

        compactDisc.setPrice(0.01);
        assertEquals(Double.valueOf(0.01), compactDisc.getPrice());
    }

    @Test
    public void testTracksWithDifferentValues() {
        compactDisc.setTracks(0);
        assertEquals(Integer.valueOf(0), compactDisc.getTracks());

        compactDisc.setTracks(1);
        assertEquals(Integer.valueOf(1), compactDisc.getTracks());

        compactDisc.setTracks(100);
        assertEquals(Integer.valueOf(100), compactDisc.getTracks());
    }

    @Test
    public void testIdWithDifferentValues() {
        compactDisc.setId(0);
        assertEquals(0, compactDisc.getId());

        compactDisc.setId(999);
        assertEquals(999, compactDisc.getId());

        compactDisc.setId(1);
        assertEquals(1, compactDisc.getId());
    }

    @Test
    public void testEmptyStringTitle() {
        compactDisc.setTitle("");
        assertEquals("", compactDisc.getTitle());
    }

    @Test
    public void testEmptyStringArtist() {
        compactDisc.setArtist("");
        assertEquals("", compactDisc.getArtist());
    }

    @Test
    public void testLongTitle() {
        String longTitle = "A Very Long Album Title That Contains Many Words and Characters for Testing Purposes";
        compactDisc.setTitle(longTitle);
        assertEquals(longTitle, compactDisc.getTitle());
    }

    @Test
    public void testLongArtist() {
        String longArtist = "An Artist With A Very Long Name Composed Of Multiple Words";
        compactDisc.setArtist(longArtist);
        assertEquals(longArtist, compactDisc.getArtist());
    }
}
