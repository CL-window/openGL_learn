from :  http://blog.csdn.net/gh_home/article/details/52399959
1. 从一个.mp4文件中解码视频流到surface上
2. 利用OpenGL ES渲染改变视频流中每一帧的内容
3. 将改变后的视频流重新编码输出到一个新的.mp4文件
初始化MediaEncoder（视频编码）、MediaDecoder（视频解码）、MediaMux（生成MP4文件合成音频）、MediaExtractor（分割视频与音频）

EGL
1. 获取Display。
Display代表显示器，在有些系统上可以有多个显示器，也就会有多个Display。获得Display要调用EGLboolean eglGetDisplay(NativeDisplay dpy)，参数一般为 EGL_DEFAULT_DISPLAY 。该参数实际的意义是平台实现相关的，在X-Window下是XDisplay ID，在MS Windows下是Window DC。

2. 初始化egl。
调用 EGLboolean eglInitialize(EGLDisplay dpy, EGLint *major, EGLint *minor)，该函数会进行一些内部初始化工作，并传回EGL版本号(major.minor)。

3. 选择Config。
所为Config实际指的是FrameBuffer的参数，在MS Windows下对应于PixelFormat，在X-Window下对应Visual。一般用EGLboolean eglChooseConfig(EGLDisplay dpy, const EGLint * attr_list, EGLConfig * config, EGLint config_size, EGLint *num_config)，其中attr_list是以EGL_NONE结束的参数数组，通常以id,value依次存放，对于个别标识性的属性可以只有 id，没有value。另一个办法是用EGLboolean eglGetConfigs(EGLDisplay dpy, EGLConfig * config, EGLint config_size, EGLint *num_config) 来获得所有config。这两个函数都会返回不多于config_size个Config，结果保存在config[]中，系统的总Config个数保存 在num_config中。可以利用eglGetConfig()中间两个参数为0来查询系统支持的Config总个数。
Config有众多的Attribute，这些Attribute决定FrameBuffer的格式和能力，通过eglGetConfigAttrib ()来读取，但不能修改。

4. 构造Surface。
Surface实际上就是一个FrameBuffer，通过 EGLSurface eglCreateWindowSurface(EGLDisplay dpy, EGLConfig confg, NativeWindow win, EGLint *cfg_attr) 来创建一个可实际显示的Surface。系统通常还支持另外两种Surface：PixmapSurface和PBufferSurface，这两种都不 是可显示的Surface，PixmapSurface是保存在系统内存中的位图，PBuffer则是保存在显存中的帧。
Surface也有一些attribute，基本上都可以故名思意， EGL_HEIGHT EGL_WIDTH EGL_LARGEST_PBUFFER EGL_TEXTURE_FORMAT EGL_TEXTURE_TARGET EGL_MIPMAP_TEXTURE EGL_MIPMAP_LEVEL，通过eglSurfaceAttrib()设置、eglQuerySurface()读取。

5. 创建Context。
OpenGL的pipeline从程序的角度看就是一个状态机，有当前的颜色、纹理坐标、变换矩阵、绚染模式等一大堆状态，这些状态作用于程序提交的顶点 坐标等图元从而形成帧缓冲内的像素。在OpenGL的编程接口中，Context就代表这个状态机，程序的主要工作就是向Context提供图元、设置状 态，偶尔也从Context里获取一些信息。
用EGLContext eglCreateContext(EGLDisplay dpy, EGLSurface write, EGLSurface read, EGLContext * share_list)来创建一个Context。

6. 绘制。
应用程序通过OpenGL API进行绘制，一帧完成之后，调用eglSwapBuffers(EGLDisplay dpy, EGLContext ctx)来显示。