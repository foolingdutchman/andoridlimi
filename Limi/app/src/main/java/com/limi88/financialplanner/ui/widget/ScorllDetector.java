//package com.limi88.financialplanner.ui.widget;
//
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//
///**
// * Created by hehao
// * Date on 16/12/5.
// * Email: hao.he@sunanzq.com
// */
//public class ScorllDetector implements View.OnTouchListener {
//    private Callback mCallback;
//
//    private   float beginX;
//    private float beginY;
//
//    private float endX;
//    private float endY;
//    private boolean isDetected=true;
//
//    public ScorllDetector(Callback callback) {
//        mCallback = callback;
//    }
//
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        if (event.getAction()==MotionEvent.ACTION_DOWN) {
//            beginX=event.getX();
//            beginY=  event.getY();
//        }
//        if (event.getAction()==MotionEvent.ACTION_MOVE) {
//            endY=event.getY();
//            endX=event.getX();
//            float dy=beginY-endY;
//            float dx=beginX-endX;
//            Log.i("home","Y move is detected:-------"+dy);
//
//            if (isDetected&&dy>5) {
//
//                mCallback.callback(dx,dy);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public   interface Callback{
//        void callback(float dx,float dy);
//    }
//
//    public boolean isDetected() {
//        return isDetected;
//    }
//
//    public void setIsDetected(boolean isDetected) {
//        this.isDetected = isDetected;
//    }
//}
