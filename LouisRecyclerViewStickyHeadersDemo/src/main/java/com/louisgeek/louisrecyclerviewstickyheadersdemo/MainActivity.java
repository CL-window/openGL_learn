package com.louisgeek.louisrecyclerviewstickyheadersdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eowise.recyclerview.stickyheaders.OnHeaderClickListener;
import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;

public class MainActivity extends AppCompatActivity implements OnHeaderClickListener {

    private StickyHeadersItemDecoration topStickyHeadersItemDecoration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView  recyclerView = (RecyclerView)findViewById(R.id.id_rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        RecyclerViewAdapterWithHeader recyclerViewAdapterWithHeader= new RecyclerViewAdapterWithHeader(this);
        recyclerViewAdapterWithHeader.setOnItemClickListener(new RecyclerViewAdapterWithHeader.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {
                Toast.makeText(MainActivity.this, "Click on item" +position+";object："+object.toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemLongClick(int position, Object object) {

            }
        });



        topStickyHeadersItemDecoration= new StickyHeadersBuilder()
                .setAdapter(recyclerViewAdapterWithHeader)
                .setRecyclerView(recyclerView)
                .setOnHeaderClickListener(this)
                .build();

       recyclerView.addItemDecoration(topStickyHeadersItemDecoration);
    }

    @Override
    public void onHeaderClick(View header, long headerId) {
        TextView textView= (TextView) header.findViewById(R.id.id_tv_head_item);
        Toast.makeText(this, "Click on headerId:" +headerId+";textView="+textView.getText(), Toast.LENGTH_SHORT).show();
    }
}
