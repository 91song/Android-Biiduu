package cn.biiduu.biiduu.widget;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import cn.biiduu.biiduu.R;

public class CustomMarkerView extends MarkerView {
    private TextView tvContent;

    public CustomMarkerView(Context context) {
        super(context, R.layout.layout_marker_view);
        tvContent = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText(String.format("%s", highlight.getY()));
    }

    @Override
    public MPPointF getOffset() {
        MPPointF pointF = new MPPointF();
        pointF.x = -(getWidth() / 2);
        pointF.y = -getHeight();
        return pointF;
    }
}
