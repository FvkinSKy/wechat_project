package com.wechat.revertEntity;

/**
 * Created by rui on 2017/5/11.
 * 回复图片消息类
 */
public class RevImage extends BaseEntity {
    private Image Image;

    public com.wechat.revertEntity.Image getImage() {
        return Image;
    }

    public void setImage(com.wechat.revertEntity.Image image) {
        Image = image;
    }
}
