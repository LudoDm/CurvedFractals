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
		controleur = new Controleur();
		stage.setTitle("CurvedFractals");
		stage.setScene(controleur.getScene());
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
