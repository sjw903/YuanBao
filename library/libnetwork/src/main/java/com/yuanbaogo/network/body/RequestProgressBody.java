package com.yuanbaogo.network.body;


import com.yuanbaogo.network.callback.OkProgressCallback;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class RequestProgressBody extends RequestBody {
    private final RequestBody requestBody;
    private final OkProgressCallback progressCallback;
    private BufferedSink bufferedSink;

    public RequestProgressBody(RequestBody requestBody, OkProgressCallback progressCallback) {
        this.requestBody = requestBody;
        this.progressCallback = progressCallback;
    }

    public MediaType contentType() {
        return this.requestBody.contentType();
    }

    public long contentLength() throws IOException {
        return this.requestBody.contentLength();
    }

    public void writeTo(BufferedSink sink) throws IOException {
        if (this.bufferedSink == null) {
            this.bufferedSink = Okio.buffer(this.sink(sink));
        }

        this.requestBody.writeTo(this.bufferedSink);
        this.bufferedSink.flush();
    }

    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            long bytesWritten = 0L;
            long contentLength = 0L;

            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (this.contentLength == 0L) {
                    this.contentLength = RequestProgressBody.this.contentLength();
                }

                this.bytesWritten += byteCount;
                if (RequestProgressBody.this.progressCallback != null && this.contentLength > 0L) {
                    RequestProgressBody.this.progressCallback.onOKProgress(this.bytesWritten, this.contentLength);
                }

            }
        };
    }
}
