package com.fw.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * author : Fuwa
 * e-mail : fufan.zhou@androidmov.com
 * date   : 2021/1/6 16:31
 * desc   : https://www.cnblogs.com/webor2006/p/12167825.html
 *
 *  1 流式布局
 *
 * version: 1.0
 */
public class Flowlayout extends ViewGroup {
    //每一行的view集合， 叠加的所有views
    private List<List<View>> linesView = new ArrayList<>();
    //所有行高 集合
    private List<Integer> lineHeight = new ArrayList<>();

    public Flowlayout(Context context) {
        this(context, null);
    }

    public Flowlayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Flowlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(getClass().getSimpleName(),"=====>onMeasure");
        linesView.clear();
        lineHeight.clear();
        //根据 mode 测量当前控件的自身高度
        int measuredWidth = 0;
        int measuredHeight = 0;
        //获取宽高 mode和size
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //宽高都是 精确值时，直接使用size      match也是固定值
        if(widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY){
            measuredWidth = widthSize;
            measuredHeight = heightSize;
        }else {
            //当前行宽、高
            int currentTotalLineWith = 0;
            int currentTotalLineHeight = 0;
            List<View> singleLineView = new ArrayList<>();
            //根据子view的宽度计算 整个viewGroup的高度===================> measuredWidth和measuredHeight
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                //先让子view测量自身
                measureChild(child, widthMeasureSpec, heightMeasureSpec);//2/3参数是父view的
                //获取子view 的 margin信息
                MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
                //获取子view的实际宽高
                int childMeasuredWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                int childMeasuredHeight = child.getMeasuredHeight() + layoutParams.bottomMargin + layoutParams.topMargin;
                //换行
                if(currentTotalLineWith + childMeasuredWidth > widthSize){
                    //获取当前行的 最大宽
                    measuredWidth = Math.max(measuredWidth, currentTotalLineWith);
                    //累加 行高到 父view中
                    measuredHeight += currentTotalLineHeight;

                    //当前行高
                    lineHeight.add(currentTotalLineHeight);
                    linesView.add(singleLineView);
                    singleLineView = new ArrayList<>();

                    //换行后，初始化当前行数据，设置成当前第一个
                    currentTotalLineWith = childMeasuredWidth;
                    currentTotalLineHeight = childMeasuredHeight;
                    //添加单行的view
                    singleLineView.add(child);
                }else {
                    //不换行， 累加
                    currentTotalLineWith += childMeasuredWidth;
                    currentTotalLineHeight = Math.max(currentTotalLineHeight, childMeasuredHeight);//取每行最高的一个
                    //添加单行的view
                    singleLineView.add(child);
                }
            }
        }


        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i(getClass().getSimpleName(),"=====>onLayout");
        int curLeft = 0;
        int curTop = 0;
        int left, top, right, bottom;
        for (int i = 0; i < linesView.size(); i++) {
            List<View> views = linesView.get(i);
            for (int j = 0; j < views.size(); j++) {
                View childView = views.get(j);
                MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
                left = curLeft + layoutParams.leftMargin;
                top = curTop + layoutParams.topMargin;
                right = childView.getMeasuredWidth() + left;
                bottom = childView.getMeasuredHeight() + top;
                childView.layout(left, top, right, bottom);
                //left 累加
                curLeft += right + layoutParams.rightMargin;
            }
            //换行view
            curLeft = 0;
            curTop += lineHeight.get(i);
        }
        linesView.clear();
        lineHeight.clear();
    }

    /**
     * 使子view 获取的layoutParams 能够转换成  MarginLayoutParams
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
