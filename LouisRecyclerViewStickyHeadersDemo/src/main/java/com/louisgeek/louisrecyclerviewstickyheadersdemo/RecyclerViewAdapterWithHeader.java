package com.louisgeek.louisrecyclerviewstickyheadersdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eowise.recyclerview.stickyheaders.StickyHeadersAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class RecyclerViewAdapterWithHeader extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyHeadersAdapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private OnItemClickListener listener;
    private List<Map<String, Object>> mapList = new ArrayList<>();
    private List<String> tempItems = new ArrayList<>();
    private LinkedHashMap<String, Boolean> linkedHashMap;

    public RecyclerViewAdapterWithHeader(Context context) {
        this.mContext = context;
       /* String[] countries =mContext.getResources().getStringArray(R.array.countries_cn);
        listStr=Arrays.asList(countries);*/
        for (int i = 0; i < 5; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "AA" + i);
            mapList.add(map);
        }
        for (int i = 0; i < 8; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "BB" + i);
            mapList.add(map);
        }
        for (int i = 0; i < 15; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "CC" + i);
            mapList.add(map);
        }


        //2016年4月12日10:41:08
        setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final MyViewHolder myViewHolder = (MyViewHolder) holder;

        //final int realPosition=myViewHolder.getPosition();

        myViewHolder.mTextView.setText(mapList.get(position).get("name").toString() + " pos:" + position);

        myViewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {

                    listener.onItemClick(position, mapList.get(position));
                }
            }
        });
        myViewHolder.mTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != listener) {

                    listener.onItemLongClick(position, mapList.get(position));
                }
                return false;
            }
        });

    }


    @Override
    public int getItemCount() {
        return mapList.size();
    }

    //必须重写  不然item会错乱
    @Override
    public long getItemId(int position) {
        //return super.getItemId(position);
        // return countries[position].hashCode();
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_header, parent, false);

        return new MyHeaderViewHolder(itemView);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        MyHeaderViewHolder myHeaderViewHolder = (MyHeaderViewHolder) viewHolder;
        myHeaderViewHolder.title.setText(mapList.get(position).get("name").toString().subSequence(0, 1) + " pos:" + position);
        //headerViewHolder.title.setText(countries[position].subSequence(0, 1));
    }

    @Override
    public long getHeaderId(int position) {
        /*     if (position<3){
            return 0;
        }else if (position<5){
            return 1;
        }else if (position<8){
            return 2;
        }else {
            return 3;
        }*/
        return mapList.get(position).get("name").toString().subSequence(0, 1).hashCode();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public MyViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.id_tv_item);
        }
    }

    public static class MyHeaderViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public MyHeaderViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.id_tv_head_item);
        }
    }

    void addItem(int position, Object object) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "新的数据" + position);
        mapList.add(position, map);
        //notifyDataSetChanged();
        notifyItemInserted(position);
    }

    void deleteItem(int position) {
        mapList.remove(position);
        //暂时解决RecyclerView删除第一项报错问题  IndexOutOfBoundsException Invalid item position
        if (position == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRemoved(position);
        }
        //后来发现https://github.com/lucasr/twoway-view/issues/134   有同样的问题 2016-4-12 20:10:49


    }


    /**
     * 内部接口回调方法
     */
    public interface OnItemClickListener {
        void onItemClick(int position, Object object);

        void onItemLongClick(int position, Object object);
    }

    /**
     * 设置监听方法
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
