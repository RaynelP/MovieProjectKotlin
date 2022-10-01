package com.raynel.alkemyproject.util.genericAdapter;

import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;

/**
 * Clase que utiliza la clase "AdaptadorRecyclerViewGenerico" para referenciar los views de cada item y poder acceder a ellos
 * para modificar conforme el recyclerView lo requiera
 * @param <T> Tipo de datos que contiene el modelo de datos que presentara cada item.
 */

public interface ViewData<T, D extends ViewDataBinding> {

    void bind(D binding, T item, OnclickItemListener<T> onClickListener);

    D instanceBinding(ViewGroup parent);
}
