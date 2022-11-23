package com.lyricgan.conn;

public class Dispatcher extends Thread {
    private Request mRequest;
    private boolean mIsRefresh;
    private boolean mCancel;
    private boolean mRunning = true;
    private String mTag;

    public Dispatcher(Request request, boolean isRefresh) {
        this.mRequest = request;
        this.mIsRefresh = isRefresh;
    }

    @Override
    public void run() {
        while (mRunning) {
            ResponseEntity responseEntity = null;
            try {
                responseEntity = mRequest.executeSync(mIsRefresh);
            } catch (InterruptedException e) {
                if (mCancel) {
                    return;
                }
            }
            if (mCancel) {
                return;
            }
            if (responseEntity != null) {
                mRequest.processResponse(responseEntity);
            }
            mRunning = false;
        }
    }

    public void setTag(String tag) {
        this.mTag = tag;
    }

    public String getTag() {
        return mTag;
    }

    public void cancel() {
        mCancel = true;
        interrupt();
    }
}
