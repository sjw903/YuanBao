package com.yuanbaogo.network.parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

public class FileParser extends BaseParser<File> {
    protected File destFile;

    public FileParser(File destFile) {
        this.destFile = destFile;
    }

    protected File parse(Response response) throws Exception {
        if (this.destFile != null && !this.destFile.isDirectory()) {
            InputStream is = null;
            byte[] buf = new byte[2048];
            FileOutputStream fos = null;

            try {
                is = response.body().byteStream();
                File var6;
                if (!this.destFile.exists() && !this.destFile.createNewFile()) {
                    var6 = null;
                    return var6;
                } else {
                    fos = new FileOutputStream(this.destFile);

                    int len;
                    while((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }

                    fos.flush();
                    var6 = this.destFile;
                    return var6;
                }
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException var18) {
                }

                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException var17) {
                }

            }
        } else {
            return null;
        }
    }
}
