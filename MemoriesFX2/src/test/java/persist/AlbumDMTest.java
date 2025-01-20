package persist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import domain.Album;
import domain.Picture;
import domain.Tag;
import main.Main;



@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AlbumDMTest {
	
	private static AlbumDM albumdm = null;
	private static PictureDM picturedm = null;
	
	@BeforeAll
	public static void setUpClass () {
		albumdm = AlbumDM.getInstance();
		picturedm = PictureDM.getInstance();
	}
	
	@Test
	@Order(1)
	public void persistAlbum() {
		Tag t = new Tag();
		t.setTag("TagTeste1");
		try {
			Album albumtest = albumdm.makeAlbum(t);
			assertNotNull(albumdm.getId(albumtest));
			System.out.println("AlbumID: " + albumdm.getId(albumtest));
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(2)
	public void atributesAlbumNovo () {
		Tag t = new Tag();
		t.setTag("TagTeste2");
		try {
			Album albumtest2 = albumdm.makeAlbum(t);
			albumdm.setName(albumtest2, "AlbumTest2");
			albumdm.setDescription(albumtest2, "Album de teste numero 2");
			Optional<Album> albumGuardado = albumdm.findByName("AlbumTest2");
			assertNotNull(albumGuardado);
			assertEquals("Album de teste numero 2", albumGuardado.get().getDescription());
			System.out.println(albumGuardado);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	//test mudar atributes sem update (so com os sets --> utiliza o update())
	
	@Test 
	@Order(3)
	public void findByTagTest () {
		Tag t = new Tag();
		t.setTag("TagTeste3");
		try {
			Album albumtest3 = albumdm.makeAlbum(t);
			Optional<Album> albumGuardado = albumdm.findByTag(t);
			assertNotNull(albumGuardado);
			System.out.println(albumGuardado);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test 
	@Order(4)
	public void setPicturesTest() {
		//Array de pictures:
		List<Picture> listPics = new ArrayList<>();
		//criação de pictures:
		Picture pic1 = picturedm.makePicture("picture/test1");
		Picture pic2 = picturedm.makePicture("picture/test2");
		Picture pic3 = picturedm.makePicture("picture/test3");
		listPics.add(pic1);
		listPics.add(pic2);
		listPics.add(pic3);
		//ir buscar um album chamado: AlbumTest2:
		try {
			Optional<Album> aTest2 = albumdm.findByName("AlbumTest2");
			Album albumTest2 = aTest2.get(); 							//.get do optional para obter o album
			albumdm.setPictures(albumTest2, listPics);
			System.out.println("FOTOS NO AlBUM DEPOIS DO .setPictures(): " + albumTest2.getPhotos().toString());
			
			//Verificar que existem fotos:
			Optional<Album> aTest2retrieved = albumdm.findByName("AlbumTest2");
			Album albumTest2retireved = aTest2retrieved.get();
			//Print Lista de fotos no album:
			System.out.println("FOTOS NO ALBUM QUE FOI RETRIEVED DA BD: " +
									albumTest2retireved.getPhotos().toString());
			assertNotNull(albumTest2retireved.getPhotos().toString());
			
		} catch (ApplicationException e) {
			System.out.println("Erro no Test de setPictures: AlbumTest2 não foi encontrado na BD");
			e.printStackTrace();
		}
		
		
		
	}

}
