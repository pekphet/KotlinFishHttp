package cn.fish.kfish;

import org.junit.Test;

import cn.fish.kfish.domain.Data;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_json() {
        System.out.println(new Data.Person("Kotlin", 1).toJson());
    }
}