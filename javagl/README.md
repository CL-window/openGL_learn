OpenGL in java demo
google api  : https://developer.android.com/guide/topics/graphics/opengl.html
from  http://blog.csdn.net/mapdigit/article/details/7526495

1.base render
2.1 Vertex （顶点） 顶点是3D建模时用到的最小构成元素，顶点定义为两条或是多条边交会的地方

定义了四个顶点和对应的Android 顶点定义:
    private float vertices[] = {
     -1.0f,  1.0f, 0.0f,  // 0, Top Left
     -1.0f, -1.0f, 0.0f,  // 1, Bottom Left
     1.0f, -1.0f, 0.0f,  // 2, Bottom Right
     1.0f,  1.0f, 0.0f,  // 3, Top Right
    };
    为了提高性能，通常将这些数组存放到java.io 中定义的Buffer类中：
    // a float is 4 bytes, therefore we multiply the number if vertices with 4.
    ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
    vbb.order(ByteOrder.nativeOrder());
    FloatBuffer vertexBuffer = vbb.asFloatBuffer();
    vertexBuffer.put(vertices);
    vertexBuffer.position(0);
2.2 Edge（边） 边定义为两个顶点之间的线段。边是面和多边形的边界线
2.3 Face (面） 在OpenGL ES中，面特指一个三角形，由三个顶点和三条边构成
    定义面的顶点的顺序很重要  因为顶点的顺序定义了面的朝向（前向或是后向），为了获取绘制的高性能，一般情况不会绘制面的前面和后面，
    只绘制面的“前面”。虽然“前面”“后面”的定义可以应人而易，但一般为所有的“前面”定义统一的顶点顺序(顺时针或是逆时针方向）。
    gl.glFrontFace(GL10.GL_CCW); // 设置逆时针方法为面的“前面”
    gl.glEnable(GL10.GL_CULL_FACE); // 打开 忽略“后面”设置
    gl.glCullFace(GL10.GL_BACK); // 明确指明“忽略“哪个面
2.4 Polygon （多边形） 多边形由多个面（三角形）拼接而成，在三维空间上，多边形并一定表示这个Polygon在同一平面上
    对定义了一个正方形  应的顶点和buffer 定义代码   (0,1,2) (0,2,3)
    private short[] indices = { 0, 1, 2, 0, 2, 3 };
    // short is 2 bytes, therefore we multiply the number if vertices with 2.
    ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
    ibb.order(ByteOrder.nativeOrder());
    ShortBuffer indexBuffer = ibb.asShortBuffer();
    indexBuffer.put(indices);
    indexBuffer.position(0);
2.5 Render (渲染）
    OpenGL ES提供了两类方法来绘制一个空间几何图形：
    public abstract void glDrawArrays(int mode, int first, int count) 使用VertexBuffer 来绘制，顶点的顺序由vertexBuffer中的顺序指定。
    public abstract void glDrawElements(int mode, int count, int type, Buffer indices) ，可以重新定义顶点的顺序，顶点的顺序由indices Buffer 指定。
    # 同样的顶点，可以定义的几何图形可以有所不同，比如三个顶点，可以代表三个独立的点，也可以表示一个三角形，这就需要使用mode 来指明所需绘制的几何图形的基本类型。
    mode :
    GL_POINTS  绘制独立的点
    GL_LINE_STRIP  绘制一系列线段
    GL_LINE_LOOP   类同上，但是首尾相连，构成一个封闭曲线
    GL_LINES   顶点两两连接，为多条线段构成
    GL_TRIANGLES   每隔三个顶点构成一个三角形，为多个三角形组成
    GL_TRIANGLE_STRIP   每相邻三个顶点组成一个三角形，为一系列相接三角形构成
    GL_TRIANGLE_FAN     以一个点为三角形公共顶点，组成一系列相邻的三角形

3   OpenGL使用了右手坐标系统，右手坐标系判断方法：在空间直角坐标系中，让右手拇指指向x轴的正方向，食指指向y轴的正方向，如果中指能指向z轴的正方向（面向自己）
from  http://blog.csdn.net/mapdigit/article/details/7526823
3.1 Translate 平移变换
    public abstract void glTranslatef (float x, float y, float z) 用于坐标平移变换。
    可以进行多次平移变换，其结果为多个平移矩阵的累计结果，矩阵的顺序不重要，可以互换
3.2 Rotate 旋转
    ublic abstract void glRotatef(float angle, float x, float y, float z)用来实现选择坐标变换，单位为角度。
    (x,y,z)定义旋转的参照矢量方向。多次旋转的顺序非常重要
    旋转后 坐标 系 的方向 跟着变化，意思就是以旋转物体本身为坐标系
    旋转都是有方向的，
    如果 gl.glFrontFace(GL10.GL_CCW); // 设置逆时针方法为面的“前面”
    那么旋转时以逆时针旋转
    ＃ 旋转变换glRotatef(angle, -x, -y, -z) 和glRotatef(-angle, x, y, z)是等价的
3.3 Translate & Rotate （平移和旋转组合变换）
    不同次序会变换不太图形
    一个基本原则是，坐标变换都是相对于变换的Mesh本身的坐标系而进行的
3.4 Scale（缩放）
    public abstract void glScalef (float x, float y, float z)用于缩放变换。
    gl.glScalef(2f, 2f, 2f) 变换后的基本，相当于把每个坐标值都乘以2.
    ＃ 缩放后，自身的坐标尺度 相对 标准界面 也要缩放
3.5 矩阵操作，单位矩阵
    在进行平移，旋转，缩放变换时，所有的变换都是针对当前的矩阵（与当前矩阵相乘），如果需要将当前矩阵回复最初的无变换的矩阵，可以使用单位矩阵（无平移，缩放，旋转）。
    public abstract void glLoadIdentity()。
    在栈中保存当前矩阵和从栈中恢复所存矩阵，可以使用
    public abstract void glPushMatrix()
    public abstract void glPopMatrix()
4 Mesh（网格）添加颜色  http://blog.csdn.net/mapdigit/article/details/7526831
    OpenGL ES使用颜色是我们熟知的RGBA模式（红，绿，蓝，透明度）
    在OpenGL 中是使用0…1之间的浮点数表示。 0为0，1相当于255（0xFF)
4.1 Flat coloring（单色）
    public abstract void glColor4f(float red, float green, float blue, float alpha)。
    缺省的red,green,blue为1，代表白色。
4.2 Smooth coloring （平滑颜色过渡）
    当给每个顶点定义一个颜色时，OpenGL自动为不同顶点颜色之间生成中间过渡颜色（渐变色）
5 使用位图来给Mesh上色（渲染材质） http://blog.csdn.net/mapdigit/article/details/7527746
    首先需要构造用来渲染的Bitmap对象，Bitmap对象可以从资源文件中读取或是从网络下载或是使用代码构造
5.1 创建材质(Generating a texture)
    使用OpenGL库创建一个材质(Texture)，首先是获取一个Texture Id
    // Create an int array with the number of textures we want,
    // in this case 1.
    int[] textures = new int[1];
    // Tell OpenGL to generate textures.
    gl.glGenTextures(1, textures, 0);
5.2 textures中存放了创建的Texture ID，使用同样的Texture Id ，也可以来删除一个Texture：
    // Delete a texture.
    gl.glDeleteTextures(1, textures, 0)
5.3 有了Texture Id之后，就可以通知OpenGL库使用这个Texture
    gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
5.4 设置Texture参数glTexParameter
    需要给Texture填充设置参数，用来渲染的Texture可能比要渲染的区域大或者小，这是需要设置Texture需要放大或是缩小时OpenGL的模式
    // Scale up if the texture if smaller.
    gl.glTexParameterf(GL10.GL_TEXTURE_2D,
     GL10.GL_TEXTURE_MAG_FILTER,
     GL10.GL_LINEAR);

    // scale linearly when image smalled than texture
    gl.glTexParameterf(GL10.GL_TEXTURE_2D,
     GL10.GL_TEXTURE_MIN_FILTER,
     GL10.GL_LINEAR);
     常用的两种模式为GL10.GL_LINEAR（较模糊） 和GL10.GL_NEAREST（比较清晰）
5.5 UV Mapping
    告知OpenGL库如何将Bitmap的像素映射到Mesh上
    bitmap 绘制到 mesh 上的位置
    会存在 绘制一部分区域，其他地方空白
    使用一些不存在的Texture去渲染平面(UV坐标为0，0-1，1 而 (0,0)-(2,2)定义超过UV定义的大小），这时需要告诉OpenGL库如何去渲染这些不存在的Texture部分。
    有两种设置
        GL_REPEAT 重复Texture。
        GL_CLAMP_TO_EDGE 只靠边线绘制一次。

    gl.glTexParameterf(GL10.GL_TEXTURE_2D,
     GL10.GL_TEXTURE_WRAP_S,
     GL10.GL_REPEAT);
    gl.glTexParameterf(GL10.GL_TEXTURE_2D,
     GL10.GL_TEXTURE_WRAP_T,
     GL10.GL_REPEAT);
5.6 将Bitmap资源和Texture绑定起来
    GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
    .......

6. java.nio 中的Buffer
    Buffer定义了三个状态变量：position, limit, capacity
    Capacity: Buffer的容量，表示可以存放的最大字节数，内存分配之后其值保持不变。
    Position: 类似于文件指针，表示下一个可以读写的字节的缺省位置，可以使用函数来重新设置当前的Position.
    Limit： 可以控制当前可以读写的区域，你只可以读写从[0,limit-1]范围内的数组空间，读写超过这个范围将导致抛出异常。
  java.nio 包中定义了基类Buffer ，还定义了和各种基本数据类型特定的Buffer类型ByteBuffer, CharBuffer,
  DoubleBuffer, FloatBuffer, IntBuffer, LongBuffer和ShortBuffer.其中ByteBuffer是其它类型的基础，
  因为分配内存是通过ByteBuffer的allocate来进行的，然后通过 ByteBuffer 的asXXXBuffer()转为其它类型

7.1 OpenGL ES能够绘制的几种基本几何图形：点，线，三角形




















