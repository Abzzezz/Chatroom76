#ifdef GL_ES
precision mediump float;
#endif

#extension GL_OES_standard_derivatives : enable

#define iTime time
#define iResolution resolution
#define iChannel0 noiseTexture
#define iChannel1 video

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;
uniform sampler2D noiseTexture;
uniform sampler2D video;


vec4 texture(sampler2D  s, vec2 c)  { return texture2D(s, c); }


float noise(vec2 p)
{
    float s = texture(iChannel1, vec2(1., 2.*cos(iTime))*iTime*8. + p*1.).x;
    s *= s;
    return s;
}

float onOff(float a, float b, float c)
{
    return step(c, sin(iTime + a*cos(iTime*b)));
}

float ramp(float y, float start, float end)
{
    float inside = step(start, y) - step(end, y);
    float fact = (y-start)/(end-start)*inside;
    return (1.-fact) * inside;

}

float stripes(vec2 uv)
{

    float noi = noise(uv*vec2(0.5, 1.) + vec2(1., 3.));
    return ramp(mod(uv.y*4. + iTime/2.+sin(iTime + sin(iTime*0.63)), 1.), 0.5, 0.6)*noi;
}

vec3 getVideo(vec2 uv)
{
    vec2 look = uv;
    float window = 1./(1.+20.*(look.y-mod(iTime/4., 1.))*(look.y-mod(iTime/4., 1.)));
    look.x = look.x + sin(look.y*10. + iTime)/50.*onOff(4., 4., .3)*(1.+cos(iTime*80.))*window;
    float vShift = 0.4*onOff(2., 3., .9)*(sin(iTime)*sin(iTime*20.) +
    (0.5 + 0.1*sin(iTime*200.)*cos(iTime)));
    look.y = mod(look.y + vShift, 1.);
    vec3 video = vec3(texture(iChannel0, look));

    return video;
}

vec2 screenDistort(vec2 uv)
{
    uv -= vec2(.5, .5);
    uv = uv*1.2*(1./1.2+2.*uv.x*uv.x*uv.y*uv.y);
    uv += vec2(.5, .5);
    return uv;
}

float normpdf(in float x, in float sigma)
{
    return 0.39894*exp(-0.5*x*x/(sigma*sigma))/sigma;
}


void mainImage(out vec4 fragColor, in vec2 fragCoord)
{
    vec2 uv = fragCoord.xy / iResolution;
    uv = screenDistort(uv);
    vec3 video = getVideo(uv);
    float vigAmt = 3.+.3*sin(iTime + 5.*cos(iTime*5.));
    float vignette = (1.-vigAmt*(uv.y-.5)*(uv.y-.5))*(1.-vigAmt*(uv.x-.5)*(uv.x-.5));
    vec3 c = texture(iChannel0, fragCoord.xy / iResolution.xy).rgb;
    //declare stuff
    const int mSize = 11;
    const int kSize = (mSize-1)/2;
    float kernel[mSize];
    vec3 final_colour = vec3(0.0);

    //create the 1-D kernel
    float sigma = 7.0;
    float Z = 0.0;
    for (int j = 0; j <= kSize; ++j)
    {
        kernel[kSize+j] = kernel[kSize-j] = normpdf(float(j), sigma);
    }

    //get the normalization factor (as the gaussian has been clamped)
    for (int j = 0; j < mSize; ++j)
    {
        Z += kernel[j];
    }

    //read out the texels
    for (int i=-kSize; i <= kSize; ++i)
    {
        for (int j=-kSize; j <= kSize; ++j)
        {
            final_colour += kernel[kSize+j]*kernel[kSize+i]*texture(iChannel0, (fragCoord.xy+vec2(float(i), float(j))) / iResolution.xy).rgb;

        }
    }

    video *= final_colour / (Z*Z);
    video += noise(uv*2.)/2.;
    video *= vignette;
    video *= (12.+mod(uv.y*32.+iTime, 1.))/13.;

    fragColor = vec4(video, 1.0);
}

void main(void) {
    mainImage(gl_FragColor, gl_FragCoord.xy);
}