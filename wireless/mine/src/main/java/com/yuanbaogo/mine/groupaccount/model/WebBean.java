package com.yuanbaogo.mine.groupaccount.model;

/**
 * @Description: WebView 与 js交互
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/16/21 11:00 AM
 * @Modifier:
 * @Modify:
 */
public class WebBean {

//    {"type":"topUpTicket","params":{"price":500}}

    private String type;
    private WebParamsBean params;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public WebParamsBean getParams() {
        return params;
    }

    public void setParams(WebParamsBean params) {
        this.params = params;
    }

    public class WebParamsBean {

        private String price;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

}
