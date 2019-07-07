/**
 * 
 */
package com.vidyo.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ysakurikar
 *Operations related to thumb nail functionality
 */
public class ImageUtils {

	protected static final Logger logger = LoggerFactory.getLogger(ImageUtils.class.getName());
	
	/**
	 * Validate the image if it is valid data or invalid/corrupted bytes.
	 * @param thumbNail
	 * @return
	 * @throws IOException
	 */
	public static boolean validateThumbNailImage(byte[] thumbNail) throws IOException {
		BufferedImage imag=ImageIO.read(new ByteArrayInputStream(thumbNail));
		
		if(imag==null){
			logger.error("Image cant be written to File System since either it is corrupted or not an image");
			
			return false;
		}
		if(!isImageJPGPNG(thumbNail)){
			logger.error("Validation failed. Image is not png or jpeg");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Check if the image format is JPEG or PNG
	 * @param thumbNail
	 * @return
	 */
	public static boolean isImageJPGPNG(byte[] thumbNail) {
		boolean jpgOrPng = true;
		try {
			ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(thumbNail));
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()){
				jpgOrPng= false;
			} else {
				ImageReader reader = iter.next();
				if (!(reader.getFormatName().equalsIgnoreCase("jpeg") || reader.getFormatName().equalsIgnoreCase("png") || reader.getFormatName().equalsIgnoreCase("jpg"))){
					jpgOrPng= false;
				}
			}
			iis.close();
		} catch (IOException e) {
			logger.error("Error occurred during validating image.It can be beacause the provided input is not a valid image",e);
		} 
		
		return jpgOrPng;
	}
	
	/**
	 * Create directory if not exist based on the provided folder path.THe newly created method will have permissions 
	 *    OWNER_READ,  OWNER_WRITE, OWNER_EXECUTE, GROUP_READ, GROUP_WRITE,  GROUP_EXECUTE
	 * @param thumbNailFolder
	 * @param imageFileName
	 * @param thumbNailLocation
	 * @return
	 */
	public static boolean  createDirectoryIfNotExist(Path thumbNailFolder, Path imageFileName,String thumbNailLocation){
		Path parent = Paths.get(thumbNailLocation);
		try {
			if (Files.exists(parent)) {
				if (Files.isReadable(parent) && Files.isWritable(parent)) {
					if (Files.notExists(thumbNailFolder)) {
						Files.createDirectory(thumbNailFolder);
						PortalUtils.setWebappsDirectoryOwnerAndPermissionsWithOutOthers(thumbNailFolder.toString());
					}
					return true;
				} else {
					logger.error("Location " + thumbNailLocation + " is not readable:" + Files.isReadable(parent)
							+ "writable:" + Files.isWritable(parent) + " for tomcatnp ");
				}
			} else {
				logger.error("Location " + thumbNailLocation + " does not exist");
			}
		} catch (Exception e) {
			logger.error("Error occurred while writeImageToFileSystem  for the path" + imageFileName, e);
		}
		return false;

	}
	/**
	 *  This will write image to the file system based on the provided path. If folder does not exist, it will create one.
	 * @param thumbNail
	 * @param thumbNailFolder
	 * @param imageFileName
	 */

	public static boolean writeImageOperationWithBackUp(String thumbNailLocation, byte[] thumbNail, Path thumbNailFolder, Path imageFileName,Path backUpFile) throws IOException {
		logger.debug("inside writeImageOperation with "+thumbNailFolder +" file name as "+imageFileName );

		if (validateThumbNailImage(thumbNail)) {			
			if (createDirectoryIfNotExist(thumbNailFolder, imageFileName,thumbNailLocation)) {
				backUpFile =takeBackUp(backUpFile, imageFileName);
				Files.write(imageFileName, thumbNail, StandardOpenOption.CREATE);
				return true;
			} else {
				throw new RuntimeException("Image cant be written to File System: unable to create directory ");
			}
		}
		return false;

	}
	
	/**
	 *  This will write image to the file system based on the provided path. If folder does not exist, it will create one.
	 * @param thumbNail
	 * @param thumbNailFolder
	 * @param imageFileName
	 */

	public static void writeImageOperation(String thumbNailLocation, byte[] thumbNail, Path thumbNailFolder,
			Path imageFileName) throws IOException {
		logger.debug("inside writeImageOperation with " + thumbNailFolder + " file name as " + imageFileName);

		if (validateThumbNailImage(thumbNail)) {
			if (Files.exists(imageFileName)) {
				byte[] fileInstheSys = Files.readAllBytes(imageFileName);
				if (!(fileInstheSys != null && ImageUtils.isBothFileSizeSame(fileInstheSys, thumbNail))) {
					writeImageToFileSystem(thumbNailLocation, thumbNail, thumbNailFolder, imageFileName);
				} else {
					logger.debug(
							"Skipping Image upload to File System, as file coming from ldap and file in the file system are same");
				}
			} else {
				writeImageToFileSystem(thumbNailLocation, thumbNail, thumbNailFolder, imageFileName);
			}
		}

	}

	private static void writeImageToFileSystem(String thumbNailLocation, byte[] thumbNail, Path thumbNailFolder,
			Path imageFileName) throws IOException {
		if (createDirectoryIfNotExist(thumbNailFolder, imageFileName,thumbNailLocation)) {
			Files.write(imageFileName, thumbNail);
		}else{
			throw new RuntimeException("Image cant be written to File System: unable to create directory ");
		}
	}

	/**
	 * This is to compare the size of two files. Since this method designed for comparing image files, chances of having two different thumbnails with same byte size will be less.
	 * For strict comparison ,compare based on checksum.
	 * @param fileInstheSys
	 * @param thumbNailImage
	 * @return
	 */
	public static boolean isBothFileSizeSame(byte[] fileInstheSys, byte[] thumbNailImage) {
	
		return fileInstheSys.length==thumbNailImage.length;
	}

	
	/**
	 * Checks if the image exists for the member
	 * @param thumbNailLocation
	 * @param tenantId
	 * @param memberID
	 * @return
	 */
	public static boolean checkIfThumbNailImageExists(String thumbNailLocation, int tenantId, int memberID) {
		
		
		Path imageFile = Paths.get(thumbNailLocation + "/" + tenantId + "/" +memberID);
	
		if (!Files.exists(imageFile)) {
			return false;
		}
		
		return true;
	}
	/**
	 * Checks if the image exists for the member
	 * @param thumbNailLocation
	 * @param tenantId
	 * @param memberID
	 * @return
	 * @throws IOException 
	 */
	public static void deleteImage(String thumbNailLocation, int tenantId, int memberID) throws IOException {
		
		
		Path imageFile = Paths.get(thumbNailLocation + "/" + tenantId + "/" +memberID);
	
		if (Files.exists(imageFile)) {
			Files.delete(imageFile);
		}
		
		
	}
	
	/**Checks if the image exists for the member.Need to provide exact path
	 * 
	 * @param imageFile
	 * @throws IOException
	 */
	public static void deleteImage(Path imageFile) throws IOException {
		if (Files.exists(imageFile)) {
			Files.delete(imageFile);
		}
		
		
	}
	
/**
 * delete the files and then remove the folder using java 7 or 8
 * @param directory
 */
	public static void deleteImageFolder(Path directory) {

		try {

			Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}

			});
		} catch (IOException e) {
			logger.error("unable to delete folder " + directory, e);
		}

	}

	public static void restoreBackUp(Path backUpFile, Path imageFile) throws IOException {
		if(backUpFile!=null){
			Files.move(backUpFile, imageFile, StandardCopyOption.REPLACE_EXISTING);
		}
	
}

	public static Path takeBackUp(Path backUpFile, Path imageFile) throws IOException {
		if(Files.exists(imageFile)){
		
					return Files.move(imageFile, backUpFile, StandardCopyOption.REPLACE_EXISTING);
		}
		return null;
	}

}
