package gui;

import domain.Tag;
import persist.TagDM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;


/**
 * This class contains the tag collection. It appears on the right side of the
 * interface.
 *
 * @author tl
 */
public class TagsPane extends VBox {

    // a layout to contain TagViews. You may choose any kind of layout.
    private final FlowPane tagCollection;
    private boolean actionSearch;
    private boolean actionTagging;

    private Set<Tag> tags;
    
    private RootPane root;    

    public TagsPane(RootPane root) {
        super();
        this.root = root;
        // You may choose a different laypout.
        tagCollection = new FlowPane();
        tagCollection.setVgap(5);
        tagCollection.setHgap(5);
        // Get tags from DB
        TagDM tagdm = TagDM.getInstance();
        Set<Tag> tagsBD = tagdm.findAllTags();
        this.tags = tagsBD;
        
        System.out.println("TAGS DA BD (no TagsPane.java): " + tagsBD);
        
        List<TagView> tagViewList = new ArrayList<>();
        
        if (tagsBD == null || tagsBD.isEmpty()) {
        	System.out.println("No Tags Found in DB!");
        }else {
        	
            // create TagViews for each tag.
            for (Tag t: tagsBD) {
            	if (t != null) {
            		TagView tagview = new TagView(root, t, GuiElement.TAGPANE);
                	System.out.println("A Criar TagView cm a Tag: " +"{" + t.toString() + "}");
                	tagViewList.add(tagview);
            	}else {
            		System.out.println("tag é null: " + t.toString());
            	}          	
        	
            }     
        }
        
        // ---
        // The following line will sort the TagViews. In order to be easier to
        // find tags it better o sort then after some criteria. A good option
        // is to sort alphabetically. It will work if the TagView class
        // implements the Comparable interface (see TagView class). When the
        // code in the TagView class is completed, you can uncomment the
        // following line. The tags should appear sorted.
        Collections.sort(tagViewList);
        // ---
        // add the list of TagViews to the interface.
        for (TagView tv : tagViewList) {
        	this.tagCollection.getChildren().add(tv);
        	
        }
        actionSearch = true;
        this.getChildren().add(this.tagCollection);
    }
    
    //retornar de tdas as tags no TagPane todas aquelas que estão Selecionadas.
    public ArrayList<Tag> getSelectedTags (){
    	ArrayList<Tag> selectedTags = new ArrayList<>();
    	for (Tag t : this.tags) {
    		if (t.isSelected() == 1) {
    			selectedTags.add(t);
    		}
    	}
    	return selectedTags;
    }
    
    public FlowPane getTagCollection () {
    	return this.tagCollection;
    }

}
