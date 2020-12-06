#version 330 core
in vec3 position;

uniform vec2 pos;

void main()
{
    gl_Position = vec4(position + vec3(pos, 0), 1);
}