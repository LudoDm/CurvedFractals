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

import modele.ChartHandler;
import modele.MetricHandler;
import modele.ShaderHandler;

public class ChartHandlerTest {

	ChartHandler s;
	String validF = "x + y";
	String invalidVar = "x*x + c";
	String incomplete = "";
	File shadB;

	@Before
	public void setUpStreams() {
		try {
			shadB = new File(this.getClass().getResource("/prototypes/frag2.glsl").toURI());
			System.out.println("URI: " + this.getClass().getResource("/prototypes/frag2.glsl").toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(shadB.getPath());
		s = new ChartHandler(shadB);
	}

	@Test
	public void testOpenFile() {
		System.out.println("openFile");
		System.out.println(s.OpenFile(shadB));
	}

	@Test
	public void testWtriteFile() {

		String[] formulas = { "x", "x", "x"};
		System.out.println("writeFile");
		s.WriteFormula(formulas);
		System.out.println("uri created file: " + s.getShaderUpdatedBase());
	}

}
