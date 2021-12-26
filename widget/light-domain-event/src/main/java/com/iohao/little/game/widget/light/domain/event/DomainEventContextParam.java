package com.iohao.little.game.widget.light.domain.event;

import com.iohao.little.game.widget.light.domain.event.disruptor.DefaultDisruptorCreate;
import com.iohao.little.game.widget.light.domain.event.disruptor.DisruptorCreate;
import com.iohao.little.game.widget.light.domain.event.message.DomainEventHandler;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author 洛朱
 * @date 2021-12-26
 */
@Getter
@Setter
@Accessors(chain = true)
public class DomainEventContextParam {
    /** 领域事件消费 */
    final Set<DomainEventHandler<?>> domainEventHandlerSet = new LinkedHashSet<>();
    /**
     * 等待策略
     * <pre>
     *
     *     措施                     适用场景                                                                         名称
     *
     *     加锁                     CPU资源紧缺，吞吐量和延迟并不重要的场景                                            {@link BlockingWaitStrategy}
     *     自旋                     通过不断重试，减少切换线程导致的系统调用，而降低延迟。推荐在线程绑定到固定的CPU的场景下使用  {@link BusySpinWaitStrategy}
     *     自旋 + yield + 自定义策略  CPU资源紧缺，吞吐量和延迟并不重要的场景                                            {@link PhasedBackoffWaitStrategy}
     *     自旋 + yield + sleep     性能和CPU资源之间有很好的折中。延迟不均匀                                          {@link SleepingWaitStrategy}
     *     加锁，有超时限制           CPU资源紧缺，吞吐量和延迟并不重要的场景                                            {@link TimeoutBlockingWaitStrategy}
     *     自旋 + yield + 自旋       性能和CPU资源之间有很好的折中。延迟比较均匀                                        {@link YieldingWaitStrategy}
     *
     *
     * </pre>
     */
    WaitStrategy waitStrategy = new BlockingWaitStrategy();
    ProducerType producerType = ProducerType.SINGLE;

    int ringBufferSize = 1024;

    /** 异常处理 */
    ExceptionHandler exceptionHandler;

    /** true 初始化完成 */
    private final AtomicBoolean init = new AtomicBoolean(false);
    /** 创建 disruptor */
    DisruptorCreate disruptorCreate = new DefaultDisruptorCreate();

    /**
     * 添加领域事件消费者，主题默认使用接口实现类的T类型
     *
     * @param domainEventHandler 领域事件消费者
     */
    public DomainEventContextParam addEventHandler(DomainEventHandler<?> domainEventHandler) {
        if (Objects.nonNull(domainEventHandler)) {
            domainEventHandlerSet.add(domainEventHandler);
        }

        return this;
    }

}
