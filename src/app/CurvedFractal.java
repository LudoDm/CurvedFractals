package app;

import controleur.Controleur;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CurvedFractal extends Application {

	private Controleur controleur;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		//avec ca, la translation est fluide... on ne prend probablement pas les coordonnées de la souris en fonction de la bonne affaire (genre p/r a image view vs Stage))
//		controleur = new Controleur(stage.getWidth(), stage.getHeight());
		controleur = new Controleur(1920.0d, 1080.0d);
		stage.setTitle("CurvedFractals");
		stage.setScene(controleur.getScene());
		stage.sizeToScene();
		stage.setFullScreen(false);
		stage.show();
		stage.setMaximized(true);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		       @Override
		       public void handle(WindowEvent e) {
		          Platform.exit();
		          System.exit(0);
		       }
		    });
	}

}
