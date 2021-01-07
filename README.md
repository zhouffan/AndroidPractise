# AndroidPractise
## Flowlayout
```java
    //先让子view测量自身
    measureChild(child, widthMeasureSpec, heightMeasureSpec);//2/3参数是父view的


   /**
     * 使子view 获取的layoutParams 能够转换成  MarginLayoutParams
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
```

```
 //仅仅测量自身， 不会测量子view。  所有一般重写该方法来布局子view
 @Override
 protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
    
 }
```