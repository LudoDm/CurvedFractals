package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import modele.ShaderHandler;

public class ShaderHandlerTest {

	ShaderHandler s;
	String validF = "z*z + c";
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
