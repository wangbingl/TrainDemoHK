package com.gl.spannable;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.widget.TextView;

import com.gl.traindemohk.R;

/**
 * Created by 王斌 on 2017/7/28.
 */

public class MySpannableStringActivity extends AppCompatActivity {
    private TextView mTextView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_spannable_string);
        mTextView = (TextView) findViewById(R.id.text_spannable);

        SpannableStringBuilder builder1 = new SpannableStringBuilder(getString(R.string.spannable_string_text1));
        builder1.setSpan(new TextAppearanceSpan(this, android.R.style.TextAppearance_Medium),0,builder1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableStringBuilder builder2 = new SpannableStringBuilder(getString(R.string.spannable_string_text2));
        builder2.setSpan(new ForegroundColorSpan(Color.BLUE),builder2.toString().indexOf("^"),builder2.toString().indexOf("&"), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mTextView.append(builder1.toString());
        mTextView.append(builder2.toString());

    }
}
