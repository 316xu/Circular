#Android Custom View --- Circular(环形条)
这次是实现一个简单的环形条，下图这样的，还是尽量简单的写，让新手能够看懂

![](https://github.com/jifaxu/image/blob/master/circle1.PNG?raw=true)

这一次没多少代码，就贴一下核心部分，别的大家可以自己看源码
```
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
```
解释一下，代码里arc1和arc2分别是圆弧的首末角度，每次绘制后即调用invalidate()立即进行下一次绘制，实现圆环不断旋转的效果



