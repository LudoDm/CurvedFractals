package modele;

public class MatUtils {
	
	public static float scale(float t, float oldMin, float oldMax, float min, float max) {
		return min + ((max - min)/(oldMax - oldMin)) *(t - oldMin);
	}

}
