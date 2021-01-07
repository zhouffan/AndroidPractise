package com.fw.custom_view.draw_paint_shader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * author : Fuwa
 * e-mail : fufan.zhou@androidmov.com
 * date   : 2021/1/7 18:11
 * desc   :
 * version: 1.0
 */
public class LinearGradientTextView extends AppCompatTextView {
    int[] colors = new int[]{0x22ffffff, 0xffffffff, 0x22ffffff};
    LinearGradient linearGradient;
    Matrix matrix;
    private float mTranslate;
    private float DELTAX = 20;
    float textWidth;
    public LinearGradientTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        matrix = new Matrix();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        TextPaint paint = getPaint();
        String text = getText().toString();
        float textWidth = paint.measureText(text);
        // 3个文字的宽度
        int gradientSize = (int) (textWidth/text.length() * 3);
        //渐变
        linearGradient = new LinearGradient(-gradientSize, 0, 0 , 0, colors, null, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mTranslate += DELTAX;
        //到底部进行返回
        if (mTranslate > textWidth + 1 || mTranslate < 1) {
            DELTAX = -DELTAX;
        }

        matrix.setTranslate(mTranslate, 0);//每次更改x的位置
        linearGradient.setLocalMatrix(matrix);
        postInvalidateDelayed(30);//间隔
    }
}
