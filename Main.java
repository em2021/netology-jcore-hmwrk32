import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void saveGame(String pathName, GameProgress save) {
        try (FileOutputStream fos = new FileOutputStream(pathName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(save);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String pathName, List<String> filesToZip) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathName));
        ) {
            for (String file : filesToZip) {
                File fileToZip = new File(file);
                FileInputStream fis = new FileInputStream(fileToZip);
                ZipEntry entry = new ZipEntry(fileToZip.getName());
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
            }
            System.out.println("Files zipped successfully");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        File fileToDelete;
        for (String file : filesToZip) {
            fileToDelete = new File(file);
            if (fileToDelete.delete())
                System.out.println(fileToDelete.getName() + " deleted successfully");
        }
    }

    public static void main(String[] args) {
        GameProgress save1 = new GameProgress(100, 3, 1, 348.00);
        GameProgress save2 = new GameProgress(94, 5, 2, 599.00);
        GameProgress save3 = new GameProgress(59, 7, 5, 1978.00);
        saveGame("/Users/egor_m/Games/savegames/save1.dat", save1);
        saveGame("/Users/egor_m/Games/savegames/save2.dat", save2);
        saveGame("/Users/egor_m/Games/savegames/save3.dat", save3);
        List<String> gameSaves = Arrays.asList("/Users/egor_m/Games/savegames/save1.dat",
                "/Users/egor_m/Games/savegames/save2.dat",
                "/Users/egor_m/Games/savegames/save3.dat");
        zipFiles("/Users/egor_m/Games/savegames/zip.zip", gameSaves);
    }
}