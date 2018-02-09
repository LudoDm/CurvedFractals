package modele;

import java.io.File;

import com.jme3.shader.Shader;

public class ShaderHandler {
	
	private Shader shaderBase;
	private Shader shaderUpdatedBase;
	private String Formula;

	public ShaderHandler(File shaderBase) {
	}
	
	private void setShaderBase(File shaderBase) {
	}
	
	public void setFormula(String formula){
		if(validateFormula(formula)) {
			this.Formula = formula;
		}
	}

	public String getFormula() {
		return Formula;
	}

	private static boolean validateFormula(String formula) {
		return false;
	}

	
	public void WriteFormula(String formula) {
	}
	
	public Shader getShaderUpdatedBase() {
		return shaderUpdatedBase;
	}

	private void setShaderUpdatedBase(Shader shaderUpdatedBase) {
		this.shaderUpdatedBase = shaderUpdatedBase;
	}



}
