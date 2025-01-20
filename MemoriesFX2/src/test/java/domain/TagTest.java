package domain;
import utils.Color;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
/**
 * 
 * @author liliacolisnyc
 *
 */
public class TagTest {

    // Teste para criar uma tag
    @Test
    public void testCreateTag() {
        // Criando uma nova tag com nome e cor
        Tag tag = new Tag("TagTeste", Color.RED);

        // Verificando se a tag não é nula
        assertNotNull(tag);
        System.out.println(tag.toString());
    }

    // Teste para atribuir e verificar os atributos de uma tag
    @Test
    public void testAttributesTag() {
        // Criando uma tag com nome e cor
        Tag tag = new Tag("TagTest1", Color.GREEN);

        // Atribuindo novos valores aos atributos
        tag.setName("TagUpdated");
        tag.setColor(Color.BLUE);

        // Verificando se o nome e a cor foram atualizados corretamente
        assertEquals("TagUpdated", tag.getName());
        assertEquals(Color.BLUE, tag.getColor());

        // Exibindo os detalhes da tag
        System.out.println(tag.toString());
    }

    // Teste para verificar o método toString()
    @Test
    public void testTagToString() {
        // Criando uma tag com nome e cor
        Tag tag = new Tag("Festa", Color.YELLOW);

        // Verificando a saída do método toString
        String expectedToString = "ID: null; Nome: Festa; Cor: YELLOW;";
        assertEquals(expectedToString, tag.toString());

        // Exibindo a string no console
        System.out.println(tag.toString());
    }
}

