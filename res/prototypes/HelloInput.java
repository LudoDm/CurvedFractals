package prototypes;


import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Quad;
import com.jme3.system.AppSettings;

import jme3tools.optimize.GeometryBatchFactory;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.input.controls.ActionListener;

/**
 * Sample 5 - how to map keys and mousebuttons to actions
 */
public class HelloInput extends SimpleApplication {
	

	public static void main(String[] args) {
		HelloInput app = new HelloInput();
		AppSettings s = new AppSettings(true);
//		s.setResolution(1200, 800);
		s.setResolution(1920, 1080);
		s.setFullscreen(true);
		app.setSettings(s);
		app.setShowSettings(false);
		app.start();
	}

	protected Geometry player;
	private boolean isRunning = true;

	//https://cis.temple.edu/~lakaemper/courses/cis4350_2014_SPRING/classNotes/JMonkeyNotes.pdf
	@Override
	public void simpleInitApp() {
		float w = this.getContext().getSettings().getWidth();
		float h = this.getContext().getSettings().getHeight();
		System.out.println("w: " + w+ " h: " + h);
		cam.setLocation(Vector3f.ZERO.add(new Vector3f(0.0f, 0.0f,10f)));
		float camZ = cam.getLocation().getZ();
		float ratio = w/h;
		float width = camZ*ratio;
		float height = camZ;

		Quad b = new Quad(width, height);
		player = new Geometry("Player", b);
//		player.setIgnoreTransform(true);
		// Material mat = new Material(assetManager,
		// "Common/MatDefs/Misc/Unshaded.j3md");

		Material mat = new Material(assetManager, "/prototypes/mat.j3md");
		mat.setColor("ColorMin", ColorRGBA.randomColor());
		mat.setColor("ColorMax", ColorRGBA.randomColor());
		mat.setVector2("Resolution", new Vector2f(w,h));
		//pour désactiver le mouvement
		flyCam.setEnabled(false);
		player.setMaterial(mat);
		player.setCullHint(CullHint.Never);
		player.setLocalTranslation(-(width/2), -(height/2) , 0);
		rootNode.attachChild(player);


		Node coord = createCoordinationNode(1, 1);
		rootNode.attachChild(coord);
		// initKeys(); // load my custom keybinding
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

	/**
	 * Custom Keybinding: Map named actions to inputs.
	 */
	// private void initKeys() {
	// // You can map one or several inputs to one named action
	// inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
	// inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_J));
	// inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_K));
	// inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_SPACE),
	// new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
	// // Add the names to the action listener.
	// inputManager.addListener(actionListener, "Pause");
	// inputManager.addListener(analogListener, "Left", "Right", "Rotate");
	//
	// }

	private final ActionListener actionListener = new ActionListener() {
		@Override
		public void onAction(String name, boolean keyPressed, float tpf) {
			if (name.equals("Pause") && !keyPressed) {
				isRunning = !isRunning;
			}
		}
	};

	// private final AnalogListener analogListener = new AnalogListener() {
	// @Override
	// public void onAnalog(String name, float value, float tpf) {
	//// if (isRunning) {
	//// if (name.equals("Rotate")) {
	//// player.rotate(0, value * speed, 0);
	//// }
	//// if (name.equals("Right")) {
	//// Vector3f v = player.getLocalTranslation();
	//// player.setLocalTranslation(v.x + value * speed, v.y, v.z);
	//// }
	//// if (name.equals("Left")) {
	//// Vector3f v = player.getLocalTranslation();
	//// player.setLocalTranslation(v.x - value * speed, v.y, v.z);
	//// }
	//// } else {
	//// System.out.println("Press P to unpause.");
	//// }
	// }
	// };
}