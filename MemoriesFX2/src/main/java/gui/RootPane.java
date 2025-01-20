package gui;

import javax.persistence.EntityManager;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.Main;
import persist.ApplicationException;
import persist.PictureDM;

/**
 * This class corresponds to the main layout i.e. the root of graphical
 * components hierarchy.
 *
 * @author tl
 * @coAuthor Manuel Barral fc56943
 */
public class RootPane extends VBox {

    private LeftColumn leftColumn;
    private RightColumn rightColumn;
    private PicturesPane picturesPane;
    private HBox middleRow;
    private Node topLabel;

    public RootPane() throws ApplicationException {
        super();
        // create a label (topLabel)
        this.topLabel = topLabel();
        this.middleRow = middleRow();
        // The 2 following lines control how the height of the VBox layout grow
        // (or shrink) when the size of the window changes.
        VBox.setVgrow(topLabel, Priority.NEVER);
        VBox.setVgrow(middleRow, Priority.ALWAYS);
        
        System.out.println("Children of rootPane: " + this.getChildren());
    }

    private HBox middleRow() throws ApplicationException{
    	EntityManager em = Main.emf.createEntityManager();
    	PictureDM picturedm = PictureDM.getInstance();
    	System.out.println("EMF no rootPane: " + Main.emf.getProperties());
        HBox box = new HBox();
        this.picturesPane = new PicturesPane(this,
                                             picturedm.getRecentPictures(em), 	/* obtain the list of most recent picture from the DB */
                                             3);
        System.out.println("RECENT PICTURES:" + picturedm.getRecentPictures(em));
        em.close();
        System.out.println("PicturesPane Created: {" + this.picturesPane.toString() + "}");
        // create left column object
        this.leftColumn = new LeftColumn(this);
        // create right column object
        this.rightColumn = new RightColumn(this);
        // You may change the following lines:
        HBox.setHgrow(leftColumn, Priority.SOMETIMES);
        HBox.setHgrow(picturesPane, Priority.ALWAYS);
        HBox.setHgrow(rightColumn, Priority.SOMETIMES);
        //leftColumn.maxWidth(350);
        // add graphical elements to box.
//        VBox searchBox = new VBox();
//        searchBox.getChildren().add(this.leftColumn);
//        
//        VBox picturesBox = new VBox();
//        picturesBox.getChildren().add(picturesPane);
//        
//        VBox tagsBox = new VBox();
//        tagsBox.getChildren().add(this.rightColumn);
//        
//        box.getChildren().add(this.topLabel);
//        box.getChildren().add(searchBox);
//        box.getChildren().add(picturesBox);
//        box.getChildren().add(tagsBox);
        box.getChildren().add(this.leftColumn);
        box.getChildren().add(this.picturesPane);
        box.getChildren().add(this.rightColumn);
        //adicionar ao root a HBox - MiddleRow.
        this.getChildren().add(box);
        return box;
    }

    private Node topLabel() {
    	
        this.topLabel = new Label("TopLabel");
        this.getChildren().add(this.topLabel);
        return this.topLabel;
    }

    public TagsPane getTagPane() {
        return this.rightColumn.getTagPane();

    }
    
    public AlbumsPane getAlbumPane() {
    	return this.rightColumn.getAlbumPane();
    }

    public PicturesPane getPicturesPane() {
        return this.picturesPane;
    }
    
    public void setPicturesPane(PicturesPane pp) {
    	this.picturesPane = pp;
    }
    
    public LeftColumn getLeftColumn () {
    	return this.leftColumn;
    }

}
