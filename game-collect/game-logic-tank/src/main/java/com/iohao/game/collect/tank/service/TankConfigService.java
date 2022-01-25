package com.iohao.game.collect.tank.service;

import com.iohao.game.collect.proto.tank.TankBulletConfig;
import com.iohao.game.collect.proto.tank.TankBulletConfigRes;
import com.iohao.game.collect.tank.config.BulletId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.*;

/**
 * @author 洛朱
 * @date 2022-01-15
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TankConfigService {

    final Map<Integer, TankBulletConfig> tankBulletConfigMap = new HashMap<>();
    final TankBulletConfigRes tankBulletConfigRes = new TankBulletConfigRes();


    public TankConfigService() {
        // 子弹配置
        initBulletConfig();
    }

    private void initBulletConfig() {
        TankBulletConfig bulletConfig1 = new TankBulletConfig();
        bulletConfig1.bulletId = BulletId.toyBullet;
        bulletConfig1.speed = 1;
        bulletConfig1.attackValue = 1;

        TankBulletConfig bulletConfig2 = new TankBulletConfig();
        bulletConfig2.bulletId = BulletId.snowBullet;
        bulletConfig2.speed = 2;
        bulletConfig2.attackValue = 2;


        List<TankBulletConfig> configList = new ArrayList<>();
        configList.add(bulletConfig1);
        configList.add(bulletConfig2);

        tankBulletConfigRes.tankBulletConfigList = configList;

        for (TankBulletConfig bulletConfig : configList) {
            tankBulletConfigMap.put(bulletConfig.bulletId, bulletConfig);
        }
    }


    public static TankConfigService me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final TankConfigService ME = new TankConfigService();
    }
}