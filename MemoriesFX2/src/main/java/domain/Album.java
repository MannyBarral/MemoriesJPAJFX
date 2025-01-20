package domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

/**
 * 
 * @author manuelbarral
 *
 */

@Entity
public class Album {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long albumId;
	
	private String name;
	
	private String description;
	
	@OneToOne (orphanRemoval = true, cascade= CascadeType.ALL)
	@JoinColumn (name= "Tag_Ids")
	private Tag tag;
	
	
	//TODO: não posso representar a lista de fotos com List
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable (name = "albumFotos",
			joinColumns = @JoinColumn(name = "ALBUMID"),
	        inverseJoinColumns = @JoinColumn(name = "ID"))
	//iniciar como Lista vazia (Mutavel mas mantem a ordem)
	private List<Picture> photos = new ArrayList<>(); 
	
	//Empty Constructor:
	public Album () {
	}
	
	public void setTag (Tag tag){
		this.tag = tag;
	}
	
	public void addPhoto (Picture pic) {
		//Adicionar uma foto:
		this.photos.add(pic);
	}
	
	public void setPictureList (List<Picture> list) {
		this.photos = list;
	}
	
	//Getters e Setters 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Long getId () {
		return this.albumId;
	}
	
	public List<Picture> getPhotos (){
		return this.photos;
	}
	
	public String toString() {
		
		return 	"ID: " + this.albumId + "; \n" +
				"Descrição: " + this.description + "; \n" +
				"Nome: " + this.name + "; \n" + 
				"Tag: " + this.tag + "; \n" +
				"Photos: " + this.photos + ";"; 
		
	}
	
	
}
