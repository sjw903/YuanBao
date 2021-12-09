package com.yuanbaogo.network.body;

import com.yuanbaogo.network.callback.OkProgressCallback;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class ResponseProgressBody extends ResponseBody {
    private final ResponseBody mResponseBody;
    private final OkProgressCallback progressCallback;
    private BufferedSource bufferedSource;

    public ResponseProgressBody(ResponseBody responseBody, OkProgressCallback progressCallback) {
        this.mResponseBody = responseBody;
        this.progressCallback = progressCallback;
    }

    public MediaType contentType() {
        return this.mResponseBody.contentType();
    }

    public long contentLength() {
        return this.mResponseBody.contentLength();
    }

    public BufferedSource source() {
        if (this.bufferedSource == null) {
            this.bufferedSource = Okio.buffer(this.source(this.mResponseBody.source()));
        }

        return this.bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead;

            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                this.totalBytesRead += bytesRead != -1L ? bytesRead : 0L;
                if (ResponseProgressBody.this.progressCallback != null) {
                    ResponseProgressBody.this.progressCallback.onOKProgress(this.totalBytesRead, ResponseProgressBody.this.mResponseBody.contentLength());
                }

                return bytesRead;
            }
        };
    }
}
