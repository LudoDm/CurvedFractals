package vue;

import java.awt.MouseInfo;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.jme3.material.Material;
import com.jme3.math.Matrix4f;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.system.AppSettings;
import com.jme3x.jfx.injfx.JmeToJFXApplication;
import com.jme3x.jfx.injfx.JmeToJFXIntegrator;
import com.sun.javafx.geom.Path2D;

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
import modele.MaterialHandler;
import prototypes.hey;

public class ControlleurVue {

	private Scene scene;
	private Controleur controleurPrincipal;
	private String m11, m12, m21, m22, function, zoomVal;
	private ObservableSet<Node> visibleSet;
	private JMonkeyApp application;

	private Color c1, c2;

	private Vector4f vec1, vec2;
	private MaterialHandler matHandler;
	private Transform zoomTrans = new Transform();
	private float xInitLocation, yInitLocation;
	private Vector3f vecTranslation;

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
	private Label lX, lXValue, lY, lYValue, lFunction, lZoom, lArrow;

	@FXML
	private HBox functionbox, zoombox, matrixbox, colorbox;

	@FXML
	private TextField tFunction, tZoom, tMatrix1, tMatrix2, tMatrix3, tMatrix4;
	@FXML
	private ColorPicker colpic1, colpic2;


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

			scene.getStylesheets().add(getClass().getResource("/vue/curved_fractals.css").toString());

			// Création du material handler
			initializeMaterialHandler();

			// Création de l'application JMonkey
			application = makeJmeApplication();

			// Intègre l'application JMonkey avec l'imageView
			JmeToJFXIntegrator.startAndBindMainViewPort(application, theImageView, Thread::new);

			// Cache le side Menu lors du lancement de l'interface
			sidemenu.setVisible(false);
			functionbox.setVisible(false);
			colorbox.setVisible(false);
			matrixbox.setVisible(false);
			zoombox.setVisible(false);

			visibleSet = FXCollections.observableSet();

		} catch (Exception ex) {
			System.out.println("Exception lors du chargement des ressources dans controlleur vue");
		}

	}

	@FXML
	void closeColorBox(ActionEvent event) {

		c1 = colpic1.getValue();
		c2 = colpic2.getValue();

		vec1 = new Vector4f((float) c1.getRed(), (float) c1.getGreen(), (float) c1.getBlue(), (float) c1.getOpacity());

		vec2 = new Vector4f((float) c2.getRed(), (float) c2.getGreen(), (float) c2.getBlue(), (float) c2.getOpacity());

		colorbox.setVisible(false);
		visibleSet.remove(colorbox);
		bColor.setStyle("-fx-background-radius: 15");
	}

	@FXML
	void closeFunctionBox(ActionEvent event) {

		function = tFunction.getText();

		functionbox.setVisible(false);
		visibleSet.remove(functionbox);
		bFunction.setStyle("-fx-background-radius: 15");

		try {
			changerEquation(tFunction.getText());
		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

		matrixbox.setVisible(false);
		visibleSet.remove(matrixbox);
		bMatrix.setStyle("-fx-background-radius: 15");

	}

	@FXML
	void closeZoomBox(ActionEvent event) {

		zoomVal = tZoom.getText();

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

		zoomTrans = zoomTrans.setScale(zoomTrans.getScale().x + (float) event.getTextDeltaY());

		System.out.println(getZoomMat());
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
	
	@FXML
	void positionInit() {
		xInitLocation = (float) MouseInfo.getPointerInfo().getLocation().getX();
		yInitLocation = (float) MouseInfo.getPointerInfo().getLocation().getY();
		System.out.println(xInitLocation + " " + yInitLocation);
	}

	@FXML
	void mouseDrag() {

		vecTranslation = new Vector3f((float) -(xInitLocation - MouseInfo.getPointerInfo().getLocation().getX()),
				(float) (yInitLocation - MouseInfo.getPointerInfo().getLocation().getY()), 0);
		System.out.println("[ " + vecTranslation.x + " " + vecTranslation.y + " ]");

	}

	public Scene getScene() {
		return scene;
	}

	public String getM11() {
		return m11;
	}

	public String getM12() {
		return m12;
	}

	public String getM21() {
		return m21;
	}

	public String getM22() {
		return m22;
	}

	public String getFunction() {
		return function;
	}

	public String getZoomVal() {
		return zoomVal;
	}

	public Vector4f getVec1() {
		return vec1;
	}

	public Vector4f getVec2() {
		return vec2;
	}
	
	public Matrix4f getZoomMat() {
		return zoomTrans.toTransformMatrix();
	}


	private void initializeMaterialHandler() throws URISyntaxException {
		File shadFrag = new File(this.getClass().getResource("/vue/genericShaderFrag.glsl").toURI());
		File matBase = new File(this.getClass().getResource("/vue/genericMat.j3md").toURI());
		matHandler = new MaterialHandler(shadFrag, matBase);

	}

	// TODO Enlever ?
	public void refreshMaterial(Material mat) {

	}

	// TODO à ajouter dans le fxml
	public void changerEquation(String eq) throws IOException {
		
		matHandler.writeFormula(eq);	
		application.refreshMaterial(matHandler.getMatdefBaseUpdated());
	}

	public void setControleurPrincipal(Controleur controleurPrincipal) {
		this.controleurPrincipal = controleurPrincipal;
	}

	// TODO Enlever l'annotation @notnull ?
	private static @NotNull JMonkeyApp makeJmeApplication() {

		// Ici c'est la magie du plugin Jme-jfx en oeuvre DONT TOUCH

		AppSettings settings = JmeToJFXIntegrator.prepareSettings(new AppSettings(true), 60);
		settings.setResolution(1920, 1080);

		final JMonkeyApp application = new JMonkeyApp();
		application.setSettings(settings);
		application.setShowSettings(false);

		return application;
	}

}
