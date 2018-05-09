package modele;

import com.jme3.math.Matrix3f;
import com.jme3.math.Matrix4f;

public class MatUtils {
	
	public static float scale(float t, float oldMin, float oldMax, float min, float max) {
		return min + ((max - min)/(oldMax - oldMin)) *(t - oldMin);
	}

	public static Matrix4f toDesiredForm(Matrix4f m) {
//		Matrix4f out = new Matrix4f(m.m00, m.m01, m.m03, m.m02, m.m10, m.m11, m.m13, m.m12, m.m20, m.m21, m.m22, m.m23, m.m30, m.m31, m.m32, m.m33);
//		return out;
		return m;
	}

}
