#ifdef GL_ES
precision mediump float;
#endif

#extension GL_OES_standard_derivatives : enable

#define PI 3.1415926538

#define scan_period 2.0

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

uniform sampler2D tex;

float rand(vec2 co, float off)
{
    float a = 12.9898;
    float b = 78.233;
    float c = 43758.5453;
    float dt = dot(co.xy, vec2(a, b));
    float sn = mod(dt, 3.14);
    return fract(sin(sn) * c * (time) + sin(off));
}



void main(void) {
    vec2 position = gl_FragCoord.xy / resolution.xy;

    gl_FragColor += rand(position, 1.0) / 1.0;// noise
    gl_FragColor.r += rand(position, PI) / 20.0;
    gl_FragColor.g += rand(position, PI * 2.0) / 20.0;
    gl_FragColor.b += rand(position, PI * 3.0) / 20.0;

    float scan_pos = mod(time, scan_period) / scan_period;
    float random_height = rand(position, 10.0 * PI) / 80.0 + 0.02;
    if (position.y > scan_pos - random_height && position.y < scan_pos + random_height) gl_FragColor.rgb += vec3(0.05);
}