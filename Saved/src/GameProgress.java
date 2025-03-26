import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    private int health;
    private int weapons;
    private int lvl;
    private double distance;

    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }

    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
            System.out.println("Сохранение игры в файл: " + filePath);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении игры: " + e.getMessage());
        }
    }

    public static void zipFiles(String zipFilePath, List<String> filesToZip) {
        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (String filePath : filesToZip) {
                try (FileInputStream fis = new FileInputStream(filePath)) {
                    ZipEntry zipEntry = new ZipEntry(new File(filePath).getName());
                    zos.putNextEntry(zipEntry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) >= 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                    System.out.println("Добавлен файл в архив: " + filePath);
                } catch (IOException e) {
                    System.out.println("Ошибка при добавлении файла в архив: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при создании ZIP-архива: " + e.getMessage());
        }
    }

    public static void deleteFiles(List<String> filesToDelete) {
        for (String filePath : filesToDelete) {
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println("Удален файл: " + filePath);
            } else {
                System.out.println("Не удалось удалить файл: " + filePath);
            }
        }
    }

    public static void main(String[] args) {
        // Создание экземпляров GameProgress
        GameProgress game1 = new GameProgress(100, 5, 1, 10.0);
        GameProgress game2 = new GameProgress(80, 3, 2, 20.5);
        GameProgress game3 = new GameProgress(50, 1, 3, 30.2);

        // Путь к папке savegames
        String saveGamesPath = "D://Games/savegames/";
        List<String> savedFiles = new ArrayList<>();

        // Сохранение игр
        saveGame(saveGamesPath + "save1.dat", game1);
        savedFiles.add(saveGamesPath + "save1.dat");
        saveGame(saveGamesPath + "save2.dat", game2);
        savedFiles.add(saveGamesPath + "save2.dat");
        saveGame(saveGamesPath + "save3.dat", game3);
        savedFiles.add(saveGamesPath + "save3.dat");

        // Упаковка файлов в ZIP
        String zipFilePath = saveGamesPath + "games.zip";
        zipFiles(zipFilePath, savedFiles);

        // Удаление исходных файлов
        deleteFiles(savedFiles);
    }
}
