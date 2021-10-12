package com.lyricgan.grace.samples.view;

import android.view.View;

/**
 * Interface definition for a callback to be invoked
 * when the children in LinkageScrollLayout {@link ELinkageScrollLayout} trigger some important event.
 *
 * @author zhanghao43
 */
public interface ChildLinkageEvent {
    /**
     * Callback method to be invoked when the children in LinkageScrollLayout {@link ELinkageScrollLayout}
     * has been scrolled to top.</p>
     */
    void onContentScrollToTop(View target);

    /**
     * Callback method to be invoked when the children in LinkageScrollLayout {@link ELinkageScrollLayout}
     * has been scrolled to bottom.</p>
     */
    void onContentScrollToBottom(View target);

    /**
     * Callback method to be invoked when the children in LinkageScrollLayout {@link ELinkageScrollLayout}
     * has been scrolled.</p>
     */
    void onContentScroll(View view);
}
