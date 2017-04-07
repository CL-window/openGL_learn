from google ：  https://github.com/google/grafika
1. mp4播放：PlayMovieActivity
    MediaExtractor 分解音视频
    MediaCodec 播放，需要设置输出的 Surface
    decoder.configure(format, mOutputSurface, null, 0);
    // 对输入的数据流进行编码或者解码处理
    decoder.dequeueInputBuffer // 获得这个用来作为媒体文件源码的ByteBuffer（从输入的buffers的数组中）的索引位置
    decoder.queueInputBuffer // 使用带有媒体文件源码的ByteBuffer之后，通过调用此方法来释放缓存区的所有权
    decoder.dequeueOutputBuffer // 获得你接收到结果的ByteBuffer的索引位置
    decoder.releaseOutputBuffer // 释放所有权
1.1  需要在 UI线程执行的代码 可以 通过 Handler.sendMessage 实现
// Send message through Handler so it runs on the right thread.
    mLocalHandler.sendMessage(mLocalHandler.obtainMessage(MSG_PLAY_STOPPED, mFeedback));
    private static class LocalHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;

            switch (what) {
                case MSG_PLAY_STOPPED:
                    PlayerFeedback fb = (PlayerFeedback) msg.obj;
                    fb.playbackStopped();
                    break;
                default:
                    throw new RuntimeException("Unknown msg " + what);
            }
        }
    }
2. ContinuousCaptureActivity
    录制出来的 方向不对， 解决 ：mEncoderSurface.makeCurrentReadFrom(mDisplaySurface);
    即使用预览的画面录制
    预览的方向 camera.setDisplayOrientation （适配2.2之上的手机）
    parameters.set("orientation", "portrait")或者parameters.setRotation(90) （适配下2.2以下）

    surfaceCreated 里
    预览显示使用的和 录制视频 使用的是相同的 EGLContext ，封装在 EglCore 里，同时还封装了(EGLDisplay,EGLConfig)
    WindowSurface 封装 EGLSurface

    设置相机预览,使用 mCameraTexture.setOnFrameAvailableListener 监听回调
    Camera.setPreviewTexture
    Camera.startPreview()
    在有一帧数据到达时，先调用
    EGL14.eglMakeCurrent(mEGLDisplay, mEGLSurface, mEGLSurface, mEGLContext)
    通知GPU以及OPENGL ES在执行绘图指令的时候，是在当前mEGLContext这个上下文绘制在mEGLSurface上的
    这样绘制就绘制在我们的布局SurfaceView 上
    updateTexImage() // 更新纹理图像为从图像流中提取的最近一帧
    getTransformMatrix(float[] mtx) // 提取最近调用的updateTexImage()为纹理图像设置的4×4的纹理坐标变换矩阵

    预览 mDisplaySurface 和 录制mEncoderSurface 使用 不同 Surface， 但是共用EGLContext，还有一个mCameraTexture 真正获取相机的返回数据
    录制的 MediaCodec createInputSurface ，和 录制mEncoderSurface 共用一个 Surface，这样就保证录制进去的是相机返回的数据

    ＃camera 默认是横屏 ，所以 width 为竖屏时的高，height为竖屏时的宽

2. BufferQueue 它的作用十分的简单：
把提供图形数据buffer的生产者与接受图形数据并显示或进一步处理的消费者连接起来。生产者与消费者可以存在与不同的进程

