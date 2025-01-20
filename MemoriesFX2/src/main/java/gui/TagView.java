package gui;

import javafx.geometry.Insets;

import java.util.ArrayList;
import java.util.Set;

import domain.Picture;
import domain.Tag;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import persist.ApplicationException;
import persist.TagDM;

/**
 * This class represents a tag in the GUI
 *
 * @author tl
 */
public class TagView extends Label implements Comparable<TagView> {

    private Tag tag;
    private RootPane root;
    private GuiElement belongsTo;
    
    private static Background TagBackground = new Background(
    		new BackgroundFill(Color.LIGHTGREY, null, null));    	
    private static Background selectedBackground = new Background(
    		new BackgroundFill(Color.CYAN, null, null)); 			

    /**
     * Main constructor
     *
     * @param root
     * @param t
     * @param belongsTo
     */
    public TagView(RootPane root, Tag t, GuiElement belongsTo) {
    	super(t.getName());
    	this.tag = t;
    	this.root = root;
    	this.belongsTo = belongsTo;
    	if (tag == null) {
            System.out.println("Tag is null during TagView construction!");
        }
    	// Define the padding
		this.setPadding(new Insets(10, 10, 10, 10));
		//Define the Background
		this.setBackground(TagBackground);
		
    	if (t!= null) {
	        this.setOnMouseClicked(event -> {
	            System.out.println("TagView " + this.tag.getName() + " clicked! With GuiElement: " + this.belongsTo);
	            //Select da Tag:
	            selectTag();
	            try {
	                executeAction();
	            } catch (ApplicationException ex) {
	                // it may not be necessary to catch an exception here
	                // anyways you know how it should be done.
	                System.err.println(ex.getMessage());
	            }
	        });
	    	} 
    }

    /**
     * This constructor is used to obtain a copy of a TagView. This constructor
     * is useful when we want an existing TagView to appear somewhere else in
     * the interface.
     *
     * @param t
     * @param belongsTo
     */
    TagView(TagView t, GuiElement belongsTo) {
        this(t.root, t.getTag(), belongsTo);
    }

    @Override
    public int compareTo(TagView t) {
        // change the following line such that the ordering is alphabetical
        // (using tag's name)
    	if (this.tag != null) {
    		
        	String thisTag = this.tag.getName();
        	
        	String otherTag = t.tag.getName();
        	
        	System.out.println("A comparar thisTag no compareTo: {" + thisTag +
    				"} à otherTag: {" + otherTag + "}");
        	
            return thisTag.compareTo(otherTag);
    		
    	}else {
    		System.out.println("this.tag é null (no TagView.java - compareTo()" + "Objeto TagView: " + ""
    				+ "{" + this.toString() + "}");
    		if (t.tag == null) {
    			System.out.println("otherTag tambem é null (no TagView.java - compareTo()" + "Objeto TagView: " + ""
        				+ "{" + this.toString() + "}");
    		}
    		
    		return 0;
    	}
    	
    }

    @Override
    public String toString() {
        return "TagView[tag=" + tag + "]";
    }

    /**
     * This method is executed when a TagView is clicked. The belongsTo
     * attribute correspond the place of the TagView. It may belong to: 1) the
     * SearchPane 2) a PictureView 3) the TagPane. TODO: define a value in enum
     * GuiElement for each case.
     *
     * @throws ApplicationException
     */
    private void executeAction() throws ApplicationException {
        switch (belongsTo) {
            // case 1) the action must remove the tag from the search query.
	        case SEARCHPANE:{
	        	doRemoveTagFromSearch(); //vamos remover este (this) tagView do rootPane.
	        	
	        	break;
	        }
	            // case 2) the action must remove the tag from the picture. 
	        	// If the tag is in the Picture Pane.
	        case PICTUREVIEW:{
	        		//this.root.getPicturesPane().removeTagFromPictures(this);
	        	doRemoveTagFromPictures();
	         	      	
	        	break;
	        }
	            // case 3) 2 possibilities:
	            //         - 1 or more pictures are selected -> add tag to pictures
	            //         - else add tag to search query
	        case TAGPANE:{
	        	
	        	//ArrayList<Tag> Tags = this.root.getTagPane().getSelectedTags();
	        	int numberSelectedPvs = 0;
	        	for (PictureView pv : this.root.getPicturesPane().getPictureViews()) {
	        		if (pv.isSelected()) {
	        			numberSelectedPvs +=1;
	        		}
	        	}
	        	
	        	if (numberSelectedPvs >0) {
	        		doAddTagToPictures();
	        	}else {
	        		//Adicionar a tag ao SearchPane.
	        		doAddTagToSearch();
	        	}
	        	
	        	break;
	        }
        }
    }

    private void doRemoveTagFromPictures() throws ApplicationException {
        this.root.getPicturesPane().removeTagFromPictures(this); //aqui removemos a cópia.
    }

    private void doAddTagToPictures() {
        // create a new TagView that represent the same tag as 'this'.
    	TagView tagViewCopy = new TagView(this, GuiElement.PICTUREVIEW);
        // add the TagView to selected pictures in TagPane (PicturePane?).
    	this.root.getPicturesPane().addTagToSelectedPictures(tagViewCopy);
    }
    
    private void doAddTagToSearch() {
        // create a new TagView that represent the same tag as 'this'.
    	TagView tagViewCopy = new TagView(this, GuiElement.SEARCHPANE);
        // add the TagView to search in SearchPane.
    	this.root.getLeftColumn().getSearchPane().addTagToSearch(tagViewCopy);
    }
    
    private void doRemoveTagFromSearch() {
        this.root.getLeftColumn().getSearchPane().removeTagFromSearch(this); //aqui removemos a cópia.

    }
    
    

    public Tag getTag() {
        return this.tag;
    }

    @Override
    public int hashCode() {
    	if (this.tag != null) {
    	    return this.tag.hashCode();
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
    
    public void selectTag() {
    	if (this.tag != null) {
    		TagDM tagdm = TagDM.getInstance();
        	if (this.tag.isSelected() == 0) {
            	this.tag.selectTag();
            	//mudar o Background para selectedBackground:
            	this.setBackground(selectedBackground);
            }else { //unselect da tag
            	this.tag.unselectTag();
            	//mudar o Background para tagBackground:
            	this.setBackground(TagBackground);

            }
        	//update da Tag na BD:
        	tagdm.update(this.tag);
        	//System.out.println("Tag: {" + this.tag.toString() + "} selected!");
    	}else {
    		System.out.println("Erro no SelectTag -> Tag no TagView é null!");
    	}
    	
    }

}
