package com.cl.slack.decodeencodemp4.decode;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 管理 解码 处理视频帧 编码
 *
 * 解密完成后 解码器通过surface将 CodecOutputSurface.mSurface对应的 mSurfaceTexture 绑定起来 {@link SurfaceDecoder#outputSurface #67}
 * 同时 SurfaceTexture的另外一边与OPRNGL ES中初始化建立的一个Texture ID绑定 {@link CodecOutputSurface#setup()}
 *
 * 这样就建立了一条由解码的mp4数据到OPENGL ES的Texture的数据流。 其中SurfaceTexture充当中介，在上述工作准备好后，
 * 开启SurfaceTexture内容侦听，即回调函数onFrameAvailable。 一旦SurfaceTexture内容发生变化（有新的编码数据流流入,有新解码处理的数据），
 * 系统会自动调用onFrameAvailable表明SurfaceTexture中有可用数据，之后我们调用SurfaceTexture的
 * 成员函数SurfaceTexture.updateTexImage{@link CodecOutputSurface#awaitNewImage()}将当前的图像流传递到OPENGL ES中的texture
 * 把 编码 encode  的 surface 共享 给 OpenGL ,openGL 处理的结果都在 surface 里
 * Created by guoheng on 2016/8/31.
 */
public class EncodeDecodeSurface {

    private static final String TAG = "EncodeDecodeSurface";
    private static final boolean VERBOSE = false;           // lots of logging

    private static final int MAX_FRAMES = 400;       // stop extracting after this many

    private SurfaceDecoder SDecoder;
    private SurfaceEncoder SEncoder;

    /**
     * @param srcPath  原文件 全路径
     * @param descPath 输出文件 全路径
     */
    public EncodeDecodeSurface(String srcPath, String descPath) {
        SDecoder = new SurfaceDecoder(srcPath);
        SEncoder = new SurfaceEncoder(descPath);
    }

    /**
     * test entry point
     */
    public void testEncodeDecodeSurface() throws Throwable {
        EncodeDecodeSurfaceWrapper.runTest(this);
    }

    private static class EncodeDecodeSurfaceWrapper implements Runnable {
        private Throwable mThrowable;
        private EncodeDecodeSurface mTest;

        private EncodeDecodeSurfaceWrapper(EncodeDecodeSurface test) {
            mTest = test;
        }

        @Override
        public void run() {
            try {
                mTest.Prepare();
            } catch (Throwable th) {
                mThrowable = th;
            }
        }

        /**
         * Entry point.
         */
        public static void runTest(EncodeDecodeSurface obj) throws Throwable {
            EncodeDecodeSurfaceWrapper wrapper = new EncodeDecodeSurfaceWrapper(obj);
            Thread th = new Thread(wrapper, "codec test");
            th.start();
            //th.join();
            if (wrapper.mThrowable != null) {
                throw wrapper.mThrowable;
            }
        }
    }

    private void Prepare() throws IOException {
        try {

            SEncoder.VideoEncodePrepare();
            SDecoder.SurfaceDecoderPrePare(SEncoder.getEncoderSurface());
            doExtract();
        } finally {
            SDecoder.release();
            SEncoder.release();
        }
    }

    void doExtract() throws IOException {
        final int TIMEOUT_USEC = 10000;
        ByteBuffer[] decoderInputBuffers = SDecoder.decoder.getInputBuffers();
        MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
        int inputChunk = 0;
        int decodeCount = 0;
        long frameSaveTime = 0;

        boolean outputDone = false;
        boolean inputDone = false;
        while (!outputDone) {
            if (VERBOSE) Log.d(TAG, "loop");

            // INOUT
            // Feed more data to the decoder.
            if (!inputDone) {
                int inputBufIndex = SDecoder.decoder.dequeueInputBuffer(TIMEOUT_USEC);
                if (inputBufIndex >= 0) {
                    ByteBuffer inputBuf = decoderInputBuffers[inputBufIndex];
                    int chunkSize = SDecoder.extractor.readSampleData(inputBuf, 0);
                    if (chunkSize < 0) {
                        SDecoder.decoder.queueInputBuffer(inputBufIndex, 0, 0, 0L,
                                MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                        inputDone = true;
                        if (VERBOSE) Log.d(TAG, "sent input EOS");
                    } else {
                        if (SDecoder.extractor.getSampleTrackIndex() != SDecoder.DecodetrackIndex) {
                            Log.w(TAG, "WEIRD: got sample from track " +
                                    SDecoder.extractor.getSampleTrackIndex() + ", expected " + SDecoder.DecodetrackIndex);
                        }
                        long presentationTimeUs = SDecoder.extractor.getSampleTime();
                        SDecoder.decoder.queueInputBuffer(inputBufIndex, 0, chunkSize,
                                presentationTimeUs, 0 /*flags*/);
                        if (VERBOSE) {
                            Log.d(TAG, "submitted frame " + inputChunk + " to dec, size=" +
                                    chunkSize);
                        }
                        inputChunk++;
                        SDecoder.extractor.advance();
                    }
                } else {
                    if (VERBOSE) Log.d(TAG, "input buffer not available");
                }
            }

            // OUT PUT
            int decoderStatus = SDecoder.decoder.dequeueOutputBuffer(info, TIMEOUT_USEC);
            if (decoderStatus == MediaCodec.INFO_TRY_AGAIN_LATER) {
                // no output available yet
                if (VERBOSE) Log.d(TAG, "no output from decoder available");
            } else if (decoderStatus == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                // not important for us, since we're using Surface
                if (VERBOSE) Log.d(TAG, "decoder output buffers changed");
            } else if (decoderStatus == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                MediaFormat newFormat = SDecoder.decoder.getOutputFormat();
                if (VERBOSE) Log.d(TAG, "decoder output format changed: " + newFormat);
            } else if (decoderStatus < 0) {

            } else { // decoderStatus >= 0
                if (VERBOSE) Log.d(TAG, "surface decoder given buffer " + decoderStatus +
                        " (size=" + info.size + ")");
                if ((info.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                    if (VERBOSE) Log.d(TAG, "output EOS");
                    outputDone = true;
                }

                boolean doRender = (info.size != 0);

                SDecoder.decoder.releaseOutputBuffer(decoderStatus, doRender);
                if (doRender) {
                    if (VERBOSE) Log.d(TAG, "awaiting decode of frame " + decodeCount);

                    if (decodeCount < MAX_FRAMES) {
                        /**
                         * 调用 eglMakeCurrent 这个使得我们通知GPU以及OPENGL ES在执行绘图指令的时候，
                         * 是在当前mEGLContext这个上下文绘制在mEGLSurface上的。所以我们在最后绘图的时候
                         * 需要makecurrent到与Encoder绑定的surface对应的那个EGLSurface上
                         */
                        SDecoder.outputSurface.makeCurrent(1);
                        SDecoder.outputSurface.awaitNewImage();
                        SDecoder.outputSurface.drawImage(true);

                        SEncoder.drainEncoder(false);
                        SDecoder.outputSurface.setPresentationTime(computePresentationTimeNsec(decodeCount));
                        SDecoder.outputSurface.swapBuffers();

                    }
                    decodeCount++;
                }

            }
        }

        SEncoder.drainEncoder(true);
        int numSaved = (MAX_FRAMES < decodeCount) ? MAX_FRAMES : decodeCount;
        Log.d(TAG, "Saving " + numSaved + " frames took " +
                (frameSaveTime / numSaved / 1000) + " us per frame");
    }


    private static long computePresentationTimeNsec(int frameIndex) {
        final long ONE_BILLION = 1000000000;
        return frameIndex * ONE_BILLION / 30;
    }


}

