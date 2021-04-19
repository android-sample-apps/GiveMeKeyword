package com.mut_jaeryo.presentation.ui.story;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StoryDecoration extends RecyclerView.ItemDecoration {

    private final int divHeight;


    public StoryDecoration(int divHeight) {
        this.divHeight = divHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1)
            outRect.bottom = divHeight;

        if (parent.getChildAdapterPosition(view) % 2 == 0) {
            outRect.right = divHeight;
        } else
            outRect.left = divHeight;
    }
}