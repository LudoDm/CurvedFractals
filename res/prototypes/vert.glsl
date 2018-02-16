
in vec3 inPosition;
uniform mat4 g_WorldViewProjectionMatrix;

void main(){
	gl_Position = g_WorldViewProjectionMatrix*vec4(inPosition.zxy,1.0);
}
