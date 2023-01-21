package com.meitong.newswipe.ui.search;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

import com.meitong.newswipe.util.LogUtils;

import java.lang.ref.WeakReference;

public class AutoPollRecyclerView extends RecyclerView {
    private static final long TIME_AUTO_POLL = 16;
    AutoPollTask autoPollTask;
    private boolean running; // notify whether it is automatic
    private boolean canRun;// notify whether it can

    public AutoPollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        autoPollTask = new AutoPollTask(this);
    }

    static class AutoPollTask implements Runnable {
        private final WeakReference<AutoPollRecyclerView> mReference;

        //使用弱引用持有外部类引用->防止内存泄漏
        public AutoPollTask(AutoPollRecyclerView reference) {
            this.mReference = new WeakReference<AutoPollRecyclerView>(reference);
        }

        @Override
        public void run() {
            AutoPollRecyclerView recyclerView = mReference.get();
            LogUtils.INSTANCE.e("lazy mode: on");
            if (recyclerView != null && recyclerView.running && recyclerView.canRun) {
//                recyclerView.scrollBy(2, 2);
                recyclerView.scrollBy(2, 2);
                recyclerView.postDelayed(recyclerView.autoPollTask, recyclerView.TIME_AUTO_POLL);
            }
        }
    }

    //开启:if it is running , stop -> run
    public void start() {
        if (running)
            stop();
        canRun = true;
        running = true;
        postDelayed(autoPollTask, TIME_AUTO_POLL);
    }

    public void stop() {
        running = false;
        removeCallbacks(autoPollTask);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (running)
                    stop();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
//                if (canRun)
//                    start();
                break;
        }
        //return  false
        return super.onTouchEvent(e);}
}
