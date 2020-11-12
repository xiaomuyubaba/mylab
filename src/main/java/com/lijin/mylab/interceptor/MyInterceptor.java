package com.lijin.mylab.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lijin.mylab.constants.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP请求拦截器
 */
public class MyInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

    private static final String QRY_PARAM_NM_PREFIX = "_QRY_";

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            this.extractQryParam(req);
        }
        return true;
    }

    /**
     * 将 _QRY_ 开头的请求参数放入 qryParamMap，并存放在请求上下文中，便于Controller使用
     * @param req
     */
    private void extractQryParam(HttpServletRequest req) {
        Map<String, String> qryParamMap = new HashMap<>();
        Map<String, String[]> reqParams = req.getParameterMap();
        if (reqParams != null) {
            reqParams.forEach((nm, values) -> {
                if (nm.startsWith(QRY_PARAM_NM_PREFIX)
                        && values != null
                        && values.length == 1) {
                    qryParamMap.put(nm.substring(QRY_PARAM_NM_PREFIX.length()), values[0].trim());
                }
            });
        }

        if (qryParamMap != null && qryParamMap.isEmpty()) {
            req.setAttribute(Constant.REQ_KEY_QRY_PARAM_MAP, qryParamMap);
            if (logger.isDebugEnabled()) {
                logger.debug("qryParamMap: {}", qryParamMap.toString());
            }
        }
    }
}
