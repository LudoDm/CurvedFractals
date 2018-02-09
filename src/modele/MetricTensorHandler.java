package modele;

import com.jme3.texture.Texture3D;

public class MetricTensorHandler {
	private String[] metricTensorIn;
	private Texture3D texture;

	public MetricTensorHandler() {
	}

	private String[] getMetricTensorIn() {
		return metricTensorIn;
	}

	public void setMetricTensorIn(String[] metricTensorIn) {
		if (validateMetricTensorIn(metricTensorIn)) {
			this.metricTensorIn = metricTensorIn;
		}
	}

	private static boolean validateMetricTensorIn(String[] metricTensorIn) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private static boolean validateMetricTensorInij(String metricij) {
		return false;
	}

	public Texture3D getTexture() {
		return texture;
	}

	public void setTexture(Texture3D texture) {
		this.texture = texture;
	}

}
