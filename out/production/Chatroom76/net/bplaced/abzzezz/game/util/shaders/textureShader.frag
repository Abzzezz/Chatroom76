#version 330

#ifdef GL_ES
precision mediump float;
#endif

#extension GL_OES_standard_derivatives : enable

uniform vec2 resolution;
uniform sampler2D tex;
uniform float opacity;

void main(void) {
    gl_FragColor = texture(tex, gl_FragCoord.xy / resolution).rgba;
    gl_FragColor.a *= opacity;
}