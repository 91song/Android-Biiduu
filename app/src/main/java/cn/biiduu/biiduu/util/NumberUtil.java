package cn.biiduu.biiduu.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.regex.Pattern;

public class NumberUtil {
    private static String roundToString(double d) {
        return new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String round1ToString(double d) {
        return new BigDecimal(d).setScale(1, BigDecimal.ROUND_HALF_UP).toString();
    }

    private static String roundToString(String s) {
        return new BigDecimal(s).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 将每三个数字加上逗号处理（通常使用金额方面的编辑）
     *
     * @param str 无逗号的数字
     * @return 加上逗号的数字
     */
    public static String addSeparator(String str) {
        return addSeparatorp(roundToString(str));
    }

    public static String addSeparator(double d) {
        return addSeparatorp(roundToString(d));
    }

    private static String addSeparatorp(String str) {
        try {
            String reverseStr = new StringBuilder(str).reverse().toString();
            String left = "";
            if (reverseStr.contains(".")) {
                left = reverseStr.substring(0, reverseStr.indexOf(".") + 1);
                reverseStr = reverseStr.substring(reverseStr.indexOf(".") + 1, reverseStr.length());
            }
            String strTemp = "";
            for (int i = 0; i < reverseStr.length(); i++) {
                if (i * 3 + 3 > reverseStr.length()) {
                    strTemp += reverseStr.substring(i * 3, reverseStr.length());
                    break;
                }
                strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";
            }
            if (strTemp.endsWith(",")) {
                strTemp = strTemp.substring(0, strTemp.length() - 1);
            }
            return new StringBuilder(left + strTemp).reverse().toString();
        } catch (Exception e) {
            return str;
        }
    }

    /**
     * 金额四舍五入保留2位小数处理 如 12,343,171.60
     */
    public static String formatNumber(double money) {
        String str = money + "";
        try {
            NumberFormat currency = NumberFormat.getNumberInstance();
            currency.setMinimumFractionDigits(2);
            currency.setMaximumFractionDigits(2);
            str = currency.format(money);//
        } catch (Exception ignored) {

        }
        return str;
    }

    /**
     * 比较两个集合是否相等
     */
    public static boolean isListEquals(List list1, List list2) {
        if (list1.size() != list2.size())
            return false;
        for (int index = 0; index < list1.size(); index++) {
            if (!list1.get(index).equals(list2.get(index)))
                return false;
        }
        return true;
    }

    /**
     * 判断是否是整数
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}
