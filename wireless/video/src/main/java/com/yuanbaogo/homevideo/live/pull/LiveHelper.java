package com.yuanbaogo.homevideo.live.pull;

import android.text.TextUtils;

import com.yuanbaogo.live.push.Constants;

public class LiveHelper {

    public static int checkPlayURL(String playURL) {

        if (TextUtils.isEmpty(playURL)) {
            return Constants.PLAY_STATUS_EMPTY_URL;
        }

        if (!playURL.startsWith(Constants.URL_PREFIX_HTTP) && !playURL.startsWith(Constants.URL_PREFIX_HTTPS)
                && !playURL.startsWith(Constants.URL_PREFIX_RTMP) && !playURL.startsWith("/")) {
            return Constants.PLAY_STATUS_INVALID_URL;
        }

        boolean isLiveRTMP = playURL.startsWith(Constants.URL_PREFIX_RTMP);
        boolean isLiveFLV = (playURL.startsWith(Constants.URL_PREFIX_HTTP) || playURL.startsWith(Constants.URL_PREFIX_HTTPS)) && playURL.contains(Constants.URL_SUFFIX_FLV);

            if (isLiveRTMP) {
                return Constants.PLAY_STATUS_SUCCESS;
            }
            if (isLiveFLV) {
                return Constants.PLAY_STATUS_SUCCESS;
            }
            return Constants.PLAY_STATUS_INVALID_URL;

    }
}
