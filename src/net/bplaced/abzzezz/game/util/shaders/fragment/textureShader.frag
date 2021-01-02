#version 330

#ifdef GL_ES
precision mediump float;
#endif

uniform vec2 resolution;
uniform sampler2D tex;
uniform float opacity;

void main(void) {
    gl_FragColor = texture(tex, gl_FragCoord.xy / resolution.xy).rgba;
    gl_FragColor.a *= opacity;
}