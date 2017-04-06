package com.cl.slack.decodeencodemp4.decode;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Environment;
import android.util.Log;
import android.view.Surface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 初始化解码器与MediaExtractor
 * @author slack
 * @time 17/4/6 下午1:10
 */
public class SurfaceDecoder {

    private static final String TAG = "EncodeDecodeSurface";
    private static final boolean VERBOSE = false;           // lots of logging

    int saveWidth = 1920;
    int saveHeight = 1080;

    MediaCodec decoder = null;

    CodecOutputSurface outputSurface = null;

    MediaExtractor extractor = null;

    public int DecodetrackIndex;

    // where to find files (note: requires WRITE_EXTERNAL_STORAGE permission)
    private final String INPUT_FILE;

    public SurfaceDecoder(String srcPath) {
        INPUT_FILE = srcPath;
    }

    void SurfaceDecoderPrePare(Surface encodersurface) {
        try {
            File inputFile = new File(INPUT_FILE);

            if (!inputFile.canRead()) {
                throw new FileNotFoundException("Unable to read " + inputFile);
            }
            extractor = new MediaExtractor();
            extractor.setDataSource(inputFile.toString());
            DecodetrackIndex = selectTrack(extractor);
            if (DecodetrackIndex < 0) {
                throw new RuntimeException("No video track found in " + inputFile);
            }
            extractor.selectTrack(DecodetrackIndex);

            MediaFormat format = extractor.getTrackFormat(DecodetrackIndex);
            if (VERBOSE) {
                Log.d(TAG, "Video size is " + format.getInteger(MediaFormat.KEY_WIDTH) + "x" +
                        format.getInteger(MediaFormat.KEY_HEIGHT));
            }

            outputSurface = new CodecOutputSurface(saveWidth, saveHeight, encodersurface);

            String mime = format.getString(MediaFormat.KEY_MIME);
            decoder = MediaCodec.createDecoderByType(mime);
            decoder.configure(format, outputSurface.getSurface(), null, 0);
            decoder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private int selectTrack(MediaExtractor extractor) {
        // Select the first video track we find, ignore the rest.
        int numTracks = extractor.getTrackCount();
        for (int i = 0; i < numTracks; i++) {
            MediaFormat format = extractor.getTrackFormat(i);
            String mime = format.getString(MediaFormat.KEY_MIME);
            if (mime.startsWith("video/")) {
                if (VERBOSE) {
                    Log.d(TAG, "Extractor selected track " + i + " (" + mime + "): " + format);
                }
                return i;
            }
        }

        return -1;
    }


    void release() {
        if (decoder != null) {
            decoder.stop();
            decoder.release();
            decoder = null;
        }
        if (extractor != null) {
            extractor.release();
            extractor = null;
        }
        if (outputSurface != null) {
            outputSurface.release();
            outputSurface = null;
        }
    }
}
