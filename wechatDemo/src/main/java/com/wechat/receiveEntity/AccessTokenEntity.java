package com.wechat.receiveEntity;

import java.io.Serializable;

/**
 * Created by rui on 2017/5/9.
 * access_token对象
 */
public class AccessTokenEntity implements Serializable {
    //凭证
    private String access_token;
    //有效时间
    private String expires_in;
    //单例
    private static final AccessTokenEntity entity = new AccessTokenEntity();

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public String toString() {
        return "AccessTokenEntity{" +
                "access_token='" + access_token + '\'' +
                ", expires_in='" + expires_in + '\'' +
                '}';
    }

    public static AccessTokenEntity getInstance() {
        return entity;
    }
}
