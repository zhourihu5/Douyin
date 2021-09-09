package com.dongnao.douyin;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    class ATest{
        int i;

    }

    class BTest extends  ATest{
        public BTest() {
            i  = 10;
        }
    }
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

    }
}