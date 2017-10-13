/*
package com.louisgeek.louisrecyclerviewstickyheadersdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eowise.recyclerview.stickyheaders.StickyHeadersAdapter;

*/
/**
 * Created by aurel on 24/09/14.
 *//*

public class RecyclerViewHeaderAdapter implements StickyHeadersAdapter<RecyclerViewHeaderAdapter.ViewHolder> {

    private String[] countries;
    private Context mContext;

    public RecyclerViewHeaderAdapter(Context context) {
        this.mContext = context;
        countries =mContext.getResources().getStringArray(R.array.countries_cn);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_header, parent, false);

        return new ViewHolder(itemView);
    }

    // changeBy louisgeek  2016-4-12 14:04:44
    @Override
    public void onBindHeaderViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.title.setText(countries[position].toString().subSequence(0, 1));
        //headerViewHolder.title.setText(countries[position].subSequence(0, 1));
    }

    // changeBy louisgeek  2016-4-12 14:04:44
  */
/*  @Override
    public void onBindViewHolder(ViewHolder headerViewHolder, int position) {
        headerViewHolder.title.setText(countries[position].toString().subSequence(0, 1));
        //headerViewHolder.title.setText(countries[position].subSequence(0, 1));
    }*//*


    @Override
    public long getHeaderId(int position) {
        */
/*     if (position<3){
            return 0;
        }else if (position<5){
            return 1;
        }else if (position<8){
            return 2;
        }else {
            return 3;
        }*//*

        return countries[position].toString().subSequence(0, 1).hashCode();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.id_tv_head_item);
        }
    }
}
*/
