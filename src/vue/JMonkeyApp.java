package vue;

import java.io.File;
import java.net.URISyntaxException;

import com.jme3.app.LegacyApplication;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.material.MaterialDef;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.system.AppSettings;
import com.jme3x.jfx.injfx.JmeToJFXApplication;

import jme3tools.optimize.GeometryBatchFactory;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix4f;
import com.jme3.math.Transform;
import com.jme3.math.Vector2f;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;

/**
 * Sample 5 - how to map keys and mousebuttons to actions
 */
public class JMonkeyApp extends JmeToJFXApplication {

	protected Geometry player;
	private boolean isRunning = true;
	private Material mat;
	private ColorRGBA ColorMin = ColorRGBA.Magenta;
	private ColorRGBA ColorMax = ColorRGBA.Blue;

	private Vector2f Resolution;
	private Matrix4f ZoomTransform = Transform.IDENTITY.toTransformMatrix();
	private Vector2f TranslatTransform = new Vector2f(0, 0);

	public JMonkeyApp(float Resx, float Resy) {
		setResolution(new Vector2f(Resx, Resy));
	}

	// https://cis.temple.edu/~lakaemper/courses/cis4350_2014_SPRING/classNotes/JMonkeyNotes.pdf
	@Override
	public void simpleInitApp() {
		setColorMin(ColorRGBA.Blue);
		setColorMax(ColorRGBA.Magenta);
//		float w = 1920f;
//		float w = this.getContext().getSettings().getWidth();
//		float h = this.getContext().getSettings().getHeight();
		float w = this.Resolution.x;
		float h = this.Resolution.y;
		System.out.println("w: " + w + " h: " + h);
		cam.setLocation(Vector3f.ZERO.add(new Vector3f(0.0f, 0.0f, 10f)));
		float camZ = cam.getLocation().getZ();
		float ratio = w / h;
		float width = camZ * ratio;
		float height = camZ;

		Quad b = new Quad(width, height);
		player = new Geometry("Player", b);

		Material mat = new Material(assetManager, "/vue/initialMat.j3md");
		mat.setTransparent(false);

		mat.setColor("ColorMin", getColorMax());
		mat.setColor("ColorMax", getColorMin());
		mat.setVector2("Resolution", new Vector2f(w, h));
		mat.setMatrix4("Zoom", getZoomTransform());
		mat.setVector2("Translat", getTranslateTransform());
		// pour désactiver le mouvement
		flyCam.setEnabled(false);
		player.setMaterial(mat);
		player.setCullHint(CullHint.Never);
		player.setLocalTranslation(-(width / 2), -(height / 2), 0);
		rootNode.attachChild(player);

		Node coord = createCoordinationNode(1, 1);
		rootNode.attachChild(coord);

	}

	/**
	 * 
	 * @author Xeratos
	 * 
	 *         Creates one arrow for each axis colored in x=red, y=green, z=blue
	 * 
	 *         and one grid plane for each plane colored xz=red, xy=green, yz=blue.
	 * 
	 *         The returned node is being optimized before return.
	 * 
	 * @param size
	 * 
	 *            - int - The size of the arrows and grids
	 * 
	 * @param segSize
	 * 
	 *            - int - The segment size of the grids.
	 * 
	 * @return Node - A node with 3 direction arrows for x, y, z axis and 3
	 *         coordination
	 * 
	 *         grid planes for xz, xy, yz planes.
	 * 
	 */

	public Node createCoordinationNode(int size, int segSize) {

		Node coordNode = new Node("Coodination helper node");
		float offset = size / 2;
		Material mat;
		Geometry xAxis;
		Geometry yAxis;
		Geometry zAxis;
		Geometry xzGrid;
		Geometry xyGrid;
		Geometry yzGrid;

		// create x-axis
		Arrow arrowX = new Arrow(new Vector3f(size, 0.0f, 0.0f));
		xAxis = new Geometry("X-Axis", arrowX);
		mat = new Material(this.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Red);
		xAxis.setMaterial(mat);

		// create xz-grid
		Grid xzPlane = new Grid(size, size, segSize);
		xzGrid = new Geometry("XZ-Plane", xzPlane);
		xzGrid.setMaterial(mat);
		xzGrid.rotateUpTo(new Vector3f(0.0f, 1.0f, 0.0f));
		xzGrid.setLocalTranslation(new Vector3f(-offset, 0.0f, -offset));

		// create y-axis
		Arrow arrowY = new Arrow(new Vector3f(0.0f, size, 0.0f));
		yAxis = new Geometry("Y-Axis", arrowY);
		mat = new Material(this.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Green);
		yAxis.setMaterial(mat);

		// create xy-grid
		Grid xyPlane = new Grid(size, size, segSize);
		xyGrid = new Geometry("XY-Plane", xyPlane);
		xyGrid.setMaterial(mat);
		xyGrid.rotateUpTo(new Vector3f(0.0f, 0.0f, 1.0f));
		xyGrid.setLocalTranslation(new Vector3f(-offset, offset, 0.0f));

		// create z-axis
		Arrow arrowZ = new Arrow(new Vector3f(0.0f, 0.0f, size));
		zAxis = new Geometry("Z-Axis", arrowZ);
		mat = new Material(this.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Blue);
		zAxis.setMaterial(mat);

		// create yz-grid
		Grid yzPlane = new Grid(size, size, segSize);
		yzGrid = new Geometry("YZ-Plane", yzPlane);
		yzGrid.setMaterial(mat);
		yzGrid.rotateUpTo(new Vector3f(1.0f, 0.0f, 0.0f));
		yzGrid.setLocalTranslation(new Vector3f(0.0f, offset, -offset));

		// attach arrows to coordination node
		coordNode.attachChild(xAxis);
		coordNode.attachChild(yAxis);
		coordNode.attachChild(zAxis);

		// attach grids to coordination node
		coordNode.attachChild(xyGrid);
		coordNode.attachChild(xzGrid);
		coordNode.attachChild(yzGrid);
		// optimize coordNode
		GeometryBatchFactory.optimize(coordNode);
		return coordNode;

	}

	public void refreshMaterial(File theFile) {
		String thePath = ("/vue/" + theFile.getName());
		mat = new Material(assetManager, thePath);
		setColorMinMat(getColorMin());
		setColorMaxMat(getColorMax());
		mat.setVector2("Resolution", new Vector2f(this.Resolution.x, this.Resolution.y));
		setZoomTransformMat(getZoomTransform());
		setTranslateTransformMat(getTranslateTransform());
		player.setMaterial(mat);
	}

	public boolean checkIfMaterialCompile(File theFile) {
		boolean toReturn = true;

		try {
			String thePath = ("/vue/" + theFile.getName());
			Material temp = new Material(assetManager, thePath);
			setColorMinMat(getColorMin());
			setColorMaxMat(getColorMax());
			temp.setVector2("Resolution", new Vector2f(this.Resolution.x, this.Resolution.y));
			setZoomTransformMat(getZoomTransform());
			setTranslateTransformMat(getTranslateTransform());

			Geometry g1 = new Geometry("hey");
			g1.setMaterial(temp);
			getRenderManager().preloadScene(g1);
		} catch (Exception e) {
			toReturn = false;
		}

		return toReturn;
	}

	public boolean isMatNull() {
		return mat == null;
	}

	public ColorRGBA getColorMin() {
		return ColorMin;
	}

	private void setColorMin(ColorRGBA colorMin) {
		ColorMin = colorMin;
	}

	public void setColorMinMat(ColorRGBA colorMin) {
		ColorMin = colorMin;
		System.out.println("mat" + mat);
		mat.setColor("ColorMin", colorMin);
	}

	public ColorRGBA getColorMax() {
		return ColorMax;
	}

	private void setColorMax(ColorRGBA colorMax) {
		ColorMax = colorMax;
	}

	public void setColorMaxMat(ColorRGBA colorMax) {
		ColorMax = colorMax;
		mat.setColor("ColorMax", colorMax);
	}

	public Vector2f getResolution() {
		return Resolution;
	}

	public void setResolution(Vector2f resolution) {
		Resolution = resolution;
	}

	public Matrix4f getZoomTransform() {
		return ZoomTransform;
	}

	private void setZoomTransform(Matrix4f zoomTransform) {
		this.ZoomTransform = zoomTransform;
	}

	public void setZoomTransformMat(Matrix4f zoomTransform) {
		setZoomTransform(zoomTransform);
		mat.setMatrix4("Zoom", zoomTransform);
		System.out.println("matrice envoyée:  " + zoomTransform);
	}

	public Vector2f getTranslateTransform() {
		return this.TranslatTransform;
	}

	private void setTranslateTransform(Vector2f translateTransform) {
		this.TranslatTransform = translateTransform;
	}

	public void setTranslateTransformMat(Vector2f translateTransform) {
		setTranslateTransform(translateTransform);
		mat.setVector2("Translat", translateTransform);
	}

}