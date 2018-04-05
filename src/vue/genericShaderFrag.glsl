precision highp float;

uniform vec4 m_ColorMin;
uniform vec4 m_ColorMax;
uniform vec2 m_Resolution;
uniform mat4 m_Zoom;
uniform vec2 m_Translat;

out vec4 color;

struct MxR3 {
	vec2 pM;
	vec3 pR3;
};



vec3 chart2(vec2 p) {
	vec3 a = vec3(-2.0*p.x / (p.x*p.x + p.y*p.y -1.),-2.0*p.y / (p.x*p.x + p.y*p.y -1.),1.0*(1. + (p.x*p.x + p.y*p.y))/(1. - (p.x*p.x + p.y*p.y)));
	return a;
}

MxR3 section(vec2 p){
	vec3 r3 = chart2(p);
	MxR3 tangent = MxR3(p, r3);
	return tangent;
}

vec2 projection(MxR3 tm){
	return tm.pM;
}

mat2 metric(vec2 p) {
	float g11 = 4./pow(p.x*p.x + p.y*p.y -1.0,2.);
	//float g11 = 1.0;
	float g21 = 0.;
	float g12 = 0.;
	//float g22 = -1.0/sin(p.x+ time);
	float g22 = 4./pow(p.x*p.x + p.y*p.y -1.,2.);
	mat2 g = mat2(g11,g21,g12,g22);
	return g;
}

mat2 inverseMetric(vec2 p){
	return inverse(metric(p));
}

float innerProdAt(vec2 p, vec2 a, vec2 b){
	mat2 g = metric(p);
	return g[0][0]*a.x*b.x + g[1][0]*a.y*b.x + g[0][1]*a.x*b.y + g[1][1]*a.y*b.y;
}

float inverseinnerProdAt(vec2 p, vec2 a, vec2 b){
	mat2 g = inverseMetric(p);
	return g[0][0]*a.x*b.x + g[1][0]*a.y*b.x + g[0][1]*a.x*b.y + g[1][1]*a.y*b.y;
}

float inducedInnerProdAt(vec2 p, MxR3 a, MxR3 b){
	return innerProdAt(p, projection(a), projection(b));
}

float inverseinducedInnerProdAt(vec2 p, MxR3 a, MxR3 b){
	return inverseinnerProdAt(p, projection(a), projection(b));
}

MxR3 gNormalize( MxR3 a){
	a.pR3 /= sqrt(inducedInnerProdAt(projection(a),a,a));
	return a;
}

MxR3 normal(vec2 p) {
	MxR3 n;
	vec3 n1in = (chart2(vec2(p.x+0.01, p.y)) - chart2(p));
	MxR3 n1 = MxR3(p,n1in);
	vec3 n2in = chart2(vec2(p.x, p.y+0.01)) - chart2(p);
	MxR3 n2 = MxR3(p,n2in);
	n = MxR3(p,cross(n1.pR3,n2.pR3));
	n.pR3 = normalize(n.pR3);
	//n.pR3 = gNormalize(n).pR3;
	return n;
}

float Gamma(vec2 p, int i, int j, int k){
	float resTemp = 0.;
	for(int i = 0; i < 3; i++){

	}

	return 0.;
}


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

//le tenseur metrique qui s'evalue a chaque point de la surface sur laquelle se trouve la fractale
//chaque composante de la matrice doit etre une fonction qui, soit ne depend de rien (genre un nombre, ex g11(x,y) = 1), mais avec

mat2 metricTensor(in vec2 p){
	float x = p.x;
	float y = p.y;
	float g11 = 1.0;
	float g21 = 0.0;
	float g12 = 0.0;
	float g22 = 1.0;
	mat2 g = mat2(g11, g21, g12, g22);
	//on doit ecrire "\tg11 = " + "FONCTION g11 qui depend de x et/ou y ou ne depend de rien" + ";"
	//OUTPUT_METRIC_G11_NEXT_LINE

	if(!isinf(g11) && !isnan(g11)){
		g[0][0] = g11;
	}


	//on doit ecrire "\tg21 = " + "FONCTION g21 qui depend de x et/ou y ou ne depend de rien" + ";"
	//OUTPUT_METRIC_G21_NEXT_LINE

	if(!isinf(g21) && !isnan(g21)){
		g[1][0] = g21;
	}



	//on doit ecrire "\tg12 = " + "FONCTION g12 qui depend de x et/ou y ou ne depend de rien" + ";"
	//OUTPUT_METRIC_G12_NEXT_LINE

	if(!isinf(g12) && !isnan(g12)){
		g[0][1] = g12;
	}



	//on doit ecrire "\tg22 = " + "FONCTION g22 qui depend de x et/ou y ou ne depend de rien" + ";"
	//OUTPUT_METRIC_G22_NEXT_LINE

	if(!isinf(g22) && !isnan(g22)){
		g[1][1] = g22;
	}



	return g;
}

//la parametrisation (fonction de M -> R3, M etant la surface sur laquelle la fractale est definie et R3 l'espace selon lequel on observe la fractale) (on a que M est une sous variete de R3, l'hyper surface de M)
//la fonction x(u,v) prend des parametres u et v reel entre -2 et 2 inclu et doit etre definit partout sur cet interval (chacune des 3 fonctions de composantes doit l'etre)
//on doit donc le specifier a lutilisateur quil doit rentrer des fonctions de ce type.
vec3 chart(in vec2 pM){
	float u = pM.x;
	float v = pM.y;

	float a = u;
	float b = v;
	float c = 1;

	vec3 pR3 = vec3 (u, v, 1);

	//on doit ecrire "\ta = " + "FONCTION 1 de x(u,v) (le premier textField) qui depend soit de rien (genre un nombre), de u et/ou de v" + ";"
	//OUTPUT_CHART_FUNCTION_1_NEXT_LINE

	if(!isinf(a) && !isnan(a)){
		pR3.x = a;
	}


	//on doit ecrire "\tb = " + "FONCTION 2 de x(u,v) (le deuxieme textField) qui depend soit de rien (genre un nombre), de u et/ou de v" + ";"
	//OUTPUT_CHART_FUNCTION_2_NEXT_LINE

	if(!isinf(b) && !isnan(b)){
		pR3.y = b;
	}


	//on doit ecrire "\tc = " + "FONCTION 3 de x(u,v) (le troisieme textField) qui depend soit de rien (genre un nombre), de u et/ou de v" + ";"
	//OUTPUT_CHART_FUNCTION_3_NEXT_LINE

	if(!isinf(c) && !isnan(c)){
		pR3.z = c;
	}

	return pR3;
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
	//coord entre -1 et 1
	vec2 uv = (2.0 * f.xy - m_Resolution) / m_Resolution.x;
	//vec2 uv = (2.0 * f.xy - m_Resolution) / max(m_Resolution.x,m_Resolution.y);
	vec4 c = vec4(uv.x,uv.y,0,0);

	vec4 transInit = vec4(0.,0.0,0,0);
	vec2 translation = (m_Translat*2.0 - m_Resolution) /m_Resolution.x;
	vec4 t = vec4(translation.x,translation.y,0,0);
	vec4 transtot = transInit + t;

	c -= transtot;
	c *= m_Zoom;
	c+= transtot;

	float ret = mandelbrot(0.4*chart2(1.*c.xy + vec2(-0.,-0.)).xy  + 1.*normal(c.xy).pR3.xy );


	// Turn the iteration count into a color.
	//Pour une raison inconnue (triste Jmonkey...), la couleur change avec le Alpha, et n'est plus aucunement la couleur voulue.
	//Donc le alpha doit toujours rester 1!!
	vec4 couleurfinale;
	vec4 couleurfinaletest;
	if (ret == 0.0) {
		couleurfinale = vec4(0.0,0.0,0.0,1.0);
	} else {
	//	couleurfinale = mix(sin(vec4(0.1,0.2,0.5,1.0)), mix(m_ColorMin, m_ColorMax, ret), ret/400);
		couleurfinale = mix(m_ColorMin, m_ColorMax, sin(ret));
		//couleurfinale = sin(mix(m_ColorMin, m_ColorMax, ret/30));
	}
		couleurfinaletest = mix(couleurfinale, vec4(normal(c.xy).pR3.xy,1.*normal(c.xy).pR3.z, 1.0), 2.9 );
	if((uv.x>-0.001 && uv.x < 0.001) || (uv.y > -0.001 && uv.y < 0.001) || (c.x > -1.01 && c.x < -0.99)|| (c.y > -1.01 && c.y < -0.99) || (c.x < 1.01 && c.x > 0.99)|| (c.y < 1.01 && c.y > 0.99)){
		return vec4(1.,0.,1.,1.);
	}
	return couleurfinaletest;
}

void main() {
	vec4 col = Image(gl_FragCoord.xy);
	color = col;
}
