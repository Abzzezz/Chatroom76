#version 120
//in vec3 position;

void main()
{
    gl_Position =  gl_ModelViewProjectionMatrix * gl_Vertex;
}