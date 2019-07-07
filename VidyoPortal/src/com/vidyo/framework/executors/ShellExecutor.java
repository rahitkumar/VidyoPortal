package com.vidyo.framework.executors;



import com.vidyo.framework.executors.exception.ShellExecutorException;

import java.io.IOException;
import java.util.List;


public class ShellExecutor {

    public static ShellCapture execute(String cmd) throws ShellExecutorException {
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            return wrapProcess(proc);
        } catch (IOException ioe) {
            throw new ShellExecutorException("io exception", ioe);
        }
    }

    public static ShellCapture execute(String[] cmd) throws ShellExecutorException {
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            return wrapProcess(proc);
        } catch (IOException ioe) {
            throw new ShellExecutorException("io exception", ioe);
        }
    }

    public static ShellCapture execute(String[] cmd, List<String> stdInContent) throws ShellExecutorException {
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            return wrapProcess(proc, stdInContent);
        } catch (IOException ioe) {
            throw new ShellExecutorException("io exception", ioe);
        }
    }

    public static ShellCapture bashExecute(String cmd) throws ShellExecutorException {
        String[] cmdArr = {"bash", "-c", cmd };
        return execute(cmdArr);
    }

    private static ShellCapture wrapProcess(Process proc) throws ShellExecutorException {
        ShellCapture shellCapture = new ShellCapture();
        try {
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), StreamGobbler.StreamType.STDERR);
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), StreamGobbler.StreamType.STDOUT);

            errorGobbler.start();
            outputGobbler.start();

            int exitVal = proc.waitFor();
            shellCapture.setExitCode(exitVal);

            errorGobbler.join();
            outputGobbler.join();

            if (outputGobbler.getOutputContent() != null) {
                shellCapture.setStdOutLines(outputGobbler.getOutputContent());
            }
            if (errorGobbler.getOutputContent() != null) {
                shellCapture.setStdErrLines(errorGobbler.getOutputContent());
            }

            return shellCapture;
        } catch (InterruptedException ie) {
            throw new ShellExecutorException("thread exception", ie);
        }
    }

    private static ShellCapture wrapProcess(Process proc, List<String> stdInContent) throws ShellExecutorException {
        ShellCapture shellCapture = new ShellCapture();
        try {
            StreamSender inputSender = new StreamSender(proc.getOutputStream(), StreamSender.StreamType.STDIN);
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), StreamGobbler.StreamType.STDERR);
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), StreamGobbler.StreamType.STDOUT);

            inputSender.setInputContent(stdInContent);

            inputSender.start();
            errorGobbler.start();
            outputGobbler.start();

            int exitVal = proc.waitFor();
            shellCapture.setExitCode(exitVal);

            inputSender.join();
            errorGobbler.join();
            outputGobbler.join();

            if (outputGobbler.getOutputContent() != null) {
                shellCapture.setStdOutLines(outputGobbler.getOutputContent());
            }
            if (errorGobbler.getOutputContent() != null) {
                shellCapture.setStdErrLines(errorGobbler.getOutputContent());
            }

            return shellCapture;
        } catch (InterruptedException ie) {
            throw new ShellExecutorException("thread exception", ie);
        }
    }
}
