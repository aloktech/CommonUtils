/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.common.utils;

import com.alibaba.fastjson.JSON;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author alok.meher
 */
public class FileIOUtils {

    private static final Logger LOG = LogManager.getLogger(FileIOUtils.class);

    public static final OpenOption[] OPEN_OPTION = new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};

    private static final String INCREMENTAL_DATE_FORMAT = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MMM_YYYY_hh_mm_ss a"));

    public static void parseForFoldersAndFiles(File file, Collection<String> fileList, Predicate<String> folderCondition, Predicate<String> fileCondition) {
        if (file != null) {
            for (File subFile : file.listFiles()) {
                String name = subFile.getName();
                if (!subFile.isHidden() && subFile.isDirectory() && folderCondition.test(name)) {
                    parseForFoldersAndFiles(subFile, fileList, folderCondition, fileCondition);
                } else if (!subFile.isHidden() && subFile.isFile() && fileCondition.test(name)) {
                    fileList.add(subFile.getAbsolutePath());
                }
            }
        }
    }

    public static <T> List<String> transferObjectToStringStream(Collection<T> list) {
        return list.stream()
                .map(o -> o.toString())
                .collect(Collectors.toList());
    }
    
    public static <T> List<String> transferObjectToJSONStringStream(Collection<T> list) {
        return list.stream()
                .map(o -> JSON.toJSONString(o))
                .collect(Collectors.toList());
    }

    public static void writeToIncrementalJSONFile(String fileName, Collection<String> lines) {
        try {
            Files.write(Paths.get(createIncrementalFileName(fileName)),
                    ("[" + lines.stream().collect(Collectors.joining(",")) + "]").getBytes(), OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), ex.getCause().getClass().getName());
        }
    }

    public static void writeToIncrementalFile(String fileName, String data) {
        try {
            Files.write(Paths.get(createIncrementalFileName(fileName)), data.getBytes(), OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), ex.getCause().getClass().getName());
        }
    }

    public static void writeToIncrementalFile(String fileName, Collection<String> lines) {
        try {
            Files.write(Paths.get(createIncrementalFileName(fileName)), lines, OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), ex.getCause().getClass().getName());
        }
    }

    public static void writeToJSONFile(String fileName, Collection<String> lines) {
        try {
            Files.write(Paths.get(fileName), ("[" + lines.stream().collect(Collectors.joining(",")) + "]").getBytes(), OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), ex.getCause().getClass().getName());
        }
    }

    public static void writeToFile(String fileName, Collection<String> lines) {
        try {
            Files.write(Paths.get(fileName), lines, OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), ex.getCause().getClass().getName());
        }
    }

    public static void writeToFile(String fileName, String data) {
        try {
            Files.write(Paths.get(fileName), data.getBytes(), OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), ex.getCause().getClass().getName());
        }
    }

    public static void writeToIncrementalFileAtResources(String fileName, String data) {
        if (!fileName.startsWith("src/main/resources")) {
            fileName = "src/main/resources/" + fileName;
        }
        try {
            Files.write(Paths.get(createIncrementalFileName(fileName)), data.getBytes(), OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), ex.getCause().getClass().getName());
        }
    }

    public static void writeToFileAtResources(String fileName, Collection<String> lines) {
        if (!fileName.startsWith("src/main/resources")) {
            fileName = "src/main/resources/" + fileName;
        }
        try {
            Files.write(Paths.get(createFileName(fileName)), lines, OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), ex.getCause().getClass().getName());
        }
    }

    public static void writeToJSONFileAtResources(String fileName, Collection<String> lines) {
        if (!fileName.startsWith("src/main/resources")) {
            fileName = "src/main/resources/" + fileName;
        }
        try {
            String name = fileName;
            if (fileName.contains(".")) {
                name = fileName.substring(0, fileName.lastIndexOf("."));
            }
            String ext = "json";
            String data = "[" + lines.stream().collect(Collectors.joining(",")) + "]";
            Files.write(Paths.get(createFileName(name, ext)), data.getBytes(), OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), ex.getCause().getClass().getName());
        }
    }

    public static void writeToFileAtResources(String fileName, String data) {
        if (!fileName.startsWith("src/main/resources")) {
            fileName = "src/main/resources/" + fileName;
        }
        try {
            Files.write(Paths.get(fileName), data.getBytes(), OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), ex.getCause().getClass().getName());
        }
    }

    public static List<String> readFile(String filePath) {
        List<String> lines;
        File file = new File(filePath);
        if (file.exists()) {
            try {
                lines = Files.readAllLines(Paths.get(file.toURI()));
            } catch (IOException ex) {
                LOG.error("{} {}", ex.getMessage(), ex.getCause().getClass().getName());
                lines = Collections.EMPTY_LIST;
            }
        } else {
            try {
                LOG.warn("{} does not exist", filePath);
                LOG.info("{} file read from resourecs folder", filePath);
                lines = Files.readAllLines(Paths.get(FileIOUtils.class.getClassLoader().getResource(filePath).toURI()));
            } catch (URISyntaxException | IOException ex) {
                LOG.error("{} {}", ex.getMessage(), ex.getCause().getClass().getName());
                lines = Collections.EMPTY_LIST;
            }
        }
        return lines;
    }

    public static List<String> readFileFromResources(String fileName) {
        try {
            return Files.readAllLines(Paths.get(FileIOUtils.class.getClassLoader().getResource(fileName).toURI()));
        } catch (IOException | URISyntaxException ex) {
            LOG.error("{} {}", ex.getMessage(), ex.getCause().getClass().getName());
        }
        return Collections.EMPTY_LIST;
    }

    private static String createIncrementalFileName(String name, String ext) {
        return name + "_" + INCREMENTAL_DATE_FORMAT + "." + ext;
    }

    private static String createFileName(String name, String ext) {
        return name + "." + ext;
    }

    private static String createIncrementalFileName(String fileName) {
        return createFileName(fileName, true);
    }

    private static String createFileName(String fileName, boolean incremental) {
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        return incremental
                ? createIncrementalFileName(name, ext)
                : createFileName(name, ext);
    }

    private static String createFileName(String fileName) {
        return createFileName(fileName, false);
    }
}
