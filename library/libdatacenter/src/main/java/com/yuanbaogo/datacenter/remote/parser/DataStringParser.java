package com.yuanbaogo.datacenter.remote.parser;


import com.yuanbaogo.datacenter.encryption.DesCbcUtil;
import com.yuanbaogo.network.parser.StringParser;

import okhttp3.Response;

public class DataStringParser extends StringParser {

    public DataStringParser() {
    }



    protected String parse(Response response) throws Exception {
        String resp = response.body().string();
        if (isSpy()) {
            String des = DesCbcUtil.decode(resp);
            return des;
        }else{
            return resp;
        }
    }
}
