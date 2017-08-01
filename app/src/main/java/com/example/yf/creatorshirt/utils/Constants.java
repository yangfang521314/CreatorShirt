package com.example.yf.creatorshirt.utils;

import com.example.yf.creatorshirt.R;

/**
 * Created by yang on 2017/6/18.
 */

public class Constants {

    //总的样式列表
    public final static int[] styleImage = {R.mipmap.icon_edit_neck, R.mipmap.icon_select_longarm, R.mipmap.icon_edit_color, R.mipmap.icon_edit_color, R.mipmap.icon_edit_pattern, R.mipmap.icon_edit_text};
    public final static String[] styleTitle = {"衣领", "衣袖", "颜色", "配饰", "图案", "标签"};
    public final static int[] neck = {R.mipmap.icon_select_circle, R.mipmap.icon_select_v, R.mipmap.icon_select_t};
    public final static String[] select_neck_title = {"圆领", "V领", "衬衫领"};
    public final static int[] longarm = {R.mipmap.icon_select_noarm, R.mipmap.icon_select_shortarm, R.mipmap.icon_select_longarm};
    public final static String[] select_neck_arm = {"无袖", "短袖", "长袖"};

    public final static int[] signature = {R.mipmap.icon_select_blank, R.mipmap.icon_select_text};
    public final static String[] select_edit = {"空白", "文字"};
    //袖子
    public final static int[] shirt_container = {R.mipmap.man_vest, R.mipmap.man_t_shirt, R.mipmap.man_long};
    //领子
    public final static int[] shirt_neck = {R.mipmap.icon_circle_neck, R.mipmap.icon_tshirt_neck, R.mipmap.icon_tshirt_neck};
    //颜色
    public final static int[] clothes_color = {R.drawable.shape_black, R.drawable.shape_blue, R.drawable.shape_white};
    public final static String[] color_name = {"黑色", "蓝色", "白色"};

    public static int[] choice_color = {R.color.onyx, R.color.glitter, R.color.white};

    //配饰
    public final static int[] select_ornament = {R.mipmap.icon_select_blank, R.mipmap.icon_select_blank};
    public final static String[] ornament_name = {"空白", "拉链"};

    //图案
    public final static int[] clothes_pattern = {R.mipmap.icon_select_circle, R.mipmap.icon_select_v, R.mipmap.icon_select_t};
    public final static String[] pattern_title = {"创意手绘", "创意手绘", "创意手绘"};
    //签名
    public final static int[] clothes_signature = {R.mipmap.icon_select_blank, R.mipmap.icon_select_text};
    public final static String[] signature_name = {"空白", "文字"};


    //透明度
    public static final float NORMAL_ALPHA = 1.0f;
    public static final float CHANGE_ALPHA = 0.4f;
}
