package com.lyricgan.grace.samples.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hjq.permissions.XXPermissions;
import com.lyricgan.grace.samples.R;
import com.lyricgan.grace.samples.app.BaseActivity;
import com.lyricgan.grace.samples.util.GifSizeFilter;
import com.lyricgan.grace.samples.app.PageHelper;
import com.lyricgan.util.ApplicationUtils;
import com.lyricgan.util.LogUtils;
import com.lyricgan.util.UriUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private static final int CODE_SELECT_VIDEO = 0x1001;

    private TextView tvContent;

    @Override
    public int getContentViewId() {
        return R.layout.main_layout;
    }

    @Override
    public void onCreateContentView(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.btn_video_play).setOnClickListener(v -> onVideoPlayClick());
        tvContent = view.findViewById(R.id.tv_content);
    }

    @Override
    public void onCreateData(Bundle savedInstanceState) {
        LogUtils.d(TAG, "DebugMode:" + ApplicationUtils.isDebugMode(ApplicationUtils.getContext()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_SELECT_VIDEO && resultCode == Activity.RESULT_OK) {
            onSelectResult(data);
        }
    }

    private void onVideoPlayClick() {
        List<String> storagePermissions = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            storagePermissions.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE);
        } else {
            storagePermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            storagePermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        XXPermissions.with(this).permission(storagePermissions).request((permissions, all) -> {
            if (all) {
                selectVideo();
            }
        });
    }

    private void selectVideo() {
        Matisse.from(this)
                .choose(MimeType.ofVideo())
                .showSingleMediaType(true)
                .countable(false)
                .maxSelectable(1)
                .addFilter(new GifSizeFilter(320, 320, 10 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .showPreview(true)
                .forResult(CODE_SELECT_VIDEO);
    }

    private void onSelectResult(Intent data) {
        List<Uri> selectedList = Matisse.obtainResult(data);
        if (selectedList == null || selectedList.isEmpty()) {
            return;
        }
        String uriStrings = TextUtils.join(",", selectedList);
        tvContent.setText(uriStrings);

        Uri uri = selectedList.get(0);
        String url = UriUtils.getPath(ApplicationUtils.getContext(), uri);
        tvContent.append(url);

        jumpVideoPage(url);
    }

    private void jumpVideoPage(String url) {
        Bundle extras = new Bundle();
        extras.putString("key_url", url);
        PageHelper.jump(this, VideoActivity.class, extras);
    }
}
