package org.example.booker.tests.base;

import org.aeonbits.owner.ConfigFactory;
import org.example.booker.config.AppConfig;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
    protected static final AppConfig CFG = ConfigFactory.create(AppConfig.class);
}