package controleur;

import com.jme3.material.Material;

import javafx.scene.Scene;
import modele.FractalFactory;

public class Controleur {
	private ViewController view;
	private FractalFactory modele;
	
	public Controleur() {
	}
	
	public Scene getScene() {
	}
	
	public Material getMaterial() {
		return modele.getMaterial();
	}
	
	public void setFormula(String formula) {
		modele.setShaderEquation(formula);
	}
	
	public void setMetric(String[] metricTensor) {
		modele.setMetricTensor(metricTensor);
	}
	

}
