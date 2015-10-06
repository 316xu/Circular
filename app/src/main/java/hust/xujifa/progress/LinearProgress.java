package hust.xujifa.progress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.animation.ValueAnimatorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by xujifa on 2015/10/6.
 */
public class LinearProgress extends View implements ValueAnimator.AnimatorUpdateListener{
    final float scale = getResources().getDisplayMetrics().density;
    int style = -1;
    int color1;
    int color2;
    Paint bg;
    Paint linear;
    int width;
    float line_scale;
    int left;
    int w;
    public LinearProgress(Context context) {
        this(context, null);
    }

    public LinearProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    public void setScale(float sc){
        this.line_scale=sc;
        invalidate();
    }
    private void init(Context context, AttributeSet attrs) {
        TypedArray at = context.getTheme().
                obtainStyledAttributes(attrs, R.styleable.LinearProgress, 0, 0);
        try {
            style = at.getInt(R.styleable.LinearProgress_linear_style, -1);
            color1 = at.getColor(R.styleable.LinearProgress_color1,
                    getResources().getColor(R.color.color1, context.getTheme()));
            color2 = at.getColor(R.styleable.LinearProgress_color2,
                    getResources().getColor(R.color.color2, context.getTheme()));
        } finally {
            at.recycle();
        }
        bg = new Paint();
        bg.setStyle(Paint.Style.FILL);
        bg.setColor(color1);
        bg.setStrokeWidth(8*scale);
        linear = new Paint();
        linear.setStyle(Paint.Style.FILL);
        linear.setColor(color2);
        linear.setStrokeWidth(8 * scale);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center=(getTop()+getBottom())/2;
        canvas.drawLine(getLeft(), center, getRight(), center, bg);

        if(style==0) {
            canvas.drawLine(getLeft(), center, getLeft() + line_scale * width, center, linear);
        }else if(style==1){
            canvas.drawLine(left, center, left+w, center, linear);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int desiredWidth = 900;
        int desiredHeight = 100;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }
        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }
        this.width = width;
        this.w=width/2;
        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
    public void start(){
        ValueAnimator a= ValueAnimator.ofInt(0, 100);
        a.setDuration(5000);
        left=getLeft()-w;
        a.addUpdateListener(this);
        a.start();
    }
    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int time=(int)animation.getAnimatedValue();
        if(time<30)
        left=(int)(getLeft()-w+time*3*w/100f);
        if(time==100)start();
        invalidate();
    }
}
