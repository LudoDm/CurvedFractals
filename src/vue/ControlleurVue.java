package vue;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jetbrains.annotations.NotNull;

import com.jme3.material.Material;
import com.jme3.system.AppSettings;
import com.jme3x.jfx.injfx.JmeToJFXApplication;
import com.jme3x.jfx.injfx.JmeToJFXIntegrator;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import prototypes.hey;

public class ControlleurVue {

	@FXML
	private ImageView theImageView;

	private Scene scene;
	private Controleur controleurPrincipal;

	public ControlleurVue() {
		try {
			// Création du loader.
			FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/vue/FxmlVue.fxml"));

			// Associer le contrôleur
			fxmlLoader.setController(this);

			// Chargement du fichier FXML
			Parent root = fxmlLoader.load();

			scene = new Scene(root);

			// TODO Ajouter la feuille de style
			// attacher la feuille de style
			// scene.getStylesheets().add(getClass().getResource("/style/Pourboire.css").toString());
			
			
			 // Création de l'application JMonkey
	        final JmeToJFXApplication application = makeJmeApplication();

	        // Intègre l'application JMonkey avec l'imageView
	        JmeToJFXIntegrator.startAndBindMainViewPort(application, theImageView, Thread::new);

		} catch (IOException ex) {
			System.out.println("Exception lors du chargement des ressources dans controlleur vue");
		}

		System.out.println(theImageView);

	}

	public Scene getScene() {
		return scene;
	}

	public void refreshMaterial(Material mat) {

	}

	public void setControleurPrincipal(Controleur controleurPrincipal) {
		this.controleurPrincipal = controleurPrincipal;
	}

	
	//TODO Enlever l'annotation @notnull ? 
	private static @NotNull JmeToJFXApplication makeJmeApplication() {
		
		final AppSettings settings = JmeToJFXIntegrator.prepareSettings(new AppSettings(true), 60);
		//TODO Changer l'application jme pour d'autre chose que la classe prototype "hey"...
		final JmeToJFXApplication application = new hey();
		application.setSettings(settings);
		application.setShowSettings(false);
		return application;
	}

}
