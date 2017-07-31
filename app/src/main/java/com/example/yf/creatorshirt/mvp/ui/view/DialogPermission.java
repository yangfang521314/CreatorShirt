package com.example.yf.creatorshirt.mvp.ui.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by yang on 28/07/2017.
 */

public class DialogPermission {
    Context mContext;
    String mNotice;
    public DialogPermission(Context context, String notice){
        mContext=context;
        mNotice=notice;
        showDialog();
    }

    private void showDialog() {
        new AlertDialog.Builder(mContext).setTitle("系统提示")//设置对话框标题

                .setMessage(mNotice)//设置显示的内容

                .setPositiveButton("设置",new DialogInterface.OnClickListener() {//添加确定按钮

                    @Override

                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
//                        SharedPreferencesMark.setHasShowLocation(true);
//
//                        // 跳转至apk权限设置界面
//                        Intent intent = new Intent();
//                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
//                        intent.setData(uri);
//                        mContext.startActivity(intent);
//                    }
//
//                }).setNegativeButton("放弃",new DialogInterface.OnClickListener() {//添加返回按钮
//            @Override
//            public void onClick(DialogInterface dialog, int which) {//响应事件
//                SharedPreferencesMark.setHasShowLocation(true);
                // TODO Auto-generated method stub
            }
        }).show();//在按键响应事件中显示此对话框

    }
}
