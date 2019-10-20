/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.common.utils;

import com.alibaba.fastjson.JSON;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.LineNumberReader;
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
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author alok.meher
 */
public class FileIOUtils {

    private static final Logger LOG = LogManager.getLogger(FileIOUtils.class);

    public static final OpenOption[] OPEN_OPTION = new OpenOption[]{
        StandardOpenOption.CREATE, 
        StandardOpenOption.TRUNCATE_EXISTING};

    private static final String INCREMENTAL_DATE_FORMAT = "dd_MMM_YYYY_hh_mm_ss a";

    public static Collection<String> searchForFoldersAndFilesAndContent(File file, 
            Collection<String> lines, 
            Predicate<String> folderCondition, 
            Predicate<String> fileCondition, 
            Predicate<String> contentCondition) {
        if (file != null && file.isDirectory() && !file.isHidden()) {
            for (File subFile : file.listFiles()) {
                String name = subFile.getName();
                if (!subFile.isHidden() && subFile.isDirectory() && folderCondition.test(name)) {
                   lines = searchForFoldersAndFilesAndContent(subFile, lines, folderCondition, fileCondition, contentCondition);
                } else if (!subFile.isHidden() && subFile.canRead() && subFile.isFile() && fileCondition.test(name)) {
                    if (contentCondition == null) {
                        continue;
                    }
                    try ( FileReader fileReader = new FileReader(new File(subFile.getAbsolutePath()));
                            LineNumberReader reader = new LineNumberReader(fileReader)) {
                        String line = "";
                        boolean showLines;
                        lines.add(subFile.getName());
                        lines.add(subFile.getAbsolutePath());
                        while ((line = reader.readLine()) != null) {
                            showLines = false;
                            if (contentCondition.test(line)) {
                                showLines = true;
                            }
                            if (showLines) {
                                lines.add(reader.getLineNumber() + " : " + line.trim());
                            }
                        }
                        lines.add("\n");
                    } catch (FileNotFoundException ex) {
                        LOG.error("{} {}", ex.getMessage(), getCauseMessage(ex));
                    } catch (IOException ex) {
                        LOG.error("{} {}", ex.getMessage(), getCauseMessage(ex));
                    }
                }
            }
        }
        return lines;
    }

    public static void searchForFoldersAndFiles(File file, 
            Collection<String> fileList, 
            Predicate<String> folderCondition, 
            Predicate<String> fileCondition) {
        if (file != null) {
            File[] files = file.listFiles();
            if (files == null) {
                return;
            }
            for (File subFile : files) {
                String name = subFile.getName();
                if (!subFile.isHidden() && subFile.isDirectory() && folderCondition.test(name)) {
                    searchForFoldersAndFiles(subFile, fileList, folderCondition, fileCondition);
                } else if (!subFile.isHidden() && subFile.isFile() && fileCondition.test(name)) {
                    fileList.add(subFile.getAbsolutePath());
                }
            }
        }
    }

    public static <T, D> List<D> transformStreamData(Collection<T> list, Function<T, D> func) {
        return list.stream()
                .map(line -> func.apply(line))
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

    public static void writeToFileAtResourcesWithTimeIncreName(String fileName, String data) {
        try {
            fileName = checkResourceFolder(fileName);
            fileName = createIncrementalFileName(fileName);
            fileName = createFilePath(fileName);
            Files.write(Paths.get(fileName), data.getBytes(), OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), getCauseMessage(ex));
        }
    }

    public static void writeToFileWithTimeIncreName(String fileName, String data) {
        try {
            fileName = createIncrementalFileName(fileName);
            fileName = createFilePath(fileName);
            Files.write(Paths.get(fileName), data.getBytes(), OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), getCauseMessage(ex));
        }

    }

    public static void writeToFileAtResourcesWithTimeIncreName(String fileName, Collection<String> lines) {
        try {
            fileName = checkResourceFolder(fileName);
            fileName = createIncrementalFileName(fileName);
            fileName = createFilePath(fileName);
            Files.write(Paths.get(fileName), lines, OPEN_OPTION);
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), getCauseMessage(ex));
        }
    }

    public static void writeToFileWithTimeIncreName(String fileName, Collection<String> lines) {
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
        File parentFolder = new File(fileName);
        String parentFolderPath = new File("").getAbsolutePath();
        if (fileName.contains(File.separator)) {
            parentFolderPath = fileName.substring(0, fileName.lastIndexOf(File.separator));
            parentFolder = new File(parentFolderPath);
        }
        String absFilePath = fileName;
        if (!parentFolder.exists()) {
            File file = new File(parentFolderPath + File.separator + fileName);
            absFilePath = file.getAbsolutePath();
            createIntermediateFolders(absFilePath);
        } else {
            createIntermediateFolders(parentFolderPath);
        }
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

    public static String readFileAsString(String fileName) {
        try {
            return Files.readAllLines(Paths.get(fileName))
                    .stream()
                    .collect(Collectors.joining("\n"));
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), ex.getCause().getClass().getName());
        }
        return "";
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
            return new String(Files.readAllBytes(Paths.get(FileIOUtils.class
                    .getClassLoader()
                    .getResource(fileName)
                    .toURI())));
        } catch (IOException | URISyntaxException ex) {
            LOG.error("{} {}", ex.getMessage(), getCauseMessage(ex));
        }
        return "";
    }

    private static String createIncrementalFileName(String name) {
        String ext = name.substring(name.lastIndexOf("."));
        name = name.substring(0, name.lastIndexOf("."));
        String time = calculateCurrentTime();
        return name + "_" + time + ext;
    }

    private static String createIncrementalFileName(String name, String ext) {
        String time = calculateCurrentTime();
        return name + "_" + time + "." + ext;
    }

    private static String createFileName(String name, String ext) {
        return name + "." + ext;
    }

    private static String createFileName(String fileName, boolean incremental) {
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        return incremental
                ? createIncrementalFileName(name, ext)
                : fileName;
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

    private static String calculateCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(INCREMENTAL_DATE_FORMAT));
    }
}
