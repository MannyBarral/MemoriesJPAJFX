
package domain;


import domain.Picture;

import org.junit.jupiter.api.*;
import persist.PictureDM;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import main.Main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;


class PictureTest {
	
	@Test
	public void createPicture () {
		Picture a = null;
		a = new Picture ();
		System.out.println("New Picture 'a' created: " + a.toString());
		assertNotNull(a);
	}
	
	@Test 
	public void filePathPicture () {
		Picture b = null;
		b = new Picture ();
		//Give atributes:
		b.setFilePath("PictureB/PictureTest");
		assertEquals("PictureB/PictureTest", b.getFilePath());
	}
	
	@Test
	public void addRemoveTagPicture () {
		Tag t = new Tag();
		t.setTag("TagPictureTest1");
		
		Picture c = null;
		c = new Picture();
		c.addTag(t);
		System.out.println("Tags em PictureC no PictureTestdepois do addTag():" + c.getTags().toString());
		//check if the Set<Tag> in Pictures is empty.
		assertFalse(c.getTags().isEmpty());
		
		//RemoveTag 
		c.removeTag(t);
		System.out.println("Tags em PictureC no PictureTestdepois do removeTag():" + c.getTags().toString());
		assertTrue(c.getTags().isEmpty());

	}
	
	
   
}

