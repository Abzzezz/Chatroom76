#ifdef GL_ES
precision mediump float;
#endif

#extension GL_OES_standard_derivatives : enable

#define PI 3.1415926538

#define scan_period 8.0
#define iChannel0 video

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

uniform sampler2D video;


float rand(vec2 co, float off){
	return fract(sin(dot(co.xy, vec2(12.9898,78.233))) * 43758.5453 * cos(time) + sin(off));
}

vec4 texture( sampler2D  s, vec2 c)  { return texture2D(s,c); }

void main( void ) {
	vec2 position = gl_FragCoord.xy / resolution.xy;

	vec3 vec = vec3(texture(iChannel0, vec2(1, 1)));


	gl_FragColor += rand(position, 0.0) / 2.0; // noise
	gl_FragColor.r += rand(position, PI) / 20.0;
	gl_FragColor.g += rand(position, PI * 2.0) / 20.0;
	gl_FragColor.b += rand(position, PI * 3.0) / 20.0;

	float scan_pos = mod(time, scan_period) / scan_period;
	float random_height = rand(position, 10.0 * PI) / 80.0 + 0.02;
	if(position.y > scan_pos - random_height && position.y < scan_pos + random_height)
	gl_FragColor.rgb += vec4(vec, 1.0);
}