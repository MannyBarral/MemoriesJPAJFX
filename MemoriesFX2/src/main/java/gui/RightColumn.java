package gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import persist.ApplicationException;

/**
 * This class represent the right column of the GUI. It contains the tag pane
 * that shows the collection of tags.
 *
 * @author tl
 */
public class RightColumn extends VBox {

    private TagsPane tagsPane;
    private AlbumsPane albumPane;
    private NewTagPane newtagpane;
    private NewAlbumPane newalbumpane;
    private RootPane root;
    private Label label;
    private Button tagsButton;
    private Button albumsButton;

    public RightColumn(RootPane root) throws ApplicationException {
        super();
        // create a label
        Label label = new Label ("Right Pane");
        this.label = label;
        // create a TagsPane
        this.tagsPane = new TagsPane(root);
        // create a "NewTagsPane"
        NewTagPane newtagpane = new NewTagPane(root);
        this.newtagpane = newtagpane;
        //Create a new AlbumsPane:
        this.albumPane = new AlbumsPane(root);
        //create a new NewAlbumPane:
        this.newalbumpane = new NewAlbumPane(root);
        
        //Buttons para fazer o Toggle entre Tags e Albums:
        Button tagsButton = new Button("Tags");
        this.tagsButton = tagsButton;
        Button albumsButton = new Button("Albums");
        this.albumsButton = albumsButton;
        //Ações:
        tagsButton.setOnAction(e -> showTagsPane());
        albumsButton.setOnAction(e -> showAlbumsPane());
        
        // add the graphical elements to the interface. (default: show tags e newTag)
        this.getChildren().add(label);
        this.getChildren().add(tagsButton);
        this.getChildren().add(albumsButton);
        this.getChildren().add(this.tagsPane);
        this.getChildren().add(newtagpane);
//        this.getChildren().add(label);
//        this.getChildren().add(this.tagsPane);
//        this.getChildren().add(newtagpane);
        
        
    }

    public TagsPane getTagPane() {
        return this.tagsPane;
    }
    
    public AlbumsPane getAlbumPane() {
    	return this.albumPane;
    }
    
    public void showAlbumsPane () {
    	this.getChildren().clear();
    	this.getChildren().add(this.label);
        this.getChildren().add(this.tagsButton);
        this.getChildren().add(this.albumsButton);
    	this.getChildren().add(this.albumPane);
    	this.getChildren().add(this.newalbumpane);

    }
    
    public void showTagsPane () {
    	this.getChildren().clear();
    	this.getChildren().add(this.label);
        this.getChildren().add(this.tagsButton);
        this.getChildren().add(this.albumsButton);
    	this.getChildren().add(this.tagsPane);
    	this.getChildren().add(this.newtagpane);
    }
}