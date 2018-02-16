package app;

import controleur.Controleur;
import javafx.application.Application;
import javafx.stage.Stage;

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
	}
	
}

