package com.train._7userInfo_sign_In.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gl.traindemohk.R;

/**
 * 联系人搜索功能---模糊搜索
 */

public class ContactSearchFragment extends Fragment{

    /*
        联系人列表分类的步骤：

        1.请求读取联系人的权限
        2.定义列表和列表项的布局
        3.定义显示联系人列表的Fragment
        4.定义全局变量
        5.初始化Fragment
        6.为ListView设置CursorAdapter
        7.设置选择联系人的监听器
        8.定义Cursor的列索引常量

        尽管我们现在从不同的表中取数据，检索列的映射顺序是一样的，所以我们可以为这个Cursor使用同样的索引常量。

        9.定义onItemClick()方法
        10.初始化loader
        11.实现onLoadFinished()方法和onLoaderReset()方法
     */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_list,container,false);
    }
}
