package com.example.yf.creatorshirt.mvp.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.utils.DisplayUtil;

public class CustomUpdateDialog extends Dialog {

    public CustomUpdateDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    /**
     * 封装Builder
     */
    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;


        private OnClickListener positiveButtonClickListener, negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }


        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }


        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }


        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }


        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }


        public Builder setPositiveButton(int positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }


        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }


        public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }


        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * 创建Dialog
         */
        public CustomUpdateDialog create() {
            final CustomUpdateDialog dialog = new CustomUpdateDialog(context, R.style.UpdateDialogStyle);
            dialog.setCanceledOnTouchOutside(false);

            if (contentView != null) {
                LinearLayout linearLayout = contentView.findViewById(R.id.llayout_update);
                DisplayUtil.calculateDialogWidth(context, linearLayout);
                ((TextView) contentView.findViewById(R.id.txt_newVersion)).setText(title);
                // 确定（安装）
                if (positiveButtonText != null) {
                    ((Button) contentView.findViewById(R.id.btn_install)).setText(positiveButtonText);
                    if (positiveButtonClickListener != null) {
                        (contentView.findViewById(R.id.btn_install)).setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                            }
                        });
                    }
                } else {
                    contentView.findViewById(R.id.btn_install).setVisibility(View.GONE);
                }

                // 取消（以后再说）
                if (negativeButtonText != null) {
                    ((Button) contentView.findViewById(R.id.btn_cancel)).setText(negativeButtonText);
                    if (negativeButtonClickListener != null) {
                        (contentView.findViewById(R.id.btn_cancel)).setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                            }
                        });
                    }
                } else {
                    contentView.findViewById(R.id.btn_cancel).setVisibility(View.GONE);
                }

                // 更新信息
                if (message != null) {
                    ((TextView) contentView.findViewById(R.id.txt_updateLog)).setText(message);
                } else if (contentView != null) {

                }
                dialog.setContentView(contentView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            }
            return dialog;
        }
    }


}

