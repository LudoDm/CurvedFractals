package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modele.ShaderHandler;

public class ShaderHandlerTest {

	ShaderHandler s;
	String validF = "vec2(z.x * z.x - z.y * z.y, 2.0 * z.x * z.y) + c";
	String validF2 = "(z.x)*z + c";
	String invalidChar = "!z*z + c";
	String invalidVar = "x*x + c";
	String incomplete = "";

	@Before
	public void setUpBeforeClass() throws Exception {
		s = new ShaderHandler(null);
	}

	@Test
	public void test() {
	}

}
