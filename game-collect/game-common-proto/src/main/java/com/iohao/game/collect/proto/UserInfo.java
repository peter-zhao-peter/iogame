package com.iohao.game.collect.proto;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.ToString;

/**
 * @author 洛朱
 * @date 2022-01-12
 */
@ToString
@ProtobufClass(description = "用户信息")
@EnableZigZap
public class UserInfo {
    /** id */
    @Protobuf(description = "id")
    public long id;

    public String name;
}
