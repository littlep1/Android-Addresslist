package com.example.addresslist;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static java.security.AccessController.getContext;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

//    public interface OnItemListener {
//        void onItemClick(View view, int position);
//
//        void onItemLongClick(View view, int position);
//    }
//
//    public void setOnListener(OnItemListener listener) {
//        this.listener = listener;
//    }

//    private OnItemListener listener;
    private List<Person> mPersonList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView personName, personTel;
        View personView;


        public ViewHolder(View view) {
            super(view);
            personView = view;
            personName = (TextView) view.findViewById(R.id.person_name);
            personTel = (TextView) view.findViewById(R.id.person_tel);
        }
    }

    public PersonAdapter(List<Person> personList) {
        mPersonList = personList;
    }

    public void setmPersonList(List<Person> personList) {
        mPersonList = personList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
//        Person person = mPersonList.get(position);
////        holder.personImage.setImageResource(person.getImageId());
//        holder.personName.setText(person.getName());
//        holder.personTel.setText(person.getTel());
//        // 设置点击事件
//        holder.personView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onItemClick(holder.personView,position);
////                int position = holder.getAdapterPosition();
////                Person person = mPersonList.get(position);
//                // 传递数据
//                // 在非activity中使用startActivity出错
////                ViewPersonActivity.actionStart(holder.personView.getContext(), person.getId(), person.getName(), person.getTel(), person.getEmail(), person.getCompany());
//            }
//        });
//        holder.personView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                listener.onItemLongClick(holder.personView,position);
////                int position = holder.getAdapterPosition();
////                Person person = mPersonList.get(position);
//                return true;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mPersonList == null ? 0 : mPersonList.size();
    }

}
