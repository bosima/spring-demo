package cn.bossma.cn.springdemo.mvc.interceptor.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class PerformanceInterceptor implements HandlerInterceptor {
    private ThreadLocal<StopWatch> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StopWatch watch = new StopWatch();
        threadLocal.set(watch);
        watch.start();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        StopWatch watch = threadLocal.get();
        watch.stop();
        watch.start();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        StopWatch watch = threadLocal.get();
        watch.stop();

        var tasks = watch.getTaskInfo();
        var handleTime = tasks[0].getTimeMillis();
        var renderTime = tasks[1].getTimeMillis();

        log.info("Request:{}, Handle Time:{}ms, Render Time:{}ms", request.getRequestURI(), handleTime, renderTime);
    }
}
