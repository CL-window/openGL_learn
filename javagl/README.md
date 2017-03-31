OpenGL in java demo
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
























