实时预览滤镜：
滤镜使用Android4.x以后提供的 Effect 类来完成

一篇很好的文章：http://tangzm.com/blog/?p=20

another from : https://github.com/nekocode/CameraFilter

在 OpenGL ES 中你必须创建两种着色器：顶点着色器 (vertex shaders) 和片段着色器 (fragment shaders)。
这两种着色器是一个完整程序的两半，你不能仅仅创建其中任何一个；想创建一个完整的着色程序，两个都是必须存在。

顶点着色器定义了在 2D 或者 3D 场景中几何图形是如何处理的。一个顶点指的是 2D 或者 3D 空间中的一个点。在图像处理中，
有 4 个顶点：每一个顶点代表图像的一个角。顶点着色器设置顶点的位置，并且把位置和纹理坐标这样的参数发送到片段着色器。

然后 GPU 使用片段着色器在对象或者图片的每一个像素上进行计算，最终计算出每个像素的最终颜色。
