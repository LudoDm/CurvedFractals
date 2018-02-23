
in vec3 inPosition;
uniform mat4 g_WorldViewProjectionMatrix;

void main(){
	//gl_Position = vec4(inPosition.zxy,0.0);
	gl_Position = g_WorldViewProjectionMatrix*vec4(inPosition.xyz,1);
}
