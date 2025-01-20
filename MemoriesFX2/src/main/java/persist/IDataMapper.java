package persist;

import java.util.Optional;

/**
 * 
 * @author liliacolisnyc
 *
 */ 

public interface IDataMapper<E> {

    Optional<E> find(Long id);  // Busca uma entidade pelo ID
    
    Long insert(E e);  // Insere uma entidade e retorna o ID gerado
    
    void remove(E e);  // Remove uma entidade do banco de dados
    
    Long getId(E e) throws ApplicationException;  // Retorna o ID da entidade (a ser implementado pela classe concreta)
    
    void update(E e);  // Atualiza a entidade no banco de dados

}
