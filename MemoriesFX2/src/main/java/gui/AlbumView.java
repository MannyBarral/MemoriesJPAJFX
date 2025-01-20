package gui;

import domain.Album;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import persist.ApplicationException;

public class AlbumView extends Label implements Comparable<AlbumView>{
	
	private Album album;
	private RootPane root;
	
	private static Background AlbumBackground = new Background(
    		new BackgroundFill(Color.LIGHTSEAGREEN, null, null));    	
    private static Background selectedBackground = new Background(
    		new BackgroundFill(Color.CYAN, null, null)); 
	
	
	public AlbumView (RootPane root, Album a) {
		super(a.getName());
		this.root = root;
		this.album = a;
		
		// Define the padding
		this.setPadding(new Insets(10, 10, 10, 10));
		//Define the Background
		this.setBackground(AlbumBackground);
		
		if (this.album!= null) {
	        this.setOnMouseClicked(event -> {
	            System.out.println("AlbumView " + this.album.getName());
	        });
		}
		
	}
	
	/**
     * This constructor is used to obtain a copy of a AlbumView. This constructor
     * is useful when we want an existing AlbumView to appear somewhere else in
     * the interface.
     *
     * @param a
     * @param belongsTo
     */
    AlbumView(AlbumView a) {
        this(a.root, a.getAlbum());
    }
    
    @Override
    public int compareTo(AlbumView t) {
        // change the following line such that the ordering is alphabetical
        // (using tag's name)
    	if (this.album != null) {
    		
        	String thisAlbum = this.album.getName();
        	
        	String otherAlbum = t.album.getName();
        	
        	System.out.println("A comparar thisAlbum no compareTo: {" + thisAlbum +
    				"} à otherAlbum: {" + otherAlbum + "}");
        	
            return thisAlbum.compareTo(otherAlbum);
    		
    	}else {
    		System.out.println("this.album é null (no AlbumView.java - compareTo()" + "Objeto AlbumView: " + ""
    				+ "{" + this.toString() + "}");
    		if (t.album == null) {
    			System.out.println("otherAlbum tambem é null (no AlbumView.java - compareTo()" + "Objeto AlbumView: " + ""
        				+ "{" + this.toString() + "}");
    		}
    		
    		return 0;
    	}
    	
    }
    
    @Override
    public String toString() {
        return "ALbumView[Album=" + this.album + "]";
    }
    
    @Override
    public int hashCode() {
    	if (this.album != null) {
    	    return this.album.hashCode();
    	} else {
    	    return 0;
    	}
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (this == obj) {
            return true;
    	}else {
    		return false;     	
        }
    }
    
    public Album getAlbum () {
    	return this.getAlbum();
    }
}
