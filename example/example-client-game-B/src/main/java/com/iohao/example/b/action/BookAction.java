package com.iohao.example.b.action;

import cn.hutool.core.util.RandomUtil;
import com.iohao.example.common.ActionCont;
import com.iohao.little.game.action.skeleton.annotation.ActionController;
import com.iohao.little.game.action.skeleton.annotation.ActionMethod;
import com.iohao.little.game.action.skeleton.core.CmdInfo;
import com.iohao.little.game.net.common.BoltClientProxy;
import com.iohao.little.game.net.common.BoltClientProxyManager;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@ActionController(ActionCont.BookModule.CMD)
public class BookAction {

    @ActionMethod(ActionCont.BookModule.NAME)
    public com.iohao.example.common.Book name(com.iohao.example.common.Book book) {
        log.debug("book: {}", book);
        // 响应给客户端的数据 string 类型. 框架可根据返回参数类型将返回结果装到响应体中

        book.setName("client book: " + book.getName());
        book.setAge(RandomUtil.randomInt(100));

        return book;
    }

    @ActionMethod(ActionCont.BookModule.GET_APPLE_AGE)
    public Integer getAppleAge(int age, CmdInfo cmdInfo) {
        BoltClientProxy boltClientProxy = BoltClientProxyManager.me().getBoltClientProxy(cmdInfo);
        // 访问其它子服务器 获得数据
        log.info("data 1: {}", age);
        CmdInfo cmdInfo1 = ActionCont.AppleModule.info.cmdInfo(ActionCont.AppleModule.AGE);
        var data = boltClientProxy.invokeModuleMessageData(cmdInfo1, age);
        log.info("data 2: {}", data);

        return (Integer) data;
    }
}