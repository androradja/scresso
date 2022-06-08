package com.putrayelfihapp.mp3juicecc.tools;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ItemDecoration {


    public static class SpaceItemDecoration extends RecyclerView.ItemDecoration{
        private final int spanCount;
        private final int spacingPx;

        public SpaceItemDecoration(int spanCount, int spacingPx) {
            this.spanCount = spanCount;
            this.spacingPx = spacingPx;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view) ; // item position

            if (position<5){
                outRect.offsetTo(spacingPx,0);
                outRect.right=0;
            }





        }


    }



    public static class SpaceItemDecorationGrid extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;


        public SpaceItemDecorationGrid(int spanCount, int spacing) {
            this.spanCount = spanCount;
            this.spacing = spacing;

        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view) ; // item position

            if (position >= 0) {
                int column = position % spanCount; // item column

                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = 0; // item top
                }
            } else {
                outRect.left = 0;
                outRect.right = 0;
                outRect.top = 0;
                outRect.bottom = 0;
            }


        }

    }


}