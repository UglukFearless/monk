
// Fragment Shader

precision mediump float;

//"in" attributes from our vertex shader
varying vec4 vColor;
varying vec2 vTexCoord;


//our different texture units
uniform sampler2D u_texture; //default GL_TEXTURE0, expected by SpriteBatch
uniform vec4 ourColor;

void main(void) {
	//sample the colour from the first texture
	vec4 texColor0 = texture2D(u_texture, vTexCoord);
    vec4 texColor1 = ourColor;

	//interpolate the colours based on the mask
	gl_FragColor = vColor*vec4(ourColor.rgb, texColor0.a);
}