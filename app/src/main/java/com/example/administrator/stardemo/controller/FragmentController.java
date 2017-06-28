package com.example.administrator.stardemo.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.administrator.stardemo.R;
import com.example.administrator.stardemo.base.BaseActivity;

/**
 * Created by Administrator on 06/18/2017.
 */

public class FragmentController {

    public static void replaceDontAddToBackStack(BaseActivity activity, String nameClass, Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.popBackStack(null, 1);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.frameLayoutPlaceHolder, fragment, nameClass);
        ft.commit();
    }

    public static void replaceDontAddToBackStackUsingAnimation(BaseActivity activity, String nameClass, Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.popBackStack(null, 1);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_left_exit,
                R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_right_exit);
        ft.replace(R.id.frameLayoutPlaceHolder, fragment, nameClass);
        ft.commit();
    }

    public static void replaceAddToBackStackUsingLeftEnterAnimation(
            BaseActivity activity, Fragment fragment, String nameClass) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_left_exit,
                R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_right_exit);
        ft.replace(R.id.frameLayoutPlaceHolder, fragment,nameClass);
        ft.addToBackStack(nameClass);
        ft.commit();
    }
    public static void replaceAddToBackStackUsingRightEnterAnimation(BaseActivity activity,String nameClass,Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_right_exit,
                R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_left_exit);
        ft.replace(R.id.frameLayoutPlaceHolder,fragment,nameClass);
        ft.addToBackStack(nameClass);
        ft.commit();
    }

    public static void addAddToBackStackUsingRightEnterAnimation(BaseActivity activity,String nameClass,Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_right_exit,
                R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_left_exit);
        ft.add(R.id.frameLayoutPlaceHolder,fragment,nameClass);
        ft.addToBackStack(nameClass);
        ft.commit();
    }

    public static void addToBackStack(BaseActivity activity,String nameClass,Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.frameLayoutPlaceHolder,fragment,nameClass);
        ft.addToBackStack(nameClass);
        ft.commit();
    }


    public static void replaceWithPopAllBackStack(BaseActivity activity, Fragment fragment) {
        activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutPlaceHolder, fragment).commit();
    }

    public static void replaceWithPopAllBackStackWithClassName(BaseActivity activity, Fragment fragment, String name) {
        activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutPlaceHolder, fragment, name).commit();
    }

    public static Fragment returnFragmentInstance(BaseActivity activity, String fragmentTag) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        return fragment;
    }
    public static void removeFragment(BaseActivity activity, String fragmentName){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction trans = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentName);
        trans.remove(fragment);
        trans.commit();
    }

    public static void popBackStack(BaseActivity mActivity){
        FragmentManager fm  = mActivity.getSupportFragmentManager();
        fm.popBackStack();
    }

    public static Fragment returnCurrentFragment(BaseActivity activity){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.frameLayoutPlaceHolder);
        return fragment;
    }
}
