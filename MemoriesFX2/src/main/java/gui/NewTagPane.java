package gui;

import domain.Tag;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import persist.ApplicationException;
import persist.TagDM;

/**
 * This class displays a form to allows the creation of new tags.
 *
 * @author tl
 */
public class NewTagPane extends VBox {

    private RootPane root;

    public NewTagPane(RootPane root) throws ApplicationException{
        super();
        this.root = root;
        // Define graphical elements to let user enter a tag name and a button
        // to trigger an action that add a new tag to the application.
        
        //TextInput 
        TextField textfield = new TextField();
        textfield.setPromptText("New Tag Name:");
   
        //Botão submit
        Button submit = new Button("Submit");
        //Adicionar os componentes a uma HBox:
        HBox hbox = new HBox();
        hbox.getChildren().add(textfield);
        hbox.getChildren().add(submit);
        
        hbox.setPadding(new Insets(15));
        
        //Handle the click of submit:
        submit.setOnMouseClicked(event ->{
        	System.out.println("New Tag Text Submited! With Name: " + textfield.getText());
        	try {
				doCreateNewTag(textfield.getText());
			} catch (ApplicationException e) {
				e.printStackTrace();
				System.out.println("ERRO: NewTagPane - setOnMouseClick - getText() do textInput");
			}
        });
        
        //adicionar a hbox a this.
        this.getChildren().add(hbox);
    }

    private void doCreateNewTag(String tagName) throws ApplicationException {
        // create a new tag according to the conents of the TextField
    	TagDM tagdm = TagDM.getInstance();
    	// persist new tag.
    	Tag newtag = tagdm.makeTag(tagName, null);
        // create a new TagView
    	TagView newTV = new TagView(this.root, newtag, GuiElement.TAGPANE); //N sei se aqui é TAGPANE!
        // place it in the TagPane -> TagCollection.
    	this.root.getTagPane().getTagCollection().getChildren().add(newTV);
    }

}
