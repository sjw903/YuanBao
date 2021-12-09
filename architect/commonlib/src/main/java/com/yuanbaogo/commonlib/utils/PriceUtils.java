package com.yuanbaogo.commonlib.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @Description: 金额保留二位数
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/9/21 5:16 PM
 * @Modifier:
 * @Modify:
 */
public class PriceUtils {

    /**
     * @param price
     * @return
     */
    public static long bigDecimalDouble(String price, int scale) {
        double result = Double.parseDouble(price);
        BigDecimal bigDecimal = new BigDecimal(result);
        return bigDecimal.setScale(scale, BigDecimal.ROUND_CEILING).longValue();
    }

    /**
     * double转String,保留小数点后两位
     *
     * @param price 接口返回到分  需要自行除100
     * @return
     */
    public static String doubleToString(double price) {
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(price / 100);
    }

    /**
     * double转String,保留小数点后一位
     *
     * @param price 接口返回到分  需要自行除100
     * @return
     */
    public static String doubleToStringOne(double price) {
        //使用0.0不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.0").format(price);
    }

    /**
     * double转String,不保留小数点后两位
     *
     * @param price 接口返回到分  需要自行除100
     * @return
     */
    public static String doubleToStringNo(double price) {
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0").format(price / 100);
    }

    /**
     * 提供精确的除法运算。当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示需要精确到小数点以后几位
     * @return 两个参数的商
     */
    public static String div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留的小数位数不能小于零");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1    被乘数
     * @param v2    乘数
     * @param scale 保留scale 位小数
     * @return 两个参数的积
     */
    public static String mul(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留的小数位数不能小于零");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        String price = b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString();
        if (price.substring(price.length() - 1).equals("0")) {
            price = price.substring(0, price.length() - 1);
            if (price.substring(price.length() - 1).equals(".")) {
                price = price.substring(0, price.length() - 1);
            }
        } else {
            return price;
        }
        return price;
    }

}
