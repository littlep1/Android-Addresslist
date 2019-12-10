package com.example.addresslist;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewWithContextMenu extends RecyclerView {
    private final static String TAG = "RVWCM";

    private RecyclerViewContextInfo mContextInfo = new RecyclerViewContextInfo();

    public RecyclerViewWithContextMenu(Context context) {
        super(context);
    }

    public RecyclerViewWithContextMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewWithContextMenu(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean showContextMenuForChild(View originalView) {
        getPositionByChild(originalView);
        return super.showContextMenuForChild(originalView);
    }

    @Override
    public boolean showContextMenuForChild(View originalView, float x, float y) {
        getPositionByChild(originalView);
//        LayoutManager layoutManager = getLayoutManager();
//        if(layoutManager == null){
//            Log.d(TAG, "showContextMenuForChild: layoutManager null");
//        }
//        if(layoutManager != null) {
//            int position = layoutManager.getPosition(originalView);
//            Log.d(TAG,"showContextMenuForChild position = " + position);
//            mContextInfo.mPosition = position;
//        }
        return super.showContextMenuForChild(originalView, x, y);
    }

    private void getPositionByChild(View originalView) {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager == null) {
            Log.d(TAG, "layoutManager null");
        }
        if (layoutManager != null) {
            int count = layoutManager.getItemCount();
            Log.d(TAG, "ItemCount: "+count);
            int position = layoutManager.getPosition(originalView);
            Log.d(TAG,"showContextMenuForChild position = " + position);
            mContextInfo.setPosition(position);
        }
    }

    @Override
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return mContextInfo;
    }

    public static class RecyclerViewContextInfo implements ContextMenu.ContextMenuInfo {
        private int mPosition = -1;

        public int getPosition() {
            return this.mPosition;
        }

        public int setPosition(int position) {
            return this.mPosition = position;
        }
    }
}