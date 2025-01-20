package persist;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import domain.Album;
import domain.Picture;
import domain.Tag;

import main.Main;

/**
 * 
 * @author manuelbarral
 * TODO: Erro de retornar 0 resultados!
 */
public class AlbumDM extends ADataMapper<Album>{
	
	private static final AlbumDM instance = new AlbumDM();
	
	private AlbumDM () {
		super(Album.class);
	}
	
	public static AlbumDM getInstance() {
		return instance;
	}
	
	public Long getId (Album e) {
		return e.getId();
	}
	
	public Album makeAlbum(Tag tag) throws ApplicationException{
		Album newAlbum = new Album();
		newAlbum.setTag(tag);
		newAlbum.setName(tag.getName());
		//persistir o album:
		insert(newAlbum);
		return newAlbum;
	}
	
	public Album makeAlbumWithName(String name) throws ApplicationException{
		Album newAlbum = new Album();
		newAlbum.setName(name);
		//persistir o album:
		insert(newAlbum);
		return newAlbum;
	}
	
	
	public Album setName(Album a, String name) throws ApplicationException{
		a.setName(name);
		//update do Album 
		update(a);
		return a;
	}
	
	public Album setDescription(Album a, String description) throws ApplicationException{
		a.setDescription(description);
		//update do Album 
		update(a);
		return a;
	}
	
	
	public Optional<Album> findByName(String albumName) throws ApplicationException{
		EntityManager em = null;
		try {
			em = Main.emf.createEntityManager();
			TypedQuery<Album> q = 
					em.createQuery("SELECT a FROM Album a WHERE a.name = :name", Album.class);
			
			return Optional.ofNullable(q.setParameter("name", albumName).getSingleResult());
			//Por agr retornamos o 1o Album com esse nome
			
		} finally {
			em.close();
		}
	}
	
	public Optional<Album> findByTag(Tag theTag) throws ApplicationException{
		EntityManager em = null;
		try {
			em = Main.emf.createEntityManager();
			TypedQuery<Album> q = 
					em.createQuery("SELECT a FROM Album a WHERE a.tag = :tag", Album.class);
			q.setParameter("tag", theTag);
			
			List<Album> results = q.getResultList();
			
			//Por agr retornamos o 1o Album com esse nome
			return Optional.of(results.get(0));
		} finally {
			em.close();
		}
	}
	
	public Set<Album> findAll (){
		Set<Album> resultSet = new HashSet<>(); 
        EntityManager em = null;

        try {
            em = Main.emf.createEntityManager();
            TypedQuery<Album> query = em.createQuery("SELECT a FROM Album a", Album.class);
            
            List<Album> resultList = query.getResultList();
            resultSet = new HashSet<>(resultList); 

        } finally {
        	
            if (em != null) {
                em.close();
            }
        }
        
        if (resultSet.isEmpty()) {
            System.out.println("Nenhum Album encontrado.");
        } else {
            System.out.println("-------- Albums Ancontrados na BD --------");
            for (Album t : resultSet) {
                System.out.println(t.toString());
            }
                
        }

        return resultSet;
	}
	
	public void setPictures(Album a, List<Picture> list) {
		a.setPictureList(list);
		System.out.println("PICTURE LIST = " + list.toString());
		System.out.println("PICTURE LIST NO ALBUM = " + a.getPhotos().toString());

		//update do Album 
		update(a);
		
	}

	
}

