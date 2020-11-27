package com.lyric.grace.samples;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyric.grace.R;
import com.lyric.grace.samples.app.BaseFragment;

import java.util.ArrayList;

/**
 * 嵌套滑动页面
 *
 * @author Lyric Gan
 * @since 2020/8/5
 */
public class NestedScrollFragment extends BaseFragment {

    public static NestedScrollFragment newInstance() {
        Bundle args = new Bundle();
        NestedScrollFragment fragment = new NestedScrollFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.nested_scroll_layout;
    }

    @Override
    public void onCreateContentView(View view, Bundle savedInstanceState) {
        WebView webView = view.findViewById(R.id.webview);
        initWebView(webView);

        RecyclerView recyclerView = view.findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LinkageRecyclerAdapter rvAdapter = new LinkageRecyclerAdapter(getListData("RecyclerView - ", 20), 0x6600ffff);
        recyclerView.setAdapter(rvAdapter);

        recyclerView = view.findViewById(R.id.recycler2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LinkageRecyclerAdapter rvAdapter2 = new LinkageRecyclerAdapter(getListData("RecyclerView - ", 5), 0x66ff00ff);
        recyclerView.setAdapter(rvAdapter2);

        recyclerView = view.findViewById(R.id.recycler_in_framelayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LinkageRecyclerAdapter rvAdapter3 = new LinkageRecyclerAdapter(getListData("RecyclerInFrameLayout - ", 20), 0x66ff0000);
        recyclerView.setAdapter(rvAdapter3);
    }

    @Override
    public void onCreateData(Bundle savedInstanceState) {
    }

    private void initWebView(WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("https://developer.android.google.cn/?hl=zh_cn");
    }

    private ArrayList<String> getListData(String temp, int count) {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            data.add(temp + i);
        }
        return data;
    }

    private static class LinkageRecyclerAdapter extends RecyclerView.Adapter<LinkageRecyclerAdapter.ViewHolder> {
        private ArrayList<String> mData;
        private int mVHBackgroundColor;

        public LinkageRecyclerAdapter(ArrayList<String> data, int color) {
            this.mData = data;
            this.mVHBackgroundColor = color;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.nested_recycler_item_layout, parent, false);
            item.setBackgroundColor(mVHBackgroundColor);
            return new ViewHolder(item);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTv.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView mTv;

            public ViewHolder(View itemView) {
                super(itemView);
                mTv = itemView.findViewById(R.id.text);
            }
        }
    }
}
