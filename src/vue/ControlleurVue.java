package vue;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.jme3.material.Material;
import com.jme3.system.AppSettings;
import com.jme3x.jfx.injfx.JmeToJFXApplication;
import com.jme3x.jfx.injfx.JmeToJFXIntegrator;

import controleur.Controleur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import javafx.scene.paint.Color;
import prototypes.hey;

public class ControlleurVue {

	private Scene scene;
	private Controleur controleurPrincipal;
	private String m11, m12, m21, m22, function, zoomVal;
	private ObservableSet<Node> visibleSet;

	private Color c1, c2;

	@FXML
	private ImageView theImageView;

	@FXML
	private BorderPane borderpane;

	@FXML
	private StackPane stackpane;

	@FXML
	private VBox sidemenu, infobox;

	@FXML
	private Button bFunction, bMatrix, bColor, bZoom, bFunctionEnter, bZoomEnter, bMatrixEnter, bColorEnter;

	@FXML
	private Label lX, lXValue, lY, lYValue, lFunction, lZoom;

	@FXML
	private HBox functionbox, zoombox, matrixbox, colorbox;

	@FXML
	private TextField tFunction, tZoom, tMatrix1, tMatrix2, tMatrix3, tMatrix4;
	@FXML
	private ColorPicker colpic1, colpic2;

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
			scene.getStylesheets().add(getClass().getResource("/vue/curved_fractals.css").toString());

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

			visibleSet = FXCollections.observableSet();

		} catch (IOException ex) {
			System.out.println("Exception lors du chargement des ressources dans controlleur vue");
		}

		System.out.println(theImageView);

	}

	// TODO - Changer le type de retour des méthodes "closeBox"

	@FXML
	void closeColorBox(ActionEvent event) {

		c1 = colpic1.getValue();
		c2 = colpic2.getValue();

		System.out.println(c1.toString() + " " + c2.toString());

		colorbox.setVisible(false);
		visibleSet.remove(colorbox);
		bColor.setStyle("-fx-background-radius: 15");
	}

	@FXML
	void closeFunctionBox(ActionEvent event) {

		function = tFunction.getText();

		System.out.println(function);

		functionbox.setVisible(false);
		visibleSet.remove(functionbox);
		bFunction.setStyle("-fx-background-radius: 15");

	}

	@FXML
	void closeMatrixBox(ActionEvent event) {

		List<String> matrix = new ArrayList<String>();

		try {
			m11 = (tMatrix1.getText());
			m12 = (tMatrix2.getText());
			m21 = (tMatrix3.getText());
			m22 = (tMatrix4.getText());

			matrix.add(m11);
			matrix.add(m12);
			matrix.add(m21);
			matrix.add(m22);

		} catch (Exception e) {

		}

		System.out.println(matrix);

		matrixbox.setVisible(false);
		visibleSet.remove(matrixbox);
		bMatrix.setStyle("-fx-background-radius: 15");

	}

	@FXML
	void closeZoomBox(ActionEvent event) {

		zoomVal = tZoom.getText();

		System.out.println(zoomVal);

		zoombox.setVisible(false);
		visibleSet.remove(zoombox);
		bZoom.setStyle("-fx-background-radius: 15");

	}

	void closeSideMenu() {

		sidemenu.setVisible(false);
		visibleSet.remove(sidemenu);

	}

	@FXML
	void gererZoom(ScrollEvent event) {

	}

	@FXML
	void showColorBox(ActionEvent event) {
		if (!colorbox.isVisible()) {
			colorbox.setVisible(true);
			visibleSet.add(colorbox);
			bColor.setStyle("-fx-background-radius: 0 50 30 0;");

		} else {
			closeColorBox(event);
		}

	}

	@FXML
	void showFunctionBox(ActionEvent event) {
		if (!functionbox.isVisible()) {
			functionbox.setVisible(true);
			visibleSet.add(functionbox);
			bFunction.setStyle("-fx-background-radius: 0 50 30 0;");

		} else {
			closeFunctionBox(event);
		}

	}

	@FXML
	void showMatrixBox(ActionEvent event) {
		if (!matrixbox.isVisible()) {
			matrixbox.setVisible(true);
			visibleSet.add(matrixbox);
			bMatrix.setStyle("-fx-background-radius: 0 50 30 0;");

		} else {
			closeMatrixBox(event);
		}
	}

	@FXML
	void showSideMenu(MouseEvent event) {

		System.out.println(visibleSet.toString());

		if (!sidemenu.isVisible()) {
			sidemenu.setVisible(true);
			visibleSet.add(sidemenu);

			for (Node n : visibleSet) {
				n.setVisible(true);
			}

		} else {
			closeSideMenu();
			for (Node n : visibleSet) {
				n.setVisible(false);
			}
		}

	}

	@FXML
	void showZoomBox(ActionEvent event) {
		if (!zoombox.isVisible()) {
			zoombox.setVisible(true);
			visibleSet.add(zoombox);
			bZoom.setStyle("-fx-background-radius: 0 50 30 0;");

		} else {
			closeZoomBox(event);
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
