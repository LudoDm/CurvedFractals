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

public class ChartHandler {

	public final static String VARX = "x";
	public final static String VARU = "y";
	public final static String VARV = "v";

	private File shaderBase;
	private File shaderUpdatedBase = null;
	private ArrayList<String> shaderBaseData = null;
	private String[] formula = new String[3];

	public static final String EQNEXTLINEINDICATOR = "//OUTPUT_CHART_FUNCTION";
	public static final String CONVERTTABLE[] = { "a", "b", "c" };

	public ChartHandler(File shaderBase) {
		setShaderBase(shaderBase);
	}

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

	public boolean setFormula(String pFormula, int formulaNumber) {
		boolean out = false;
		if (validateFormulaIndex(formulaNumber) && validateFormula(pFormula)) {
			this.formula[formulaNumber] = pFormula;
			out = true;
			System.out.println("Chart shit " + formulaNumber + " set: " + pFormula.toString());
		}
		return out;
	}

	public String getFormula(int num) {
		String toReturn = null;
		if (validateFormulaIndex(num))
			toReturn = this.formula[num];
		return toReturn;
	}

	public String[] getFormulas() {
		return this.formula;
	}

	private boolean validateFormulaIndex(int num) {
		return (num >= 0 && num < getFormulas().length);
	}

	private static boolean validateFormula(String pFormula) {
		boolean out = false;

		if (pFormula.trim().length() <= 0)
			return false;

		try {
			new ExpressionBuilder(pFormula).variables(VARX, VARU, VARV).build();
			out = true;
		} catch (UnknownFunctionOrVariableException e) {
		}
		return out;
	}

	public boolean WriteFormula(String[] pFormulas) {
		boolean out = false;
		boolean formulesValides = true;

		try {

			if (pFormulas.length == 3) {
				for (int z = 0; z < this.getFormulas().length; z++) {
					if (setFormula(pFormulas[z], z) == false)
						formulesValides = false;
				}
			}

			System.out.println(formulesValides);
			if (formulesValides) {
				File newShader;
				newShader = File.createTempFile("tempShaderFrag", ".glsl", getShaderBase().getParentFile());
				newShader.deleteOnExit();
				System.out.println(newShader.exists());
				setShaderUpdatedBase(newShader);
				setShaderBaseData(OpenFile(getShaderBase()));

				ArrayList<String> data = getShaderBaseData();
				boolean done = false;

				int indiceDeLaMatrice = 0;

				for (int i = 0; i < data.size() && !done; i++) {
					if (data.get(i).contains(EQNEXTLINEINDICATOR)) {

						System.out.println("hey");
						data.add(i + 1, "        " + CONVERTTABLE[indiceDeLaMatrice] + " = "
								+ getFormula(indiceDeLaMatrice) + ";");
						indiceDeLaMatrice++;

						if (indiceDeLaMatrice == getFormulas().length)
							done = true;
					}
				}
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