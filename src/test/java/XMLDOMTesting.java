/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.imos.common.utils.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 *
 * @author p
 */
public class XMLDOMTesting {

    List<Thread> threads = new ArrayList<>();

    @Test
    public void test() throws InterruptedException {
        XMLDOMUtils inst1 = new XMLDOMUtils();
        addThread1(threads, inst1);
        addThread2(threads, inst1);

        XMLDOMUtils inst2 = new XMLDOMUtils();
        addThread1(threads, inst2);
        addThread2(threads, inst2);

        XMLDOMUtils inst3 = new XMLDOMUtils();
        addThread1(threads, inst3);
        addThread2(threads, inst3);

        XMLDOMUtils inst4 = new XMLDOMUtils();
        addThread1(threads, inst4);
        addThread2(threads, inst4);

        XMLDOMUtils inst5 = new XMLDOMUtils();
        addThread1(threads, inst5);
        addThread2(threads, inst5);

        for (Thread thread : threads) {
            thread.join();
        }

    }

    private static void addThread1(List<Thread> threadList, XMLDOMUtils xmlDom) {
        Thread thread = new Thread(() -> {
            xmlDom.readDocument(XMLDOMTesting.class.getClassLoader().getResourceAsStream("default1.xml"), d -> "");
        });
        thread.start();
        threadList.add(thread);
    }

    private static void addThread2(List<Thread> threadList, XMLDOMUtils xmlDom) {
        Thread thread = new Thread(() -> {
            xmlDom.readDocument(XMLDOMTesting.class.getClassLoader().getResourceAsStream("default2.xml"), d -> "");
        });
        thread.start();
        threadList.add(thread);
    }
}
