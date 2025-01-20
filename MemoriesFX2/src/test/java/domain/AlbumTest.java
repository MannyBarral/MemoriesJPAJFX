package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class AlbumTest {
	
	@Test
	public void createAlbum () {
		Album a = null;
		a = new Album();
		assertNotEquals(null, a);
		System.out.println(a.toString());
	}
	
	@Test 
	public void atributesAlbum () {
		Album nameless = null;
		nameless = new Album();
		nameless.setName("Album123");
		nameless.setDescription("Album of memories 123");
		//TODO: album.setPictures e album.setTag
		assertEquals("Album123", nameless.getName());
		assertEquals("Album of memories 123", nameless.getDescription());
		System.out.println(nameless.toString());
	}
	
	
}
