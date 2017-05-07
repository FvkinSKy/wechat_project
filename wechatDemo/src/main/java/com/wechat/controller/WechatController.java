package com.wechat.controller;

import com.wechat.entity.RecNormalMsg;
import com.wechat.util.WechatUtil;

/**
 * Created by a07 on 2017/5/7.
 * 流程控制类
 */
public class WechatController {
    /**
     *
     * @param recxml
     * @return
     */
    public static String flowControl(String recxml) {
        RecNormalMsg normalMsg = WechatUtil.parseXMLtoEntity(recxml);
        return WechatUtil.buildReturnXML(normalMsg, "测试成功!!!");
    }
}
