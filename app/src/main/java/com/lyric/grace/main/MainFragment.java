package com.lyric.grace.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.lyric.arch.AppExtras;
import com.lyric.arch.AppFragment;
import com.lyric.grace.R;

/**
 * @author lyricgan
 * @since 2019/2/13
 */
public class MainFragment extends AppFragment {
    private String mTitle;
    private IMainCallback mCallback;

    public static MainFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(AppExtras.TITLE, title);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IMainCallback) {
            mCallback = (IMainCallback) context;
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onCreateExtras(Bundle savedInstanceState, @NonNull Bundle args) {
        super.onCreateExtras(savedInstanceState, args);
        mTitle = args.getString(AppExtras.TITLE);
    }

    @Override
    public void onCreateContentView(View view, Bundle savedInstanceState) {
        TextView tvContent = view.findViewById(R.id.tv_content);
        tvContent.setText(mTitle);

        tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback != null) {
                    mCallback.showMessage(mTitle);
                }
            }
        });
    }
}
