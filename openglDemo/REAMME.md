### OpenGL ES 三种类型修饰 uniform attribute varying

1.uniform变量
uniform变量是外部application程序传递给（vertex和fragment）shader的变量。因此它是application通过

函数glUniform**（）函数赋值的。在（vertex和fragment）shader程序内部，uniform变量就像是C语言里面

的常量（const ），它不能被shader程序修改。（shader只能用，不能改）

如果uniform变量在vertex和fragment两者之间声明方式完全一样，则它可以在vertex和fragment共享使用。

（相当于一个被vertex和fragment shader共享的全局变量）

uniform变量一般用来表示：变换矩阵，材质，光照参数和颜色等信息。

以下是例子：

uniform mat4 viewProjMatrix; //投影+视图矩阵
uniform mat4 viewMatrix;        //视图矩阵
uniform vec3 lightPosition;     //光源位置



2.attribute变量
attribute变量是只能在vertex shader中使用的变量。（它不能在fragment shader中声明attribute变量，

也不能被fragment shader中使用）

一般用attribute变量来表示一些顶点的数据，如：顶点坐标，法线，纹理坐标，顶点颜色等。

在application中，一般用函数glBindAttribLocation（）来绑定每个attribute变量的位置，然后用函数

glVertexAttribPointer（）为每个attribute变量赋值。

以下是例子：

uniform mat4 u_matViewProjection;
attribute vec4 a_position;
attribute vec2 a_texCoord0;
varying vec2 v_texCoord;
void main(void)
{
gl_Position = u_matViewProjection * a_position;
v_texCoord = a_texCoord0;
}

3.varying变量
varying变量是vertex和fragment shader之间做数据传递用的。一般vertex shader修改varying变量的值，

然后fragment shader使用该varying变量的值。因此varying变量在vertex和fragment shader二者之间的声

明必须是一致的。application不能使用此变量。

以下是例子：

// Vertex shader
uniform mat4 u_matViewProjection;
attribute vec4 a_position;
attribute vec2 a_texCoord0;
varying vec2 v_texCoord; // Varying in vertex shader
void main(void)
{
gl_Position = u_matViewProjection * a_position;
v_texCoord = a_texCoord0;
}


// Fragment shader
precision mediump float;
varying vec2 v_texCoord; // Varying in fragment shader
uniform sampler2D s_baseMap;
uniform sampler2D s_lightMap;
void main()
{
vec4 baseColor;
vec4 lightColor;
baseColor = texture2D(s_baseMap, v_texCoord);
lightColor = texture2D(s_lightMap, v_texCoord);
gl_FragColor = baseColor * (lightColor + 0.25);
}


### GLSL中的数据类型主要分为标量、向量、矩阵、采样器、结构体、数组、空类型七种类型：
* 标量：标量表示的是只有大小没有方向的量，在GLSL中标量只有bool、int和float三种。对于int，和C一样，可以写为十进制（16）、八进制（020）或者十六进制（0x10）。关于进制不了解的，得自己补补，这是编程基础。对于标量的运算，我们最需要注意的是精度，防止溢出问题。
* 向量：向量我们可以看做是数组，在GLSL通常用于储存颜色、坐标等数据，针对维数，可分为二维、三维和四位向量。针对存储的标量类型，可以分为bool、int和float。共有vec2、vec3、vec4，ivec2、ivec3、ivec4、bvec2、bvec3和bvec4九种类型，数组代表维数、i表示int类型、b表示bool类型。需要注意的是，GLSL中的向量表示竖向量，所以与矩阵相乘进行变换时，矩阵在前，向量在后（与DirectX正好相反）。向量在GPU中由硬件支持运算，比CPU快的多。

    * 作为颜色向量时，用rgba表示分量，就如同取数组的中具体数据的索引值。三维颜色向量就用rgb表示分量。比如对于颜色向量vec4 color，color[0]和color.r都表示color向量的第一个值，也就是红色的分量。其他相同。
    * 作为位置向量时，用xyzw表示分量，xyz分别表示xyz坐标，w表示向量的模。三维坐标向量为xyz表示分量，二维向量为xy表示分量。
    * 作为纹理向量时，用stpq表示分量，三维用stp表示分量，二维用st表示分量。
* 矩阵：在GLSL中矩阵拥有2*2、3*3、4*4三种类型的矩阵，分别用mat2、mat3、mat4表示。我们可以把矩阵看做是一个二维数组，也可以用二维数组下表的方式取里面具体位置的值。

* 采样器：采样器是专门用来对纹理进行采样工作的，在GLSL中一般来说，一个采样器变量表示一副或者一套纹理贴图。所谓的纹理贴图可以理解为我们看到的物体上的皮肤。
* 结构体：和C语言中的结构体相同，用struct来定义结构体，关于结构体参考C语言中的结构体。
* 数组：数组知识也和C中相同，不同的是数组声明时可以不指定大小，但是建议在不必要的情况下，还是指定大小的好。
* 空类型：空类型用void表示，仅用来声明不返回任何值得函数。
