package modele;

import com.jme3.texture.Texture3D;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

public class MetricTensorHandler {
	private String metricTensor11;
	private String metricTensor21;
	private String metricTensor12;
	private String metricTensor22;

	private Expression m11;
	private Expression m21;
	private Expression m12;
	private Expression m22;

	public final static String VARX = "x";
	public final static String VARY = "y";

	public MetricTensorHandler() {

	}

	public double[] evaluateAt(int x, int y) {
		double[] out = null;
		boolean worked = false;
		if (x >= 0 && y >= 0) {
			if (!(m11 == null || m21 == null || m12 == null || m22 == null)) {

				double g11 = m11.setVariable(VARX, x).setVariable(VARY, y).evaluate();
				if (!Double.isNaN(g11) || !Double.isInfinite(g11)) {
					worked = true;
					out[0] = g11;
				}

				double g21 = m21.setVariable(VARX, x).setVariable(VARY, y).evaluate();
				if (!(Double.isNaN(g21) || Double.isInfinite(g21))) {
					worked = true;
					out[1] = g21;
				} else {
					worked = false;
				}

				double g12 = m12.setVariable(VARX, x).setVariable(VARY, y).evaluate();
				if (!(Double.isNaN(g12) || Double.isInfinite(g12))) {
					worked = true;
					out[2] = g12;
				} else {
					worked = false;
				}

				double g22 = m22.setVariable(VARX, x).setVariable(VARY, y).evaluate();
				if (!(Double.isNaN(g22) || Double.isInfinite(g22))) {
					worked = true;
					out[3] = g22;
				} else {
					worked = false;
				}

			}
		}
		return (worked) ? out : null;

	}

	private static boolean validateMetricTensorInij(String metricij) {
		boolean out = false;
		try {
			new ExpressionBuilder(metricij).variables(VARX, VARY).build();
			out = true;
		} catch (UnknownFunctionOrVariableException e) {
		}
		return out;
	}

	public String getMetricTensor11() {
		return metricTensor11;
	}

	public void setMetricTensor11(String metricTensor11) {
		if (validateMetricTensorInij(metricTensor11)) {
			this.metricTensor11 = metricTensor11;
			this.m11 = new ExpressionBuilder(metricTensor11).variables(VARX, VARY).build();
		}
	}

	public String getMetricTensor21() {
		return metricTensor21;
	}

	public void setMetricTensor21(String metricTensor21) {
		if (validateMetricTensorInij(metricTensor21)) {
			this.metricTensor21 = metricTensor21;
			this.m21 = new ExpressionBuilder(metricTensor21).variables(VARX, VARY).build();
		}
	}

	public String getMetricTensor12() {
		return metricTensor12;
	}

	public void setMetricTensor12(String metricTensor12) {
		if (validateMetricTensorInij(metricTensor12)) {
			this.metricTensor12 = metricTensor12;
			this.m12 = new ExpressionBuilder(metricTensor12).variables(VARX, VARY).build();
		}
	}

	public String getMetricTensor22() {
		return metricTensor22;
	}

	public void setMetricTensor22(String metricTensor22) {
		if (validateMetricTensorInij(metricTensor22)) {
			this.metricTensor22 = metricTensor22;
			this.m22 = new ExpressionBuilder(metricTensor22).variables(VARX, VARY).build();
		}
	}

}
