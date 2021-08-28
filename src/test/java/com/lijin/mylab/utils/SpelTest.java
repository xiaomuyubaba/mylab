package com.lijin.mylab.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class SpelTest {

    private ExpressionParser spelParser;

    @Before
    public void prepare() {
        spelParser = new SpelExpressionParser();
    }

    @Test
    public void evaluateBlankTest() {
        try {
            spelParser.parseExpression(null);
            spelParser.parseExpression("");
            Assert.assertTrue(false);
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void parseExceptionTest() {
        try {
            String exp = "'Hello'  'world'";
            spelParser.parseExpression(exp);
            Assert.assertTrue(false);
        } catch (ParseException pe) {
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void evaluationExceptionTest() {
        try {
            String exp = "'Hello' + + 'world'";
            Expression expression = spelParser.parseExpression(exp);
            expression.getValue();
            Assert.assertTrue(false);
        } catch (EvaluationException pe) {
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void evaluateTest() {
        String exp = "'Hello' + ' ' + 'world'";
        Expression expression = spelParser.parseExpression(exp);
        String msg = (String) expression.getValue();
        Assert.assertEquals(msg, "Hello world");
    }
}
