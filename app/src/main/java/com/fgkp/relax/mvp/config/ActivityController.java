package com.fgkp.relax.mvp.config;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by Administrator
 *  Activity 管理器
 */

public class ActivityController {
    private static ActivityController instance;
    private Stack<Activity> activityStack;//activity栈

    private ActivityController() {
    }

    /**
     * 单例模式
     * @return
     */
    public static ActivityController getInstance() {
        if (instance == null) {
            synchronized (ActivityController.class) {
                if (instance == null) {
                    instance = new ActivityController();
                }
            }
        }
        return instance;
    }

    /**
     * 添加一个activity
     * @param actvity
     */
    public void pushOneActivity(Activity actvity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(actvity);
    }

    /**
     * 获取栈顶的activity，先进后出原则
     * @return
     */
    public Activity getLastActivity() {
        if (activityStack == null || activityStack.empty()) {
            return null;
        }
        return activityStack.lastElement();
    }

    /**
     * 移除指定activity
     * @param activity
     */
    public void popOneActivity(Activity activity) {
        if (activityStack != null && activityStack.size() > 0) {
            if (activity != null&&activityStack.contains(activity)) {
                activity.finish();
                activityStack.remove(activity);
            }
        }
    }


    /**
     * 退出所有activity
     */
    public void finishAllActivity() {
        if (activityStack != null) {
            while (activityStack.size() > 0) {
                Activity activity = getLastActivity();
                if (activity == null) break;
                popOneActivity(activity);
            }
        }
    }



}

