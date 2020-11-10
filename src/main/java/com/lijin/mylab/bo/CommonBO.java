package com.lijin.mylab.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lijin.mylab.exception.BizzException;
import com.lijin.mylab.utils.AssertUtil;
import org.springframework.stereotype.Service;

import com.lijin.mylab.entity.AjaxResult;
import com.lijin.mylab.entity.IEntityTransfer;
import com.lijin.mylab.enums.AjaxRespEnums;
import com.lijin.mylab.utils.BeanUtil;
import com.lijin.mylab.utils.StringUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class CommonBO {

    public <T> List<Map<String, String>> transferLst(List<T> lst, IEntityTransfer transfer) {

        List<Map<String, String>> resultLst = new ArrayList<Map<String, String>>();
        if (lst != null) {
            for (int i = 0; i < lst.size(); i++) {
                T o = lst.get(i);
                Map<String, String> m = BeanUtil.desc(o, null, null);
                if (transfer != null) {
                    transfer.transfer(m);
                }
                resultLst.add(m);
            }
        }
        return resultLst;
    }

    public <T> Map<String, String> transferEntity(T entity, IEntityTransfer transfer) {
        Map<String, String> m = BeanUtil.desc(entity, null, null);
        if (transfer != null) {
            transfer.transfer(m);
        }
        return m;
    }

    /**
     * 从请求上下文中获取属性
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getRequestAttribute(String key) {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        Object rslt = null;
        if (attributes != null) {
            rslt = attributes.getAttribute(key, RequestAttributes.SCOPE_REQUEST);
        }
        return rslt == null ? null : (T) rslt;
    }
}
