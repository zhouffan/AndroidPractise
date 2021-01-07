package com.fw.custom_view.draw_paint_shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.fw.custom_view.R;

/**
 * author : Fuwa
 * e-mail : fufan.zhou@androidmov.com
 * date   : 2021/1/7 15:50
 * desc   : https://www.cnblogs.com/webor2006/p/12178704.html
 *
 * version: 1.0
 */
public class ZoomImageView extends View {
    //放大倍数
    private static final int FACTOR = 2;
    //放大镜的半径
    private static final int RADIUS = 100;
    //原图
    Bitmap mBitmap;
    //放大后的整张图片
    Bitmap scaleBitmap;
    //放大 局部图片 绘制
    ShapeDrawable shapeDrawable;
    Matrix matrix;
    public ZoomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        scaleBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth() * FACTOR, mBitmap.getHeight()*FACTOR, true);
        //绘制放大镜图
        shapeDrawable = new ShapeDrawable(new OvalShape());
        BitmapShader bitmapShader = new BitmapShader(scaleBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shapeDrawable.getPaint().setShader(bitmapShader);
        //矩形区域，画内切圆
        shapeDrawable.setBounds(0,0,RADIUS * 2, RADIUS * 2);
        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制原图
        canvas.drawBitmap(mBitmap,0, 0, null);
        //绘制放大镜图
        shapeDrawable.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        //放大内容  反方向移动
        matrix.setTranslate(RADIUS - x * FACTOR, RADIUS - y * FACTOR);
        shapeDrawable.getPaint().getShader().getLocalMatrix(matrix);
        //矩形区域，画内切圆
        shapeDrawable.setBounds(x -RADIUS, y - RADIUS, x + RADIUS, y + RADIUS);
        //触发onDraw
        invalidate();
        return super.onTouchEvent(event);
    }
}
