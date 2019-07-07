package com.vidyo.utils;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.*;
import java.util.HashSet;
import java.util.Set;

public class CompressionUtils {

    protected static final Logger logger = LoggerFactory.getLogger(CompressionUtils.class.getName());

    /**
     * Gzips a file, input is the file to gzip, e.g. /home/tomcat/test.tar
     * and outputFilePath is the resulting gzipped file, e.g. /home/tomcat/test.tar.gz
     *
     * @param filePath
     * @param outputFilePath
     */
    public static void gzip(String filePath, String outputFilePath) throws FileNotFoundException, IOException {
        logger.debug("gzip: " + filePath + " to " + outputFilePath);
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
             FileOutputStream outputStream = new FileOutputStream(new File(outputFilePath));
             GzipCompressorOutputStream gzipOutputStream = new GzipCompressorOutputStream(outputStream)) {
            IOUtils.copy(inputStream, gzipOutputStream);
        }
    }

    /**
     * Gunzips a file, input is the file to ungzip, e.g. /home/tomcat/test.tar.gz
     * and outputFilePath is the result of ungzipping, e.g. /home/tomcat/test.tar
     *
     * @param filePath
     * @param outputFilePath
     */
    public static void gunzip(String filePath, String outputFilePath) throws FileNotFoundException, IOException {
        logger.debug("ungzip: " + filePath + " to " + outputFilePath);
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
             FileOutputStream outputStream = new FileOutputStream(new File(outputFilePath));
             GzipCompressorInputStream gzipInputStream = new GzipCompressorInputStream(inputStream)) {
            IOUtils.copy(gzipInputStream, outputStream);
        }
    }

    /**
     * This file ungzips and then untars a file, the input is the file, e..g /home/tomcat/test.tar.gz
     * and the outputPath is the directory to where contents are placed, optionally you may
     * want to preserve the file timestamps or use the newly created files timestamp
     *
     * @param filePath
     * @param outputPath
     * @param preserveLastModifiedTime
     */
    public static void untargzip(String filePath, String outputPath, boolean preserveLastModifiedTime) throws FileNotFoundException, IOException {
        logger.debug("untargzip: " + filePath + " to dir " + outputPath);

        String tmpTar = outputPath + "." + System.currentTimeMillis() + ".tmp.tar";
        try {
            gunzip(filePath, tmpTar);
            untar(tmpTar, outputPath, preserveLastModifiedTime);
        } finally {
            FileUtils.deleteQuietly(new File(tmpTar));
        }
    }

    /**
     * Untars a a file, input is filePath, e.g. /home/tomcat/test.tar and the outputPath is the
     * is the directory to where contents are placed, optionally you may
     * want to preserve the file timestamps or use the newly created files timestamp
     *
     * @param filePath
     * @param outputPath
     * @param preserveLastModifiedTime
     */
    public static void untar(String filePath, String outputPath, boolean preserveLastModifiedTime)  throws FileNotFoundException, IOException {
        logger.debug("untar: " + filePath + " to dir " + outputPath);
        File tarFile = new File(filePath);

        try (TarArchiveInputStream tarInputStream  = new TarArchiveInputStream(new FileInputStream(tarFile));) {
            TarArchiveEntry entry = tarInputStream.getNextTarEntry();
            while (entry != null) {
                File outFileOrDir = new File(outputPath, entry.getName());
                if (entry.isDirectory()) {
                    if (!outFileOrDir.mkdir()) {
                        logger.error("while untarring " + filePath + ", failed to make directory: " + entry.getName());
                    }
                } else {
                    if (!(entry.isSymbolicLink() || entry.isLink())) {
                        IOUtils.copy(tarInputStream, new FileOutputStream(outFileOrDir));
                    }
                }
                if (preserveLastModifiedTime) {
                    if (!outFileOrDir.setLastModified(entry.getLastModifiedDate().getTime())) {
                        logger.error("while untarring " + filePath + ", failed to update last modified: " + entry.getName());
                    }
                }

                Path file = Paths.get(outFileOrDir.getAbsolutePath());
                if (entry.isSymbolicLink()) {
                    Files.createSymbolicLink(file, Paths.get(entry.getLinkName()));
                } else  if (entry.isLink()) {
                    Files.createLink(file, Paths.get(entry.getLinkName()));
                }
                try {
                    Files.setPosixFilePermissions(file, getFilePermissionsFromOctal(Integer.toOctalString(entry.getMode())));
                } catch (IOException ioe) {
                    // ignore
                    logger.error("while untarring " + filePath + ", io exception setting file perms: " + entry.getName());
                }

                try {
                    UserPrincipal user = file.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName(entry.getUserName());
                    Files.getFileAttributeView(file, PosixFileAttributeView.class).setOwner(user);
                } catch (IOException ioe) {
                    // ignore
                    logger.error("while untarring " + filePath + ", io exception setting file user ownership: " + entry.getName());
                }
                try {
                    GroupPrincipal group = file.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByGroupName(entry.getGroupName());
                    Files.getFileAttributeView(file, PosixFileAttributeView.class).setGroup(group);
                } catch (IOException ioe) {
                    // ignore
                    logger.error("while untarring " + filePath + ", io exception setting file group ownership: " + entry.getName());
                }
                logger.debug(Integer.toOctalString(entry.getMode()) + " - " + entry.getLongUserId() + " - " + entry.getLongGroupId() + " - " + entry.getUserName() + " - " + entry.getGroupName() + " - " + entry.getName());
                entry = tarInputStream.getNextTarEntry();
            }

        }
    }

    /**
     * Tars a given file, input is a filePath for the single file to tar .e.g /home/tomcat/test.txt
     * and the outputTarFilePath is the tar file to create, e.g. /home/tomcat/test.tar
     * You may also drop the directory entries in the tar file, so for example, no home/tomcat would
     * appear when untarring in the previous example.
     *
     * note: Symbolic links will be followed and real files will
     * be created with the contents of that file.
     *
     * @param filePath
     * @param outputTarFilePath
     * @param dropParentPath
     */
    public static void tar(String filePath, String outputTarFilePath, boolean dropParentPath) throws FileNotFoundException, IOException {
        tar(new String[]{filePath}, outputTarFilePath, dropParentPath);
    }

    /**
     * Same as above but for multiple files.
     *
     * @param filePaths
     * @param outputTarFilePath
     * @param dropParentPath
     */
    public static void tar(String[] filePaths, String outputTarFilePath, boolean dropParentPath) throws FileNotFoundException, IOException {
        File outputTarFile = new File(outputTarFilePath);

        try (OutputStream out = new FileOutputStream(outputTarFile);
             TarArchiveOutputStream os = new TarArchiveOutputStream(out)) {
            for (String filePath : filePaths) {
                logger.debug("tar: " + filePath + " to dir " + outputTarFilePath);
                File file = new File(filePath);
                if (!file.exists()) {
                    logger.error("ignoring file to tar: " + filePath + " since it does not exist");
                    continue;
                }
                String base = file.getParent() + "/";
                if (dropParentPath) {
                    base = "";
                }
                os.setBigNumberMode(TarArchiveOutputStream.BIGNUMBER_STAR); // correct?
                os.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU); // correct?
                if (file.isDirectory()) {
                    File[] filesInDirectory = file.listFiles();
                    if (filesInDirectory != null) {
                        for (File fileS : filesInDirectory) {
                            addFileOrDirectoryToTar(os, fileS, base);
                        }
                    }
                } else {
                    addFileOrDirectoryToTar(os, file, base);
                }
                os.flush();
                out.flush();
            }
        }
    }

    private static void addFileOrDirectoryToTar(ArchiveOutputStream os, File fileN, String base) throws IOException {
        Path file = Paths.get(fileN.getAbsolutePath());
        TarArchiveEntry entry = new TarArchiveEntry(file.toFile(), base + file.toFile().getName());
        PosixFileAttributes attrs = Files.readAttributes(file,PosixFileAttributes.class);
        entry.setUserId((Integer) Files.getAttribute(file, "unix:uid"));
        entry.setUserName(attrs.owner().getName());
        entry.setGroupId((Integer) Files.getAttribute(file, "unix:gid"));
        entry.setGroupName(attrs.group().getName());
        logger.debug("mode: " + getOctalFromFilePermissions(attrs.permissions()) + " for " + file.toFile().getAbsolutePath());
        entry.setMode(Integer.parseInt(getOctalFromFilePermissions(attrs.permissions()), 8));
        entry.setModTime(attrs.lastModifiedTime().toMillis());
        if (!attrs.isDirectory()) {
            entry.setSize(file.toFile().length());
        }
        os.putArchiveEntry(entry);
        if (file.toFile().isFile()) {
            IOUtils.copy(new FileInputStream(file.toFile()), os);
            os.closeArchiveEntry();
        } else {
            os.closeArchiveEntry();
            File[] filesInDirectory = file.toFile().listFiles();
            if (filesInDirectory != null) {
                for (File fileS : filesInDirectory) {
                    addFileOrDirectoryToTar(os, fileS, base + file.toFile().getName() + "/");
                }
            }
        }
    }

    /**
     * Same as above but combines tarring and gzipping in one command for a single file.
     *
     * @param filePath
     * @param outputTarGzFilePath
     * @param dropParentPath
     */
    public static void targzip(String filePath, String outputTarGzFilePath, boolean dropParentPath) throws FileNotFoundException, IOException {
        targzip(new String[]{filePath}, outputTarGzFilePath, dropParentPath);
    }

    /**
     * Same as above but for multiple files.
     *
     * @param filePaths
     * @param outputTarGzFilePath
     * @param dropParentPath
     */
    public static void targzip(String[] filePaths, String outputTarGzFilePath, boolean dropParentPath) throws FileNotFoundException, IOException {
        String tmpTar = outputTarGzFilePath + "." + System.currentTimeMillis() + ".tmp.tar";
        try {
            tar(filePaths, tmpTar, dropParentPath);
            gzip(tmpTar, outputTarGzFilePath);
        } finally {
            FileUtils.deleteQuietly(new File(tmpTar));
        }
    }

    private static String getOctalFromFilePermissions(Set<PosixFilePermission> perms) {
        int octal = 0;
        for (PosixFilePermission perm : perms) {
            if (perm == PosixFilePermission.OTHERS_EXECUTE) {
                octal = octal + 1;
            } else if (perm == PosixFilePermission.OTHERS_READ) {
                octal = octal + 4;
            } else if (perm == PosixFilePermission.OTHERS_WRITE) {
                octal = octal + 2;
            } else if (perm == PosixFilePermission.GROUP_EXECUTE) {
                octal = octal + 10;
            } else if (perm == PosixFilePermission.GROUP_READ) {
                octal = octal + 40;
            } else if (perm == PosixFilePermission.GROUP_WRITE) {
                octal = octal + 20;
            } else if (perm == PosixFilePermission.OWNER_EXECUTE) {
                octal = octal + 100;
            } else if (perm == PosixFilePermission.OWNER_READ) {
                octal = octal + 400;
            } else if (perm == PosixFilePermission.OWNER_WRITE) {
                octal = octal + 200;
            }
        }
        return ""+octal;
    }

    private static Set<PosixFilePermission> getFilePermissionsFromOctal(String octalStr) {
        int octal = Integer.parseInt(octalStr);
        Set<PosixFilePermission> filePerms = new HashSet<PosixFilePermission>();
        int otherPerm = octal % 10;
        int groupPerm = (octal / 10) % 10;
        int userPerm = (octal /100) % 10;

        switch (userPerm) {
            case 1:
                filePerms.add(PosixFilePermission.OWNER_EXECUTE);
                break;
            case 2:
                filePerms.add(PosixFilePermission.OWNER_WRITE);
                break;
            case 3:
                filePerms.add(PosixFilePermission.OWNER_EXECUTE);
                filePerms.add(PosixFilePermission.OWNER_WRITE);
                break;
            case 4:
                filePerms.add(PosixFilePermission.OWNER_READ);
                break;
            case 5:
                filePerms.add(PosixFilePermission.OWNER_READ);
                filePerms.add(PosixFilePermission.OWNER_EXECUTE);
                break;
            case 6:
                filePerms.add(PosixFilePermission.OWNER_WRITE);
                filePerms.add(PosixFilePermission.OWNER_READ);
                break;
            case 7:
                filePerms.add(PosixFilePermission.OWNER_EXECUTE);
                filePerms.add(PosixFilePermission.OWNER_WRITE);
                filePerms.add(PosixFilePermission.OWNER_READ);
                break;
            default:
                filePerms.add(PosixFilePermission.OWNER_WRITE);
                filePerms.add(PosixFilePermission.OWNER_READ);
                break;
        }

        switch (groupPerm) {
            case 1:
                filePerms.add(PosixFilePermission.GROUP_EXECUTE);
                break;
            case 2:
                filePerms.add(PosixFilePermission.GROUP_WRITE);
                break;
            case 3:
                filePerms.add(PosixFilePermission.GROUP_EXECUTE);
                filePerms.add(PosixFilePermission.GROUP_WRITE);
                break;
            case 4:
                filePerms.add(PosixFilePermission.GROUP_READ);
                break;
            case 5:
                filePerms.add(PosixFilePermission.GROUP_READ);
                filePerms.add(PosixFilePermission.GROUP_EXECUTE);
                break;
            case 6:
                filePerms.add(PosixFilePermission.GROUP_WRITE);
                filePerms.add(PosixFilePermission.GROUP_READ);
                break;
            case 7:
                filePerms.add(PosixFilePermission.GROUP_EXECUTE);
                filePerms.add(PosixFilePermission.GROUP_WRITE);
                filePerms.add(PosixFilePermission.GROUP_READ);
                break;
            default:
                filePerms.add(PosixFilePermission.GROUP_READ);
                break;
        }


        switch (otherPerm) {
            case 1:
                filePerms.add(PosixFilePermission.OTHERS_EXECUTE);
                break;
            case 2:
                filePerms.add(PosixFilePermission.OTHERS_WRITE);
                break;
            case 3:
                filePerms.add(PosixFilePermission.OTHERS_EXECUTE);
                filePerms.add(PosixFilePermission.OTHERS_WRITE);
                break;
            case 4:
                filePerms.add(PosixFilePermission.OTHERS_READ);
                break;
            case 5:
                filePerms.add(PosixFilePermission.OTHERS_READ);
                filePerms.add(PosixFilePermission.OTHERS_EXECUTE);
                break;
            case 6:
                filePerms.add(PosixFilePermission.OTHERS_WRITE);
                filePerms.add(PosixFilePermission.OTHERS_READ);
                break;
            case 7:
                filePerms.add(PosixFilePermission.OTHERS_EXECUTE);
                filePerms.add(PosixFilePermission.OTHERS_WRITE);
                filePerms.add(PosixFilePermission.OTHERS_READ);
                break;
            default:
                filePerms.add(PosixFilePermission.OTHERS_READ);
                break;
        }


        return filePerms;
    }

    public static void unzip(String filePath, String outputPath) {
        File zipFile = new File(filePath);
        if (!zipFile.exists()) {
            return;
        }
        File outputDir = new File(outputPath);
        if (!outputDir.isDirectory() || !outputDir.exists()) {
            return;
        }
        try (ZipArchiveInputStream zipInputStream = new ZipArchiveInputStream(new FileInputStream(zipFile));) {
            ZipArchiveEntry entry = zipInputStream.getNextZipEntry();
            while (entry != null) {
                File outFileOrDir = new File(outputPath, entry.getName());
                if (entry.isDirectory()) {
                    if (!outFileOrDir.mkdir()) {
                        logger.error("Failed to make directory: " + entry.getName());
                    }
                } else {
                    if (entry.getName().contains("/")) {
                        File createDirs = new File(outputPath, entry.getName().substring(0, entry.getName().lastIndexOf('/')));
                        if (!createDirs.mkdirs()) {
                            logger.error("Failed to make directories for: " + entry.getName());
                        }
                    }
                    IOUtils.copy(zipInputStream, new FileOutputStream(outFileOrDir));
                }
                entry = zipInputStream.getNextZipEntry();
            }
        } catch (FileNotFoundException fnfe) {
            logger.error("FileNotFoundException processing : " + filePath);
        } catch (IOException ioe) {
            logger.error("IOException unzipping: " + filePath);
        }
    }


    public static void main(String[] args) {

        try {

            System.out.println("untarring");
            long t1 = System.currentTimeMillis();
            untar("/home/vikram/test/code.tar", "/home/vikram/test/extract", true);
            long t2 = System.currentTimeMillis();
            System.out.println("  " + (t2 - t1)  + " ms");

            System.out.println("tarring what was untarred");
            t1 = System.currentTimeMillis();
            tar("/home/vikram/test/extract", "/home/vikram/test/extract.tar", true);
            t2 = System.currentTimeMillis();
            System.out.println("  " + (t2 - t1)  + " ms");

            System.out.println("untarring the new tar");
            t1 = System.currentTimeMillis();
            untar("/home/vikram/test/extract.tar", "/home/vikram/test/extract2", true);
            t2 = System.currentTimeMillis();
            System.out.println("  " + (t2 - t1)  + " ms");

            System.out.println("gzipping the new tar");
            t1 = System.currentTimeMillis();
            gzip("/home/vikram/test/extract.tar", "/home/vikram/test/extract.tar.gz");
            t2 = System.currentTimeMillis();
            System.out.println("  " + (t2 - t1)  + " ms");
            System.out.println("gunzipping the new tar.gz");
            t1 = System.currentTimeMillis();
            gunzip("/home/vikram/test/extract.tar.gz", "/home/vikram/test/extract2.tar");
            t2 = System.currentTimeMillis();
            System.out.println("  " + (t2 - t1)  + " ms");

            System.out.println("tarring and zipping the untarred dir in one step");
            t1 = System.currentTimeMillis();
            targzip("/home/vikram/test/extract", "/home/vikram/test/extract3.tar.gz", true);
            t2 = System.currentTimeMillis();
            System.out.println("  " + (t2 - t1)  + " ms");
            System.out.println("untarring and unzipping the new tar.gz done in one step");
            t1 = System.currentTimeMillis();
            untargzip("/home/vikram/test/extract3.tar.gz", "/home/vikram/test/extract3", true);
            t2 = System.currentTimeMillis();
            System.out.println("  " + (t2 - t1)  + " ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
