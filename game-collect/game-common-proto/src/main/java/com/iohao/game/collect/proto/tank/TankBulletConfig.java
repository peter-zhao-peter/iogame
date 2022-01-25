package com.iohao.game.collect.proto.tank;

import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.iohao.game.collect.proto.GameProtoFile;
import com.iohao.little.game.widget.light.protobuf.ProtoFileMerge;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * 坦克子弹配置
 *
 * @author 洛朱
 * @date 2022-01-15
 */
@Setter
@ProtobufClass
@EnableZigZap
@FieldDefaults(level = AccessLevel.PUBLIC)
@ToString
@ProtoFileMerge(fileName = GameProtoFile.tankFileName, filePackage = GameProtoFile.tankFilePackage)
public class TankBulletConfig {
    /** 子弹 id */
    int bulletId;

    /** 子弹 攻击力 */
    long attackValue;

    /** 子弹 速度 */
    int speed;
}