package com.gl.spannable;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.gl.traindemohk.R;

/**
 * Created by 王斌 on 2017/7/28.
 * 参考文档：
 * http://www.2cto.com/kf/201610/553312_2.html
 */

public class MySpannableStringActivity extends AppCompatActivity {
    private TextView mTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable_string);
        mTextView = (TextView) findViewById(R.id.text_spannable);

//        buildSpan();

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("哈哈哈哈呵呵嘻嘻嘻嘻");
        mTextView.setTextColor(Color.GREEN);
//        builder.setSpan(new ForegroundColorSpan(Color.RED),0,10,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.BLUE), 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.setSpan(new ForegroundColorSpan(Color.BLUE), 5, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.setSpan(new ForegroundColorSpan(Color.BLUE), 7, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(builder);

    }


    private int titleFontSize;// 标题字号
    private int transColor;// 透明色
    int titleColor; // 标题色
    int subtitleColor;


    // 正确
    private void buildSpan() {

        titleFontSize = getResources().getDimensionPixelSize(R.dimen.spannable_string_titleFontSize); // 标题字号
        int subtitleSize = getResources().getDimensionPixelSize(R.dimen.spannable_string_subtitleSize); //副标题字号
        titleColor = Color.parseColor("#FF666666"); // 标题颜色
        subtitleColor = Color.parseColor("#FF888888"); // 副标题颜色
        int spanColor = Color.parseColor("#FFFF9000");
        transColor = getResources().getColor(android.R.color.transparent); // 透明色

        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableStringBuilder builder1 = buildSpannableString(R.string.spannable_string_text1, titleFontSize, titleColor, spanColor, false);
        SpannableStringBuilder builder2 = buildSpannableString(R.string.spannable_string_text2, subtitleSize, subtitleColor, spanColor, true);
        SpannableStringBuilder builder3 = buildSpannableString(R.string.spannable_string_text3, titleFontSize, titleColor, spanColor, false);
        SpannableStringBuilder builder4 = buildSpannableString(R.string.spannable_string_text4, subtitleSize, subtitleColor, spanColor, true);

        builder.append(builder1);
        builder.append(builder2);
        builder.append(builder3);
        builder.append(builder4);

        SpannableStringBuilder builder5 = buildSpannableString(R.string.spannable_string_text5, titleFontSize, titleColor, spanColor, false);
        builder.append(builder5);
        mTextView.setText(builder);


    }

    private SpannableStringBuilder buildSpannableString(int resId, int fontSize, int textColor, int spanColor, boolean isNeedIndent) {

        SpannableStringBuilder builder = new SpannableStringBuilder(getString(resId));


        mTextView.setTextColor(subtitleColor);


        if (isNeedIndent) {
            // 设置缩进
            builder.setSpan(new AbsoluteSizeSpan(fontSize, false), 3, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new ForegroundColorSpan(transColor), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        } else {
            setTextFontSize(builder, titleFontSize);
        }

        setSpanColor(builder, spanColor);


        return builder;
    }


    private void setTextColor(SpannableStringBuilder builder, int color) {
        builder.setSpan(new ForegroundColorSpan(color), 0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void setTextFontSize(SpannableStringBuilder builder, int size) {
        builder.setSpan(new AbsoluteSizeSpan(size, false), 0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /*
     设置高亮字符串颜色
     */
    private void setSpanColor(SpannableStringBuilder builder, int color) {
        String text = builder.toString();


        int start = text.indexOf("^");
        int end = text.indexOf("$");


        int normal_start = 0;
        int normal_end = 0;

        int previousEnd = 0;

        while (start >= 0 && end >= 0) {

            builder.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

//            builder.
//            if (previousEnd == 0){
//                normal_start = 0;
//                normal_end = start;
//            }else {
//                normal_start =
//
//            }
//
//            if (normal_start >= 0 && normal_end >= 0)
//                builder.setSpan(new ForegroundColorSpan(subtitleColor), normal_start, normal_end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);





            builder.replace(start, start + 1, "");
            builder.replace(end - 1, end, "");

            text = builder.toString();

            start = text.indexOf("^");
            end = text.indexOf("$");


        }
    }

}
