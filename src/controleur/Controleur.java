package controleur;

import com.jme3.material.Material;

import javafx.scene.Scene;
import modele.FractalFactory;
import vue.ControlleurVue;

public class Controleur {
	private ControlleurVue view;
	private FractalFactory modele;
	
	public Controleur() {
	}
	
	public Scene getScene() {
		return null;
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
