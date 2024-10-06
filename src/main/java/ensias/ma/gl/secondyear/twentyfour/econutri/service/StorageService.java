package ensias.ma.gl.secondyear.twentyfour.econutri.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.github.slugify.Slugify;

import ensias.ma.gl.secondyear.twentyfour.econutri.exception.StorageException;


@Component
public final class StorageService {
    

    private Slugify slugify;

    private String storageDirectoryName;

    private List<String> SUPPORTED_EXTENSIONS = List.of("jpg", "jpeg", "webp", "png");


    public StorageService(@Value("${storageLocation}") String storageDirectoryName, 
        Slugify slugify) {

        this.storageDirectoryName = storageDirectoryName;
        this.slugify = slugify;

        createStorageDirectory();
    }
    

    private void createStorageDirectory() {
        Path storageDirectoryRelativePath = Paths.get(storageDirectoryName);
        Path storageDirectoryAbsolutePath = storageDirectoryRelativePath.normalize().toAbsolutePath();
        try {
            if(!Files.exists(storageDirectoryAbsolutePath)) {
                Files.createDirectory(storageDirectoryAbsolutePath);
            }
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }


    public String saveFile(MultipartFile file) throws StorageException {
        String originalFilename = file.getOriginalFilename();
        int lastDotIndex = originalFilename.lastIndexOf(".");

        String extension = originalFilename.substring(lastDotIndex + 1);
        if(!SUPPORTED_EXTENSIONS.contains(extension)) {
            throw new StorageException("The file extension " + extension + " is not supported");
        }

        String basename = originalFilename.substring(0, lastDotIndex);
        String urlFriendlyOriginalFileName = slugify.slugify(basename) + "." + extension;

        Path storageDirectoryPath = Paths.get(storageDirectoryName).toAbsolutePath();
        try (InputStream inputStream = file.getInputStream()) {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());  
            File imageFile = File.createTempFile(timeStamp, urlFriendlyOriginalFileName, storageDirectoryPath.toFile());
            Files.copy(inputStream, imageFile.toPath().toAbsolutePath(), StandardCopyOption.REPLACE_EXISTING);
            return imageFile.getName();

        } catch(IOException exception) {
            exception.printStackTrace();
            throw new StorageException(exception.getCause().toString());
        }

    }


    public Path getFile(String filename) {
        return Paths.get(this.storageDirectoryName).resolve(filename);
    }
    
}
