package modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.jme3.material.Material;
import com.jme3.material.MaterialDef;

public class MaterialHandler {
	private File matdefBase;
	private ArrayList<String> dataMatDef;
	private File matdefBaseUpdated;
	// private ShaderHandler shaderHandler;
	// private MetricHandler metricHandler;
	// private ChartHandler chartHandler;
	private FormulasHandler formHandler;

	public MaterialHandler(File shaderBase, File matDefBase) {
		formHandler = new FormulasHandler(shaderBase);
		setMatdefBase(matDefBase);
	}

	public void clear() {

	}

	public void setMatdefBase(File matdef) {
		if (matdef.exists() && matdef.canRead() && matdef.isFile()) {
			this.matdefBase = matdef;
		}
	}

	public void setMetric(String[] metrictensor) {
	}

	public void writeFormula(String formula) {
		if (formula != null) {
			String[] tempForm = { formula };
			if (formHandler.WriteFormula(tempForm)) {
				writeMat(formHandler.getShaderUpdatedBase());
			}
		}
	}

	public void writeMetric(String string1, String string2, String string3, String string4) {
		boolean entreesValides = true;
		String[] listeDeString = { string1, string2, string3, string4 };

		for (String i : listeDeString) {
			if (i == null || i.length() == 0)
				entreesValides = false;
		}

		if (entreesValides) {

			if (formHandler.WriteFormula(listeDeString)) {
				writeMat(formHandler.getShaderUpdatedBase());
			}
		}
	}

	public void writeChart(String string1, String string2, String string3) {
		boolean entreesValides = true;
		String[] listeDeString = { string1, string2, string3 };

		for (String i : listeDeString) {
			if (i == null || i.length() == 0)
				entreesValides = false;
		}

		if (entreesValides) {
			if (formHandler.WriteFormula(listeDeString)) {
				writeMat(formHandler.getShaderUpdatedBase());
			}
		}
	}

	public void writeMat(File shaderUpdated) {
		try {

			File newMat = File.createTempFile("tempMatDef", ".j3md", getMatdefBase().getParentFile());
			newMat.deleteOnExit();
			setMatdefBaseUpdated(newMat);
			setDataMatDef(OpenFile(getMatdefBase()));

			ArrayList<String> data = getDataMatDef();
			boolean done = false;
			for (int i = 0; i < data.size() && !done; i++) {
				if (data.get(i).contains("FragmentShader")) {
					data.set(i, data.get(i) + shaderUpdated.getName());
					done = true;
				}
			}
			setDataMatDef(data);
			System.out.println("ecriture du fichier");
			writeMatDef(getDataMatDef(), getMatdefBaseUpdated());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeMatDef(ArrayList<String> data, File file) {
		try {
			PrintWriter out = new PrintWriter(getMatdefBaseUpdated());

			for (int i = 0; i < data.size(); i++) {
				out.println(data.get(i));
				System.out.println("L'ecriture des lignes de 0 à " + data.size() + " a été effectuée");
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

	public void updateMetric() {
	}

	public void updateFormula() {
	}

	public ArrayList<String> getDataMatDef() {
		return dataMatDef;
	}

	public void setDataMatDef(ArrayList<String> dataMatDef) {
		this.dataMatDef = dataMatDef;
	}

	public File getMatdefBaseUpdated() {
		return matdefBaseUpdated;
	}

	public void setMatdefBaseUpdated(File matdefBaseUpdated) {
		if (matdefBaseUpdated.exists() && matdefBaseUpdated.canRead() && matdefBaseUpdated.isFile()) {
			this.matdefBaseUpdated = matdefBaseUpdated;
		}
	}

	public File getMatdefBase() {
		return matdefBase;
	}
}