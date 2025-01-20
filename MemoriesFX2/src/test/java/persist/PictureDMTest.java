package persist;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import domain.Picture;
import domain.Tag;
import utils.Color;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PictureDMTest {
	
	private static PictureDM picturedm = null;
	private static TagDM tagdm = null;
	
	@BeforeAll
	public static void setUpClass () {
		picturedm = PictureDM.getInstance();
		tagdm = TagDM.getInstance();
//		em.getTransaction().begin();
//		em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
//		em.createNativeQuery("DROP TABLE IF EXISTS " + "PICTURE").executeUpdate();
//		em.getTransaction().commit();
//		em.close();
	}
	
	@Test 
	@Order(1)
	public void makePictureTest() throws ApplicationException {
		String pathtest = "Picture1/pathTest";
		Picture picTest = picturedm.makePicture(pathtest);
		System.out.println("Picture Teste no makePictureTest antes depois insert: " + picTest.toString());
		Long picTestId = picTest.getId();
		System.out.println("ID da Picture Test no makePictureTest depois do insert:" + picTestId);
		//ir buscar a pictures a BD (n o mais indicado):
		Set<Picture> picsRetrieved = picturedm.findAllPictures();
		System.out.println("Fotos retrieved com o findAll() no makePictureTest: " + picsRetrieved.toString());
		assertNotNull(picsRetrieved);
		assertTrue (picsRetrieved.contains(picTest));		
	}
	
	@Test
	@Order(2)
	public void tagPictureNovaTest() throws ApplicationException {
		Tag tTeste = tagdm.makeTag("tagParaPictureTest", Color.AMARELO);
		System.out.println("TagTeste no tagPictureNovaTeste: " + tTeste.toString());
		//criar Picture Nova:
		Picture p2 = picturedm.makePicture("Picture2/pathTest2");
		System.out.println("Picture Test 2 depois do insert no atributesPictureNovaTest: " + p2.toString());
		//setTag:
		picturedm.addTag(p2, tTeste);
		System.out.println("Picture Test 2 depois do addTag() no atributesPictureNovaTest: " + p2.toString());
		//procurar a picture por Tag:
		Set<Picture> picsTagT = picturedm.findByTag(tTeste);
		System.out.println("Fotos retrieved com o findByTag(TagT) no tagPictureNovaTest: " + picsTagT.toString());
		assertNotNull(picsTagT);
		assertTrue (picsTagT.contains(p2));
		
		//Remove a tag:
		picturedm.removeTag(p2, tTeste);
		Set<Picture> picsTagTR = picturedm.findByTag(tTeste);
		System.out.println("Fotos retrieved com o findByTag(TagT) no tagPictureNovaTest depois "
				+ "do removeTag(pictureTeste2, tagT): " + picsTagTR.toString());
		assertFalse(picsTagTR.contains(p2));
		
	}
	
	@Test
	@Order(3)
	public void searchByTagsTeste () throws ApplicationException {
		Tag tTeste2 = tagdm.makeTag("tagParaPictureTest2", Color.AMARELO);
		System.out.println("TagTeste2 criada no searchByTagsTeste: " + tTeste2.toString());
		Tag tTeste3 = tagdm.makeTag("tagParaPictureTest3", Color.AZUL);
		System.out.println("TagTeste3 criada no searchByTagsTeste: " + tTeste3.toString());
		Picture p = picturedm.makePicture("PictureTesteP/TestePath");
		System.out.println("Criação da pictureTesteP no searchByTagsTeste: "+ p.toString());
		//Adicionar as Tags á PictureP
		picturedm.addTag(p, tTeste2);
		picturedm.addTag(p, tTeste3);
		Set<Tag> newTags = new HashSet<>();
		newTags.add(tTeste2);
		newTags.add(tTeste3);
		//Procurar:
		Set<Picture> picturesRetrieved = picturedm.findByTags(newTags);
		System.out.println("Fotos com as Tags Teste2 e Teste3: " + picturesRetrieved.toString());
		assertTrue(picturesRetrieved.contains(p));
	}
	
	@Test
	@Order(4)
	public void searchByAnyTagsTest () throws ApplicationException {
		Tag tTeste4 = tagdm.makeTag("tagParaPictureTest4", Color.GREEN);
		System.out.println("TagTeste4 criada no searchByAnyTagsTeste: " + tTeste4.toString());
		Tag tTeste5 = tagdm.makeTag("tagParaPictureTest5", Color.AZUL);
		System.out.println("TagTeste5 criada no searchByAnyTagsTeste: " + tTeste5.toString());
		Picture c = picturedm.makePicture("PictureTesteC/TestePath");
		System.out.println("Criação da pictureTesteC no searchByTagsTeste: "+ c.toString());
		//adicionar as tags:
		picturedm.addTag(c, tTeste4);
		picturedm.addTag(c, tTeste5);
		Set<Tag> newTags = new HashSet<>();
		newTags.add(tTeste4);
		System.out.println("Tags que estamos a procurar no searchByAnyTags: " + newTags.toString());
		//Procurar by Any Tags:
		Set<Picture> picturesRetrieved = picturedm.findByAnyTags(newTags);
		System.out.println("Fotos com pelo menos a Tags Teste4: " + picturesRetrieved.toString());
		assertTrue(picturesRetrieved.contains(c));
	}
	
	

}
