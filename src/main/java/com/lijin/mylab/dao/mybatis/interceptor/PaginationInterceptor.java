package com.lijin.mylab.dao.mybatis.interceptor;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;

@Intercepts({@Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})})
public class PaginationInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(PaginationInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        logger.info("args: {}", Arrays.toString(args));
        MappedStatement statement = (MappedStatement) args[0];
        if (statement.getId().endsWith("selectByExample")) {
            Object parameter = args[1];
            if (parameter != null) {
                Executor executor = (Executor) invocation.getTarget();
                RowBounds rowBounds = (RowBounds) args[2];
                ResultHandler resultHandler = (ResultHandler) args[3];
                CacheKey cacheKey;
                BoundSql boundSql;
                if (args.length == 4) {
                    boundSql = statement.getBoundSql(parameter);
                    cacheKey = executor.createCacheKey(statement, parameter, rowBounds, boundSql);
                } else {

                }
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        // TODO...
    }
}
