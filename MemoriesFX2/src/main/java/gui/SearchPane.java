package gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import domain.Picture;
import domain.Tag;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import persist.ApplicationException;
import persist.PictureDM;

public class SearchPane extends VBox {

    private FlowPane selectedTags;
    private RootPane root;
    private Set<Tag> tags = new HashSet();
    private static PictureDM picturedm = PictureDM.getInstance();

    SearchPane(RootPane root) {
        super();
        this.root = root;
        // create a Label
        Label label = new Label("Search");
        // create a layout that will contain TagViews for a query
        FlowPane layoutTagsQuery = new FlowPane(10,10);
        this.selectedTags = layoutTagsQuery;
        // create a layout that will contain 3 buttons
        FlowPane layoutButtonsQuery = new FlowPane(10,10);
        // create 3 buttons with respective actions that call methods defined
        // in this class (see below).
        Button button1 = new Button("Reset");
        Button button2 = new Button("Search All");
        Button button3 = new Button("Search Any");
        
        button1.setOnMouseClicked(event ->{
        	try {
				doReset();
			} catch (ApplicationException e) {
				System.out.println("ERRO: no Reset -> SearchPane, Button1");
				e.printStackTrace();
			}
        });
        
        button2.setOnMouseClicked(event ->{
        	try {
				doSearchAll();
			} catch (ApplicationException e) {
				System.out.println("ERRO: no Search All -> SearchPane, Button2");
				e.printStackTrace();
			}
        });
        
        button3.setOnMouseClicked(event ->{
        	try {
				doSearchAny();
			} catch (ApplicationException e) {
				System.out.println("ERRO: no Search Any -> SearchPane, Button3");
				e.printStackTrace();
			}
        });
        // add components to layout.
        layoutButtonsQuery.getChildren().add(button1);
        layoutButtonsQuery.getChildren().add(button2);
        layoutButtonsQuery.getChildren().add(button3);
        
        this.getChildren().add(label);
        this.getChildren().add(layoutButtonsQuery);
        this.getChildren().add(this.selectedTags);

    }

    private void doReset() throws ApplicationException {
        // remove TagViews from the query layout.
    	this.selectedTags.getChildren().clear();
        // add recent pictures to PicturesPane.
    		//Clear pictures do PicturePane:
    	this.root.getPicturesPane().getChildren().clear();
    		//add recentPictures:
    	EntityManager em = main.Main.emf.createEntityManager();
    	try {
            Collection<Picture> recentPictures = picturedm.getRecentPictures(em);

            // Set the recent pictures to the PicturesPane
            this.root.getPicturesPane().setPictures(recentPictures);
        } finally {
            em.close(); // Fechar EntityManager!!!
        }
    	
    	
    }

    private void doSearchAll() throws ApplicationException {
    	System.out.println("Tags Selecionadas no SearchPane para o Search All: " + this.tags);
        // get the set of pictures from the persistence layer
    	EntityManager em = main.Main.emf.createEntityManager();
    	Collection<Picture> picturesDB = picturedm.findByTags(this.tags);
    	System.out.println("Pictures Recebidas no SearchAll: " + picturesDB);
        // add search result to PicturePane.
    	this.root.getPicturesPane().setPictures(picturesDB);

    }

    private void doSearchAny() throws ApplicationException{
    	System.out.println("Tags Selecionadas no SearchPane para o Search Any: " + this.tags);
        // get the set of pictures from the persistence layer
    	EntityManager em = main.Main.emf.createEntityManager();
    	Collection<Picture> picturesDB = picturedm.findByAnyTags(tags);
        // add search result to PicturePane.
    	this.root.getPicturesPane().setPictures(picturesDB);
    }

    public void addTagToSearch(TagView t) {
        // add the TagView to the query layout.
    	System.out.println("Adding TagView: " + t.getTag() + " to SearchPane's Selected Tags");
    	this.selectedTags.getChildren().add(t);
    	this.tags.add(t.getTag());
    	
    }

    public void removeTagFromSearch(TagView t) {
        // add the TagView to the query layout.
    	this.selectedTags.getChildren().remove(t);
    	this.tags.remove(t.getTag());
    }
}
