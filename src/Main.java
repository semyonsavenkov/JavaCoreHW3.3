import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) {

        String directoryPath = "D:\\Games/test/";

        File savesDir = new File(directoryPath);
        if (savesDir.isDirectory()) {
            for (File item : savesDir.listFiles()) {
                if (item.getName().contains(".zip")) {
                    openZip(directoryPath, item.getName(),"D://Games/test/");
                }
            }

            System.out.println(openProgress("D://Games/test/"));
        }

    }

    public static void openZip(String traceToZip, String fileName, String traceToDirectory) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(traceToZip + fileName))) {
            ZipEntry entry;
            String name;
            while ((entry = zis.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fouts = new FileOutputStream(traceToDirectory + name);
                for (int c = zis.read(); c != -1; c = zis.read()) {
                    fouts.write(c);
                }
                fouts.flush();
                zis.closeEntry();
                fouts.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static GameProgress openProgress(String traceToFile) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(traceToFile + "save2.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameProgress;
    }

}
