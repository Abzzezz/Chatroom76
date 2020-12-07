/**
Taken from: https://www.shadertoy.com/view/Xltfzj
*/
#version 330

#ifdef GL_ES
precision mediump float;
#endif

#extension GL_OES_standard_derivatives : enable

uniform vec2 resolution;
uniform sampler2D tex;

uniform float weight[5] = float[] (0.227027, 0.1945946, 0.1216216, 0.054054, 0.016216);

void main()
{
    vec2 tex_offset = 1.0 / textureSize(tex, 0);// gets size of single texel
    vec3 result = texture(tex, gl_FragCoord.xy / resolution).rgb * weight[0];// current fragment's contribution

    for (int i = 1; i < 5; ++i)
    {
        result += texture(tex, gl_FragCoord.xy + vec2(0.0, tex_offset.y * i)).rgb * weight[i];
        result += texture(tex, gl_FragCoord.xy - vec2(0.0, tex_offset.y * i)).rgb * weight[i];
    }

    gl_FragColor = vec4(result, 1.0);
}