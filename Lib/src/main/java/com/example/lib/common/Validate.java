package com.example.lib.common;

import android.view.View;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
    public static boolean isValidName(String value, TextView textView) {
        if(value.trim().length() <= 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("Vui lòng nhập tên khách hàng!");
            return false;
        }
            textView.setVisibility(View.GONE);
            textView.setText(" ");
            return true;
    }

    public static boolean isValidAddress(String value, TextView textView) {
        if(value.trim().length() <= 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("Vui lòng nhập địa chỉ!");
            return false;
        }
            textView.setVisibility(View.GONE);
            textView.setText(" ");
            return true;
    }

    public static boolean isValidPhone(String value, int max, TextView textView) {
        Pattern p = Pattern.compile("[^0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(value);
        if(value.trim().length() <= 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("Vui lòng nhập số điện thoại!");
            return false;
        }else if(value.trim().length() > max) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("Số điện thoại không được vượt quá "+max+" ký tự!");
            return false;
        }
        else if(m.find()){
            textView.setVisibility(View.VISIBLE);
            textView.setText("Số điện thoại không được chứa chữ cái hoặc ký tự đặc biệt!");
            return false;
        }
            textView.setVisibility(View.GONE);
            textView.setText(" ");
            return true;
    }
}
