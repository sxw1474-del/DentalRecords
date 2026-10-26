import java.util.Scanner;

/**
 * DentalRecords
 * A program to record and manage the dental records for a Florida family.
 * @author Sonan Wagari
 * @version 1.0
 */
public class Main {

    private static final int MAX_PEOPLE = 6;
    private static final int MAX_TEETH = 8;
    private static final int UPPER_ROW = 0;
    private static final int LOWER_ROW = 1;

    public static void main(String[] args) {
        Scanner scanner;
        int numPeople;
        String[] names;
        char[][][] teeth;
        char menuOption;
        int i;
        String uppers;
        String lowers;

        scanner = new Scanner(System.in);
        names = new String[MAX_PEOPLE];
        teeth = new char[MAX_PEOPLE][2][MAX_TEETH];
        menuOption = ' ';

        System.out.println("Welcome to the Floridian Tooth Records");
        System.out.println("--------------------------------------");

        numPeople = getNumPeople(scanner);

        for (i = 0; i < numPeople; i++) {
            System.out.print("Please enter the name for family member " + (i + 1) + "   : ");
            names[i] = scanner.next();

            uppers = getValidTeeth(scanner, names[i], "uppers");
            teethRowCreate(teeth, i, UPPER_ROW, uppers);

            lowers = getValidTeeth(scanner, names[i], "lowers");
            teethRowCreate(teeth, i, LOWER_ROW, lowers);
        }

        while (menuOption != 'X') {
            menuOption = getMenuOption(scanner);

            if (menuOption == 'P') {
                printRecords(names, teeth, numPeople);
            } else if (menuOption == 'E') {
                getTooth(scanner, names, teeth, numPeople);
            } else if (menuOption == 'R') {
                calculateRoots(teeth, numPeople);
            } else if (menuOption == 'X') {
                System.out.println("\nExiting the Floridian Tooth Records :-)");
            }
        }
    } // End of main

    /**
     * Asks the user for a valid number of family members.
     * @param scanner the Scanner object for input
     * @return a valid integer between 1 and MAX_PEOPLE
     */
    public static int getNumPeople(Scanner scanner) {
        int num;
        boolean isValid;

        isValid = false;
        num = 0;

        System.out.print("Please enter number of people in the family : ");
        while (!isValid) {
            num = scanner.nextInt();
            if (num > 0 && num <= MAX_PEOPLE) {
                isValid = true;
            } else {
                System.out.print("Invalid number of people, try again         : ");
            }
        }
        return num;
    } // end of getValidNumPeople

    /**
     * Asks the user for a valid number of teeth.
     *
     * @param scanner The input
     * @param name    the name of the family member
     * @param layer   the layer of teeth ("uppers" or "lowers")
     * @return a valid uppercase string of teeth types
     */
    public static String getValidTeeth(Scanner scanner, String name, String layer) {
        String input;
        boolean isValid;
        int i;
        char tooth;
        boolean hasInvalidChar;

        input = "";
        isValid = false;

        System.out.print("Please enter the " + layer + " for " + name + "       : ");
        while (!isValid) {
            input = scanner.next().toUpperCase();

            if (input.length() > MAX_TEETH) {
                System.out.print("Too many teeth, try again                   : ");
            } else {
                hasInvalidChar = false;
                for (i = 0; i < input.length(); i++) {
                    tooth = input.charAt(i);
                    if (tooth != 'I' && tooth != 'B' && tooth != 'M') {
                        hasInvalidChar = true;
                    }
                }

                if (hasInvalidChar) {
                    System.out.print("Invalid teeth types, try again              : ");
                } else {
                    isValid = true;
                }
            }
        }
        return input;
    } // End of getValidTeeth

    /**
     * Populates a specific row in the 3D array with teeth characters.
     *
     * @param teeth the 3D array of teeth
     * @param personIndex the index of the family member
     * @param rowIndex the row index
     * @param teethStr the string of teeth
     */
    public static void teethRowCreate(char[][][] teeth, int personIndex, int rowIndex, String teethStr) {
        int i;

        for (i = 0; i < teethStr.length(); i++) {
            teeth[personIndex][rowIndex][i] = teethStr.charAt(i);
        }
    } // end of populateTeethRow

    /**
     * Asks the user for a valid menu option.
     * @param scanner the Scanner object for input
     * @return a valid uppercase menu character
     */
    public static char getMenuOption(Scanner scanner) {
        String input;
        char option;
        boolean isValid;

        isValid = false;
        option = ' ';

        while (!isValid) {
            System.out.print("\n(P)rint, (E)xtract, (R)oot, e(X)it          : ");
            input = scanner.next().toUpperCase();

            if (input.length() == 1) {
                option = input.charAt(0);
                if (option == 'P' || option == 'E' || option == 'R' || option == 'X') {
                    isValid = true;
                } else {
                    System.out.print("Invalid menu option, try again              : ");
                }
            } else {
                System.out.print("Invalid menu option, try again              : ");
            }
        }
        return option;
    } // End of getValidMenuOption

    /**
     * Prints the complete dental records for all family members.
     *
     * @param names     the array of names
     * @param teeth     the 3D array of teeth
     * @param numPeople the number of family members
     */
    public static void printRecords(String[] names, char[][][] teeth, int numPeople) {
        int i;
        int j;

        System.out.println();
        for (i = 0; i < numPeople; i++) {
            System.out.println(names[i]);

            System.out.print("  Uppers:  ");
            for (j = 0; j < MAX_TEETH; j++) {
                if (teeth[i][UPPER_ROW][j] != '\0') {
                    System.out.print((j + 1) + ":" + teeth[i][UPPER_ROW][j] + "  ");
                }
            }
            System.out.println();

            System.out.print("  Lowers:  ");
            for (j = 0; j < MAX_TEETH; j++) {
                if (teeth[i][LOWER_ROW][j] != '\0') {
                    System.out.print((j + 1) + ":" + teeth[i][LOWER_ROW][j] + "  ");
                }
            }
            System.out.println();
        }
    } // End of printRecords

    /**
     * Does the extraction of a tooth from a family member.
     *
     * @param scanner   the Scanner object for input
     * @param names     the array of names
     * @param teeth     the 3D array of teeth
     * @param numPeople the number of family members
     */
    public static void getTooth(Scanner scanner, String[] names, char[][][] teeth, int numPeople) {
        String targetName;
        int personIndex;
        int i;
        boolean found;
        char layer;
        int rowIndex;
        int toothNum;
        boolean isValidTooth;

        personIndex = -1;
        found = false;
        isValidTooth = false;
        rowIndex = 0;
        toothNum = 0;

        System.out.print("Which family member                         : ");
        while (!found) {
            targetName = scanner.next();
            for (i = 0; i < numPeople; i++) {
                if (names[i].equalsIgnoreCase(targetName)) {
                    personIndex = i;
                    found = true;
                }
            }
            if (!found) {
                System.out.print("Invalid family member, try again            : ");
            }
        }

        System.out.print("Which tooth layer (U)pper or (L)ower        : ");
        while (true) {
            layer = scanner.next().toUpperCase().charAt(0);
            if (layer == 'U') {
                rowIndex = UPPER_ROW;
                break;
            } else if (layer == 'L') {
                rowIndex = LOWER_ROW;
                break;
            } else {
                System.out.print("Invalid layer, try again                    : ");
            }
        }

        System.out.print("Which tooth number                          : ");
        while (!isValidTooth) {
            toothNum = scanner.nextInt();
            if (toothNum < 1 || toothNum > MAX_TEETH || teeth[personIndex][rowIndex][toothNum - 1] == '\0') {
                System.out.print("Invalid tooth number, try again             : ");
            } else if (teeth[personIndex][rowIndex][toothNum - 1] == 'M') {
                System.out.print("Missing tooth, try again                    : ");
            } else {
                teeth[personIndex][rowIndex][toothNum - 1] = 'M';
                isValidTooth = true;
            }
        }
    } // End of extractTooth

    /**
     * Calculates and prints the root canal for the family.
     *
     * @param teeth     the 3D array of teeth
     * @param numPeople the number of family members
     */
    public static void calculateRoots(char[][][] teeth, int numPeople) {
        int iCount;
        int bCount;
        int mCount;
        int i;
        int j;
        int k;
        double discriminant;
        double root1;
        double root2;

        iCount = 0;
        bCount = 0;
        mCount = 0;

        for (i = 0; i < numPeople; i++) {
            for (j = 0; j < 2; j++) {
                for (k = 0; k < MAX_TEETH; k++) {
                    if (teeth[i][j][k] == 'I') {
                        iCount++;
                    } else if (teeth[i][j][k] == 'B') {
                        bCount++;
                    } else if (teeth[i][j][k] == 'M') {
                        mCount++;
                    }
                }
            }
        }

        discriminant = (bCount * bCount) - (4 * iCount * -mCount);

        if (iCount == 0) {
            System.out.println("Cannot calculate roots (no incisors found).");
        } else if (discriminant < 0) {
            System.out.println("No real roots found.");
        } else {
            root1 = (-bCount + Math.sqrt(discriminant)) / (2 * iCount);
            root2 = (-bCount - Math.sqrt(discriminant)) / (2 * iCount);

            System.out.printf("One root canal at     %5.2f\n", root1);
            System.out.printf("Another root canal at %5.2f\n", root2);
        }
    } // end of calculateRoots
} // end of DentalRecords