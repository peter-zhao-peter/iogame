/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License..
 */
package com.iohao.game.example.one;

import com.iohao.little.game.action.skeleton.core.BarSkeleton;
import com.iohao.little.game.action.skeleton.core.BarSkeletonBuilderParamConfig;
import com.iohao.little.game.action.skeleton.core.flow.interal.DebugInOut;
import com.iohao.little.game.net.client.core.ClientStartupConfig;
import com.iohao.little.game.net.client.core.RemoteAddress;
import com.iohao.little.game.net.message.common.ModuleKeyKit;
import com.iohao.little.game.net.message.common.ModuleMessage;
import lombok.Setter;

/**
 * demo 逻辑服
 *
 * @author 洛朱
 * @date 2022-02-24
 */
@Setter
public class DemoLogicServer implements ClientStartupConfig {
    /** 游戏网关端口 */
    int gatewayPort;

    public DemoLogicServer(int gatewayPort) {
        this.gatewayPort = gatewayPort;
    }

    @Override
    public BarSkeleton createBarSkeleton() {
        // 业务框架构建器 配置
        var config = new BarSkeletonBuilderParamConfig()
                // 扫描 DemoAction.class 所在包
                .addActionController(DemoAction.class);

        // 业务框架构建器
        var builder = config.createBuilder();

        // 添加控制台输出插件
        builder.addInOut(new DebugInOut());

        return builder.build();
    }

    @Override
    public ModuleMessage createModuleMessage() {
        // 逻辑服的模块id，标记不同的逻辑服模块。
        int moduleId = 1;
        var moduleKey = ModuleKeyKit.getModuleKey(moduleId);

        // 子模块 逻辑服的信息描述
        return new ModuleMessage(moduleKey)
                .setName("游戏逻辑服 demo")
                .setDescription("demo业务");
    }

    @Override
    public RemoteAddress createRemoteAddress() {
        // 游戏网关 ip
        String gatewayIp = "127.0.0.1";
        // 游戏网关 ip 和 游戏网关端口
        return new RemoteAddress(gatewayIp, gatewayPort);
    }
}