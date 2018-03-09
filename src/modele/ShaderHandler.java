package modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import com.jme3.shader.Shader;

public class ShaderHandler {

	private File shaderBase;
	private File shaderUpdatedBase = null;
	private ArrayList<String> shaderBaseData = null;
	private String formula = "";

	public static final String VAR_ITT = "z";
	public static final String VAR_POS = "c";
	public static final String EQNEXTLINEINDICATOR = "//OUTPUT_EQ_NEXT_LINE";

	public ShaderHandler(File shaderBase) {
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

	public boolean setFormula(String formula) {
		boolean out = false;
		if (validateFormula(formula)) {
			this.formula = formula;
			out = true;
			System.out.println("formula set: " + formula);
		}
		return out;
	}

	public String getFormula() {
		return formula;
	}

	private static boolean validateFormula(String formula) {
		boolean out = false;

		if (formula != null) {

			StringTokenizer t = new StringTokenizer(formula);
			boolean containZ = false;
			boolean containC = false;
			while (t.hasMoreTokens() && !out) {
				String tok = t.nextToken();
				System.out.println(tok);
				if (tok.contains(VAR_ITT)) {
					containZ = true;
				}

				if (tok.matches("(" + VAR_POS + "|" + VAR_POS + ".*|[+-]" + VAR_POS + ")")) {
					containC = true;
				}

				if (containZ && containC) {
					out = true;
				}
			}

		}
		return out;
	}

	public boolean WriteFormula(String formula) {
		boolean out = false;
		
		try {

			if (setFormula(formula)) {
				File newShader;
				newShader = File.createTempFile("tempShaderFrag", ".glsl", getShaderBase().getParentFile());
				newShader.deleteOnExit();
				System.out.println(newShader.exists());
				setShaderUpdatedBase(newShader);
				setShaderBaseData(OpenFile(getShaderBase()));

				ArrayList<String> data = getShaderBaseData();
				boolean done = false;
				for (int i = 0; i < data.size() && !done; i++) {
					if (data.get(i).contains(EQNEXTLINEINDICATOR)) {
						data.add(i + 1, "        z = " + getFormula() + ";");
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

	private ArrayList<String> OpenFile(File file) {
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

	// TODO
	public void clear() {

	}

}
