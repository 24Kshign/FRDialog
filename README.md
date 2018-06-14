# FRDialog
用Builder模式重新打造一个dialog，案例中有两种Builder，分别是CommonBuilder和MDBuilder，如果还想实现其他的通用，继承自FRBaseDialogBuilder即可。

### 1、用法：

#### 1.1、普通Dialog

```
private void showCommonDialog() {
    final FRDialog dialog = new FRDialog.CommonBuilder(this)
            .setContentView(R.layout.dialog_common)
            .setText(R.id.dcu_tv_cancel, "否")
            .setText(R.id.dcu_tv_confirm, "是")
            .setText(R.id.dcu_tv_title, "温馨提示")
            .setText(R.id.dcu_tv_content, "1.文字文字我是文字文字文字我是文字文字文字我是文字！\n2.文字文字文字文字文字\n3.文字文字文字文字文字")
            .setDefaultAnim()
            .show();

    dialog.setText(R.id.dcu_tv_confirm, "确定");

    dialog.setOnClickListener(R.id.dcu_tv_cancel, new FRDialogClickListener() {
        @Override
        public boolean onDialogClick(View view) {
            Toast.makeText(MainActivity.this, "点击了否", Toast.LENGTH_SHORT).show();
            return true;
        }
    });

    dialog.setOnClickListener(R.id.dcu_tv_confirm, new FRDialogClickListener() {
        @Override
        public boolean onDialogClick(View v) {
            Toast.makeText(MainActivity.this, "点击了是", Toast.LENGTH_SHORT).show();
            return false;
        }
    });
}
```

#### 1.2、MaterialDesign Dialog

```
private void showMDDialog() {
    FRDialog dialog = new FRDialog.MDBuilder(this)
                    .setMessage("1.文字文字我是文字文字！\n2.文字文字文字文字文字\n3.文字文字文字文字文字")
                    .setTitle("温馨提示")
                    .setNegativeContentAndListener("否", null)
                    .setNegativeTextColor(ContextCompat.getColor(this,R.color.c999999))
                    .setPositiveTextColor(ContextCompat.getColor(this,R.color.colorPrimary))
                    .setPositiveContentAndListener("是", new FRDialogClickListener() {
                        @Override
                        public boolean onDialogClick(View view) {
                            return true;
                        }
                    }).show();
}
```

### 2、特殊设置：
继承所有dialog的设置，同时还可以自定义以下设置

```
//设置宽度全屏
dialog.setFullWidth()

//设置从底部弹出
dialog.setFromBottom()

//设置弹出动画
dialog.setAnimation(int anim)
```

### 2018.5.24日更新

将mWidth改成mWidthOffset，不让用户设置一个具体的宽度，而是让用户去设置一个宽度比例，然后通过改变window的LayoutParams来设置dialog的宽高：

```
WindowManager.LayoutParams lp = window.getAttributes();
lp.width = (int) (baseBuilder.mContext.getResources().getDisplayMetrics().widthPixels * baseBuilder.mWidthOffset);
lp.height = baseBuilder.mHeight;
window.setAttributes(lp);
```

用法还是和之前一样：

```
dialog.setWidthOffset(0——1)  默认是0.9
```
