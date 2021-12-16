package com.iohao.little.game.action.skeleton.core;

import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import lombok.Setter;

/**
 * 该handler用于执行 {@link ActionCommand} 对象
 * <BR>
 *
 * @author 洛朱
 * @date 2021/12/12
 */
public class ActionCommandHandler implements Handler<RequestMessage> {

    @Setter
    BarSkeleton barSkeleton;

    @Override
    public boolean handler(ParamContext paramContext, RequestMessage request, BarSkeleton barSkeleton) {
        /*
        这里不做任何null判断了. 使用者们自行注意
        根据客户端的请求信息,获取对应的命令对象来处理这个请求
         */
        var actionCommandManager = barSkeleton.actionCommandManager;
        var cmd = request.getCmd();
        var subCmd = request.getSubCmd();
        var actionCommand = actionCommandManager.getActionCommand(cmd, subCmd);

        // 命令流程执行器
        var actionCommandFlowExecute = barSkeleton.getActionCommandFlowExecute();
        actionCommandFlowExecute.execute(paramContext, actionCommand, request, barSkeleton);

        return true;
    }
}