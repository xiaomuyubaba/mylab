package com.lijin.mylab.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(args =  "--app.test=one")
public class ApplicationArgumentsTest {

    @Autowired
    private ApplicationArguments args;

    @Autowired
    private MockEnvironment env;

    @Test
    public void applicationArgumentsPopulated() {
        System.out.println(args.getOptionNames());
        System.out.println(env.getPropertySources());
    }

}
