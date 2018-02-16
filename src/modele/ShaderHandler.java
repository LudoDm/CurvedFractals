package modele;

import java.io.File;

import com.jme3.shader.Shader;

public class ShaderHandler {
	
	private Shader shaderBase;
	private Shader shaderUpdatedBase;
	private String formula = "";

	public enum SyntaxCheckCode {
		VALID, INVALIDVAR, INVALIDCHAR, INCOMPLETE;
		
	}

	public ShaderHandler(File shaderBase) {
	}
	
	private void setShaderBase(File shaderBase) {
	}
	
	public SyntaxCheckCode setFormula(String formula){
		SyntaxCheckCode c = SyntaxCheckCode.INCOMPLETE;
		if(validateFormula(formula)) {
			this.formula = formula;
		}
		return c;
	}

	public String getFormula() {
		return formula;
	}

	private static boolean validateFormula(String formula) {
		boolean out = false;
//		if(formula.matches(
//				"(vec2([].[xy]))"
//				)
		return out;
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
