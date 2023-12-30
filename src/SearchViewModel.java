import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Arrays;

public class SearchViewModel {4
    446545656566
    private final File file;
    private final String fileTypeFilter = "csv";
    private String _positiveTitle, _positiveTitle2, _positiveTags, _positiveCategory, _negativeTitle, _negativeTags, _negativeCategory;

    SearchViewModel() {
        file = new File(".");
        checkForSearchFile();
    }

    public void checkForSearchFile() {
        if (Arrays.stream(file.list()).noneMatch(a -> a.contains("search.cfg"))) {
            System.out.println("Arquivo search.cfg não encontrado.\n Criando um...");
            createSearchFile();
            System.out.println("Feito.\n Abra o arquivo e adicione as tags necessárias para busca.");
        } else {
            readSearchFile();
        }
    }

    public File returnCVSAsFileObject() {
        JFileChooser fileChooser = new JFileChooser(".");
        String fileTypeDescryptor = "Xvideos Database File";
        FileNameExtensionFilter fileExtensionFilter = new FileNameExtensionFilter(fileTypeDescryptor, fileTypeFilter);
        fileChooser.setFileFilter(fileExtensionFilter);
        Integer returnVal = fileChooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Filename is correctly loaded");
            return new File(String.valueOf(fileChooser.getSelectedFile()));
        }
        return null;
    }

    private void createSearchFile() {
        try (FileWriter cfgFile = new FileWriter("search.cfg")) {
            cfgFile.write("Positive Title:\n" + "Positive Title2:\n" + "Positive Tag:\n" + "Positive Category:\n" + "Negative Title:\n" + "Negative Tag:n" + "Negative Category:n");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems Creating search.cfg");
        }
    }

    private void readSearchFile() {
        try (BufferedReader readFile = new BufferedReader(new FileReader("search.cfg"))) {
            if (readFile.ready()) {
                do {
                    System.out.println(readFile.readLine());
                } while (readFile.read() != -1);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
