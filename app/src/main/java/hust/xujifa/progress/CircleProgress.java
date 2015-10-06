package hust.xujifa.progress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.renderscript.Sampler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by xujifa on 2015/10/6.
 */
public class CircleProgress extends View {
    Paint circle;
    Path path;
    RectF r;
    int sweepangle = 20;
    int startangle=0;
    int arc1=0;
    int arc2=20;
    int temparc;
    boolean f =true;
    final float scale = getResources().getDisplayMetrics().density;

    public CircleProgress(Context context) {
        this(context, null);
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        circle = new Paint();
        circle.setColor(getResources().getColor(R.color.color2));
        circle.setStyle(Paint.Style.STROKE);
        circle.setStrokeWidth(12);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(f){
            arc1+=10;
            arc2+=3;
        }else{
            arc1+=3;
            arc2+=10;
        }
        if(arc1>360) {
            temparc = arc1 % 360;
            arc2 = temparc + arc2 - arc1;
            arc1 = temparc;
        }
        if(arc1-arc2<=20)f=true;
        else if(arc1-arc2>=320)f=false;

        canvas.drawArc(r, arc2, arc1-arc2, false, circle);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        r = new RectF(getLeft() + 20, getTop() + 20, getRight() - 20, getBottom() - 20);
        setMeasuredDimension((int) (48 * scale), (int) (48 * scale));

    }


}
