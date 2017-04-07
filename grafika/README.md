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

2. BufferQueue 它的作用十分的简单：
把提供图形数据buffer的生产者与接受图形数据并显示或进一步处理的消费者连接起来。生产者与消费者可以存在与不同的进程

