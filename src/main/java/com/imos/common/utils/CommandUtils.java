/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author alok.meher
 */
public class CommandUtils {

    private static final Logger LOG = LogManager.getLogger(CommandUtils.class);

    public void executeBatchCommand(String dir, String batchFile, long waitingTime) {
        List<String> pcmds = new ArrayList<>();
        pcmds.add("cmd.exe");
        pcmds.add("/C");
        pcmds.add("Start");
        pcmds.add(batchFile);
        ProcessBuilder builder = new ProcessBuilder(pcmds);
        builder.directory(new File(dir));

        try {
            builder.start();
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), ex.getCause().getClass().getName());
        }
    }

    public void executeBatchCommand(String batchFile) {
        executeBatchCommand(new File(".").getAbsolutePath(), batchFile);
    }

    public void executeBatchCommand(String dir, String batchFile) {
        executeBatchCommand(dir, batchFile, 0);
    }

    public List<String> executeCommand(String dir, long waitingTime, List<String> cmds) {
        List<String> lines = new ArrayList<>();
        List<String> pcmds = new ArrayList<>();
        pcmds.add("cmd.exe");
        pcmds.add("/C");
        pcmds.addAll(cmds);
        lines = executeCommand(pcmds, dir, lines, waitingTime);

        return lines;
    }

    private List<String> executeCommand(List<String> pcmds, String dir, List<String> lines, long waitingTime) {
        ProcessBuilder builder = new ProcessBuilder(pcmds);
        builder.directory(new File(dir));
        Process process;
        try {
            process = builder.start();
            InputStream output = process.getInputStream();
            if (output != null) {
                lines = readInputStreamData(output);
            }
            InputStream error = process.getErrorStream();
            if (error != null && lines.isEmpty()) {
                lines = readInputStreamData(error);
            }
            if (waitingTime == 0) {
                process.waitFor();
            } else {
                process.waitFor(waitingTime, TimeUnit.SECONDS);
            }
            int exitValue = process.exitValue();
            if (exitValue == 0) {
                LOG.info("Success");
            } else {
                LOG.error("Cmd {} Failed: {}",pcmds, exitValue);
            }
        } catch (IOException | InterruptedException ex) {
            LOG.error("{} {}", ex.getMessage(), ex.getCause().getClass().getName());
        }
        return lines;
    }

    public List<String> executeCommand(List<String> cmds) {
        return executeCommand(new File(".").getAbsolutePath(), cmds);
    }

    public List<String> executeCommand(String dir, List<String> cmds) {
        return executeCommand(dir, 0, cmds);
    }

    private List<String> readInputStreamData(InputStream output) {
        List<String> lines = Collections.EMPTY_LIST;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(output))) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            LOG.error("{} {}", ex.getMessage(), ex.getCause().getClass().getName());
        }
        return lines;
    }
}
