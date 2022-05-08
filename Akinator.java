/********************************
 * ReadMe TEXT
 *
 *
 * Name: Adwait Pujari
 * Student ID: 7924422
 * Assignment 4
 ********************************/
import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class Akinator {

    public static void main(String[] args) {
        Akinator obj1 = new Akinator();
        obj1.helperMethod();
    }

    /**
     * Helper Method which creates an object of Questioner class and calls the respective methods
     */
    public void helperMethod() {
        Questioner obj = new Questioner();
        obj.readTree();
        int userAnswer = JOptionPane.showConfirmDialog(null, "Wanna play 20 Questions?Lemme guess what you are thinking of", "20 Questions", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        while (userAnswer == 0) {
            obj.playRound();
            userAnswer = JOptionPane.showConfirmDialog(null, "Wanna play again? I get better at guessing  by time", "20 Questions", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(userAnswer == 1) {
                int saveFile = JOptionPane.showConfirmDialog(null, "Do you wanna save the data", "Save progress", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(saveFile==0)
                    obj.writeTree();
            }
        }
    }

    /*
    Class which handles the entire assignment
     */
    class Questioner {
        public DTNode root;

        /**
         * Constructor of questioner class which hard codes a 20Question example
         */
        public Questioner() {
            root = new DTNode("Is it an animal?");

            /*
            Just to use the constructor DTNode(String item, DTNode left, DTNode right) the code is redundant
             */
            //Left Node
            root.left = new DTNode("");
            root.left.left = new DTNode("Human");
            root.left.right = new DTNode("Shark");
            root.left = new DTNode("Is it a mammal?", root.left.left, root.left.right);

            //Right node
            root.right = new DTNode("");
            root.right.left = new DTNode("Carrot");
            root.right.right = new DTNode("Diamond");
            root.right = new DTNode("Is is a plant?", root.right.left, root.right.right);

        }

        /**
         * Play Round method which ask users for input and display the corresponding answer for the hardcoded tree.
         */
        void playRound() {
            DTNode curr = root;
            int userAnswer = JOptionPane.showConfirmDialog(null, curr.item, "20 Questions", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            //If YES then userAnswer is 0
            //If NO then userAnswer is 1

            //If it is interior node then it should move left/right of the tree depending on users answer.
            while (!curr.isLeaf()) {
                if (userAnswer == 0)
                    curr = curr.left;
                else
                    curr = curr.right;
                userAnswer = JOptionPane.showConfirmDialog(null, curr.item, "20 Questions", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            }

            if (userAnswer == 0)
                JOptionPane.showMessageDialog(null, "I guessed it correctly");
            //If the code don't guess according to users choice then ask them for their answer and improve the BST
            else {
                JOptionPane.showMessageDialog(null, "Sorry i couldn't guess it properly.");
                String newAnswer = JOptionPane.showInputDialog("What were you thinking of?");
                String newQuestion = JOptionPane.showInputDialog("To improve provide a question which you think should be added to this 20Question play.");

                curr.right = new DTNode(curr.item);
                curr.left = new DTNode(newAnswer);
                curr.item = newQuestion;
            }
        }

        /**
         *Reads the data from a file and creates the corresponding Binary Tree.
         */
        public void readTree() {
            try {
                JFileChooser fileObject = new JFileChooser();
                fileObject.showOpenDialog(null);
                File file = fileObject.getSelectedFile(); //Btree readFile.txt

                FileReader fr = new FileReader(file);
                Scanner sc = new Scanner(fr);

                //Called the helper method which will recursively run through the code.
                root = recursiveHelperTree(sc);

            } catch (Exception e) {
                System.out.println("Sorry couldn't read the file");
                System.out.println(e.getMessage());
            }
        }

        /**
         * Recursive helper method of ReadTree
         * @param sc Scanner parameter. Which will keep track of each line in the file.
         * @return Return returns node , which links the Nodes to the root
         */
        private DTNode recursiveHelperTree(Scanner sc) {
            DTNode curr = root;
//            if (sc.hasNext("<")) {
                String line = sc.nextLine();   // To store the entire line.
                if (sc.hasNext("<")) {
                    curr.left = recursiveHelperTree(sc);
                    curr.right = recursiveHelperTree(sc);
                    curr = new DTNode(line.substring(1), curr.left, curr.right);
                }
//            }
            if (sc.hasNext(">")) {
                curr = new DTNode(line.substring(1));
            }
            return curr;
        }

        /**
         * Public method of writeTree, which writes the data of either hardcoded file and user defined file in a blank file.
         */
        void writeTree()
        {
            try {
                    JFileChooser fileObject = new JFileChooser();
                    fileObject.showSaveDialog(null);
                    File file = fileObject.getSelectedFile();
                    FileWriter fw = new FileWriter(file);

                    PrintWriter pw = new PrintWriter(fw);
                    //Calling recursive helper method of WriteTree.
                    recursiveWriteTree(pw,root);
                    pw.close();
            }
            catch(Exception e)
            {
                    System.out.println("Error writing the file");
                    System.out.println(e.getMessage());
            }
        }

        /**
         * Helper method of write tree, which writes in the file via pre- order traversal
         * @param pw PrinterWriter which writes the file.
         * @param curr The node passed which will traverse through the BST
         */
        void recursiveWriteTree(PrintWriter pw, DTNode curr) {
            String opening="<";
            String closing=">";
            String line="";

            if(curr.isLeaf()) {
                line =  line + opening + " "  + curr.item ;
            }
            else {
                line =  line +  opening + " "  + curr.item ;
            }
            pw.println(line);
            if(curr.left!=null)
                recursiveWriteTree(pw,curr.left);
            if(curr.right!=null)
                recursiveWriteTree(pw,curr.right);
            pw.println(closing);
        }
        /**
         * DTNode class which creates the structure of any binary tree
         */
        private class DTNode {
            //Instance variables
            public String item;
            public DTNode left;
            public DTNode right;

            /**
             * Single constructor of DTNode which is used to store value in curr
             * @param item The value at the current node
             */
            public DTNode(String item) {
                this.item = item;
                this.left = null;
                this.right = null;
            }

            /**
             * Constructor with three parameter to create t
             * @param item
             * @param left
             * @param right
             */
            public DTNode(String item, DTNode left, DTNode right) {
                this.item = item;
                this.left = left;
                this.right = right;
            }

            public boolean isLeaf() {
                boolean check = false;
                if (left == null && right == null)
                    check = true;
                return check;
            }

        } //DT class ends
    }
}


