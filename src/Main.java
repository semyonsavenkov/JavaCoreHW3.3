import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) {

        File savesDir = new File("Games/savegames");
        if (savesDir.isDirectory()) {

            for (File item : savesDir.listFiles()) {
                if (item.getName().contains(".zip")) {
                    openZip(savesDir.getAbsolutePath(), item.getAbsolutePath());
                }
            }

            for (File item : savesDir.listFiles()) {
                if (item.getName().contains(".dat")) {
                    openProgress(item.getAbsolutePath());
                }
            }
        }

    }

    public static void openZip(String directoryPath, String fileZipPath) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(fileZipPath))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName(); // получим название файла // распаковка
                FileOutputStream fout = new FileOutputStream(name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage()); }
    }

    public static void openProgress (String path) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(gameProgress);
    }

}
