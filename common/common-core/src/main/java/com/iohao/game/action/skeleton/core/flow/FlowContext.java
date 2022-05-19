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
package com.iohao.game.action.skeleton.core.flow;

import com.iohao.game.action.skeleton.core.*;
import com.iohao.game.action.skeleton.core.commumication.BrokerClientContext;
import com.iohao.game.action.skeleton.core.flow.attr.FlowAttr;
import com.iohao.game.action.skeleton.core.flow.attr.FlowOption;
import com.iohao.game.action.skeleton.core.flow.attr.FlowOptionDynamic;
import com.iohao.game.action.skeleton.core.flow.codec.DataCodec;
import com.iohao.game.action.skeleton.protocol.HeadMetadata;
import com.iohao.game.action.skeleton.protocol.RequestMessage;
import com.iohao.game.action.skeleton.protocol.ResponseMessage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.jctools.maps.NonBlockingHashMap;

import java.util.Map;
import java.util.Objects;

/**
 * 业务框架 flow 上下文
 * <pre>
 *     生命周期存在于这一次的 flow 过程
 *
 *     实现了类型明确的动态属性接口 （FlowOptionDynamic），实现类只需要实现 getOptions 方法就能具有动态属性的功能。
 *     动态属性可以更方便的为 FlowContext 实现属性的扩展，以方便开发者。
 * </pre>
 *
 * @author 渔民小镇
 * @date 2021-12-21
 */
@Setter
@Getter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlowContext implements FlowOptionDynamic {
    /** 动态属性 */
    final Map<FlowOption<?>, Object> options = new NonBlockingHashMap<>();

    /** 业务框架 */
    BarSkeleton barSkeleton;
    /** command */
    ActionCommand actionCommand;
    /** 控制器类对象 */
    Object actionController;
    /** 请求对象 */
    RequestMessage request;
    /** 响应对象 */
    ResponseMessage response;
    /** 业务方法参数 */
    Object[] methodParams;
    /** 业务方法的返回值 */
    Object methodResult;
    /** userId */
    long userId;
    /** true 业务方法有异常 */
    boolean error;

    /**
     * cmdInfo
     *
     * @param cmd    主 cmd
     * @param subCmd 子 cmd
     * @return cmdInfo
     */
    public CmdInfo getCmdInfo(int cmd, int subCmd) {
        return CmdInfoFlyweightFactory.me().getCmdInfo(cmd, subCmd);
    }

    /**
     * 根据路由信息来请求其他子服务器（其他逻辑服）的数据
     *
     * @param cmdInfo 路由信息
     * @param data    请求参数
     * @param clazz   pb class
     * @param <T>     t
     * @return pb 对象
     */
    public <T> T invokeModuleMessageData(CmdInfo cmdInfo, Object data, Class<T> clazz) {
        // 其他逻辑服给回的响应
        ResponseMessage responseMessage = this.invokeModuleMessage(cmdInfo, data);

        DataCodec dataCodec = this.option(FlowAttr.dataCodec);
        // 将字节解析成对象
        byte[] dataContent = responseMessage.getData();
        return dataCodec.decode(dataContent, clazz);
    }

    /**
     * 根据路由信息来请求其他子服务器（其他逻辑服）的数据
     *
     * @param cmdInfo 路由信息
     * @param clazz   pb class
     * @param <T>     t
     * @return pb 对象
     */
    public <T> T invokeModuleMessageData(CmdInfo cmdInfo, Class<T> clazz) {
        return this.invokeModuleMessageData(cmdInfo, null, clazz);
    }

    /**
     * 根据路由信息来请求其他子服务器（其他逻辑服）的数据
     *
     * @param cmdInfo cmdInfo
     * @param data    请求参数
     * @return ResponseMessage
     */
    public ResponseMessage invokeModuleMessage(CmdInfo cmdInfo, Object data) {

        HeadMetadata headMetadata = new HeadMetadata()
                .setUserId(this.getUserId())
                .setCmdInfo(cmdInfo);

        RequestMessage requestMessage = new RequestMessage();
        requestMessage
                .setHeadMetadata(headMetadata)
        ;

        if (Objects.nonNull(data)) {
            requestMessage.setData(data);
        }

        // 当前项目启动的服务上下文
        BrokerClientContext brokerClientContext = this.option(FlowAttr.brokerClientContext);
        return brokerClientContext.invokeModuleMessage(requestMessage);
    }

    private <T> T invokeModuleMessageData(CmdInfoRoute cmdInfoRoute, Object data, Class<T> clazz) {
        return this.invokeModuleMessageData(cmdInfoRoute.getCmdInfo(), data, clazz);
    }
}