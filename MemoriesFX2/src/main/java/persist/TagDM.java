package persist;

import domain.Tag;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import main.Main;
import utils.Color;


/**
 * 
 * @author liliacolisnyc
 *
 */ 

public class TagDM extends ADataMapper<Tag> {

    private static final TagDM instance = new TagDM(); 

    private TagDM() {
        super(Tag.class);
    }

    public Long getId(Tag t) {
            return t.getId();
    }

    public static TagDM getInstance() {
        return instance;
    }

 // Método para criar ou buscar uma tag
    public Tag makeTag(String name, Color color) throws ApplicationException {
        EntityManager em = null;
        try {
            em = Main.emf.createEntityManager();

            TypedQuery<Tag> query = em.createQuery("SELECT t FROM Tag t WHERE t.name = :name", Tag.class);
            query.setParameter("name", name);
            List<Tag> resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                return resultList.get(0);
            } else {
                
                Tag newTag = new Tag(name, color);
                insert(newTag); 
                return newTag;
            }
        } catch (Exception e) {
            throw new ApplicationException("Erro ao criar ou buscar a tag no banco de dados.");
        } finally {
            if (em != null) {
                em.close();  
            }
        }
    }
    
    public Tag setTag(Tag tag, String newName, Color newColor) throws ApplicationException {
        if (newName == null || newName.isEmpty()) {
            throw new ApplicationException("O nome da tag não pode ser vazia.");
        }

        tag.setName(newName);
        tag.setColor(newColor);
        update(tag);
        return tag;
    }
    
    public Tag setName(Tag tag, String name) throws ApplicationException {
        tag.setName(name); 
        update(tag);    
        return tag;         
    }

    public Tag setColor(Tag tag, Color color) throws ApplicationException {
        tag.setColor(color);  
        update(tag);           
        return tag;            
    }

    public List<Tag> findByColor(Color string) {
    	EntityManager em = null;
        try {
        	em = Main.emf.createEntityManager();
            TypedQuery<Tag> query = em.createQuery("SELECT t FROM Tag t WHERE t.color = :color", Tag.class);
            query.setParameter("color", string);  

            return query.getResultList();
        
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Optional<List<Tag>> findByPath(String testTag) throws ApplicationException {
        EntityManager em = null;
        try {
            em = Main.emf.createEntityManager();
            TypedQuery<Tag> query = em.createQuery("SELECT t FROM Tag t WHERE t.name = :name", Tag.class);
            query.setParameter("name", testTag);

            List<Tag> result = query.getResultList();
            if (result.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(result); 

        } catch (Exception e) {
            throw new ApplicationException("Erro ao buscar a tag.");
        } finally {
            if (em != null) em.close();
        }
    }

   
    public Set<Tag> findAllTags() {
    	Set<Tag> resultSet = new HashSet<>(); 
        EntityManager em = null;

        try {
            em = Main.emf.createEntityManager();
            TypedQuery<Tag> query = em.createQuery("SELECT t FROM Tag t", Tag.class);
            
            List<Tag> resultList = query.getResultList();
            resultSet = new HashSet<>(resultList); 

        } finally {
        	
            if (em != null) {
                em.close();
            }
        }
        
        if (resultSet.isEmpty()) {
            System.out.println("Nenhuma tag encontrada.");
        } else {
            System.out.println("-------- Tags --------");
            for (Tag t : resultSet) {
                System.out.println("ID: " + (t.getId() != null ? t.getId().toString() : "N/A"));
                System.out.println("Nome: " + t.getName());
                System.out.println("Cor: " + (t.getColor() != null ? t.getColor() : "Sem cor"));
                System.out.println("----------------------");
            }
        }

        return resultSet;
    }

}


