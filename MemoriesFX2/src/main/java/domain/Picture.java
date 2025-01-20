package domain;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PICTUREID")
    private Long id;

    @Column(name = "FILEPATH", nullable = false)
    private String filePath;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
    			name = "picture_tags",
    			joinColumns = @JoinColumn(name = "PICTUREID"),
    			inverseJoinColumns = @JoinColumn(name = "TAGID")
    )
    private Set<Tag> tags = new HashSet<>();
    
    @Column(name = "SELECTED")
    private boolean isSelected = false;
        
    
    //mapped by o dono da relação album (se n ficamos com 4 colunas em vez de apenas 2)
    //TODO: Ver nome das columns!
    @ManyToMany (mappedBy = "photos")
	//iniciar como Lista vazia (Mutavel mas mantem a ordem)
	private List<Album> albums = new ArrayList<>(); 

    public Picture() {}

    public Picture(String filePath) {
        this.filePath = filePath;
    }

    public long getId() {
        return id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    
    //N percebo estes 2 metodos João...
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Picture)) return false;
        Picture picture = (Picture) o;
        return id == picture.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                 ", Tags: " + this.tags.toString() +'}';
    }
    
    public void select() {
    	this.isSelected = true;
    }
    
    public boolean isSelected () {
    	return this.isSelected;
    }
    
    public void  unSelect() {
    	this.isSelected = false;
    }
}

