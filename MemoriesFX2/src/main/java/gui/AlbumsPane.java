package gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import domain.Album;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import persist.AlbumDM;

public class AlbumsPane extends VBox{
	
	private FlowPane albumCollection;
	private Set<Album> albums;
	private RootPane root;
	
	private boolean actionSearch;
    private boolean actionTagging;
	
	public AlbumsPane (RootPane root) {
		super();
		this.root = root;
		
		this.albumCollection = new FlowPane();
		albumCollection.setVgap(5);
        albumCollection.setHgap(5);
        
        AlbumDM albumdm = AlbumDM.getInstance();
        this.albums = albumdm.findAll();
        
        List<AlbumView> avs = new ArrayList<>();
        
        if (this.albums == null || this.albums.isEmpty()) {
        	System.out.println("No Tags Found in DB!");
        }else {
        	
            // create AlbumViews for each Album.
            for (Album a: this.albums) {
            	if (a != null) {
            		AlbumView albumview = new AlbumView(root, a);
                	System.out.println("A Criar AlbumView cm o Album: " +"{" + a.toString() + "}");
                	avs.add(albumview); //adicionar o album view aos avs;
            	}else {
            		System.out.println("Album é null: " + a.toString());
            	}          	
        	
            }     
        }
        
        Collections.sort(avs);
        // ---
        // add the list of TagViews to the interface.
        for (AlbumView av : avs) {
        	this.albumCollection.getChildren().add(av);
        	
        }
        actionSearch = true;
        //adicionar a album collection ao AlbumPane (this):
        this.getChildren().add(this.albumCollection);
			
	}
	
	public FlowPane getAlbumCollection () {
    	return this.albumCollection;
    }
	
	
	

}
