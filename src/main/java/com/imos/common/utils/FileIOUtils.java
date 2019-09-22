/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.common.utils;

import com.alibaba.fastjson.JSON;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
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

    public static void searchForFoldersAndFiles(File file, Collection<String> fileList, Predicate<String> folderCondition, Predicate<String> fileCondition) {
        if (file != null) {
            for (File subFile : file.listFiles()) {
                String name = subFile.getName();
                if (!subFile.isHidden() && subFile.isDirectory() && folderCondition.test(name)) {
                    searchForFoldersAndFiles(subFile, fileList, folderCondition, fileCondition);
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
            fileName = createIncrementalFileName(fileName);
            fileName = createFilePath(fileName);
            Files.write(Paths.get(fileName),
                    ("[" + lines.stream().collect(Collectors.joining(",")) + "]").getBytes(), OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), getCauseMessage(ex));
        }
    }

    public static void writeToIncrementalFile(String fileName, String data) {
        try {
            fileName = createIncrementalFileName(fileName);
            fileName = createFilePath(fileName);
            Files.write(Paths.get(fileName), data.getBytes(), OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), getCauseMessage(ex));
        }
    }

    public static void writeToIncrementalFile(String fileName, Collection<String> lines) {
        try {
            fileName = createIncrementalFileName(fileName);
            fileName = createFilePath(fileName);
            Files.write(Paths.get(fileName), lines, OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), getCauseMessage(ex));
        }
    }

    public static void writeToJSONFile(String fileName, Collection<String> lines) {
        try {
            fileName = createFilePath(fileName);
            Files.write(Paths.get(fileName), ("[" + lines.stream().collect(Collectors.joining(",")) + "]").getBytes(), OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), getCauseMessage(ex));
        }
    }

    public static void writeToFile(String fileName, Collection<String> lines) {
        try {
            fileName = createFilePath(fileName);
            Files.write(Paths.get(fileName), lines, OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), getCauseMessage(ex));
        }
    }

    public static void writeToFile(String fileName, String data) {
        try {
            fileName = createFilePath(fileName);
            Files.write(Paths.get(fileName), data.getBytes(), OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), getCauseMessage(ex));
        }
    }

    public static void writeToIncrementalFileAtResources(String fileName, String data) {
        fileName = checkResourceFolder(fileName);
        try {
            fileName = createIncrementalFileName(fileName);
            fileName = createFilePath(fileName);
            Files.write(Paths.get(fileName), data.getBytes(), OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), getCauseMessage(ex));
        }
    }

    public static void writeToFileAtResources(String fileName, Collection<String> lines) {
        fileName = checkResourceFolder(fileName);
        try {
            fileName = createFilePath(fileName);
            Files.write(Paths.get(fileName), lines, OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), getCauseMessage(ex));
        }
    }

    private static String createFilePath(String fileName) {
        String absUserDirPath = new File("").getAbsolutePath();
        File file = new File(absUserDirPath + File.separator + fileName);
        String absFilePath = file.getAbsolutePath();
        createIntermediateFolders(absFilePath);
        return absFilePath;
    }

    private static void createIntermediateFolders(String absFilePath) {
        File file;
        String absFolderPath = absFilePath.substring(0, absFilePath.lastIndexOf(File.separator));
        file = new File(absFolderPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private static String checkResourceFolder(String fileName) {
        String resourcesFolderPath = getResourcesFolderPath();
        if (!fileName.startsWith(resourcesFolderPath)) {
            fileName = resourcesFolderPath + File.separator + fileName;
        }
        return fileName;
    }

    public static void writeToJSONFileAtResources(String fileName, Collection<String> lines) {
        fileName = checkResourceFolder(fileName);
        try {
            if (fileName.contains(".")) {
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
            }
            fileName = createFileName(fileName, "json");
            fileName = createFilePath(fileName);
            String data = "[" + lines.stream().collect(Collectors.joining(",")) + "]";
            Files.write(Paths.get(fileName), data.getBytes(), OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), getCauseMessage(ex));
        }
    }

    public static void writeToFileAtResources(String fileName, String data) {
        fileName = checkResourceFolder(fileName);
        try {
            fileName = createFilePath(fileName);
            Files.write(Paths.get(fileName), data.getBytes(), OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), getCauseMessage(ex));
        }
    }

    public static List<String> readFile(String filePath) {
        List<String> lines;
        File file = new File(filePath);
        if (file.exists()) {
            try {
                lines = Files.readAllLines(Paths.get(file.toURI()));
            } catch (IOException ex) {
                LOG.warn("File path: {}", file.getAbsolutePath());
                LOG.error("{} {}", ex.getMessage(), getCauseMessage(ex));
                lines = Collections.EMPTY_LIST;
            }
        } else {
            try {
                LOG.warn("{} does not exist", filePath);
                LOG.warn("File path: {}", file.getAbsolutePath());
                LOG.info("{} file read from resourecs folder", filePath);
                URL url = FileIOUtils.class.getClassLoader().getResource(filePath);
                if (url == null) {
                    throw new FileNotFoundException("File does note exist in resourecs folder");
                }
                lines = Files.readAllLines(Paths.get(url.toURI()));
            } catch (FileNotFoundException ex) {
                LOG.warn("File path: {}", file.getAbsolutePath());
                LOG.error("{} {}", ex.getMessage(), ex.getClass().getName());
                file = new File("." + File.separator + filePath);
                if (file.exists()) {
                    try {
                        lines = Files.readAllLines(Paths.get(file.toURI()));
                    } catch (IOException ex1) {
                        LOG.warn("File path: {}", file.getAbsolutePath());
                        LOG.error("{} {}", ex1.getMessage(), getCauseMessage(ex));
                        lines = Collections.EMPTY_LIST;
                    }
                } else {
                    lines = Collections.EMPTY_LIST;
                }
            } catch (URISyntaxException | IOException ex) {
                LOG.error("{} {}", ex.getMessage(), getCauseMessage(ex));
                lines = Collections.EMPTY_LIST;
            }
        }
        return lines;
    }

    public static List<String> readFileFromResourcesAsLines(String fileName) {
        try {
            return Files.readAllLines(Paths.get(FileIOUtils.class
                    .getClassLoader()
                    .getResource(fileName)
                    .toURI()));
        } catch (IOException | URISyntaxException ex) {
            LOG.error("{} {}", ex.getMessage(), getCauseMessage(ex));
        }
        return Collections.EMPTY_LIST;
    }

    public static String readFileFromResources(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(FileIOUtils.class.getClassLoader().getResource(fileName).toURI())));
        } catch (IOException | URISyntaxException ex) {
            LOG.error("{} {}", ex.getMessage(), getCauseMessage(ex));
        }
        return "";
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
                : fileName;
    }

    private static String createFileName(String fileName) {
        return createFileName(fileName, false);
    }

    private static String getResourcesFolderPath() {
        return "src" + File.separator + "main" + File.separator + "resources";
    }

    private static String getCauseMessage(Throwable th) {
        Throwable temp = th;
        StringBuilder builder = new StringBuilder();
        while (temp != null) {
            builder.append(th.getMessage());
            builder.append(":");
            builder.append(th.getClass().getName());
            builder.append("\n");
            temp = temp.getCause();
        }
        return builder.toString();
    }
}
