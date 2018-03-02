package vue;

import java.io.File;

import com.jme3.app.LegacyApplication;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.material.MaterialDef;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.system.AppSettings;
import com.jme3x.jfx.injfx.JmeToJFXApplication;

import jme3tools.optimize.GeometryBatchFactory;

import com.jme3.math.ColorRGBA;
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

	// https://cis.temple.edu/~lakaemper/courses/cis4350_2014_SPRING/classNotes/JMonkeyNotes.pdf
	@Override
	public void simpleInitApp() {
		float w = this.getContext().getSettings().getWidth();
		float h = this.getContext().getSettings().getHeight();
		System.out.println("w: " + w + " h: " + h);
		cam.setLocation(Vector3f.ZERO.add(new Vector3f(0.0f, 0.0f, 10f)));
		float camZ = cam.getLocation().getZ();
		float ratio = w / h;
		float width = camZ * ratio;
		float height = camZ;

		Quad b = new Quad(width, height);
		player = new Geometry("Player", b);

		Material mat = new Material(assetManager, "/vue/initialMat.j3md");

		mat.setColor("ColorMin", ColorRGBA.randomColor());
		mat.setColor("ColorMax", ColorRGBA.randomColor());
		mat.setVector2("Resolution", new Vector2f(w, h));
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
		mat.setColor("ColorMin", ColorRGBA.randomColor());
		mat.setColor("ColorMax", ColorRGBA.randomColor());
		mat.setVector2("Resolution", new Vector2f(1920, 1080));
		player.setMaterial(mat);
	}

}