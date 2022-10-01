package com.raynel.alkemyproject.util.genericAdapter;

import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Clase que implementa un Adapter de RecyclerView "generico" que ahorra la implementacion repetitiva de muchos Adapters
 *, implementa escuchadores a cada view que recicla el RecyclerView y metodos tipicos.
 *
 * @param <T> tipo de datos que contendra la lista
 */
public class GenericAdapter<T, D extends ViewDataBinding> extends
        RecyclerView.Adapter<GenericAdapter.Holder> {

    private List<T> list;
    private OnclickItemListener<T> onClickListener;
    private ViewData<T, D> viewData;

    public GenericAdapter() {
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(viewData.instanceBinding(parent));
    }

    @Override
    public void onBindViewHolder(GenericAdapter.Holder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addList(List<T> list){
        this.list = list;
    }

    public void addOnClickListener(OnclickItemListener<T> onClickItems){
        this.onClickListener = onClickItems;
    }

    public void addViewData(ViewData<T, D> viewData) {
        this.viewData = viewData;
    }

    public void deleteItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public List<T> getList() {
        return list;
    }

    public T getItemList(int position) {
        return list.get(position);
    }

    class Holder extends RecyclerView.ViewHolder {

        public Holder(D binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(T item){
            viewData.bind(binding, item, onClickListener);
        }

        private final D binding;
    }

    public static class Builder<T, D extends ViewDataBinding> {

        private List<T> list;
        private OnclickItemListener onClickListener;
        private ViewData<T, D> viewData;

        public Builder<T, D> addList(List<T> list) {
            this.list = list;
            return this;
        }

        public Builder<T, D> addOnClickListener(OnclickItemListener<T> onClickItems) {
            this.onClickListener = onClickItems;
            return this;
        }

        public Builder<T, D> addViewData(ViewData<T, D> viewData) {
            this.viewData = viewData;
            return this;
        }

        public GenericAdapter<T, D> build() {
            GenericAdapter<T, D> adapter = new GenericAdapter<>();
            adapter.addList(list);
            adapter.addViewData(viewData);
            adapter.addOnClickListener(onClickListener);
            return adapter;
        }
    }


}



