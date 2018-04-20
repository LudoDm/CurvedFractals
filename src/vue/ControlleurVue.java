package vue;

import java.awt.MouseInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix4f;
import com.jme3.math.Transform;
import com.jme3.math.Vector2f;
import com.jme3.system.AppSettings;
import com.jme3x.jfx.injfx.JmeToJFXIntegrator;
import controleur.Controleur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tooltip;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ControlleurVue {

	private Scene scene;
	private Controleur controleurPrincipal;
	private String m11, m12, m21, m22, function, zoomVal;
	private ObservableSet<Node> visibleSet;
	private JMonkeyApp application;
	private Color c1, c2;
	private Transform zoomMat = new Transform();
	private float xInitLocation, yInitLocation, X, U, V;
	private Vector2f vecTranslation = new Vector2f(0, 0);
	private boolean changed = false;
	private Task currenttask;

	private float zoomFix = 0f;

	@FXML
	private ImageView theImageView;

	@FXML
	private BorderPane borderpane;

	@FXML
	private StackPane stackpane;

	@FXML
	private VBox sidemenu;

	@FXML
	private Button bFunction, bMatrix, bColor, bZoom, bFunctionEnter, bZoomEnter, bMatrixEnter, bColorEnter, bR2toR3,
			bR2toR3Enter;

	@FXML
	private Label lX, lXValue, lY, lYValue, lFunction, lZoom, lArrow;

	@FXML
	private HBox functionbox, zoombox, matrixbox, colorbox, r2tor3box;

	@FXML
	private TextField tFunction, tZoom, tMatrix1, tMatrix2, tMatrix3, tMatrix4, tX, tU, tV;
	@FXML
	private ColorPicker colpic1, colpic2;
	private int nbrZoom;

	public ControlleurVue(Controleur ctrl) {
		try {
			setControleurPrincipal(ctrl);

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
			r2tor3box.setVisible(false);

			visibleSet = FXCollections.observableSet();

			initHoverInfos();

		} catch (Exception ex) {
			System.out.println("Exception lors du chargement des ressources dans controlleur vue");
			ex.printStackTrace();
		}

	}

	private void initHoverInfos() {

		tFunction.setTooltip(new Tooltip("Équation de la fractale" + "\n" + "c : Position" + "\n" + "z : Itération"));

		tMatrix1.setTooltip(new Tooltip("x ∈ [-1,1]" + "\n" + "y ∈ [-1,1]"));
		tMatrix2.setTooltip(new Tooltip("x ∈ [-1,1]" + "\n" + "y ∈ [-1,1]"));
		tMatrix3.setTooltip(new Tooltip("x ∈ [-1,1]" + "\n" + "y ∈ [-1,1]"));
		tMatrix4.setTooltip(new Tooltip("x ∈ [-1,1]" + "\n" + "y ∈ [-1,1]"));

		colpic1.setTooltip(new Tooltip("Couleur de départ"));
		colpic2.setTooltip(new Tooltip("Couleur de fin"));

		tZoom.setTooltip(new Tooltip("Nombre de zooms à effectuer"));

		tX.setTooltip(new Tooltip("u ∈ [-1,1]" + "\n" + "v ∈ [-1,1]"));
		tU.setTooltip(new Tooltip("u ∈ [-1,1]" + "\n" + "v ∈ [-1,1]"));
		tV.setTooltip(new Tooltip("u ∈ [-1,1]" + "\n" + "v ∈ [-1,1]"));

		bFunction.setTooltip(new Tooltip("Équation"));
		bMatrix.setTooltip(new Tooltip("Tenseur métrique"));
		bColor.setTooltip(new Tooltip("Couleur"));
		bZoom.setTooltip(new Tooltip("Zoom"));
		bR2toR3.setTooltip(new Tooltip("Paramétrisation de M vers R3"));

	}

	@FXML
	void closeColorBox(ActionEvent event) {

		c1 = colpic1.getValue();
		c2 = colpic2.getValue();

		// TODO regler ca, sur le premier mat, il est null et on ne peut pas changer la
		// couleur...
		if (!application.isMatNull()) {
			application.setColorMaxMat(new ColorRGBA((float) c1.getRed(), (float) c1.getGreen(), (float) c1.getBlue(),
					(float) c1.getOpacity()));
			application.setColorMinMat(new ColorRGBA((float) c2.getRed(), (float) c2.getGreen(), (float) c2.getBlue(),
					(float) c2.getOpacity()));
		}

		colorbox.setVisible(false);
		visibleSet.remove(colorbox);
		bColor.setStyle("-fx-background-radius: 15");
	}

	@FXML
	void closeFunctionBox(ActionEvent event) {

		functionbox.setVisible(false);
		visibleSet.remove(functionbox);
		bFunction.setStyle("-fx-background-radius: 15");

		try {
			changerEquation(tFunction.getText());
			// on reset le zoom sur le changement d'équation pour pas avoir de zoom trop
			// brusque au premier scroll
			application.setZoomTransformMat(Transform.IDENTITY.toTransformMatrix());
			this.zoomMat = Transform.IDENTITY;
		} catch (IOException e) {
			// TODO Bloc catch généré automatiquement
			// e.printStackTrace();
		}
	}

	@FXML
	void closeMatrixBox(ActionEvent event) {

		// TODO enlever ce gros try n catch et filtrer comme il faut les données

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

			matrixbox.setVisible(false);
			visibleSet.remove(matrixbox);
			bMatrix.setStyle("-fx-background-radius: 15");

			changerMetric(matrix.get(0), matrix.get(1), matrix.get(2), matrix.get(3));
			// on reset le zoom sur le changement d'équation pour pas avoir de zoom trop
			// brusque au premier scroll
			application.setZoomTransformMat(Transform.IDENTITY.toTransformMatrix());
			this.zoomMat = Transform.IDENTITY;

		} catch (Exception e) {
			System.out.println("Le input de la matrice a explosé");
		}

	}

	@FXML
	void closeZoomBox(ActionEvent event) {
		this.zoomVal = tZoom.getText();

		if (zoomVal != "") {
			try {
				this.nbrZoom = Integer.parseInt(zoomVal);
			} catch (Exception e) {

			}
		}
		
		if(currenttask == null) {
			creerTask();
		}else if(!currenttask.isRunning()) {
			creerTask();
		}


		zoombox.setVisible(false);
		visibleSet.remove(zoombox);
		bZoom.setStyle("-fx-background-radius: 15");

	}

	@FXML
	void closeR2toR3Box(ActionEvent event) {

		// TODO : enlever ce gros try n catch

		try {
			r2tor3box.setVisible(false);
			visibleSet.remove(r2tor3box);
			bR2toR3.setStyle("-fx-background-radius: 15");

			changerChart(tX.getText(), tU.getText(), tV.getText());
			// on reset le zoom sur le changement d'équation pour pas avoir de zoom trop
			// brusque au premier scroll
			application.setZoomTransformMat(Transform.IDENTITY.toTransformMatrix());
			this.zoomMat = Transform.IDENTITY;

		} catch (Exception e) {
			System.out.println("Le input de la parametrisation à explosé ");
			e.printStackTrace();
		}

	}

	void closeSideMenu() {

		sidemenu.setVisible(false);
		visibleSet.remove(sidemenu);

	}

	@FXML
	void gererZoom(ScrollEvent event) {

		// zoomFix = (event.getDeltaY() / 40f == 1f) ? zoomFix + 1f : zoomFix - 1f;

		changerEquationInitilialisation();

		// TODO ça tout seul ca chie.
		float ds = (float) event.getTextDeltaY();
		ds /= 10;

		System.out.println(ds);
		if (ds < 0 && Math.abs(ds) != 1) {
			ds = 1.0f / -ds;
		} else if (ds == 0) {
			ds = 1;
		}
		float out = zoomMat.getScale().x;
		zoomMat = zoomMat.setScale(out * ds);

		if (!application.isMatNull()) {
			application.setZoomTransformMat(getZoomMat());
		} else {
			System.out.println("FUUUUUUUUUUUUUUUUUUCCCCCCCCCCCCCCCCCCCLLLLLLLLLLLLLLLLLL");
		}
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

		if (event.getButton().equals(MouseButton.SECONDARY)) {
			System.out.println("yo bitch");
			gererRightClick();
		} else if (event.getButton().equals(MouseButton.PRIMARY)) {
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
	}

	private void gererRightClick() {

		// Button b = new Button("allo");
		// VBox promptBox = new VBox();
		// promptBox.getChildren().add(b);
		//
		// stackpane.getChildren().add(promptBox);

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
	void showR2toR3Box(ActionEvent event) {
		if (!r2tor3box.isVisible()) {
			r2tor3box.setVisible(true);
			visibleSet.add(r2tor3box);
			bR2toR3.setStyle("-fx-background-radius: 0 50 30 0;");

		} else {
			closeR2toR3Box(event);
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

		Vector2f NvecTranslation = new Vector2f(
				(float) (xInitLocation - MouseInfo.getPointerInfo().getLocation().getX()) / 100.0f,
				(float) -(yInitLocation - MouseInfo.getPointerInfo().getLocation().getY()) / 100.0f);
		System.out.println("[ " + NvecTranslation.x + " " + NvecTranslation.y + " ]");
		System.out.println("                [ " + vecTranslation.x + " " + vecTranslation.y + " ]");

		System.out.println(xInitLocation + ", " + yInitLocation);

		if (!application.isMatNull()) {
			vecTranslation = vecTranslation.add(NvecTranslation);
			application.setTranslateTransformMat(vecTranslation);
			// System.out.println(zoomTrans.toTransformMatrix());
			// application.setZoomTransformMat(zoomTrans.toTransformMatrix());
		}
	}

	@FXML
	void gererReset(KeyEvent event) {

		if (event.getCode() == KeyCode.R && !application.isMatNull()) {
			
			// TODO : MARCHE PAS. Le code se perd dans JMonkeyApp.......
			
			application.setZoomTransformMat(Transform.IDENTITY.toTransformMatrix());
			setZoomMat(Transform.IDENTITY);
			System.out.println("reset GROS");
		}

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

	public Matrix4f getZoomMat() {
		return zoomMat.toTransformMatrix();
	}

	private void setZoomMat(Transform mat) {
		this.zoomMat = mat;
	}

	public void changerEquation(String eq) throws IOException {
		getControleurPrincipal().writeFormula(eq);
		application.refreshMaterial(getControleurPrincipal().getMatUpdated());
	}

	public void changerMetric(String string1, String string2, String string3, String string4) {
		getControleurPrincipal().writeMetric(string1, string2, string3, string4);
		application.refreshMaterial(getControleurPrincipal().getMatUpdated());
	}

	public void changerChart(String string1, String string2, String string3) {
		getControleurPrincipal().writeChart(string1, string2, string3);
		application.refreshMaterial(getControleurPrincipal().getMatUpdated());
	}

	private void setControleurPrincipal(Controleur controleurPrincipal) {
		this.controleurPrincipal = controleurPrincipal;
	}

	private Controleur getControleurPrincipal() {
		return this.controleurPrincipal;
	}

	// TODO faire mieux ?
	/***
	 * Methode hacky pour changer faire marcher le zoom lors d'ouverture de
	 * l'application
	 */
	private void changerEquationInitilialisation() {
		if (changed == false) {

			try {
				changerEquation(tFunction.getText());
				// on reset le zoom sur le changement d'équation pour pas avoir de zoom trop
				// brusque au premier scroll
				application.setZoomTransformMat(Transform.IDENTITY.toTransformMatrix());
				setZoomMat(Transform.IDENTITY);
				changed = true;
			} catch (IOException e) {
				System.out.println("Exception lance dans la methode hacky pour regler le zoom au lancement de l'app");
				e.printStackTrace();
			}
		}
	}

	// TODO Enlever l'annotation @notnull ?
	private static @NotNull JMonkeyApp makeJmeApplication() {

		AppSettings settings = JmeToJFXIntegrator.prepareSettings(new AppSettings(true), 60);

		final JMonkeyApp application = new JMonkeyApp(1920, 1280);
		application.setSettings(settings);
		application.setShowSettings(false);
		application.setDisplayFps(true);
		return application;
	}

	public void zoom(float x) {
		if (x < 0 && Math.abs(x) != 1) {
			x = 1.0f / -x;
		} else if (x == 0) {
			x = 1;
		}
		float out = zoomMat.getScale().x;
		zoomMat = zoomMat.setScale(out * x);

		if (!application.isMatNull()) {
			application.setZoomTransformMat(getZoomMat());
		} else {
			System.out.println("FUUUUUUUUUUUUUUUUUUCCCCCCCCCCCCCCCCCCCkkkkkkkkkkkkkkkkkkkk");
		}
		System.out.println(getZoomMat());
	}
	
	public void creerTask() {

		currenttask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				for (int i = 0; i < nbrZoom; i++) {
					System.out.println("Zoom: " + i);
					zoom((float) 0.9);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException interrupted) {
						if (isCancelled()) {
							updateMessage("Cancelled");
							break;
						}
					}
				}
				return null;
			}
		};

		new Thread(currenttask).start();
	}

}
