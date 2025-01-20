package gui;

import domain.Album;
import domain.Tag;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import persist.AlbumDM;
import persist.ApplicationException;
import persist.TagDM;

public class NewAlbumPane extends VBox{
	
	 private RootPane root;
	 
	 public NewAlbumPane (RootPane root) {
		 super();
		 this.root = root;
	        // Define graphical elements to let user enter a tag name and a button
	        // to trigger an action that add a new tag to the application.
	        
	        //TextInput 
	        TextField textfield = new TextField();
	        textfield.setPromptText("New Album Name:");
	   
	        //Botão submit
	        Button submit = new Button("Submit");
	        //Adicionar os componentes a uma HBox:
	        HBox hbox = new HBox();
	        hbox.getChildren().add(textfield);
	        hbox.getChildren().add(submit);
	        
	        hbox.setPadding(new Insets(15));
	        
	        //Handle the click of submit:
	        submit.setOnMouseClicked(event ->{
	        	System.out.println("New Album Text Submited! With Name: " + textfield.getText());
	        	try {
					doCreateNewAlbum(textfield.getText());
				} catch (ApplicationException e) {
					e.printStackTrace();
					System.out.println("ERRO: NewAlbumPAne - setOnMouseClick - getText() do textInput");
				}
	        });
	        
	        //adicionar a hbox a this.
	        this.getChildren().add(hbox);
	 }
	 
	 private void doCreateNewAlbum(String albumName) throws ApplicationException {
	        // create a new album according to the conents of the TextField
	    	AlbumDM albumdm = AlbumDM.getInstance();
	    	// persist new album.
	    	Album newAlbum = albumdm.makeAlbumWithName(albumName);
	        // create a new TagView
	    	AlbumView newAV = new AlbumView(this.root, newAlbum); 
	        // place it in the AlbumPane -> AlbumCollection.
	    	this.root.getAlbumPane().getAlbumCollection().getChildren().add(newAV);
	    }
}
