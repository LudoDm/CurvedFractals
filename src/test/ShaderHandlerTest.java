package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.net.URISyntaxException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modele.ShaderHandler;

public class ShaderHandlerTest {

	ShaderHandler s;
	String validF = "vec2(z.x * z.x - z.y * z.y, 2.0 * z.x * z.y) +c";
	String invalidVar = "x*x + c";
	String incomplete = "";
	File shadB;

	@Before
	public void setUpStreams() {
		try {
			shadB = new File(this.getClass().getResource("/prototypes/frag.glsl").toURI());
			System.out.println("URI: " + this.getClass().getResource("/prototypes/frag.glsl").toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(shadB.getPath());
		s = new ShaderHandler(shadB);
	}

	@Test
	public void testFormula() {
		s.setFormula(validF);
		String outF = s.getFormula();
		System.out.println("out :" + outF);
		assertTrue(validF.equals(outF));

		s.setFormula(invalidVar);
		String outF1 = s.getFormula();
		System.out.println("out :" + outF1);
		assertFalse(invalidVar.equals(outF1));

		s.setFormula(incomplete);
		String outF2 = s.getFormula();
		System.out.println("out :" + outF2);
		assertFalse(incomplete.equals(outF2));
	}

	@Test
	public void testShaderBase() {
		System.out.println("shader exisit: " +shadB.exists());
		System.out.println("shader: " + s.getShaderBase());
	}
	
	@Test
	public void testOpenFile() {
		System.out.println("openFile");
		System.out.println(s.OpenFile(shadB));
	}

	@Test
	public void testWtriteFile() {
		System.out.println("writeFile");
		s.WriteFormula(validF + " + q");
		System.out.println("uri created file: " + s.getShaderUpdatedBase());
	}

}
