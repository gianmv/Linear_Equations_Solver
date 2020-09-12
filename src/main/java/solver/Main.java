package solver;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        String inputFilePath = "";
        String outFilePath = "";
        //Look for the parameters -in and -out and its values
        for (int i = 0; i < args.length -1; i++) {
            if ("-in".equals(args[i])) {
                inputFilePath = args[i + 1];
                i++;
            }

            if ("-out".equals(args[i])) {
                outFilePath = args[i + 1];
                i++;
            }
        }

        Matrix mat = getMatrix(inputFilePath);
        Matrix solved = mat.solveGaussJordan();
        String ans = solved.solutions();
        //If it is ok, the linear system has solution
        if (ans.equals("it is ok")) {
            Row sol = solved.extendedValues();
            System.out.print("The solution is: (");
            try (PrintWriter printWriter = new PrintWriter(new File(outFilePath))) {
                for (int i = 0; i < solved.getNumIncognites(); i++) {
                    printWriter.println(sol.getElement(i));
                    System.out.print(sol.getElement(i) + (i == solved.getNumIncognites() - 1 ? ")\n" : ", "));
                }
            }
            System.out.println("Saved to file " + outFilePath);
        //Maybe it has not solution or has infinitely solutions
        } else {
            try (PrintWriter printWriter = new PrintWriter(new File(outFilePath))) {
                printWriter.println(ans);
            }
            System.out.println(ans);
            System.out.println("Saved to file " + outFilePath);
        }
    }

    /**
     * This function reads from the Standard Input and try to generate
     * a matrix
     * @return a Matrix.
     */

    private static Matrix getMatrix(String Filepath) throws IOException {
        /* We choose a list because we don't know the
         * matrix size beforehand, and the list can be
         * filled dynamically.
         */
        List<LinearEquation> equations = new ArrayList<>();
        File in = new File(Filepath);
        try (Scanner reader = new Scanner(in)){
            int numIncog = reader.nextInt();
            int numEquat = reader.nextInt();
            //Debug line
            System.out.printf("%d %d\n",numEquat,numIncog);
            //Necessary because nextInt() left an \n
            reader.nextLine();
            while (reader.hasNext()) {
                String equationRep = reader.nextLine();
                System.out.println(equationRep);
                //Create a lis of equations, equations know
                //how to format the data
                equations.add(new LinearEquation(equationRep));
            }
        }

        //Convert from LinearEquation, because Matrix need
        // Row objects to be created
        List<Row> temp = new ArrayList<>();
        for (LinearEquation x: equations) {
            temp.add(new Row(x));
        }

        return new Matrix(temp);

    }
}