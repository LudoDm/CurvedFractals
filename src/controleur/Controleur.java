package controleur;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jme3.material.Material;

import javafx.scene.Scene;
import modele.FractalFactory;
import modele.MaterialHandler;
import vue.ControlleurVue;

public class Controleur {
	private ControlleurVue vue;
	private MaterialHandler matHandler;

	public Controleur() {
		try {
			File shadFrag = new File(this.getClass().getResource("/vue/genericShaderFrag.glsl").toURI());
			File matBase = new File(this.getClass().getResource("/vue/genericMat.j3md").toURI());
			matHandler = new MaterialHandler(shadFrag, matBase);
			vue = new ControlleurVue(this);

		} catch (URISyntaxException e) {
			System.out.println("Exception dans le controlleur entre le modele et la vue");
			e.printStackTrace();
		}

	}

	public MaterialHandler getMatHandler() {
		return matHandler;
	}

	public void setMatHandler(MaterialHandler matHandler) {
		this.matHandler = matHandler;
	}

	public void writeFormula(String formula) {
		getMatHandler().writeFormula(formula);
	}

	public File getMatBase() {
		return getMatHandler().getMatdefBase();
	}

	public File getMatUpdated() {
		return getMatHandler().getMatdefBaseUpdated();
	}

	public Scene getScene() {
		return vue.getScene();
	}

}
