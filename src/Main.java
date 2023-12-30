import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        SearchViewModel svmodel = new SearchViewModel();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat fmt = new SimpleDateFormat("ddMMyy_HH-mm-ss");
        String fileDate = fmt.format(date);
        String writeFile = String.format("Links_%s.txt", fileDate);
        ArrayList<String> linksList = new ArrayList<>();
        boolean willWrite = false; //Alterar

        try (BufferedReader fileReader = new BufferedReader(new FileReader(Objects.requireNonNull(svmodel.returnCVSAsFileObject())))) {
            Integer count = 0;
            String positiveTitleSearch = DatabaseLoader.returnTagsFromUser("Adicione termos para o Título");
            String positiveTitleSearch2 = DatabaseLoader.returnTagsFromUser("Adicone tags para o Título 2");
            String positiveTagSearch = DatabaseLoader.returnTagsFromUser("Adicione termos para as Tags");
            String positiveCategorySearch = DatabaseLoader.returnTagsFromUser("Adicione termos para a Categoria");
            String negativeTagSearch = DatabaseLoader.returnTagsFromUser("Adicione Tags a serem negadas");
            String negativeCategorySearch = DatabaseLoader.returnTagsFromUser("Adicione Categorias a serem negadas"); //alumna new tag to explore
            String negativeTitleSearch = DatabaseLoader.returnTagsFromUser("Adicione Títulos a serem negados");

            while (fileReader.read() != -1) {
                String[] line = fileReader.readLine().split(";");
                // Algumas colunas não possuem o valor length = 10 e criam um ArrayIndexOutOfBoundsException

                if (line.length == 10) {
                    boolean negativeTag = Filter.byListRegex(negativeTagSearch, line, Field.TAGS);
                    boolean negativeCategory = Filter.byListRegex(negativeCategorySearch, line, Field.CATEGORY);
                    boolean positiveTitle = Filter.byListRegex(positiveTitleSearch, line, Field.TITLE);
                    boolean negativeTitle = Filter.byListRegex(negativeTitleSearch, line, Field.TITLE);
                    boolean positiveCategory = Filter.byListRegex(positiveCategorySearch, line, Field.CATEGORY);
                    boolean positiveTag = Filter.byListRegex(positiveTagSearch, line, Field.TAGS);
                    boolean positiveTitle2 = Filter.byListRegex(positiveTitleSearch2, line, Field.TITLE);

                    if (positiveTitle
                            //&& positiveTitle2
                            && !negativeTitle
                            && !negativeCategory
                            && !negativeTag) {
                        count++;
                        //Arrays.stream(line).forEach(System.out::println);
                        linksList.add(String.format("Vídeo %s \n Link = h%s \n Título = %s \n Tags = %s \n Categoria = %s%n \n\n", count, line[0], line[1], line[5], line[8]));
                        //linksList.add(String.format("h%s", line[Field.LINK.ordinal()]));
                    }
                } else {
                    System.out.println(Arrays.toString(line));
                }
            }
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        if (willWrite) {
            try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(writeFile))) {
                if (linksList.isEmpty()) {
                    return;
                } else {
                    for (String links : linksList) {
                        fileWriter.write(links);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        linksList.forEach(System.out::println);
        System.out.println("Total result = " + linksList.size());
    }
}