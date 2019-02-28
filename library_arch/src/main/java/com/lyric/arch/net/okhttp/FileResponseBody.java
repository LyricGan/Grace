package com.lyric.arch.net.okhttp;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 文件响应类
 * @author lyricgan
 */
public class FileResponseBody extends ResponseBody {
    private ResponseBody responseBody;
    private Handler handler;
    /** 文件保存路径 */
    private String filePath;
    /** 文件保存名称 */
    private String fileName;

    public FileResponseBody(ResponseBody responseBody, FileCallback fileCallback) {
        this.responseBody = responseBody;
        this.handler = new InnerHandler(fileCallback);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public MediaType contentType() {
        if (responseBody != null) {
            return responseBody.contentType();
        }
        return null;
    }

    @Override
    public long contentLength() {
        if (responseBody != null) {
            return responseBody.contentLength();
        }
        return 0;
    }

    @NonNull
    @Override
    public BufferedSource source() {
        return Okio.buffer(new InnerForwardingSource(responseBody.source(), contentLength(), handler));
    }

    private static class InnerForwardingSource extends ForwardingSource {
        long currentSize;
        long totalSize;
        Handler handler;

        InnerForwardingSource(Source delegate, long totalSize, Handler handler) {
            super(delegate);
            this.totalSize = totalSize;
            this.handler = handler;
        }

        @Override
        public long read(@NonNull Buffer sink, long byteCount) throws IOException {
            final long bytesRead = super.read(sink, byteCount);
            currentSize += bytesRead != -1 ? bytesRead : 0;

            if (handler != null) {
                handler.sendMessage(Message.obtain(handler, 0, new FileMessage(totalSize, currentSize, bytesRead == -1)));
            }
            return bytesRead;
        }
    }

    private static class InnerHandler extends Handler {
        private WeakReference<FileCallback> mReference;

        InnerHandler(FileCallback callback) {
            this.mReference = new WeakReference<>(callback);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            FileCallback fileCallback = null;
            if (mReference != null) {
                fileCallback = mReference.get();
            }
            if (fileCallback != null) {
                FileMessage fileMessage = (FileMessage) msg.obj;
                if (fileMessage != null) {
                    fileCallback.onProgress(fileMessage.getTotalSize(), fileMessage.getCurrentSize(), fileMessage.isFinished());
                }
            }
        }
    }
}
