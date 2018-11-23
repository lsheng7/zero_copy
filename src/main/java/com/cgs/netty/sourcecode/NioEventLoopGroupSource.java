package com.cgs.netty.sourcecode;

import io.netty.channel.ChannelPromise;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorChooserFactory;
import io.netty.util.concurrent.MultithreadEventExecutorGroup;
import io.netty.util.concurrent.ThreadPerTaskExecutor;
import io.netty.util.internal.SystemPropertyUtil;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.function.Function;

/**
 * @Description TODO
 * @Author Harry
 * @Date 2018/11/21 21:41
 **/
public class NioEventLoopGroupSource {

  /**
   *
   * 是一种特殊的事件循环组 这个特定的事件循环组允许注册通道(可以理解成为一个连接)
   * 这个通道用于处理在事件循环期间的后续的selection的处理
   *
   * Special {@link EventExecutorGroup} which allows registering {@link Channel}s that get
   * processed for later selection during the event loop.
   */

  /** 返回一个事件循环
   * Return the next {@link EventLoop} to use
   */
//  @Override
//  EventLoop next();

  /**
   * 将一个通道注册到事件循环中
   * 注册完成后会返回一个ChannelFuture 用于接收通知
   * 分析得出这个方法是一个异步的操作
   * 可以通过ChannelFure来检查注册是否完成
   * 声明如下:
   * public interface ChannelFuture extends Future<Void>
   * Register a {@link Channel} with this {@link EventLoop}. The returned {@link ChannelFuture}
   * will get notified once the registration was complete.
   */
//  ChannelFuture register(Channel channel);

  /**
   * 使用一个ChannelFuture(ChannelPromise是ChannelFuture的一个子接口)将一个Channel注册到EventLoop
   *
   * @Override
   *     Channel channel();
   * ChannelPromise包含一个channel对象的引用
   * Register a {@link Channel} with this {@link EventLoop} using a {@link ChannelFuture}. The passed
   * {@link ChannelFuture} will get notified once the registration was complete and also will get returned.
   */
//  ChannelFuture register(ChannelPromise promise);

  /**
   * Register a {@link Channel} with this {@link EventLoop}. The passed {@link ChannelFuture}
   * will get notified once the registration was complete and also will get returned.
   *
   * @deprecated Use {@link #register(ChannelPromise)} instead.
   */
//  @Deprecated
//  ChannelFuture register(Channel channel, ChannelPromise promise);

  /**
   * EventExecutorGroup的职责是可以通过next()方法提供EventExecutor事件执行器
   *
   * 除此之外,它也会用于处理他们的生命周期 允许以一种全局的方式关闭它们
   *
   * The {@link EventExecutorGroup} is responsible for providing the {@link EventExecutor}'s to use
   * via its {@link #next()} method. Besides this, it is also responsible for handling their
   * life-cycle and allows shutting them down in a global fashion.
   *
   */
//  public interface EventExecutorGroup extends ScheduledExecutorService, Iterable<EventExecutor>

  /**
   * Returns one of the {@link EventExecutor}s managed by this {@link EventExecutorGroup}.
   */
//  EventExecutor next();

  /**
   * NioEventLoopGroup是基于NIO的
   * {@link MultithreadEventLoopGroup} implementations which is used for NIO {@link Selector} based {@link Channel}s.
   */
//  public class NioEventLoopGroup extends MultithreadEventLoopGroup

  /**
   * 使用默认的线程数、默认的线程工及SelectProvider(这个SelectorProvider可以通过provider方法创建)
   * 来创建一个实例
   *
   * Create a new instance using the default number of threads, the default {@link ThreadFactory} and
   * the {@link SelectorProvider} which is returned by {@link SelectorProvider#provider()}.
   */

//  public NioEventLoopGroup() {
//    this(0);
//  }
//上面的方法会调用含参的构造函数->

  /**
   * Create a new instance using the specified number of threads, {@link ThreadFactory} and the
   * {@link SelectorProvider} which is returned by {@link SelectorProvider#provider()}.
   */
//  public NioEventLoopGroup(int nThreads) {
//    this(nThreads, (Executor) null);
//  }
//继续调用->
//  public NioEventLoopGroup(int nThreads, Executor executor) {
//    this(nThreads, executor, SelectorProvider.provider());
//  }
//继续调用->
//  public NioEventLoopGroup(
//      int nThreads, Executor executor, final SelectorProvider selectorProvider) {
//    this(nThreads, executor, selectorProvider, DefaultSelectStrategyFactory.INSTANCE);
//  }
//继续调用->
//  public NioEventLoopGroup(int nThreads, Executor executor, final SelectorProvider selectorProvider,
//      final SelectStrategyFactory selectStrategyFactory) {
//    super(nThreads, executor, selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject());
//  }
//继续调用->
  /**
   * @see MultithreadEventExecutorGroup#MultithreadEventExecutorGroup(int, Executor, Object...)
   */
//  protected MultithreadEventLoopGroup(int nThreads, Executor executor, Object... args) {
//    super(nThreads == 0 ? DEFAULT_EVENT_LOOP_THREADS : nThreads, executor, args);
//  }
//继续调用->
  /**
   * Create a new instance.
   *
   * @param nThreads          the number of threads that will be used by this instance.
   * @param executor          the Executor to use, or {@code null} if the default should be used.
   * @param args              arguments which will passed to each {@link #newChild(Executor, Object...)} call
   */
//  protected MultithreadEventExecutorGroup(int nThreads, Executor executor, Object... args) {
//    this(nThreads, executor, DefaultEventExecutorChooserFactory.INSTANCE, args);
//  }
//继续调用->

  /**
   * Create a new instance.
   *
   * 使用默认的线程数
   *
   *  cstatic代码块在类的初始化之前被执行
   *
   *  private static final int DEFAULT_EVENT_LOOP_THREADS;
   *
   *     static {
   *         DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt(
   *                 "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
   *
   *         if (logger.isDebugEnabled()) {
   *             logger.debug("-Dio.netty.eventLoopThreads: {}", DEFAULT_EVENT_LOOP_THREADS);
   *         }
   *     }
   //   * @param nThreads          the number of threads that will be used by this instance.
   //   * @param executor          the Executor to use, or {@code null} if the default should be used.
   //   * @param chooserFactory    the {@link EventExecutorChooserFactory} to use.
   //   * @param args              arguments which will passed to each {@link #newChild(Executor, Object...)} call
   */
//  protected MultithreadEventExecutorGroup(int nThreads, Executor executor,
//      EventExecutorChooserFactory chooserFactory, Object... args) {
//    if (nThreads <= 0) {
//      throw new IllegalArgumentException(String.format("nThreads: %d (expected: > 0)", nThreads));
//    }
//
//  传统的线程创建是将线程的创建和线程的执行业务代码绑定到一起了
//  是一种紧密的耦合关系
//  通过使用线程工厂的方式可以将线程的创建和业务的代码执行区分开
//  降低了线程和业务代码之间的耦合度 高内聚低耦合是软件开发中提倡的编程模式
//
//    if (executor == null) {
//      executor = new ThreadPerTaskExecutor(newDefaultThreadFactory());
//    }
//
//    children = new EventExecutor[nThreads];
//
//    for (int i = 0; i < nThreads; i ++) {
//      boolean success = false;
//      try {

  /**
   * 创建一个EventExecutor这个事件执行器在后面的过程中 会通过next方法获取到 这个方法会被服务于MultithreadEventExecutorGroup 的每一个线程调用
   * Create a new EventExecutor which will later then accessible via the {@link //#next()}  method.
   * This method will be called for each thread that will serve this {@link
   * MultithreadEventExecutorGroup}.
   */
//  protected abstract EventExecutor newChild(Executor executor, Object... args) throws Exception;

//        children[i] = newChild(executor, args);
//        success = true;
//      } catch (Exception e) {
//        // TODO: Think about if this is a good exception type
//        throw new IllegalStateException("failed to create a child event loop", e);
//      } finally {
//        if (!success) {
//          for (int j = 0; j < i; j ++) {
//            children[j].shutdownGracefully();
//          }
//
//          for (int j = 0; j < i; j ++) {
//            EventExecutor e = children[j];
//            try {
//              while (!e.isTerminated()) {
//                e.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
//              }
//            } catch (InterruptedException interrupted) {
//              // Let the caller handle the interruption.
//              Thread.currentThread().interrupt();
//              break;
//            }
//          }
//        }
//      }
//    }
//
//    chooser = chooserFactory.newChooser(children);
//
//    final FutureListener<Object> terminationListener = new FutureListener<Object>() {
//      @Override
//      public void operationComplete(Future<Object> future) throws Exception {
//        if (terminatedChildren.incrementAndGet() == children.length) {
//          terminationFuture.setSuccess(null);
//        }
//      }
//    };
//
//    for (EventExecutor e: children) {
//      e.terminationFuture().addListener(terminationListener);
//    }
//
//    Set<EventExecutor> childrenSet = new LinkedHashSet<EventExecutor>(children.length);
//    Collections.addAll(childrenSet, children);
//    readonlyChildren = Collections.unmodifiableSet(childrenSet);
//  }

  //8
  public static void main(String[] args) {
/**
 *
 *
 * Returns the value of the Java system property with the specified
 * {@code key}, while falling back to the specified default value if
 * the property access fails.
 *
 * @return the property value.
 *         {@code def} if there's no such property or if an access to the
 *         specified property is not allowed.
 */
//    public static int getInt(String key, int def) {
//      String value = get(key);
//      if (value == null) {
//        return def;
//      }
//
//      value = value.trim();
//      try {
//        return Integer.parseInt(value);
//      } catch (Exception e) {
//        // Ignore
//      }
//
//      logger.warn(
//          "Unable to parse the integer system property '{}':{} - using the default value: {}",
//          key, value, def
//      );
//
//      return def;
//    }
    //取得最大值
    //尝试获取系统属性
    //获取不到使用系统的核心数*2
    /**
     *  /**
     *          * Get the configured number of available processors. The default is {@link Runtime#availableProcessors()}.
     *          * This can be overridden by setting the system property "io.netty.availableProcessors" or by invoking
     *          * {@link #setAvailableProcessors(int)} before any calls to this method.
     *          *
     *          * @return the configured number of available processors
     *          */
//     *@SuppressForbidden(reason = "to obtain default number of available processors")
//     *synchronized int availableProcessors () {
//     *if (this.availableProcessors == 0) {
//     *final int availableProcessors =
//     *SystemPropertyUtil.getInt(
//            * "io.netty.availableProcessors",
//     *Runtime.getRuntime().availableProcessors());
//     *setAvailableProcessors(availableProcessors);
//     *}
//     *return this.availableProcessors;
//     *}
//     */

    //分析后发现主要完成的变量赋值操作

    /**
     * 事件执行器是一个特殊的EventExecutorGroup事件执行器组
     * 用于提供一些便捷的方法用于查看线程在事件循环中被执行
     * 除此之外 它也继承了EventExecutorGroup允许以一个通用的方式去访问方法
     * The {@link EventExecutor} is a special {@link EventExecutorGroup} which comes
     * with some handy methods to see if a {@link Thread} is executed in a event loop.
     * Besides this, it also extends the {@link EventExecutorGroup} to allow for a generic
     * way to access methods.
     *
     */
//    public interface EventExecutor extends EventExecutorGroup
//    package io.netty.util.internal;Netty内部使用的不建议被使用

//    /**
//     * An object that creates new threads on demand.  Using thread factories
//     * removes hardwiring of calls to {@link Thread#Thread(Runnable) new Thread},
//     * enabling applications to use special thread subclasses, priorities, etc.
//     *
//     * <p>
//     * The simplest implementation of this interface is just:
//     * <pre> {@code
//     * class SimpleThreadFactory implements ThreadFactory {
//     *   public Thread newThread(Runnable r) {
//     *     return new Thread(r);
//     *   }
//     * }}</pre>
//     *
//     * 在返回之前设置线程的上下文信息
//     * The {@link Executors#defaultThreadFactory} method provides a more
//     * useful simple implementation, that sets the created thread context
//     * to known values before returning it.
//     * @since 1.5
//     * @author Doug Lea
//     */
//    public interface ThreadFactory {
//
//      /**
//       * Constructs a new {@code Thread}.  Implementations may also initialize
//       * priority, name, daemon status, {@code ThreadGroup}, etc.
//       *
//       * @param r a runnable to be executed by new thread instance
//       * @return constructed thread, or {@code null} if the request to
//       *         create a thread is rejected
//       */
//      Thread newThread(Runnable r);
//    }

    int threads = Math.max(1, SystemPropertyUtil.getInt(
        "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));

    int runtime = Runtime.getRuntime().availableProcessors() * 2;

    System.out.println(runtime);
    System.out.println(threads);

    //线程工厂的使用
    //讨论
//    ThreadFactory factory = runnable -> {
//      runnable.run();//线程执行的代码逻辑
//      return new Thread();//返回一个线程
//    };
//
//    factory.newThread(() -> {
//      System.out.println("线程执行");
//    });

    //上面饿这种操作在Executor中
    //已经说明了是同步大在calller调用者线程执行的业务逻辑

//    ThreadFactory factory=runnable->new Thread(runnable);
//  factory.newThread(()->{
//
//  }).start();




    Function<String, String> function = (i1) -> i1 + "hello";
    String result = function.apply("1");
    System.out.println(result);
    ThreadPerTaskExecutor executor = new ThreadPerTaskExecutor(
        new DefaultThreadFactory(NioEventLoopGroupSource.class));

    /**
     * 测试下来好像并没有多线程
     */
    try {
      Thread.sleep(1000);//让主线程停留10ms
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    executor.execute(() -> {
      System.out.println("线程执行的业务代码");
    });

    for (int i = 0; i < 100; i++) {
      System.out.println(i);
    }
  }
}


