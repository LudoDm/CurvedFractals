package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

public class MetricTensorHandlerTest {

	String exp = "sqrt(x) + r + 0";
	ExpressionBuilder express = new ExpressionBuilder(exp);;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		try {
			Expression expr = express.variables("x", "y", "z").build();
			System.out.println(expr.setVariable("x", -1).setVariable("y", 0).setVariable("z", 2).evaluate());
		} catch (UnknownFunctionOrVariableException e) {
			assertTrue(true);
		}
	}

}
