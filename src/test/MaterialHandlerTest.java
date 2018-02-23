package test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import modele.MaterialHandler;
import modele.ShaderHandler;

public class MaterialHandlerTest {
	MaterialHandler m;
	File shadB;
	File matB;

	@Before
	public void setUp() throws Exception {
		shadB = new File(this.getClass().getResource("/prototypes/frag.glsl").toURI());
		matB = new File(this.getClass().getResource("/prototypes/mat.j3md").toURI());
		m = new MaterialHandler(shadB, matB);
	}

	@Test
	public void test() {
		System.out.println(matB.getPath());
		System.out.println(matB.exists());
	}

	@Test
	public void testOpenFile() {
		System.out.println("openFile");
		System.out.println(m.OpenFile(matB));
	}

	@Test
	public void testWriteFile() {
		System.out.println("writeFile");
		m.writeMat(m.getShaderHandler().getShaderBase());
		System.out.println("uri created file: " + m.getMatdefBaseUpdated());
	}

	@Test
	public void testWriteFormula() {
		m.writeFormula("z + c");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
