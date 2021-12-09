package com.yuanbaogo.router.login;

/**
 * 登录模块的scheme<BR>
 */
public interface LoginConstantValues {

    String SCHEME   = "yuanbaoCustomer://";
    String HOST     = "ybLoginModule/";
    String BASE_URI = SCHEME + HOST;

    interface LOGIN {
        String URI           = BASE_URI + "login";
        String PARAM_ACCOUNT = "account";
    }

    interface REGISTER {
        String URI              = BASE_URI + "register";
        String PARAM_TYPE       = "type";
        String PARAM_PHONE      = "phone";
        String PARAM_EMAIL      = "email";
        String PARAM_TYPE_RANGE = "typeRange";
        int    TYPE_PHONE       = 1;
        int    TYPE_EMAIL       = 16;
        int    TYPE_RANGE_PHONE = 1;
        int    TYPE_RANGE_EMAIL = 1 << 1;
    }

}
