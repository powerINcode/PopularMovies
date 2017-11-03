package com.example.powerincode.popularmovies.common.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.powerincode.popularmovies.R;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by powerman23rus on 01.11.17.
 * Enjoy ;)
 */

public class SegmentView extends CustomView implements View.OnTouchListener {
    @BindView(R.id.ll_segment_container)
    LinearLayout mContainer;

    private SegmentAction mActionListener;

    public interface SegmentAction {
        void onSegmentSelected(int position);
    }

    private ArrayList<Button> mSegments = new ArrayList<>();
    private Button mSelectedSegment;

    public SegmentView(@NonNull Context context) {
        super(context);
    }

    public SegmentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SegmentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_segment;
    }

    public void initialize(final SegmentAction actionListener, String... segmentsLabels) {
        if(segmentsLabels.length < 2) {
            throw new IllegalArgumentException("Segments must be more than 2.");
        }

        mActionListener = actionListener;
        int i = 0;
        for(String segment : segmentsLabels) {
            final Button button = new Button(getContext());
            this.mSegments.add(button);
            mContainer.addView(button);

            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            button.setTextAppearance(getContext(), R.style.SegmentButtonStyle);
            button.setText(segment);

            int backgroundId = 0;
            if (i == 0) {
               backgroundId = R.drawable.segment_left;
            } else if(i == segmentsLabels.length - 1) {
                backgroundId = R.drawable.segment_right;
            } else {
                backgroundId = R.drawable.segment;
            }

            button.setBackground(ResourcesCompat.getDrawable(getResources(), backgroundId, null));
            button.setOnTouchListener(this);
            i++;
        }
    }

    public void setActive(int position) {
        if(position < mSegments.size()) {
            if (mSelectedSegment != null) {
                mSelectedSegment.setPressed(false);
            }

            Button button = mSegments.get(position);
            button.setPressed(true);
            mSelectedSegment = button;

            if (mActionListener != null) {
                mActionListener.onSegmentSelected(mSegments.indexOf(button));
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view instanceof Button) {
            Button button = (Button) view;

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                setActive(mSegments.indexOf(button));
            }
        }
        return true;
    }
}
