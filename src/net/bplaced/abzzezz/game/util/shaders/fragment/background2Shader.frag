#ifdef GL_ES
precision mediump float;
#endif

uniform float time;
//uniform vec2 mouse;
uniform vec2 resolution;

#extension GL_OES_standard_derivatives : enable
#define CHS 0.18
float sdBox2(in vec2 p, in vec2 b) { vec2 d=abs(p)-b;return length(max(d, vec2(0))) + min(max(d.x, d.y), 0.0); }
float line2(float d, vec2 p, vec4 l){ vec2 pa=p-l.xy;vec2 ba=l.zw-l.xy;float h=clamp(dot(pa, ba)/dot(ba, ba), 0.0, 1.0);return min(d, length(pa-ba*h)*(.2*(1.-gl_FragCoord.y/resolution.y)*sin(time-gl_FragCoord.x/7.)+.05+length(pa.x-ba.x*h)*7.)); }
float line22(float d, vec2 p, vec4 l){ vec2 pa=p-l.xy;vec2 ba=l.zw-l.xy;float h=clamp(dot(pa, ba)/dot(ba, ba), 0.0, 1.0);return min(d, length(pa-ba*h)); }
float TB(vec2 p, float d){ p.y=abs(p.y);return line22(d, p, vec4(2, 3.25, -2, 3.25)*CHS); }
float B(vec2 p, float d){ d=line2(d, p, vec4(-2, 3.25, -2, -3.25)*CHS); p.y+=1.75*CHS;    d=min(d, abs(sdBox2(p, vec2(2.0, 1.5)*CHS))); p+=vec2(0.5, -3.25)*CHS; return min(d, abs(sdBox2(p, vec2(1.5, 1.75)*CHS))); }
float E(vec2 p, float d){ d=TB(p, d);d=line2(d, p, vec4(-2, 3.25, -2, -3.25)*CHS);return line22(d, p, vec4(0, -0.25, -2, -0.25)*CHS); } float I(vec2 p, float d){ d=line2(d, p, vec4(0, -3.25, 0, 3.25)*CHS);return d; } float R(vec2 p, float d){ d=line2(d, p, vec4(0.5, -0.25, 2, -3.25)*CHS);d=line2(d, p, vec4(-2, -3.25, -2, 3.25)*CHS);p.y-=1.5*CHS;return min(d, abs(sdBox2(p, vec2(2.0, 1.75)*CHS))); } float T(vec2 p, float d){ d=line2(d, p, vec4(0, -3.25, 0, 3.25)*CHS);return line22(d, p, vec4(2, 3.25, -2, 3.25)*CHS); } float X(vec2 p, float d){ d = line2(d, p, vec4(-2, 3.25, 2, -3.25)*CHS);return line2(d, p, vec4(-2, -3.25, 2, 3.25)*CHS); }

float GetText(vec2 uv)
{
    uv.x += 1.75;
    float d = 2000.0;

    d = E(uv, d);uv.x -= 1.1;
    d = X(uv, d);    uv.x -= .8;
    d = I(uv, d);uv.x -= .75;
    d = T(uv, d);
    return smoothstep(0.0, 0.08, d-0.312*CHS);
}

void main(void) {
    vec2 position = (gl_FragCoord.xy / resolution.xy);
    gl_FragColor = vec4(1.- (GetText(position*vec2(8., 4.)-vec2(4., 2.)))*(1.3+   .25*sin(position.y*60.+time*6.+abs(sin(position.x*120.)))), 0., 0., 1.);
}