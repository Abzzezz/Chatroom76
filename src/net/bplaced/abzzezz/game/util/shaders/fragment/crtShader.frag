#ifdef GL_ES
precision mediump float;
#endif

#extension GL_OES_standard_derivatives : enable

uniform sampler2D tex;
uniform float time;
uniform vec2 resolution;
uniform float alpha;

vec2 curve(vec2 uv)
{
    uv = (uv - 0.5) * 2.0;
    uv *= 1.1;
    uv.x *= 1.0 + pow((abs(uv.y) / 5.0), 2.0);
    uv.y *= 1.0 + pow((abs(uv.x) / 4.0), 2.0);
    uv  = (uv / 2.0) + 0.5;
    uv =  uv * 0.92 + 0.04;
    return uv;
}

void mainImage(out vec4 fragColor, in vec2 fragCoord)
{
    vec2 q = fragCoord.xy / resolution.xy;
    vec2 uv = q;
    uv = curve(uv);
    vec3 col;
    float x = sin(0.3*time+uv.y*21.0)*sin(0.7*time+uv.y*29.0)*sin(0.3+0.33*time+uv.y*31.0)*0.0017;


    col.r += 0.5;
    col.g += 0.5;
    col.b += 0.5;

    col = clamp(col*0.6+0.4*col*col*1.0, 0.0, 1.0);

    float vig = (0.0 + 1.0*16.0*uv.x*uv.y*(1.0-uv.x)*(1.0-uv.y));
    col *= vec3(pow(vig, 0.3));

    col *= vec3(0.95, 1.05, 0.95);
    col *= 2.8;

    float scans = clamp(0.35+0.35*sin(3.5*time+uv.y*resolution.y*1.5), 0.0, 1.0);

    float s = pow(scans, 1.7);
    col = col*vec3(0.4+0.7*s);

    col *= 1.0+0.01*sin(110.0*time);
    if (uv.x < 0.0 || uv.x > 1.0)
    col *= 0.0;
    if (uv.y < 0.0 || uv.y > 1.0)
    col *= 0.0;

    col*=1.0-0.65*vec3(clamp((mod(fragCoord.x, 2.0)-1.0)*2.0, 0.0, 1.0));

    float comp = smoothstep(0.1, 0.9, sin(time));

    // Remove the next line to stop cross-fade between original and postprocess
    //	col = mix( col, oricol, comp );
    fragColor = vec4(col, 1.0);
    fragColor.a = alpha;
}

void main() {
    mainImage(gl_FragColor, gl_FragCoord.xy);
}