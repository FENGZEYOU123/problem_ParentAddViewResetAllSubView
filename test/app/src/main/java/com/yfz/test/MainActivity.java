package com.yfz.test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * 作者：游丰泽
 *
 * layout方法虽然会改变组件位置，但是其位置坐标信息不会被记录在layoutParam中，其父组件是根据子组件的layoutParam来判断其位置的。
 * 所以当父组件有新添加其他组件时（刷新页面），所有的子组件也会被刷新，那么子组件的layoutParam的值没有变化，自然就会回到原来位置。
 * 结论：如果父组件刷新时，不想重置子组件位置，就要把子组件的位置记录在子组件的layoutParam中。
 */
public class MainActivity extends AppCompatActivity {
    private ArrayList<Integer> colorArray=new ArrayList<>();
    private int index=0;
    private boolean openRecordLayoutParams=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        colorArray.add(Color.RED);
        colorArray.add(Color.GREEN);
        colorArray.add(Color.BLACK);
    }

    /**
     * 添加新的组件
     * @param v
     */
    public void addView(View v){
        LinearLayout linearLayout=new LinearLayout(this);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(200, 200);
        linearLayout.setLayoutParams(lp);
        linearLayout.setBackgroundColor(colorArray.get(index));
        linearLayout.setOnTouchListener(new OnMovingListener());
        this.addContentView(linearLayout,lp);
        index++;
        if(index==3)index=0;
    }

    /**
     * 当移动放开手的时候，将子组件位置记录在其layoutParam中
     * @param v
     */
    public void recordPosition(View v){
        openRecordLayoutParams=true;
        v.setBackgroundColor(Color.YELLOW);
    }

    /**
     * 移动组件
     */
    private class OnMovingListener implements OnTouchListener{
        private float mDownX,mDownY,mDistanceX,mDistanceY;
        private int left,right,top,bottom;
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mDownX= event.getX();
                    mDownY= event.getY();
                    break;
                case MotionEvent.ACTION_MOVE://移动子组件，并使用layout实时刷新其位置
                    mDistanceX=event.getX()-mDownX;
                    mDistanceY=event.getY()-mDownY;
                     left=(int)(view.getLeft()+mDistanceX);
                     right=(int)(left+view.getWidth());
                     top=(int)(view.getTop()+mDistanceY);
                     bottom=(int)(top+view.getHeight());
                    view.layout(left,top,right,bottom);
                    break;
                case MotionEvent.ACTION_UP: //松开手的时候将其位置记录在其layoutParam
                    if(openRecordLayoutParams) {
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                        marginLayoutParams.leftMargin = left;
                        marginLayoutParams.topMargin = top;
                        marginLayoutParams.rightMargin = right;
                        marginLayoutParams.bottomMargin = bottom;
                        marginLayoutParams.height=view.getHeight();
                        marginLayoutParams.width=view.getWidth();
                        view.setLayoutParams(marginLayoutParams);
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    }

}