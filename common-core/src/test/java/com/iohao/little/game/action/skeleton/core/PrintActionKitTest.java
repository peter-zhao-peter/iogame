package com.iohao.little.game.action.skeleton.core;

import com.iohao.little.game.action.skeleton.core.data.TestDataKit;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodInOut;
import com.iohao.little.game.action.skeleton.core.flow.interal.ExecuteTimeInOut;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PrintActionKitTest {

    @org.junit.Test
    public void printInout() {
        log.info("hello");
        List<ActionMethodInOut<RequestMessage, ResponseMessage>> inOutList = new ArrayList<>();
        inOutList.add(new ExecuteTimeInOut());
        PrintActionKit.printInout(inOutList);
    }

    @org.junit.Test
    public void printHandler() {
        List<Handler<RequestMessage>> handlerList = new ArrayList<>();
        handlerList.add(new ActionCommandHandler());
        PrintActionKit.printHandler(handlerList);
    }

    @org.junit.Test
    public void log() {
        BarSkeleton barSkeleton = TestDataKit.newBarSkeleton();

        log.info("BarSkeleton: {}", barSkeleton);
    }
}