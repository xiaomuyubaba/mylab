
package com.lijin.mylab.freemarker.directive;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Freemarker自定义标签
 * 
 * @author lijin
 */
@Component("hello")
public class HelloDirective implements TemplateDirectiveModel {
    
    @SuppressWarnings("rawtypes")
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
        throws TemplateException, IOException {
        System.out.println("Hello a ~~~");
        env.getOut().append("Hello~~~");
    }

}
