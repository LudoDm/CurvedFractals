precision highp float;

uniform vec4 m_ColorMin;
uniform vec4 m_ColorMax;
uniform vec2 m_Resolution;
uniform mat4 m_Zoom;
uniform vec2 m_Translat;

out vec4 color;

vec2 ccjg(in vec2 c) {
	return vec2(c.x, -c.y);
}

vec2 cmul(in vec2 a, in vec2 b) {
	return vec2(a.x * b.x - a.y * b.y, a.y * b.x + a.x * b.y);
}

vec2 cpow(in vec2 c, int p) {
	for (int i = 1; i < p; ++i) {
		c = cmul(c, c);
	}
	return c;
}

vec2 cdiv(in vec2 a, in vec2 b) {
	return cmul(a, ccjg(b));
}

float cnorm(in vec2 c) {
	return sqrt(c.x * c.x + c.y * c.y);
}

int mandelbrot(vec2 c) {
	vec2 z = c;
	for (int i = 0; i < 1000; i++) {
		// dot(z, z) > 4.0 is the same as length(z) > 2.0, but perhaps faster.
		// (x+yi)^2 = (x+yi) * (x+yi) = x^2 + (yi)^2 + 2xyi = x^2 - y^2 + 2xyi
		if (dot(z, z) > 4.0)
			return i;
		//Equation (la ligne suivante sera celle qui sera overwrite par l'utilisateur
		//Elle doit mettre en relation la variable d'it?ration(nombre complexe aka vecteur R2) et la condition initiale (le point a tester) (nb complexe aka vec R2)
		//OUTPUT_EQ_NEXT_LINE

	}
	return 0;
}

vec4 Image(vec2 f) {
	// Screen coordinate, roughly -2 to +2
	vec2 uv = (f.xy * 2.0 - m_Resolution.xy) * 2.0 / m_Resolution.x;
	vec4 c = vec4(uv.x,uv.y,0,0);

	vec4 transInit = vec4(2.,1.5,0,0);
	vec2 translation = (m_Translat*2.0 - m_Resolution) *2.0 /m_Resolution.x;
	vec4 t = vec4(translation.x,translation.y,0,0);
	vec4 transtot = transInit + t;

	c -= transtot;
	c *= m_Zoom;
	c+= transtot;

	float ret = float(mandelbrot(c.xy));

	// Turn the iteration count into a color.
	//Pour une raison inconnue (triste Jmonkey...), la couleur change avec le Alpha, et n'est plus aucunement la couleur voulue.
	//Donc le alpha doit toujours rester 1!!
	vec4 couleurfinale;
	if (ret == 0.0) {
		couleurfinale = vec4(0.0,0.0,0.0,1.0);
	} else {
		couleurfinale = mix(sin(vec4(0.1,0.2,0.5,1.0)), mix(m_ColorMin, m_ColorMax, ret), ret/400);
		//couleurfinale = sin(mix(m_ColorMin, m_ColorMax, ret/30));
	}
	return couleurfinale;
}

void main() {
	vec4 col = Image(gl_FragCoord.xy);
	color = col;
}
