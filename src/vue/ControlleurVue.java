package vue;

import java.net.URL;
import java.util.ResourceBundle;

import com.jme3.material.Material;
import com.jme3.system.AppSettings;
import com.jme3x.jfx.injfx.JmeToJFXApplication;
import com.jme3x.jfx.injfx.JmeToJFXIntegrator;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class ControlleurVue {

	@FXML
	private ImageView theImageView;

	private Scene scene;
	private Controleur controleurPrincipal;

	public ControlleurVue() {

	}

	public Scene getScene() {
		return scene;
	}

	public void refreshMaterial(Material mat) {

	}

	public void setControleurPrincipal(Controleur controleurPrincipal) {
		this.controleurPrincipal = controleurPrincipal;
	}

	@FXML
	void actionView(ActionEvent event) {
	}

}
