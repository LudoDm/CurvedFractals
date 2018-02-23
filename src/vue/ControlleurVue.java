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
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import prototypes.hey;

public class ControlleurVue {

	@FXML
	private ImageView theImageView;

	private Scene scene;
	private Controleur controleurPrincipal;

	@FXML
	private BorderPane borderpane;

	@FXML
	private StackPane stackpane;

	@FXML
	private VBox sidemenu;

	@FXML
	private Button bFunction;

	@FXML
	private Button bMatrix;

	@FXML
	private Button bColor;

	@FXML
	private Button bZoom;

	@FXML
	private VBox infobox;

	@FXML
	private Label lX;

	@FXML
	private Label lXValue;

	@FXML
	private Label lY;

	@FXML
	private Label lYValue;

	@FXML
	private HBox functionbox;

	@FXML
	private Label lFunction;

	@FXML
	private TextField tFunction;

	@FXML
	private Button bFunctionEnter;

	@FXML
	private HBox zoombox;

	@FXML
	private Label lZoom;

	@FXML
	private TextField tZoom;

	@FXML
	private Button bZoomEnter;

	@FXML
	private HBox matrixbox;

	@FXML
	private TextField tMatrix1;

	@FXML
	private TextField tMatrix2;

	@FXML
	private TextField tMatrix3;

	@FXML
	private TextField tMatrix4;

	@FXML
	private Button bMatrixEnter;

	@FXML
	private HBox colorbox;

	@FXML
	private ColorPicker colpic1;

	@FXML
	private ColorPicker colpic2;

	@FXML
	private Button bColorEnter;

	public ControlleurVue() {
		try {

			// Création du loader.
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/FxmlVue.fxml"));

			// Associer le contrôleur
			fxmlLoader.setController(this);

			// Chargement du fichier FXML
			Parent root = fxmlLoader.load();

			scene = new Scene(root);

			// TODO Ajouter la feuille de style
			// attacher la feuille de style
			scene.getStylesheets().add(getClass().getResource("/vue/NewFile.css").toString());

			// Création de l'application JMonkey
			final JmeToJFXApplication application = makeJmeApplication();

			// Intègre l'application JMonkey avec l'imageView
			JmeToJFXIntegrator.startAndBindMainViewPort(application, theImageView, Thread::new);

			// Cache le side Menu lors du lancement de l'interface
			sidemenu.setVisible(false);
			functionbox.setVisible(false);
			colorbox.setVisible(false);
			matrixbox.setVisible(false);
			zoombox.setVisible(false);

		} catch (IOException ex) {
			System.out.println("Exception lors du chargement des ressources dans controlleur vue");
		}

		System.out.println(theImageView);

	}

	@FXML
	void closeColorBox(ActionEvent event) {
		String s = tFunction.getText();

	}

	@FXML
	void closeFunctionBox(ActionEvent event) {

	}

	@FXML
	void closeMatrixBox(ActionEvent event) {

	}

	@FXML
	void closeZoomBox(ActionEvent event) {

	}

	@FXML
	void gererZoom(ScrollEvent event) {

	}

	@FXML
	void showColorBox(ActionEvent event) {
		if (!colorbox.isVisible()) {
			colorbox.setVisible(true);
		} else {
			colorbox.setVisible(false);
		}

	}

	@FXML
	void showFunctionBox(ActionEvent event) {
		if (!functionbox.isVisible()) {
			functionbox.setVisible(true);
		} else {
			functionbox.setVisible(false);
		}

	}

	@FXML
	void showMatrixBox(ActionEvent event) {
		if (!matrixbox.isVisible()) {
			matrixbox.setVisible(true);
		} else {
			matrixbox.setVisible(false);
		}
	}

	@FXML
	void showSideMenu(MouseEvent event) {
		System.out.println("lol");
		sidemenu.setVisible(true);
	}

	@FXML
	void showZoomBox(ActionEvent event) {
		if (!zoombox.isVisible()) {
			zoombox.setVisible(true);
		} else {
			zoombox.setVisible(false);
		}
	}

	public Scene getScene() {
		return scene;
	}

	public void refreshMaterial(Material mat) {

	}

	public void setControleurPrincipal(Controleur controleurPrincipal) {
		this.controleurPrincipal = controleurPrincipal;
	}

	// TODO Enlever l'annotation @notnull ?
	private static @NotNull JmeToJFXApplication makeJmeApplication() {

		final AppSettings settings = JmeToJFXIntegrator.prepareSettings(new AppSettings(true), 60);
		// TODO Changer l'application jme pour d'autre chose que la classe prototype
		// "hey"...
		final JmeToJFXApplication application = new hey();
		application.setSettings(settings);
		application.setShowSettings(false);
		return application;
	}

}
