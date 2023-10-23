package com.lcy.simulate;

import android.accessibilityservice.AccessibilityService;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.LinkedList;

public class SimulateAccessibilityService extends AccessibilityService {

    private String TAG = "lcyy";
    private StringBuilder sb;

    public SimulateAccessibilityService() {
        Log.e(TAG, "SimulateAccessibilityService");
    }
//
//    @Override
//    protected void onServiceConnected() {
//        super.onServiceConnected();
//        // Set the type of events that this service wants to listen to. Others
//        // won't be passed to this service.'
//        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
//        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED |
//                AccessibilityEvent.TYPE_VIEW_FOCUSED;
//
//        // If you only want this service to work with specific applications, set their
//        // package names here. Otherwise, when the service is activated, it will listen
//        // to events from all applications.
////        info.packageNames = new String[]{"fun.learnlife.androidmq"};
//
//        // Set the type of feedback your service will provide.
//        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
//
//        // Default services are invoked only if no package-specific ones are present
//        // for the type of AccessibilityEvent generated. This service *is*
//        // application-specific, so the flag isn't necessary. If this was a
//        // general-purpose service, it would be worth considering setting the
//        // DEFAULT flag.
//
//        // info.flags = AccessibilityServiceInfo.DEFAULT;
//
//        info.notificationTimeout = 100;
//
//        this.setServiceInfo(info);
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "create");
    }

    @Override
    public void onInterrupt() {
        Log.e(TAG, "onInterrupt");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e(TAG, "getEventType = " + event.getEventType());
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            // 获取当前活动的应用包名
            String packageName = event.getPackageName().toString();
            Log.e(TAG, "packageName = " + packageName);
            // 如果是目标应用 com.test.simulate，则进行处理
            if ("com.xiaomi.market".equals(packageName)) {
                AccessibilityNodeInfo rootNode = getRootInActiveWindow();
                if (rootNode != null) {
                    // 提取并打印页面内容
                    sb = new StringBuilder();
                    printNodeInfo(rootNode);
                    Log.d(TAG, "Text: " + sb.toString());
                }
            }
        }
    }

    boolean canAdd = false;

    private void printNodeInfo(AccessibilityNodeInfo node) {
        // 打印节点的文本内容
        CharSequence text = node.getText();
        if (!TextUtils.isEmpty(text)) {
            sb.append(",").append(text);
            canAdd = true;
        }
        if (canAdd) {
            sb.append("----------\n");
            canAdd = false;
        }
        int childCount = node.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo childNode = node.getChild(i);
            if (childNode != null) {
                printNodeInfo(childNode);
            }
        }
    }


}