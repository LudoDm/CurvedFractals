package view;

import java.net.URL;
import java.util.ResourceBundle;

import com.jme3.material.Material;
import com.jme3.system.AppSettings;

import controllers.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.TestApplication;

public class ViewController {

	@FXML
	private ImageView theImageView;

	private Scene scene;
	private Controller controleurPrincipal;

	public ViewController() {

		final ImageView imageView = new ImageView();

		final AppSettings settings = JmeToJFXIntegrator.prepareSettings(new AppSettings(true), 60);
		final JmeToJFXApplication application = new TestApplication();

		JmeToJFXIntegrator.startAndBindMainViewPort(application, imageView, Thread::new);
	}

	public Scene getScene() {
		return scene;
	}

	public void refreshMaterial(Material mat) {
		
	}

	public void setControleurPrincipal(Controller controleurPrincipal) {
		this.controleurPrincipal = controleurPrincipal;
	}

	@FXML
	void actionView(ActionEvent event) {
	}

}
