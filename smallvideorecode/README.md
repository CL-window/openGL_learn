from : http://blog.csdn.net/mabeijianxi/article/details/63335722
ffmpeg 视频录制与压缩
but need  targetsdk <= 22
本库暂时是在秒拍开源库上做的二次开发，旨在开发简单好用高效的视频录制库。
利用FFmpeg录制定制化的视频，并可对其定制化的压缩处理。如设置视频尺寸、设置码率、码率模式、帧率、视频质量等级、压缩速度等等，当然这些只是暂时的，后期会继续维护

MediaRecorderActivity 负责视频的录制
基本过程就是调用系统camera与AudioRecord得到视频和音频的byte回调，然后输入配置好参数FFmpeg，结束后得到目标视频
我们录制的视频是竖着的，所以需要旋转90°（默认是横屏录制）：camera.setDisplayOrientation(90）； 然后设置显示控件：camera.setPreviewDisplay(mSurfaceHolder)；
设置 帧率 mParameters.setPreviewFrameRate(mFrameRate);
    如果当前摄像头支持此帧率那么就使用，如果不支持那么就选择个最接近且小于它的，有可能还是找不到，这时就选择最小的一个
摄像头输出尺寸设置：通过系统API mParameters.getSupportedPreviewSizes()可以得到当前摄像头所支持的尺寸，注意这里返回的Size里面其height对应的屏幕短边，width对应的是屏幕长边
    mParameters.setPreviewSize()

需要知道几个FFmpeg命令：
-vf 可以添加滤镜，特别强大，可以旋转缩放剪切等等，我们需要用到旋转和剪切（我一直考虑需不需要用缩放的方式，因为这样可以在预览界面设置高分辨率看着清晰一些）。
transpose,旋转，对应的值有0、1、2、3，0:逆时针旋转90°然后垂直翻转1:顺时针旋转90°，2:逆时针旋转90°，3:顺时针旋转90°然后水平翻转。
剪切，关键字是crop,其有四个参数，分别是宽度、高度、其实剪切位置的X值与Y值，如ffmpeg -i a.mp4 -vf crop=480:360:0:0...;
-vcodec 指定视频编解码器；
-acodec 指定音频编解码器；
vbr 动态码率；
cbr 静态码率；
-crf 视频质量等级0~51，越大质量越差，建议18~28即可，与cbr模式不兼容；
-preset 转码速度，快慢的优劣应该都懂的，可根据自己业务场景设置，具体有：ultrafast、superfast、veryfast、faster、fast、medium、slow、slower、veryslow、placebo；
-i 指定输入；
-x264opts 配置其编解码参数；
maxrate 最大码率；
bitrate 固定码率;
-f 输出格式;
-s 设置帧大小。格式为 ‘wxh’;
-ss 指定开始时间;
-vframes 指定多少帧;

多段视频合并 : mabeijianxi.camera.MediaRecorderBase.concatVideoParts
进一步转码压缩 : mabeijianxi.camera.MediaRecorderBase.doCompress
截取视频中的一帧作为封面 : mabeijianxi.camera.FFMpegUtils.captureThumbnails(java.lang.String, java.lang.String, java.lang.String)