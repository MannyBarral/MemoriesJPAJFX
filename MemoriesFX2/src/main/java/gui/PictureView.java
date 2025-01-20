package gui;

import domain.Picture;
import domain.Tag;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import main.ApplicationSettings;
import persist.PictureDM;
import utils.Thumbnails;

/**
 * This class is responsible for displaying a pane that shows a picture and the
 * set of associated tags.
 *
 * @author tl
 */
public class PictureView extends HBox {

    private domain.Picture pic;
    // JavaFX for picture
    private ImageView iv;
    // A layout that for displaying a frame around the picture. It is used as a
    // visual feedback when the picture is selected. (color change).
    private HBox frame;
    // The image object (JavaFX API)
    private Image im;
    // private Label info;
    // A layout for the set of tags.
    private FlowPane tags;
    private RootPane root;

    // The background for PictureView
    private static Background PVBackground = new Background(
    		new BackgroundFill(Color.BLUE, null, null));      		//DONE?
    private static Background frameBackground = new Background(
    		new BackgroundFill(Color.DARKBLUE, null, null));    	//DONE?
    private static Background selectedBackground = new Background(
    		new BackgroundFill(Color.CYAN, null, null)); 			//DONE?

    private PictureView(RootPane root) {
        super();
        this.root = root;
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setBackground(PVBackground);
        this.setMaxWidth(700);
        this.im = null;
        this.frame = new HBox();
        this.frame.setPadding(new Insets (10));
        this.frame.setBackground(PVBackground);
        this.iv = new ImageView();
        this.iv.setFitWidth(220);
        this.iv.setPreserveRatio(true);
        iv.setOnMouseClicked(event -> {
            System.out.println("Image " + iv + " clicked!");
            toggleSelection();
           
        });
        // add ImageView to frame
        this.frame.getChildren().add(this.iv);
        // create layout with TagViews
        this.tags = new FlowPane();
        // add frame and tags to 'this'.
        this.getChildren().add(frame);
        this.getChildren().add(this.tags);
        HBox.setHgrow(frame, Priority.NEVER);
        // for layout that contains TagViews:
        HBox.setHgrow(this.tags, Priority.ALWAYS);
    }

    /**
     * This constructor is used in the pagination process.
     *
     * @param root
     * @param m
     */
    PictureView(RootPane root, domain.Picture m) {
        this(root);
        setImage(m);
    }

    private static int getPVHeight() {
        return ApplicationSettings.ThumbnailHeight + 4;
    }

    private void toggleSelection() {
        // Attention !!!!!!!!!!!!!!
        // the instances of PictureView are created during the pagination
        // process (see method createPage in class PicturesPane). It not a good
        // option to associate the selected/not_selected information to an
        // atribute of this class. To solve this problem we add a 'selected'
        // attribute (boolean) to the class domain.Picture. This has the
        // advantage that pictures remain selected when the user go forward
        // or backwards in the sequence of pages.
        // -> create a method isSelected in domain.Picture.
        if (pic.isSelected()) {
            unselect();
        } else {
            select();
        }
    }

    public void select() {
    	System.out.println("Selecting Picture: {" + this.pic.toString() + "}");
        // change the background of the image.
    	this.frame.setBackground(selectedBackground);
        // do whatever is needed.
    	PictureDM picdm = PictureDM.getInstance();
        this.pic.select();
        picdm.update(this.pic);
    }

    public void unselect() {
        // change the background of the image.
    	System.out.println("Un-Selecting Picture: {" + this.pic.toString() + "}");
    	this.frame.setBackground(PVBackground);
        // do whatever is needed.
    	PictureDM picdm = PictureDM.getInstance();
        this.pic.unSelect();
        picdm.update(this.pic);
    }

    @Override
    public String toString() {
        return "PictureView" + this.hashCode() + " [pic=" + pic + ", iv="
               + iv + ", im=" + im + "]";
    }

    private void setImage(domain.Picture im) {
        this.pic = im;
        if (pic.isSelected()) {
            // change background of frame
        	this.frame.setBackground(selectedBackground);
        } else {
            // change background of frame
        	this.frame.setBackground(PVBackground);
        }
        
        this.im = new Image("file:" + Thumbnails.thumbnailFilename(this.pic));
        this.iv.setImage(this.im);
        for (Tag t : im.getTags()) {
            // create a new TagView for tag t
        	TagView newTag = new TagView (this.root, t, GuiElement.PICTUREVIEW);
            // add to the interface
        	this.tags.getChildren().add(newTag);
        }
        
    }

    // Note: the signature of following methods may be modified.
    public void addTag(TagView tagView) {
        // Add corresponding tag to picture
    	Tag t = tagView.getTag();
    	PictureDM picturedm = PictureDM.getInstance();
    	Picture p = this.pic;
    	p.addTag(t);
    	picturedm.update(p);
        // create a new TagView com o construtor para criar copias
    	TagView tvCopy = new TagView(tagView, GuiElement.PICTUREVIEW); 
    	//TODO: ainda n percebi cm este switch case vai funcionar!
        // add to interface
    	this.tags.getChildren().add(tvCopy);
    	
    }

    public void removeTag(TagView tagView) {
        // remove tag from picture
    	Tag t = tagView.getTag();
    	PictureDM picturedm = PictureDM.getInstance();
    	Picture p = this.pic;
    	p.removeTag(t);
    	picturedm.update(p);
        // remove Tagview from interface.
    	this.tags.getChildren().remove(tagView);
    	System.out.print("Removed Tag: " + tagView.getTag().getName() + " from Picture: " + this.pic.getFilePath());
    }
    
    public boolean isSelected() {
    	return this.pic.isSelected();
    }
    
    public Picture getPicture() {
    	return this.pic;
    }

}
