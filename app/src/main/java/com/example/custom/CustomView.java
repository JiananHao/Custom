package com.example.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;

import java.util.Random;

public class CustomView extends View {

    private Paint mPaint;
    private Path mPath;
    private Point point;
    private ViewPropertyAnimator mPropertyAnimator;

    //判断触摸的区域方法
    public boolean isInTouchArea(Path path,Point point){
        //创建矩形
        RectF bounds = new RectF();
        //计算path的边界
        path.computeBounds(bounds, true);
        //创建区域范围
        Region region = new Region();
        //将path和矩形bounds区域取交集
        region.setPath(path, new Region((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom));
        return region.contains(point.x, point.y);//区域是否包含触摸点
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //触摸坐标点
            point = new Point((int) event.getX(), (int) event.getY());
            if (isInTouchArea(mPath,point)) {//传递触摸事件
                mPropertyAnimator.alpha(0.5f).scaleX(1.1f).start();
                return super.onTouchEvent(event);
            } else {
                //不继续传递触摸事件，ACTION_DOWN后续的ACTION_MOVE和ACTION_UP不再触发
                return false;
            }
        }else if (event.getAction() == MotionEvent.ACTION_UP){
            mPropertyAnimator.alpha(1).scaleX(1f).start();
        }
        return super.onTouchEvent(event);
    }

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private void init(){
        mPropertyAnimator = this.animate();
        mPaint = new Paint();
        //画笔设置随机16进制颜色
        mPaint.setColor(Color.parseColor("#"+Integer.toHexString(0 - new Random().nextInt(16777216))));
        mPaint.setStyle(Paint.Style.FILL);
        mPath = new Path();
        mPath.setFillType(Path.FillType.EVEN_ODD);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //贝赛尔曲线
        mPath.moveTo(50, 0);
        mPath.quadTo(50, 100, 0, 100);
        mPath.quadTo(0, 100, 50, 100);
        mPath.quadTo(100, 100, 100, 0);
        mPath.quadTo(100, 0, 50, 0);
        canvas.drawPath(mPath, mPaint);
    }
}
