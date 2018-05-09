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
            .setMessage("1.文字文字我是文字文字文字我是文字文字文字我是文字！\n2.文字文字文字文字文字\n3.文字文字文字文字文字")
            .setTitle("温馨提示")
            .setNegativeAndPositive("否", "是")
            .setPositiveListener(new FRDialogClickListener() {
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