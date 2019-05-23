# FRDialog

用Builder模式重新打造一个dialog，案例中有两种Builder，分别是CommonBuilder和MDBuilder，如果还想实现其他的通用，继承自FRBaseDialogBuilder即可。

[![jcenter](https://api.bintray.com/packages/cyg24kshign/maven/FRDialog/images/download.svg)](https://bintray.com/cyg24kshign/maven/FRDialog/_latestVersion)
[![Api](https://img.shields.io/badge/Api-15%2B-green.svg)](https://img.shields.io/badge/Api-15%2B-green.svg)
[![Author](https://img.shields.io/badge/Author-24Kshign-blue.svg)](https://github.com/24Kshign)

### 注意

从2019.05.23日开始，该库升级为支持androidx，如果你的项目还没有升级支持的话，那么只能使用1.0.6之前的版本。

### 1、添加依赖

Gradle：

```
repositories {
    jcenter()
}

dependencies {
    //lastVersion请参照上述版本号
    compile 'com.jack.frdialog:FRDialog:latestVersion'
}
```

### 2、使用案例

#### 2.1、普通Dialog

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

![普通dialog](http://upload-images.jianshu.io/upload_images/490111-1e3afc3ba53a2b1c.jpg?imageMogr2/auto-orient/strip)

#### 2.2、MaterialDesign Dialog

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

![MD效果的dialog](http://upload-images.jianshu.io/upload_images/490111-04cd7476909d7cb6.jpg?imageMogr2/auto-orient/strip)

#### 2.3、从底部弹出来的Dialog

```
FRDialog dialog = new FRDialog.CommonBuilder(this)
  .setContentView(R.layout.dialog_from_bottom)
  .setFullWidth()
  .setFromBottom()
  .show();
```

![从底部弹出的dialog](http://upload-images.jianshu.io/upload_images/490111-70b3397b12f13aac.jpg?imageMogr2/auto-orient/strip)

### 3、特殊设置：
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

将mWidth改成mWidthRatio，不让用户设置一个具体的宽度，而是让用户去设置一个宽度比例，然后通过改变window的LayoutParams来设置dialog的宽高：

```
WindowManager.LayoutParams lp = window.getAttributes();
lp.width = (int) (baseBuilder.mContext.getResources().getDisplayMetrics().widthPixels * baseBuilder.mWidthRatio);
lp.height = baseBuilder.mHeight;
window.setAttributes(lp);
```

用法还是和之前一样：

```
dialog.setWidthRatio(0——1)  默认是0.9
```

### 2018.7.19日更新
新增点击dialog中非EditText区域隐藏软键盘

```
重写dispatchTouchEvent方法进行拦截：

/**
 * 点击dialog中除EditText以外的区域隐藏软键盘
 *
 * @param ev
 * @return
 */
@Override
public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
    FRInputMethodManager.autoHideSoftInput(this, ev);
    return super.dispatchTouchEvent(ev);
}

//核心方法
public static boolean isAutoHideSoftInput(View view, MotionEvent event) {
    if (event.getAction() != MotionEvent.ACTION_DOWN) {
        return false;
    }

    if (!(view instanceof EditText)) {
        return false;
    }

    float x = event.getX();
    float y = event.getY();

    int[] location = {0, 0};
    view.getLocationInWindow(location);
    int left = location[0];
    int top = location[1];
    int bottom = top + view.getHeight();
    int right = left + view.getWidth();
    if (left <= x && x < right && top <= y && y < bottom) {
        // 点击事件在EditText的区域里
        return false;
    }

    return true;
}
```
用法不变。

### 2018.9.11日更新

新增recyclerview样式的dialogBuilder

![](http://ooaap25kv.bkt.clouddn.com/18-9-11/67216384.jpg)

具体功能有：

- 设置一个列表布局，只需要传一个adapter就好了

- 可以给列表添加头部和底部

- 可以给dialog添加头部和底部

用法：

```
    private void showRecyclerViewDialog() {
        List<Object> mDataList = new ArrayList<>();
        mDataList.add(new TestDataBean("张三", "2018-09-11 14:00"));
        mDataList.add(new TestDataBean("李四", "2018-09-11 11:00"));
        mDataList.add(new TestDataBean("王五", "2018-09-11 12:00"));
        mDataList.add(new TestDataBean("李四", "2018-09-11 13:00"));
        mDataList.add(new TestDataBean("张三", "2018-09-11 16:00"));
        mDataList.add(new TestDataBean("王五", "2018-09-11 15:00"));


        final FRDialog dialog = new FRDialog.RecyclerViewBuilder(this)
                .setLayoutManager(new LinearLayoutManager(MainActivity.this))
                .setAdapter(new FRBaseDialogAdapter<TestDataBean>(MainActivity.this) {

                    @Override
                    protected int getLayoutRes() {
                        return R.layout.item_test;
                    }

                    @Override
                    protected void convert(FRBaseDialogViewHolder holder, TestDataBean dataBean, int position, List<Object> payloads) {
                        holder.setImageResource(R.id.it_iv_image, R.mipmap.ic_launcher_round);
                        holder.setText(R.id.it_tv_title, dataBean.getName());
                        holder.setText(R.id.it_tv_time, dataBean.getTime());
                    }
                }).setDataList(mDataList)
                .setHeightRatio(0.5)
                .addRecyclerViewHeader(R.layout.layout_header)
                .addDialogFooter(R.layout.layout_footer)
                .setOnClickListener(R.id.lf_tv_cancel, view -> true)
                .setOnClickListener(R.id.lf_tv_confirm, view -> {
                    Toast.makeText(MainActivity.this, "点击了确定", Toast.LENGTH_SHORT).show();
                    return false;
                })
                .show();
    }
```

详细可以参照demo中的。

### 2018.11.14日更新

新增适配在Service中国弹出dialog

![](https://ws1.sinaimg.cn/large/005MjwGuly1fx7fox1rq0g309j0hhto8.jpg)

首先Android5.x以上的版本都需要适配，8.x版本的需要单独设置 window 的 type 类型，8.x以下版本，还需要申请悬浮窗的权限，因为我们是将dialog作为一个悬浮窗来弹出来

```
dialog中：

if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    Objects.requireNonNull(window).setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY - 1);
} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
    Objects.requireNonNull(window).setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
}
```

```
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
    openOverlaySettings();
} else {
    startService(new Intent(this, DialogService.class));
}

private void openOverlaySettings() {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        try {
            //打开权限申请页面
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, RC_OVERLAY);
        } catch (ActivityNotFoundException e) {
            Log.e("TAG", "ActivityNotFoundException--->" + e.getMessage());
        }
    }
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
        case RC_OVERLAY:
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                // 判断当前有没有授权成功
                if (Settings.canDrawOverlays(this)) {
                    startService(new Intent(this, DialogService.class));
                }
            }
		break;
    }
}

@Override
protected void onDestroy() {
    super.onDestroy();
    stopService(new Intent(this, DialogService.class));
}

```

### 2018.12.29日更新

新增dialog的偏移量，可以自定义dialog的显示位置

![](https://ws1.sinaimg.cn/large/005MjwGuly1fynq0zh5x9g309i0hiq4b.jpg)

```
FRDialog dialog = new FRDialog.CommonBuilder(this, R.style.transparent_dialog)
                .setContentView(R.layout.dialog_offsetx)
                .setGravity(Gravity.LEFT | Gravity.TOP)
                .setOnClickListener(R.id.do_tv_praised, view -> {
                    Toast.makeText(DialogOffsetActivity.this, "赞", Toast.LENGTH_SHORT).show();
                    return true;
                })
                .setOnClickListener(R.id.do_tv_comment, view -> {
                    Toast.makeText(DialogOffsetActivity.this, "评论", Toast.LENGTH_SHORT).show();
                    return true;
                })
                .setOffsetX(dp2px(100))
                .setOffsetY(dp2px(51))
                .show();
```

### 2019.05.23日更新

此次更新主要是升级到androidx，然后将内部使用的三方库（support包和recyclerview包）设置成implemention，外部不可访问，用法不变

### 公众号

欢迎关注我的个人公众号【IT先森养成记】，专注大前端技术分享，包含Android，Java基础，Kotlin，HTML，CSS，JS等技术；在这里你能得到的不止是技术上的提升，还有一些学习经验以及志同道合的朋友，赶快加入我们，一起学习，一起进化吧！！！

![公众号：IT先森养成记](http://upload-images.jianshu.io/upload_images/490111-cfc591d001bf4cc6.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
