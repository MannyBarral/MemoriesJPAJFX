package domain;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import persist.ApplicationException;
import utils.Color;
import persist.TagDM;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
/**
 * 
 * @author liliacolisnyc
 *
 */

@Entity
public class Tag {
	
	@Id 
	@GeneratedValue (strategy = GenerationType.AUTO) 
	@Column(name = "TAGID") // Mapeia a coluna "ID" da tabela
	private Long id;
	

	@Column(name = "TAG", unique = true, nullable= false)
	private String name;
	
	@Enumerated(EnumType.STRING) // Salva o nome do enum no banco
	@Column(name = "Color", nullable= true)
	private Color color;
	
	@ManyToMany(mappedBy= "tags")
	private Set<Picture> pictures = new HashSet<>();
	
	private int isSelected = 0;
	

	public Tag() {
		
	}
	
	//Construtor
	public Tag(String name, Color color) {
		this.name = name;
		//Se color não for null, coloca uma cor
		if(color != null) {
			this.color = color;
		}
	}
	
	
	//Getters e Setters
	public Long getId() {
		return id;
	}
	
    public void setTag(String newName) {
       this.name = newName;
    }
	
	public String getName() {
		return name;
	}
	
	public Color getColor() {
	    return color; 
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
    public static Tag getTag(String tagName,Color cor) throws ApplicationException {
			return TagDM.getInstance().makeTag(tagName,cor);
		   }
   
    public Set<Picture> getPictures(){
    	return this.pictures;
    }
    
    public void setPictures (Set<Picture> newPictures) {
    	this.pictures = newPictures;
    }
    
    public void addPicture (Picture pic) {
    	this.pictures.add(pic);
    }
	
	//Método toString
    public String toString() {
        return "ID: " + id + "; " +
               "Nome: " + name + "; " +
               "Cor: " + (color != null ? color.name() : "Sem cor") + ";";
    }
    
    public int isSelected() {
    	return this.isSelected;
    }
    public void selectTag() {
    	this.isSelected = 1;
    	System.out.println("Tag: {" + this.toString() + "} is Selected!");
    }
    
    public void unselectTag() {
    	this.isSelected = 0;
    	System.out.println("Tag: {" + this.toString() + "} is Un-Selected!");
    }

}
