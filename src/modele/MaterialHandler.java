package modele;

import java.io.File;

import com.jme3.material.Material;
import com.jme3.material.MaterialDef;

public class MaterialHandler {
	private MaterialDef matdef;
	private ShaderHandler shaderHandler;
	private MetricTensorHandler metricHandler;

	public MaterialHandler(File shaderBase) {
		shaderHandler = new ShaderHandler(shaderBase);
	}
	

	public void setMatdef(MaterialDef matdef) {
		this.matdef = matdef;
	}

	public Material generateMaterial() {

		return null;
	}
	
	public void setMetric(String[] metrictensor) {
	}
	
	public void writeFormula(String formula) {
		
	}

	public void updateMetric() {
	}
	
	public void updateFormula() {
	}
}
