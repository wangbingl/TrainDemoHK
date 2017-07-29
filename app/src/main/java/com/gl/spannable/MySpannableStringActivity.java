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
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gl.traindemohk.R;

/**
 * Created by 王斌 on 2017/7/28.
 * http://www.2cto.com/kf/201610/553312_2.html
 */

public class MySpannableStringActivity extends AppCompatActivity {
    private TextView mTextView;


    int titleFontSize;/*= getResources().getDimensionPixelSize(R.dimen.spannable_string_titleFontSize); // 标题字号*/
    int subtitleSize; /*= getResources().getDimensionPixelSize(R.dimen.spannable_string_subtitleSize); //副标题字号*/
    int titleColor;/*= Color.parseColor("#FF666666"); // 标题颜色*/
    int subtitleColor;/*= Color.parseColor("#FF888888"); // 副标题颜色*/
    int spanColor;/*= Color.parseColor("#FFFF9000");*/
    int transColor;/*= getResources().getColor(android.R.color.transparent); // 透明色*/


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_spannable_string);
        mTextView = (TextView) findViewById(R.id.text_spannable);


        titleFontSize = getResources().getDimensionPixelSize(R.dimen.spannable_string_titleFontSize); // 标题字号
        subtitleSize = getResources().getDimensionPixelSize(R.dimen.spannable_string_subtitleSize); //副标题字号
        titleColor = Color.parseColor("#FF666666"); // 标题颜色
        subtitleColor = Color.parseColor("#FF888888"); // 副标题颜色
        spanColor = Color.parseColor("#FFFF9000");
        transColor = getResources().getColor(android.R.color.transparent); // 透明色


        buildSpan();
        mTextView.setVisibility(View.VISIBLE);
    }


    private void buildSpan() {

        SpannableStringBuilder builder = new SpannableStringBuilder();


        SpannableStringBuilder builder1 = buildSpannableString(R.string.spannable_string_text1, titleFontSize, titleColor, spanColor);

        SpannableStringBuilder builder2 = buildSpannableString(R.string.spannable_string_text2, subtitleSize, subtitleColor, spanColor);
        setTextTextAlignment(builder2, titleFontSize, transColor);

        SpannableStringBuilder builder3 = buildSpannableString(R.string.spannable_string_text3, titleFontSize, titleColor, spanColor);

        SpannableStringBuilder builder4 = buildSpannableString(R.string.spannable_string_text4, subtitleSize, subtitleColor, spanColor);
//        setTextTextAlignment(builder4,titleFontSize,transColor);
        // 设置文本颜色


        builder.append(builder1);
        builder.append(builder2);
        builder.append(builder3);
        builder.append(builder4);


        mTextView.setText(builder);

    }

    private SpannableStringBuilder buildSpannableString(int resId, int fontSize, int textColor, int spanColor) {
        SpannableStringBuilder builder = new SpannableStringBuilder(getString(resId));

        setTextColor(builder, textColor);
        if (resId == R.string.spannable_string_text4) {
            builder.setSpan(new ForegroundColorSpan(transColor), 0, 3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }

        setTextFontSize(builder, fontSize);

        if (resId == R.string.spannable_string_text4) {
            builder.setSpan(new AbsoluteSizeSpan(titleFontSize, false), 0, 3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
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

    /**
     * 设置高亮字符串颜色
     */
//    private void setSpanColor(SpannableStringBuilder builder,int color){}
    private void setSpanColor(SpannableStringBuilder builder, int color) {
        String text = builder.toString();

        int start = text.indexOf("^");
        int end = text.indexOf("$");

        while (start >= 0 && end >= 0) {
            Log.i("wb", "[setSpanColor] start:" + start + " | end:" + end);
            builder.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

            builder.replace(start, start + 1, "");
            builder.replace(end - 1, end, "");

            text = builder.toString();
            Log.i("wb", "[setSpanColor] after ed text:" + text);
            start = text.indexOf("^");
            end = text.indexOf("$");
        }
    }

    private void setTextTextAlignment(SpannableStringBuilder builder, int size, int color) {
        builder.setSpan(new AbsoluteSizeSpan(size, false), 0, 3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(color), 0, 3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    }
}
