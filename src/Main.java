import java.io.*;
import java.util.Scanner;
//-----------------------------------------------------
//Assignment: 3
//Written by: (Alexander Smagorinski (ID: 40190986) && Zakaria El Manar (ID: 40190432)
//----------------------------------------------------- 

/**
 * This is the driver's class that will contains all the methods to try and convert
 * a file of type CSV to type tex. In order to do so, this class will utilize different
 * methods within itself to convert a file of type CSV to type tex
 * @author Alexander Smagorinski
 * @author Zakaria El Manar
 */
public class Main {

	/**
	* Main method that will ask the user to enter a number of files to work with and their names.
	* It will then try to convert the files into latex format while using using the different methods
	* within this class.
	* @param args N/A
	*/
    public static void main(String[] args) {

        welcome();
        Scanner in = new Scanner(System.in);
        System.out.print("\n\nHow many files do you want to convert: ");
        int numOfFiles = in.nextInt();
        in.nextLine();

        File[] csvFiles = new File[numOfFiles];
        String[][][] allFilesData = new String[numOfFiles][][];

        for (int i = 0; i < numOfFiles; i++) {
            System.out.print("Enter file #" + (i+1) + " name: ");
            String fileName = in.nextLine();
            System.out.println(fileName + "\n");
            csvFiles[i] = new File("C:\\Users\\ZackE\\Desktop\\COMP 249-F21-A3-Files\\" + fileName + ".csv");
            allFilesData[i] = reader(csvFiles[i]);
        }

        processFileForValidation(csvFiles, allFilesData);

        boolean isValid = true;

        while(isValid){

            System.out.print("\nAll files have been processed, do you want to open a LaTex file? (y/n): ");
            String choice = in.nextLine();

            if(choice.equalsIgnoreCase("y")){
                System.out.print("Which file do you want to read?: ");
                String fileName = in.nextLine();
                System.out.println("\n\n");
                    texReader(fileName);
            } else {
                System.out.println("Thank you for using our converter!");
                isValid = false;
            }

        }
        in.close();
        System.exit(0);
    }

    // ================================ FILE VALIDATION PROCESS =================================

    /**
	* Method that will process the CSV input files and output the tex and necessary log files
	* @param csvFiles File type array (2D) containing all the CSV files created
	* @param allFilesData String type array (3D) containing the CSV files as an array, the rows and cols of each file
	*/
    public static void processFileForValidation(File[] csvFiles, String[][][] allFilesData){

        for (int i = 0; i < csvFiles.length; i++){
            if(logWriter(allFilesData[i], csvFiles[i])){
                texWriter(allFilesData[i], csvFiles[i]);
            }
        }
    }

    // ================================ LOG FILE WRITER =================================
    
    /**
	* Method that will write to a log file the missing elements of the CSV file that is being read
	* @param datas String type array (2D) containing the datas of the CSV file that we are working with
	* @param file File type which represents the file that is being read from
	* @return Boolean which will return true if nothing is written in the log file and false if something is written in the log file
	*/
    public static boolean logWriter(String [][] datas, File file){
        PrintWriter pw = null;
        File logFile = new File(file.toString().replaceAll("CSV.csv", "LOG.txt"));

        try{
            pw = new PrintWriter(logFile);

            if(datas[0][0].isBlank()){  //test 1
                throw new InvalidException("File " + file.getName() + " is invalid: title missing");
            }

            for(int i = 0; i < datas[1].length; i++){ //test 2
                if(datas[1][i].isBlank()){
                    throw new CSVFileInvalidException("File " + file.getName()
                                                      + " is invalid: attribute is missing.");
                }
            }

            for(int i = 2; i < datas.length; i++){ //test 3

                for(int j = 0; j < datas[i].length; j++){

                    if(datas[i][j].isBlank()){
                        throw new CSVDataMissing("In file " + file.getName() +
                                                 " line " + i + " not converted to LATEX: missing data.");
                    }
                }
            }

        } catch (InvalidException IE) {
            System.out.println("A problem occured: " + IE);
            pw.println(IE + "\nFile " + file.getName() + " is invalid: title missing.\nFile not" +
                       "converted to LATEX.");
            pw.close();
            return false;

        } catch (CSVFileInvalidException CSVFIE) {
            System.out.println("A problem occured: " + CSVFIE);
            pw.println("File " + file.getName() + " is invalid: attribute is missing. " +
                       "File not converted to LATEX");
            for(int i = 0; i < datas[1].length; i++){

                if(datas[1][i].isBlank()){
                    pw.print("***,");
                } else {
                    pw.print(datas[1][i] + ',');
                }
            }
            pw.println("\nFile is not converted to LATEX.");
            pw.close();
            return false;

        } catch(CSVDataMissing CSVDM){
            System.out.println("A problem occured: " + CSVDM);
            for(int i = 2; i < datas.length; i++){

                for(int j = 0; j < datas[i].length; j++){

                    if(datas[i][j].isBlank()){
                        pw.println("In file " + file.getName() +
                                   " line " + i + " not converted to LATEX: missing data.");
                        pw.println(file.getName() + " line " + i + '.');

                        for(int k = 0; k < datas[i].length; k++){

                            if(datas[i][k].isBlank()){
                                pw.print("***,");
                            } else {
                                pw.print(datas[i][k] + ',');
                            }
                        }
                        pw.println("\nMissing: " + datas[1][j] + "\n");
                    }
                }
            }
            pw.close();
            return true;

        } catch (FileNotFoundException e) {
            System.out.println("A problem occured: " + e);
            e.printStackTrace();
            return false;
        }

        pw.close();
        return logFile.delete();
    }

    
    // ================================ LATEX FILE WRITER =================================

    /**
  	* Method that will create a txt file which will output the tex format of the CSV file
  	* @param datas String type array (2D) containing the datas of the CSV file that we are working with
  	* @param file File type which represents the file being converted
  	*/
    public static void texWriter(String[][] datas, File file){
        PrintWriter pw;
        int count = 0;
        String col = "1|";
        String header = "\\documentclass{article}\n\\usepackage[utf8]{inputenc}\n\\usepackage{booktabs}" +
                        "\n\n\\title{comp249_assignment_3}\n\\author{Zakaria El Manar, Alexander Smagorinski}\n" +
                        "\\date{" + java.time.LocalDate.now() + '}' + "\n\n\\begin{document}\n\n\\begin{table}[]";
        String prefix = "\n\\begin{tabular}{|";

        try{
            File texFile = new File(file.toString().replaceAll("CSV.csv", "LATEX.tex"));
            pw = new PrintWriter(texFile);
            pw.println(header + prefix + col.repeat(datas[1].length) + '}');
            pw.println("\t\\toprule");

            for(int i = 1; i < datas.length; i++){

                for (int j = 0; j < datas[i].length; j++){

                    if(datas[i][j].isBlank()){
                        count++;
                    }
                }
                if(count > 0){
                    count = 0;
                    continue;

                } else {
                    pw.print("\t    ");
                    for (int j = 0; j < datas[i].length; j++){

                        if(j == datas[i].length-1){
                            pw.print(datas[i][j] + " \\\\ \\midrule ");
                        } else {
                            pw.print(datas[i][j] + " & ");
                        }
                    }
                    pw.println(" ");
                }
                count = 0;
            }
            pw.println("\t\\end{tabular}");
            pw.println("\t\\caption{" + datas[0][0] + '}');
            pw.println("\t\\label{" + file.getName().replaceAll("CSV.csv", "")+ '}');
            pw.println("\\end{table}");
            pw.println("\n\\end{document}");
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // ================================ CSV FILE READER =================================

    /**
  	* Method that will read a CSV file and output the contents of this file
  	* @param file File type which represents the CSV file that is being read from
  	* @return String array (2D) containing the elements of the CSV file
  	*/
    public static String[][] reader(File file) {
        String[][] datas = null;
        try{
            Scanner sc = new Scanner(file);
            int rows = 0; int indexLine = 0; String line;

            while(sc.hasNextLine()){
                line = sc.nextLine();
                rows++;
            }
            datas = new String[rows][];
            sc = new Scanner(file);

            while(sc.hasNextLine()){
                line = sc.nextLine();
                datas[indexLine] = line.split(",");
                indexLine++;
            }
            sc.close();
        }catch (FileNotFoundException e){
            System.out.println("Could not open file " + file.getName() + " for reading\n" +
                               "Please check if file exists! Program will terminate after closing all files.");
            System.exit(0);
        }
        return datas;
    }

    // ================================ TEX FILE READER =================================
    
    /**
  	* Method that will read a CSV file and output the contents of the new tex file that was created
  	* @param fileName String type which represents the name of the tex file that will be read from
  	*/
    public static void texReader(String fileName){

        try{
            BufferedReader br = new BufferedReader(
                    new FileReader("C:\\Users\\ZackE\\Desktop\\COMP 249-F21-A3-Files\\" + fileName + ".tex"));

            String str;
            while((str = br.readLine()) != null){
                System.out.println(str);
            }
            br.close();
        }catch (FileNotFoundException e){

            System.out.println("Could not open  the file for reading\n" +
                               "Please check if file exists! Program will terminate after (1) attempt.");

        } catch (IOException e) {
            System.out.println("Error: Input row cannot be parsed due to missing information");
        }
    }

    // ================================ WELCOME MESSAGE =================================
    /**
  	* Method that will welcome the user to the program
  	*/
    public static void welcome(){
        for(int i = 0; i < 55; i++){
            System.out.print("=");
        }
        System.out.println("\n\tWelcome to CSV to latex by Zak and Alex");

        for(int i = 0; i < 55; i++){
            System.out.print("=");
        }
    }

}
