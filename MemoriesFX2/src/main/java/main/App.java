package main;

import java.io.File;

import javax.persistence.Persistence;

import handlers.PictureHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persist.ApplicationException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("MemoriesFX");
        gui.RootPane main;
		try {
			main = new gui.RootPane();
			
			Scene scene = new Scene(main);
	        stage.setScene(scene);
	        stage.show();
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private static void initializeDBMaybe() throws ApplicationException{
        // We will use a Dervy database. If the data folder does not exist, the
        // database will be initialized. If the folder exists do nothing, the
        // application will use the existing database.
    	
    	if (! new File("fotosManaged").exists()) {
    		PictureHandler.importPictures();
    	}else {
    		System.out.println("BD já Inicializada!");
    	}
    	
    }

    public static void main(String[] args) throws ApplicationException {
        try {
        	System.out.println("Starting App!");
        	if (args.length > 0 && args[0].equals("initdb")){
                 initializeDBMaybe();

        	}else {

        		Main.emf = Persistence.createEntityManagerFactory("JPAMemories");
            	System.out.println("EMF: " + Main.emf.getProperties());

        	}
            
        }finally{
        	launch();
        	System.out.println("App Launched!");
        }
        
    }

}