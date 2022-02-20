package com.iohao.little.game.action.skeleton.core.doc;

import com.iohao.little.game.action.skeleton.annotation.DocActionSend;
import com.iohao.little.game.action.skeleton.annotation.DocActionSends;
import com.iohao.little.game.action.skeleton.core.CmdKit;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jctools.maps.NonBlockingHashMap;

import java.util.*;
import java.util.function.Predicate;

/**
 * @author 洛朱
 * @date 2022-02-01
 */
@Slf4j
public class ActionSendDocs {

    @Getter
    Map<Integer, ActionSendDoc> actionSendDocMap = new NonBlockingHashMap<>();

    private void put(ActionSendDoc actionSendDoc) {
        int cmdMerge = CmdKit.merge(actionSendDoc.getCmd(), actionSendDoc.getSubCmd());
        actionSendDocMap.put(cmdMerge, actionSendDoc);
    }

    public ActionSendDoc getActionSendDoc(int cmd, int subCmd) {
        return this.getActionSendDoc(CmdKit.merge(cmd, subCmd));
    }

    public ActionSendDoc getActionSendDoc(int cmdMerge) {
        ActionSendDoc actionSendDoc = actionSendDocMap.get(cmdMerge);

        if (Objects.nonNull(actionSendDoc)) {
            actionSendDoc.setRead(true);
        }

        return actionSendDoc;
    }

    public void buildActionSendDoc(List<Class<?>> actionSendClassList) {

        Set<Class<?>> classSet = new HashSet<>(actionSendClassList);

        // 条件: 类上配置了 ActionController 注解
        Predicate<Class<?>> predicate = controllerClazz -> Objects.nonNull(controllerClazz.getAnnotation(DocActionSends.class));

        classSet.stream().filter(predicate).forEach(actionSendClass -> {

            DocActionSends annotation = actionSendClass.getAnnotation(DocActionSends.class);

            DocActionSend[] docActionSends = annotation.value();

            for (DocActionSend docActionSend : docActionSends) {

                ActionSendDoc actionSendDoc = new ActionSendDoc(docActionSend);

                this.put(actionSendDoc);
            }

        });

    }
}