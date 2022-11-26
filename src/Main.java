import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {

        GameProgress game1 = new GameProgress(50, 1, 3, 20.0);
        GameProgress game2 = new GameProgress(100, 5, 1, 5.0);
        GameProgress game3 = new GameProgress(99, 2, 10, 7.0);

        final List<String> srcFiles = Arrays.asList("D://GamesNet/savegames/save1.dat",
                "D://GamesNet/savegames/save2.dat",  "D://GamesNet/savegames/save3.dat");

        saveGame("D://GamesNet/savegames/save1.dat", game1);
        saveGame("D://GamesNet/savegames/save2.dat", game2);
        saveGame("D://GamesNet/savegames/save3.dat", game3);

        try {
            zipFiles("D://GamesNet/savegames/gameszip.zip", srcFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void saveGame(String absolutePath, GameProgress game) {
        try (FileOutputStream fos = new FileOutputStream(absolutePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(game);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        }
    }

    static void zipFiles(String pathToZip, List<String> srcFiles) throws IOException {
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(pathToZip)))
        {
        for (String srcFile : srcFiles) {
            File fileToZip = new File(srcFile);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
            if (fileToZip.delete()){
                System.out.println("Файл " + fileToZip.getName() + " вне архива удален");
            } else {
                System.out.println("Файл " + fileToZip.getName() + " вне архива не удален");
            }
        }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}