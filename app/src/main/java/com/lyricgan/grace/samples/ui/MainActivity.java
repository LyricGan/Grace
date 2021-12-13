package com.lyricgan.grace.samples.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hjq.permissions.XXPermissions;
import com.lyricgan.grace.samples.R;
import com.lyricgan.grace.samples.SampleApplication;
import com.lyricgan.grace.samples.app.BaseActivity;
import com.lyricgan.grace.samples.constants.IExtras;
import com.lyricgan.grace.samples.util.GifSizeFilter;
import com.lyricgan.grace.samples.util.JumpHelper;
import com.lyricgan.util.UriUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.util.List;

public class MainActivity extends BaseActivity {
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_SELECT_VIDEO && resultCode == Activity.RESULT_OK) {
            onSelectResult(data);
        }
    }

    private void onVideoPlayClick() {
        XXPermissions.with(this).permission(Manifest.permission.WRITE_EXTERNAL_STORAGE).request((permissions, all) -> {
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
        String url = UriUtils.getPath(SampleApplication.getContext(), uri);
        tvContent.append(url);

        jumpVideoPage(url);
    }

    private void jumpVideoPage(String url) {
        Bundle extras = new Bundle();
        extras.putString(IExtras.KEY_URL, url);
        JumpHelper.jumpActivity(this, VideoActivity.class, extras);
    }
}
