package com.wechat.revertEntity;

/**
 * Created by rui on 2017/5/11.
 * 回复图片消息类
 */
public class RevImage extends BaseEntity {
    private IncludeImage Image;

    public IncludeImage getImage() {
        return Image;
    }

    public void setImage(IncludeImage image) {
        Image = image;
    }
}
