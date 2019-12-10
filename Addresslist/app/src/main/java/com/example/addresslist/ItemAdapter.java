package com.example.addresslist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addresslist.azlist.AZBaseAdapter;
import com.example.addresslist.azlist.AZItemEntity;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {

    public interface OnItemListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnListener(OnItemListener listener) {
        this.listener = listener;
    }

    private OnItemListener listener;
    private List<Person> mPersonList;

    public ItemAdapter(List<Person> personList) {
        mPersonList = personList;
    }

    public List<Person> getmPersonList() {
        return mPersonList;
    }

    public void setPersonList(List<Person> personList) {
        mPersonList = personList;
        notifyDataSetChanged();
    }

    public String getSortLetters(int position) {
        if (mPersonList == null || mPersonList.isEmpty()) {
            return null;
        }
        return mPersonList.get(position).getSortLetters();
    }

    public int getSortLettersFirstPosition(String letters) {
        if (mPersonList == null || mPersonList.isEmpty()) {
            return -1;
        }
        int position = -1;
        for (int index = 0; index < mPersonList.size(); index++) {
            if (mPersonList.get(index).getSortLetters().equals(letters)) {
                position = index;
                break;
            }
        }
        return position;
    }

    public int getNextSortLetterPosition(int position) {
        if (mPersonList == null || mPersonList.isEmpty() || mPersonList.size() <= position + 1) {
            return -1;
        }
        int resultPosition = -1;
        for (int index = position + 1; index < mPersonList.size(); index++) {
            if (!mPersonList.get(position).getSortLetters().equals(mPersonList.get(index).getSortLetters())) {
                resultPosition = index;
                break;
            }
        }
        return resultPosition;
    }


    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);
        final ItemHolder holder = new ItemHolder(view);
//        // 设置点击事件
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                Person person = mPersonList.get(position);
//                // 传递数据
//                // 在非activity中使用startActivity出错
//                ViewPersonActivity.actionStart(holder.mView.getContext(), person.getId(), person.getName(), person.getTel(), person.getEmail(), person.getCompany());
//            }
//        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int position) {
        Person person = mPersonList.get(position);
        holder.mTextName.setText(person.getName());
        holder.mTextTel.setText(person.getTel());
        // 设置点击事件
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.mView,position);
            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClick(holder.mView,position);
                return false;
            }
        });
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        TextView mTextName;
        TextView mTextTel;
        View mView;

        ItemHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mTextName = itemView.findViewById(R.id.person_name);
            mTextTel = itemView.findViewById(R.id.person_tel);
        }
    }

    @Override
    public void onViewRecycled(ItemHolder holder){
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return mPersonList == null ? 0 : mPersonList.size();
    }

    public Person getItem(int position){
        if(mPersonList!=null&&(mPersonList.size()>=position)){
            return mPersonList.get(position);
        }
        return null;
    }
}