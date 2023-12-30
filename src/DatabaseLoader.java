import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class DatabaseLoader {
    private final static String fileTypeDescryptor = "Xvideos Database File";
    private final static String fileTypeFilter = "csv";
    private final static String startingDirectory = ".";

    public static File returnCSVAsFileObject() {
        JFileChooser fileChooser = new JFileChooser(".");
        FileNameExtensionFilter fileExtensionFilter = new FileNameExtensionFilter(
                fileTypeDescryptor, fileTypeFilter);
        fileChooser.setFileFilter(fileExtensionFilter);
        Integer returnVal = fileChooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Filename is correctly loaded");
            return new File(String.valueOf(fileChooser.getSelectedFile()));
        }
        return null;
    }
    public static String returnTagsFromUser(String message){
        return JOptionPane.showInputDialog( message, "use vírgula para múltiplas palavras");
    }
}
