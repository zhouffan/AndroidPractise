package com.fw.custom_view;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        User user = new User("aaaaaa");
        Integer a = 10;
        t(a, user);
        System.out.println(a);
        System.out.println(user);
    }
    private void t(Integer a, User user){
        a = 20;
        user.setName("bbbbbbbbbb");
    }
    class User{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    '}';
        }

        public User(String name) {

            this.name = name;
        }
    }
}