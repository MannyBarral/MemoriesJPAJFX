package persist;

import domain.Picture;
import domain.Tag;
import main.Main;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.sql.SQLException;
import java.time.Year;

public class PictureDM extends ADataMapper<Picture> {
    private static PictureDM instance = new PictureDM();

    private PictureDM() {
        super(Picture.class);
    }

    public static PictureDM getInstance() {
        if (instance == null) {
            instance = new PictureDM();
        }
        return instance;
    }

    public Long getId(Picture picture) {
        return picture.getId();
    }
    

    public Picture makePicture (String path) {
    	Picture pic = new Picture();
    	pic.setFilePath(path);
    	insert(pic);
    	return pic;
    }

    public Set<Picture> findAllPictures() throws ApplicationException {
    	EntityManager em = null;
		try {
			em = Main.emf.createEntityManager();
			TypedQuery<Picture> query = em.createQuery("SELECT p FROM Picture p", Picture.class);
	        return new HashSet<>(query.getResultList());
		} finally {
			em.close();
		}
        
    }

    
    public Set<Picture> findAllPictures(EntityManager em) throws ApplicationException {
        TypedQuery<Picture> query = em.createQuery("SELECT p FROM Picture p", Picture.class);
        return new HashSet<>(query.getResultList());
    }

    public Set<Picture> getRecentPictures(EntityManager em) throws ApplicationException {
    	
    	int currentYear = Year.now().getValue();
    	
    	TypedQuery<Picture> query = em.createQuery(
        		"SELECT p FROM Picture p "
        		+ "WHERE LENGTH(p.filePath) >= 18 AND "
        		+ "SUBSTRING(p.filePath, 14, 4) = :currentYear", 
        	    Picture.class);
    	
    	query.setParameter("currentYear", String.valueOf(currentYear));
        
        Set<Picture> pictures =  new HashSet<>(query.getResultList());
        
        while (pictures.isEmpty() && currentYear > 1900) {
        	
        	query.setParameter("currentYear", String.valueOf(currentYear)); // Update parameter
            pictures = new HashSet<>(query.getResultList());
            currentYear -= 1;
        }
        
        return pictures;
    }

    public Set<Picture> findByTag(Tag tag) throws ApplicationException {
        EntityManager em = Main.emf.createEntityManager();
        try {
            TypedQuery<Picture> query = em.createQuery(
                "SELECT p FROM Picture p JOIN p.tags t WHERE t.id = :tagId", Picture.class);
            query.setParameter("tagId", tag.getId());
            return new HashSet<>(query.getResultList());
        } finally {
            em.close();
        }
    }

    public Set<Picture> findByTags(Set<Tag> tags) throws ApplicationException {
        EntityManager em = Main.emf.createEntityManager();
        try {
            TypedQuery<Picture> query = em.createQuery(
        		"SELECT p " +
	            "FROM Picture p " +
	            "JOIN p.tags t " +
	            "WHERE t IN :tags " +
	            "GROUP BY p " +
	            "HAVING COUNT(t) = :tagCount " +
	            "   AND COUNT(t) = (SELECT COUNT(t2) FROM Tag t2 WHERE t2 MEMBER OF p.tags)",
                Picture.class);
            query.setParameter("tags", tags);
            query.setParameter("tagCount", (long) tags.size());
            return new HashSet<>(query.getResultList());
        } finally {
            em.close();
        }
    }

    public Set<Picture> findByAnyTags(Set<Tag> tags) throws ApplicationException {
        EntityManager em = Main.emf.createEntityManager();
        try {
            TypedQuery<Picture> query = em.createQuery(
                "SELECT DISTINCT p FROM Picture p JOIN p.tags t WHERE t IN :tags", Picture.class);
            query.setParameter("tags", tags);
            return new HashSet<>(query.getResultList());
        } finally {
            em.close();
        }
    }

    public void addTag(Picture pic, Tag tag) throws ApplicationException {
        EntityManager em = Main.emf.createEntityManager();
        try {
            em.getTransaction().begin();
            pic.addTag(tag);
            em.merge(pic);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void removeTag(Picture pic, Tag tag) throws ApplicationException {
        EntityManager em = Main.emf.createEntityManager();
        try {
            em.getTransaction().begin();
            pic.removeTag(tag);
            em.merge(pic);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


}

