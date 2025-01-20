package handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Persistence;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import domain.Picture;
import main.Main;
import persist.ApplicationException;

import persist.PictureDM;
import utils.Thumbnails;

/**
 * 
 * @author manuelbarral
 *
 */
public class PictureHandler {	
	/**
	 * 
	 * @param importFilename, String.
	 * @return Picture
	 * @throws ApplicationException
	 * 
	 * O método trata de:
	 * copiar a fotografia para a pasta correta, e
	 * inserir a fotografia na base de dados.
	 */
	public static void importPictures() throws ApplicationException{
		String origem = main.ApplicationSettings.importFolder;
		String destino = main.ApplicationSettings.managedImagesFolder;
		File dirDestino = new File(destino);
		File dirOrigem = new File(origem);
		
		if(!dirDestino.exists()) {
			dirDestino.mkdir();
		}
		
		if(!dirOrigem.exists()) {
			System.out.println("ERRO: Diretoria de Origem Não Existe!");
		}
		
		ArrayList<String> datas = new ArrayList<>();
		
		try {
			Stream<Path> pathStream = 
					Files.find(Paths.get(origem),
							999,
							(p, bfa) -> bfa.isRegularFile() 
							&& (p.toString().endsWith("jpg") 
							|| p.toString().endsWith("JPG")));
			
			//Lista com todas as photos na pasta origem:
			List<Path> pathList = pathStream.collect(Collectors.toList());
			
			//Iterar sobre pathList e extrair,ler e SORT! na pasta destino.
			for (Path p : pathList) {
				String pString = p.toString();
				try {
					//ler dirs na dirDestino:
					File[] dirs = dirDestino.listFiles();
					ArrayList<File> dirsAL = new ArrayList<>();
					//Converter dirs em ArrayList cm o .asList():
					if (dirs != null) {
						dirsAL = new ArrayList<>(Arrays.asList(dirs));
					} 
					
					Metadata exif = readExifMetadata(pString); 	//ler Exif
					String dateExif = extractDate(exif);		//Extrair Data
					
					//check date == Null
					if (dateExif == null) {
						//check if dir noExif exists:
						File noExif = new File(destino + "/noExif");
						if (!dirsAL.contains(noExif)) {
							//criar diretoria noExif
							noExif.mkdir();
						} 
						//Guardar a file na dir noExif:
						File fileOg = new File(p.toString());
						//nova File:
						copyFile(fileOg, noExif);
												
					//Date != Null:
					} else {
						//check if Year Dir exists:
						File yearDir = new File(destino + "/" + dateExif.split(":")[0]);
						if (!yearDir.exists()) {
							yearDir.mkdir();
						}
						File monthDir = new File(destino + "/" + dateExif.split(":")[0] + "/" + dateExif.split(":")[1]);
						if (!monthDir.exists()) {
							monthDir.mkdir();
						}
						File dayDir = new File(destino + "/" + dateExif.split(":")[0] + "/" + dateExif.split(":")[1]
											+'/'+ dateExif.split(":")[2]);
						if (!dayDir.exists()) {
							dayDir.mkdir();
						}
						File fileOg = new File(p.toString());
						copyFile(fileOg, dayDir);
						
						//Com a foto guardada vamos inicializa-la nas 2 BDS:
						PictureDM picturedm = PictureDM.getInstance();
						Main.emf = Persistence.createEntityManagerFactory("JPAMemoriesTest");
						
						System.out.println("Vamos guardar a foto com a dir: " + dayDir.getPath()+ "/" + fileOg.getName()
						+ "na BD: JPAMemoriesTest" );
						
						Picture pic = picturedm.makePicture(dayDir.getPath() + "/" + fileOg.getName());
						
						//Outra BD:
						Main.emf = Persistence.createEntityManagerFactory("JPAMemories");
						System.out.println("Vamos guardar a foto com a dir: " + dayDir.getPath()+ "/" + fileOg.getName()
						+ "na BD: JPAMemories" );
						Picture pic2 = picturedm.makePicture(dayDir.getPath() + "/" + fileOg.getName());
						
						
						Thumbnails.makeThumbnail(pic);
					}
				} catch (ApplicationException e) {
					System.out.println("Exception ao extrair metadata exif!");
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Picture importPicture (String importFileName) {
		return null;
	}
	
	//Copiar uma File:
	private static void copyFile (File origem, File destino) throws IOException {
		
		Path origemPath = Paths.get(origem.getAbsolutePath());
		Path destinoPath = Paths.get(destino.getAbsolutePath());
		if (!destino.exists()) {
			destino.mkdir();
		}
		//se destino for diretoria:
		if (destino.isDirectory()) {
			destinoPath = destinoPath.resolve(origem.getName());
		}
		
		Files.copy(origemPath, destinoPath);
	}
	
	//Read ExifMetadata
	private static Metadata readExifMetadata(String filename) throws ApplicationException {
		try {
			return ImageMetadataReader.readMetadata(new File(filename));
			
			} catch (ImageProcessingException ex) {
				// the file contains no exif data.
				return null;
			} catch (IOException ex) {
				throw new ApplicationException("IO Error reading " + filename + " " + ex.getMessage());
		}
	}
	
	//Extract Date from MetadataExif
	private static String extractDate(Metadata metadata) {
		if (metadata == null) {
			return null;
		}
		Directory exifSubIFDDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
		if (exifSubIFDDirectory != null) {
			String date = exifSubIFDDirectory.getString(
			ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
			if (date != null) {
				System.out.println("DateTimeOriginal: " + date.split(" ")[0]);
				return date.split(" ")[0];
			} else {
				System.out.println("Exif data not found in the image.");
				return null;
			}
			
		} else {
			System.out.println("Exif data not found in the image.");
			return null;
		}
	}
	
	public static void initBD () throws ApplicationException {
		File fotosmanaged = new File("fotosManaged");
		if (fotosmanaged.exists()){
			deleteDirectoryRecursively(fotosmanaged);
		}
		importPictures();
	}
	
	// Recursive method to delete a directory and its contents
	private static void deleteDirectoryRecursively(File directory) {
	    if (directory.isDirectory()) {
	        // List all files and subdirectories
	        File[] files = directory.listFiles();
	        if (files != null) { // Avoid NullPointerException
	            for (File file : files) {
	                deleteDirectoryRecursively(file); // Recursive call
	            }
	        }
	    }
	    // Delete the empty directory or file
	    directory.delete();
	}
	
	
	
}