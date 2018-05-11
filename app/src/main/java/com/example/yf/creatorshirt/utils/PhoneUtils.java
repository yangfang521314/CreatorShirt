package com.example.yf.creatorshirt.utils;

import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机的号码的正则表达式
 * Created by yangfang on 2015/12/29.
 */
public class PhoneUtils {
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "^1[0-9]{10}$";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(phoneNumber);
        if (matcher.matches()) {
            isValid = true;
        }
        return !isValid;
    }

    public static boolean notPassWord(String str) {
        return (str != null && str.trim().length() > 5);
    }

    public static boolean notEmpty(String str) {
        return (str != null && str.trim().length() > 0);
    }


    public static boolean notEmptyText(EditText text) {
        return (text.getText() != null && notEmpty(text.getText().toString()));
    }

    public static boolean notEmpty(TextView text) {
        return (text.getText() != null && notEmpty(text.getText().toString()));
    }


    /**
     * 比较两个字符串
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean match(String str1, String str2) {
        return str1 != null && str2 != null && str1.equals(str2);
    }

    /**
     * 获得长度的方法
     *
     * @param editText
     * @return
     */

    public static int getTextLength(EditText editText) {
        return editText.getText().toString().length();
    }

    /**
     * 获得字符串
     *
     * @param edt
     * @return
     */
    public static String getTextString(EditText edt) {
        return edt.getText().toString();
    }


}
