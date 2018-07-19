package cn.jake.share.frdialog.image;

import android.widget.ImageView;

/**
 * Created by jack
 * Date：2018/7/19 下午4:03
 * Desc：
 */
public abstract class CommonImageLoader {

    private String mImagePath;

    public CommonImageLoader(String imagePath) {
        mImagePath = imagePath;
    }

    public abstract void loadImageView(ImageView imageView, String imagePath);

    public String getImagePath() {
        return mImagePath;
    }
}
