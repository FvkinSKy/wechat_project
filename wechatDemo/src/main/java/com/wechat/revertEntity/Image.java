package com.wechat.revertEntity;

/**
 * Created by a07 on 2017/5/11.
 * 回复图片消息分类
 */
public class Image {
    //通过素材管理中的接口上传多媒体文件，得到的id。
    private String MediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
