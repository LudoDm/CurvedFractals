int mandelbrot(vec2 c) {
    vec2 z = c;
    for (int i = 0; i < 300; i++) {
        // dot(z, z) > 4.0 is the same as length(z) > 2.0, but perhaps faster.
        // (x+yi)^2 = (x+yi) * (x+yi) = x^2 + (yi)^2 + 2xyi = x^2 - y^2 + 2xyi

	//Equation (la ligne suivante sera celle qui sera overwrite par l'utilisateur
	//Elle doit mettre en relation la variable d'itération(nombre complexe aka vecteur R2) et la condition initiale (le point a tester) (nb complexe aka vec R2)
        z = vec2(z.x * z.x - z.y * z.y, 2.0 * z.x * z.y) + c;
    }
    return 0;
}

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    // Screen coordinate, roughly -2 to +2
    vec2 uv = (fragCoord.xy*2.0 - iResolution.xy) * 2.0/ iResolution.x;
    vec2 ms = (iMouse.xy*2.0 - iResolution.xy) * 2.0/ iResolution.x;
    vec2 ms2 = (iMouse.zw*2.0) * 2.0/ iResolution.x;
    uv += ms;
    float zm = dot(ms2,ms2);
    uv /= zm;
    // Evaluate mandelbrot for this coordinate.
    float ret = float(mandelbrot(uv));
    
    // Turn the iteration count into a color.
	fragColor = vec4(sin(vec3(0.1, 0.2, 0.5) * ret), 1);
}