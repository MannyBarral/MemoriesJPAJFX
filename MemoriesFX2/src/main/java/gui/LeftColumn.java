package gui;

import javafx.scene.layout.VBox;

/**
 * This class displays the left column of the GUI. It contains the search pane
 * for searching the picture database using tags and the album pane that shows
 * the list of albums and allows the creation, edition and display of albums.
 *
 * @author tl
 */
public class LeftColumn extends VBox {
	
	private SearchPane searchPane;

    public LeftColumn(RootPane root) {
        // create a SearchPane
    	SearchPane searchpane = new SearchPane(root);
    	this.searchPane = searchpane;
        // add to interface
    	this.getChildren().add(searchpane);
    }

    public SearchPane getSearchPane() {
        return this.searchPane;
    }
}
