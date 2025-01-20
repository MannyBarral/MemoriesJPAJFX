package persist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import domain.Tag;
import utils.Color;

import java.util.Optional;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
/**
 * 
 * @author liliacolisnyc
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TagDMTest {
    
    private static TagDM tagDM = null;

    @BeforeEach
    public void setUp() {
        tagDM = TagDM.getInstance();
    }

    @Test
    @Order(1)
    public void persistTag() throws ApplicationException {
        Tag tagTest = new Tag("persistTag", Color.RED);
        tagDM.insert(tagTest);
        assertNotNull(tagTest.getId());
        System.out.println("Tag ID: " + tagTest.getId());
    }

    @Test
    @Order(2)
    public void testTagSemCor() throws ApplicationException {
        Tag tag = tagDM.makeTag("TagSemCor", null);

        assertNotNull(tag);
        assertEquals("TagSemCor", tag.getName());
        assertNull(tag.getColor());

        System.out.println("Nome: " + tag.getName());  
        System.out.println("Cor: " + tag.getColor());  
    }

    @Test
    @Order(3)
    public void testTagComCor() throws ApplicationException {
        Tag tag = tagDM.makeTag("TagComCorfase2", Color.WHITE);

        assertNotNull(tag);
        assertEquals("TagComCorfase2", tag.getName());
        assertEquals(Color.WHITE, tag.getColor());

        System.out.println("Nome: " + tag.getName());  
        System.out.println("Cor: " + tag.getColor());  
    }

    @Test
    @Order(4)
    public void testMakeTag_CreateNewTag() throws ApplicationException {
        Tag newTag = tagDM.makeTag("TagTestefase3", Color.BLACK);
        assertNotNull(newTag);
        assertEquals("TagTestefase3", newTag.getName());
        assertEquals(Color.BLACK, newTag.getColor());
    }

    @Test
    @Order(5)
    public void testMakeTag_ExistingTag() throws ApplicationException {
        tagDM.makeTag("TagExistente", Color.GREEN);

        Tag existingTag = tagDM.makeTag("TagExistente", Color.GREEN);

        assertNotNull(existingTag);
        assertEquals("TagExistente", existingTag.getName());
        assertEquals(Color.GREEN, existingTag.getColor());
    }

    @Test
    @Order(6)
    public void testSetTag() throws ApplicationException {
        Tag tag = tagDM.makeTag("TagTeste", Color.BLUE);

        Tag updatedTag = tagDM.setTag(tag, "TagAtualizada", Color.YELLOW);

        assertNotNull(updatedTag);
        assertEquals("TagAtualizada", updatedTag.getName());
        assertEquals(Color.YELLOW, updatedTag.getColor());
    }

    @Test
    @Order(7)
    public void testSetName() throws ApplicationException {
        Tag tag = tagDM.makeTag("TagTeste", Color.RED);

        Tag updatedTag = tagDM.setName(tag, "NovoNome");

        assertNotNull(updatedTag);
        assertEquals("NovoNome", updatedTag.getName());
        assertEquals(Color.RED, updatedTag.getColor());
    }

    @Test
    @Order(8)
    public void testSetColor() throws ApplicationException {
        Tag tag = tagDM.makeTag("TagTeste", Color.RED);

        Tag updatedTag = tagDM.setColor(tag, Color.GREEN);

        assertNotNull(updatedTag);
        assertEquals("TagTeste", updatedTag.getName());
        assertEquals(Color.GREEN, updatedTag.getColor());
    }

    @Test
    @Order(9)
    public void testFindByColor() throws ApplicationException {
        List<Tag> redTags = tagDM.findByColor(Color.RED);

        assertNotNull(Color.RED); 
        assertTrue(redTags.size() > 0);  

        for (Tag tag : redTags) {
            assertEquals(Color.RED, tag.getColor());  
        }
    }

//    @Test
//    @Order(10)
//    public void testFindByPath() throws ApplicationException {
//
//        tagDM.makeTag("TagTeste", Color.RED);
//
//        Optional<Tag> foundTag = tagDM.findByPath("TagTeste");
//
//        assertTrue(foundTag.isPresent(), "A tag deveria ser encontrada.");
//        assertEquals("TagTeste", foundTag.get().getName(), "O nome da tag encontrada deveria ser 'TagTeste'.");
//        assertEquals(Color.RED, foundTag.get().getColor(), "A cor da tag encontrada deveria ser RED.");
//    }

//    @Test
//    @Order(10)
//    public void testFindByPath_TagNotFound() throws ApplicationException {
//        
//        Optional<Tag> foundTag = tagDM.findByPath("TagInexistente");
//
//        assertFalse(foundTag.isPresent(), "A tag não deveria ser encontrada.");
//    }


    @Test
    @Order(11)
    public void testFindAllTags() throws ApplicationException {
        Set<Tag> tags = tagDM.findAllTags();

        assertNotNull(tags); 
        assertTrue(tags.size() > 0);  

        System.out.println("-------- Tags --------");
        for (Tag t : tags) {
            System.out.println("ID: " + t.getId());
            System.out.println("Nome: " + t.getName());
            System.out.println("Cor: " + (t.getColor() != null ? t.getColor() : "Sem cor"));
            System.out.println("----------------------");
        }
    }
}
