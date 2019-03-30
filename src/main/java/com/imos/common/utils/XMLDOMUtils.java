/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import lombok.extern.log4j.Log4j2;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author p
 */
@Log4j2
public class XMLDOMUtils {

    private static final DocumentBuilderFactory FACTORY = DocumentBuilderFactory.newInstance();

    private final Lock LOCK1 = new ReentrantLock();
    private final Lock LOCK2 = new ReentrantLock();

    public <T> Optional<T> readDocument(InputStream input, Function<Document, T> func) {
        Optional<T> result = Optional.empty();
        DocumentBuilder builder;
        func = Objects.requireNonNull(func);

        try {
            LOCK1.lock();
            builder = FACTORY.newDocumentBuilder();
            Document doc = builder.parse(input);
            result = Optional.ofNullable(func.apply(doc));
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            log.error("{}: {}", ex.getClass().getName(), ex.getMessage());
        } finally {
            LOCK1.unlock();
        }
        return result;
    }

    public <T> Optional<T> readDocument(String fileName, Function<Document, T> func) {
        Optional<T> result = Optional.empty();
        DocumentBuilder builder;
        func = Objects.requireNonNull(func);
        try {
            LOCK2.lock();
            builder = FACTORY.newDocumentBuilder();
            Document doc = builder.parse(new File(fileName));
            result = Optional.ofNullable(func.apply(doc));
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            log.error("{}: {}", ex.getClass().getName(), ex.getMessage());
        } finally {
            LOCK2.unlock();
        }
        return result;
    }

    public <T> Optional<T> readDocument(Document doc, Function<Document, T> func) {
        Optional<T> result = Optional.empty();
        try {
            LOCK1.lock();
            result = Optional.ofNullable(func.apply(doc));
        } catch (Exception ex) {
            log.error("{}: {}", ex.getClass().getName(), ex.getMessage());
        } finally {
            LOCK1.unlock();
        }

        return result;
    }
}
