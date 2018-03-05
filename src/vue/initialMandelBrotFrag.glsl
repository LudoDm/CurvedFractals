
uniform vec4 m_ColorMin;
uniform vec4 m_ColorMax;
uniform vec2 m_Resolution;
uniform mat4 m_Zoom;
uniform vec2 m_Translat;

out vec4 color;

int mandelbrot(vec2 c) {
    vec2 z = c;
    for (int i = 0; i < 400; i++) {
        // dot(z, z) > 4.0 is the same as length(z) > 2.0, but perhaps faster.
        // (x+yi)^2 = (x+yi) * (x+yi) = x^2 + (yi)^2 + 2xyi = x^2 - y^2 + 2xyi
    	if (dot(z, z) > 4.0) return i;
	//Equation (la ligne suivante sera celle qui sera overwrite par l'utilisateur
	//Elle doit mettre en relation la variable d'it�ration(nombre complexe aka vecteur R2) et la condition initiale (le point a tester) (nb complexe aka vec R2)
    z = vec2(z.x * z.x - z.y * z.y, 2.0 * z.x * z.y) + c;

    }
    return 0;
}

vec4 Image(vec2 f) {
    // Screen coordinate, roughly -2 to +2
    vec2 uv = (f.xy*2.0 - m_Resolution.xy) * 2.0/ m_Resolution.x;
    //vec2 ms = (iMouse.xy*2.0 - iResolution.xy) * 2.0/ iResolution.x;
    //vec2 ms2 = (iMouse.zw*2.0) * 2.0/ iResolution.x;
    //uv += ms;
   // float zm = dot(ms2,ms2);
    //uv /= zm;
    // Evaluate mandelbrot for this coordinate.
	vec4 pos = m_Zoom * vec4(uv.x,uv.y,0,0);

    float ret = float(mandelbrot(pos.xy));

    // Turn the iteration count into a color.
	//Pour une raison inconnue (triste Jmonkey...), la couleur change avec le Alpha, et n'est plus aucunement la couleur voulue.
	//Donc le alpha doit toujours rester 1!!
	vec4 couleurfinale;
	if (ret == 0.0) {
		couleurfinale = vec4(0.0,0.0,0.0,1.0);
	} else {
		couleurfinale = mix(sin(vec4(0.1,0.2,0.5,1.0)), mix(m_ColorMax,m_ColorMin, ret), ret/400);
	}
	return couleurfinale;
}


void main(){
	vec4 col = Image(gl_FragCoord.xy);
	color = col;
}
