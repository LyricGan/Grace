package com.lyric.support.net.retrofit.multiple;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * @author lyricgan
 */
public class FileResponseBody extends ResponseBody {
    private final ResponseBody responseBody;
    private BufferedSource bufferedSource;
    private Handler handler;
    /**
     * file save path
     */
    private String filePath;
    /**
     * file save name
     */
    private String fileName;

    public FileResponseBody(ResponseBody responseBody, FileCallback callback) {
        this.responseBody = responseBody;
        this.handler = new InnerHandler(callback);
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
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(new InnerForwardingSource(responseBody.source(), contentLength(), handler));
        }
        return bufferedSource;
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
        public long read(Buffer sink, long byteCount) throws IOException {
            final long bytesRead = super.read(sink, byteCount);
            currentSize += bytesRead != -1 ? bytesRead : 0;

            if (handler != null) {
                handler.sendMessage(Message.obtain(handler, 0, new FileMessage(totalSize, currentSize, bytesRead == -1)));
            }
            return bytesRead;
        }
    }

    private static class InnerHandler extends BaseHandler<FileCallback> {

        InnerHandler(FileCallback object) {
            super(object);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            FileCallback fileCallback = get();
            if (fileCallback != null) {
                FileMessage fileMessage = (FileMessage) msg.obj;
                if (fileMessage != null) {
                    fileCallback.onProgress(fileMessage.getTotalSize(), fileMessage.getCurrentSize(), fileMessage.isFinished());
                }
            }
        }
    }
}