package persist;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import main.Main;

/**
 * 
 * @author manuelbarral
 *
 */
public abstract class ADataMapper <E> implements IDataMapper<E>{
	
	private Class <E> theClass;
	
	//private EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAMemories");
	
	//Construtor (instanciado no inicio de cada classe xxxDM para
	// termos acesso a classe de dados (para o find).
	protected ADataMapper (Class <E> theClass) {
		this.theClass = theClass;
	}

	public Optional<E> find (Long id) {
		EntityManager em = null;
		try {
			em = Main.emf.createEntityManager();
			return (Optional<E>) em.find(theClass, id);
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			em.close();
		}
		
		return null;
	}
	
	@Override
	public Long insert(E e) {
		EntityManager em = null;
		try {
			em = Main.emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(e);
			em.getTransaction().commit();
			//return (long) em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(e);
			em.close();
			try {
				return getId(e);
			} catch (ApplicationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}catch (Exception exc) {
			System.out.println("Erro no insert da classe na BD!!!");
			System.out.println(exc);
			
		} 
		
		return (long) 0;
	}
	

	//TODO: if isManaged() -> Apenas commit().
	@Override
	public void update (E e) {
		EntityManager em = null;
		try {
			em = Main.emf.createEntityManager();
			em.getTransaction().begin();
			
			em.merge(e);
			em.getTransaction().commit();
			em.close();
		} catch (Exception exc){
			System.out.println(exc);
		}
		
	}
	
	@Override
	public void remove (E e) {
		EntityManager em = null;
		try {
			em = Main.emf.createEntityManager();
			em.getTransaction().begin();
			em.remove(e);
			em.getTransaction().commit();
			em.close();
		} catch (Exception exc) {
			System.out.println(exc);
		}
	}
	
		
	
}
