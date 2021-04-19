package com.mut_jaeryo.presentation.ui.detail;

import android.os.Handler;
import android.view.View;

/**
 * @author pedromassango on 12/20/17.
 * @contributing to development samirtf
 * Interface to be notified from a new click event.
 */
public class DoubleClick implements View.OnClickListener {

    private long DOUBLE_CLICK_INTERVAL;  // Time to wait the second click.
    private final Handler mHandler = new Handler();
    private final DoubleClickListener doubleClickListener;
    private int clicks;

    private boolean isBusy = false;

    public DoubleClick(final DoubleClickListener doubleClickListener) {
        this(doubleClickListener, 200L);
        DOUBLE_CLICK_INTERVAL = 200L; // default time to wait the second click.
    }

    public DoubleClick(final DoubleClickListener doubleClickListener, final long DOUBLE_CLICK_INTERVAL) {
        this.doubleClickListener = doubleClickListener;
        this.DOUBLE_CLICK_INTERVAL = DOUBLE_CLICK_INTERVAL; // developer specified time to wait the second click.
    }

    @Override
    public void onClick(final View view) {

        if (!isBusy) {
            //  Prevent multiple click in this short time
            isBusy = true;

            // Increase clicks count
            clicks++;

            mHandler.postDelayed(new Runnable() {
                public final void run() {

                    if (clicks >= 2) {  // Double tap.
                        doubleClickListener.onDoubleClick(view);
                    }

                    if (clicks == 1) {  // Single tap
                        doubleClickListener.onSingleClick(view);
                    }

                    // we need to  restore clicks count
                    clicks = 0;
                }
            }, DOUBLE_CLICK_INTERVAL);
            isBusy = false;
        }

    }

    public interface DoubleClickListener {

        void onSingleClick(final View view);

        void onDoubleClick(final View view);
    }
}