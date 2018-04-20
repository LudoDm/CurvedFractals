package modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

public class FormulasHandler {

	public final static String EQNEXTLINEINDICATORM_METRIC = "//OUTPUT_METRIC";
	public final static String EQNEXTLINEINDICATOR_CHART = "//OUTPUT_CHART_FUNCTION";
	public final static String[] CONVERTTABLE_METRIC = { "g11", "g21", "g12", "g22" };
	public final static String[] CONVERTTABLE_CHART = { "a", "b", "c" };
	public final static String VARX = "x";
	public final static String VARY = "y";
	public final static String VARU = "u";

	private File shaderBase;
	private File shaderUpdatedBase = null;
	private ArrayList<String> shaderBaseData = null;
	private String[] metricFormula = new String[4];
	private String[] chartFormula = new String[3];

	public FormulasHandler(File pShaderBase) {
		setShaderBase(pShaderBase);
	}

	// Shaders methods
	public File getShaderBase() {
		return this.shaderBase;
	}

	private void setShaderBase(File shaderBase) {
		if (shaderBase.exists() && shaderBase.canRead() && shaderBase.isFile()) {
			this.shaderBase = shaderBase;
		}
	}

	private void setShaderUpdatedBase(File shaderUpBase) {
		if (shaderUpBase.exists() && shaderUpBase.canRead() && shaderUpBase.isFile()) {
			this.shaderUpdatedBase = shaderUpBase;
			System.out.println(shaderUpBase);
		}
	}

	private ArrayList<String> getShaderBaseData() {
		return shaderBaseData;
	}

	private void setShaderBaseData(ArrayList<String> shaderBaseData) {
		this.shaderBaseData = shaderBaseData;
	}

	// Metric methods
	public boolean setMetricFormula(String pFormula, int formulaNumber) {
		boolean out = false;
		if (validateMetricFormulaIndex(formulaNumber) && validateMetricFormula(pFormula)) {
			this.metricFormula[formulaNumber] = pFormula;
			out = true;
			System.out.println("MetricTensor " + formulaNumber + " set: " + pFormula.toString());
		}
		return out;
	}

	public String getMetricFormula(int num) {
		String toReturn = null;
		if (validateMetricFormulaIndex(num))
			toReturn = this.metricFormula[num];
		return toReturn;
	}

	public String[] getMetricFormulas() {
		return this.metricFormula;
	}

	private boolean validateMetricFormulaIndex(int num) {
		return (num >= 0 && num < getMetricFormulas().length);
	}

	private static boolean validateMetricFormula(String pFormula) {
		boolean out = false;

		if (pFormula.trim().length() <= 0)
			return false;

		try {
			new ExpressionBuilder(pFormula).variables(VARX, VARY).build();
			out = true;
		} catch (UnknownFunctionOrVariableException e) {
		}
		return out;
	}

	private void writeMetricFormula(ArrayList<String> data) {
		boolean done = false;
		int indiceDeLaMatrice = 0;

		for (int i = 0; i < data.size() && !done; i++) {
			if (data.get(i).contains(EQNEXTLINEINDICATORM_METRIC)) {
				data.add(i + 1, "        " + CONVERTTABLE_METRIC[indiceDeLaMatrice] + " = "
						+ getMetricFormula(indiceDeLaMatrice) + ";");
				indiceDeLaMatrice++;

				if (indiceDeLaMatrice == getMetricFormulas().length)
					done = true;
			}
		}
	}
	
//Chart methods 
	
	
	
	
// end

	public boolean WriteFormula(String[] pFormulas) {
		boolean out = false;
		boolean formulesValides = true;

		try {

			if (pFormulas.length == 4) {
				for (int z = 0; z < this.getMetricFormulas().length; z++) {
					if (setMetricFormula(pFormulas[z], z) == false)
						formulesValides = false;
				}
			}

			if (formulesValides) {
				File newShader;
				newShader = File.createTempFile("tempShaderFrag", ".glsl", getShaderBase().getParentFile());
				newShader.deleteOnExit();
				System.out.println(newShader.exists());
				setShaderUpdatedBase(newShader);
				setShaderBaseData(OpenFile(getShaderBase()));

				ArrayList<String> data = getShaderBaseData();
				
				writeMetricFormula(data);
				
				setShaderBaseData(data);
				System.out.println("ecriture du fichier");
				writeFile(getShaderBaseData(), getShaderUpdatedBase());
			}

			out = true;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return out;
	}

	private void writeFile(ArrayList<String> data, File file) {
		try {
			PrintWriter out = new PrintWriter(getShaderUpdatedBase());

			for (int i = 0; i < data.size(); i++) {
				out.println(data.get(i));
				System.out.println("ecriture ligne " + i);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println(e);
		}

	}

	public ArrayList<String> OpenFile(File file) {
		ArrayList<String> out = new ArrayList<>();
		String ligne;
		try {

			BufferedReader input = new BufferedReader(new FileReader(file));
			ligne = input.readLine();

			while (ligne != null) {
				out.add(ligne);
				ligne = input.readLine();
			}
			input.close();

		} catch (IOException e) {
			System.out.println(e);
		}

		return out;
	}

	public File getShaderUpdatedBase() {
		return shaderUpdatedBase;
	}

}
