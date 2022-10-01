package com.raynel.alkemyproject.util.genericAdapter;
import android.view.View;

public interface OnclickItemListener<T> {
    void onClickItem(View v, T item);
}
