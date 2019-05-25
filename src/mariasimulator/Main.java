package mariasimulator;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 *
 * @author rcbgalido
 */
public class Main extends javax.swing.JFrame {

    int variablesCounter;
    String[] variablesArray, variableLocationsArray;
    boolean errorFlag, haltFlag, returnToFirstPassFlag;
    
    public Main() throws IOException {
        initComponents();
        setLocationRelativeTo(null);
        initializeAddressTable();
    }
    
    private void initializeAddressTable() {
        for (int a = 1; a <= 10; a++) {
            for (int b = 1; b <= 16; b++) {
                addressTable.setValueAt("0000", a, b);
            }
        }
        addressTable.setValueAt("000", 1, 0);
        for (int c = 2; c <= 10; c++) {
            addressTable.setValueAt("0" + ((c - 1) * 10), c, 0);
        }
    }

    public void initializeRegisters() {
        ACtextfield.setText("0000");
        MARtextfield.setText("000");
        MBRtextfield.setText("0000");
        IRtextfield.setText("0000");
        PCtextfield.setText("000");
    }

    public void checkPneumonicFirstPass(String pneumonic, int counter) {
        String pneumonicArray2[] = pneumonic.split(", ");
        if (pneumonicArray2.length == 2) {
            String valueArray[] = pneumonicArray2[1].split(" ");
            if (checkIfVariableIsTaken(pneumonicArray2[0]) == false) {
                if (valueArray[0].equals("HEX") || valueArray[0].equals("hex")) {
                    variablesArray[variablesCounter] = pneumonicArray2[0];
                    variableLocationsArray[variablesCounter] = addZerosThree(Integer.toHexString(counter));
                    variablesCounter++;
                    storePneumonic(addZerosFour(valueArray[1]), counter);
                } else if (valueArray[0].equals("DEC") || valueArray[0].equals("dec")) {
                    String hexiEquivalent = Integer.toHexString(Integer.parseInt(valueArray[1]));
                    variablesArray[variablesCounter] = pneumonicArray2[0];
                    variableLocationsArray[variablesCounter] = addZerosThree(Integer.toHexString(counter));
                    variablesCounter++;
                    storePneumonic(addZerosFour(hexiEquivalent), counter);
                } else if (valueArray[0].equals("OUTPUT") || valueArray[0].equals("output")) {
                    variablesArray[variablesCounter] = pneumonicArray2[0];
                    variableLocationsArray[variablesCounter] = addZerosThree(Integer.toHexString(counter));
                    variablesCounter++;
                    storePneumonic("b000", counter);
                } else if (valueArray[0].equals("HALT") || valueArray[0].equals("halt")) {
                    variablesArray[variablesCounter] = pneumonicArray2[0];
                    variableLocationsArray[variablesCounter] = addZerosThree(Integer.toHexString(counter));
                    variablesCounter++;
                    storePneumonic("c000", counter);
                } else if (valueArray[0].equals("LOAD") || valueArray[0].equals("load")) {
                    if (!findVariableB(valueArray[1]).equals("ERROR")) {
                        variablesArray[variablesCounter] = pneumonicArray2[0];
                        variableLocationsArray[variablesCounter] = addZerosThree(Integer.toHexString(counter));
                        variablesCounter++;
                        storePneumonic("1" + getVariableLocation(valueArray[1]), counter);
                    } else {
                        returnToFirstPassFlag = false;
                    }
                } else if (valueArray[0].equals("STORE") || valueArray[0].equals("store")) {
                    if (!findVariableB(valueArray[1]).equals("ERROR")) {
                        variablesArray[variablesCounter] = pneumonicArray2[0];
                        variableLocationsArray[variablesCounter] = addZerosThree(Integer.toHexString(counter));
                        variablesCounter++;
                        storePneumonic("2" + getVariableLocation(valueArray[1]), counter);
                    } else {
                        returnToFirstPassFlag = false;
                    }
                } else if (valueArray[0].equals("ADD") || valueArray[0].equals("add")) {
                    if (!findVariableB(valueArray[1]).equals("ERROR")) {
                        variablesArray[variablesCounter] = pneumonicArray2[0];
                        variableLocationsArray[variablesCounter] = addZerosThree(Integer.toHexString(counter));
                        variablesCounter++;
                        storePneumonic("3" + getVariableLocation(valueArray[1]), counter);
                    } else {
                        returnToFirstPassFlag = false;
                    }
                } else if (valueArray[0].equals("SUBT") || valueArray[0].equals("subt")) {
                    if (!findVariableB(valueArray[1]).equals("ERROR")) {
                        variablesArray[variablesCounter] = pneumonicArray2[0];
                        variableLocationsArray[variablesCounter] = addZerosThree(Integer.toHexString(counter));
                        variablesCounter++;
                        storePneumonic("4" + getVariableLocation(valueArray[1]), counter);
                    } else {
                        returnToFirstPassFlag = false;
                    }
                } else if (valueArray[0].equals("MULTI") || valueArray[0].equals("multi")) {
                    if (!findVariableB(valueArray[1]).equals("ERROR")) {
                        variablesArray[variablesCounter] = pneumonicArray2[0];
                        variableLocationsArray[variablesCounter] = addZerosThree(Integer.toHexString(counter));
                        variablesCounter++;
                        storePneumonic("5" + getVariableLocation(valueArray[1]), counter);
                    } else {
                        returnToFirstPassFlag = false;
                    }
                } else if (valueArray[0].equals("SQUARE") || valueArray[0].equals("square")) {
                    if (!findVariableB(valueArray[1]).equals("ERROR")) {
                        variablesArray[variablesCounter] = pneumonicArray2[0];
                        variableLocationsArray[variablesCounter] = addZerosThree(Integer.toHexString(counter));
                        variablesCounter++;
                        storePneumonic("6" + getVariableLocation(valueArray[1]), counter);
                    } else {
                        returnToFirstPassFlag = false;
                    }
                } else if (valueArray[0].equals("SKIPCOMP") || valueArray[0].equals("skipcomp")) {
                    if (!findVariableB(valueArray[1]).equals("ERROR")) {
                        variablesArray[variablesCounter] = pneumonicArray2[0];
                        variableLocationsArray[variablesCounter] = addZerosThree(Integer.toHexString(counter));
                        variablesCounter++;
                        storePneumonic("7" + getVariableLocation(valueArray[1]), counter);
                    } else {
                        returnToFirstPassFlag = false;
                    }
                } else if (valueArray[0].equals("JUMP") || valueArray[0].equals("jump")) {
                    if (!findVariableB(valueArray[1]).equals("ERROR")) {
                        variablesArray[variablesCounter] = pneumonicArray2[0];
                        variableLocationsArray[variablesCounter] = addZerosThree(Integer.toHexString(counter));
                        variablesCounter++;
                        storePneumonic("8" + getVariableLocation(valueArray[1]), counter);
                    } else {
                        returnToFirstPassFlag = false;
                    }
                } else if (valueArray[0].equals("CNJUMP") || valueArray[0].equals("cnjump")) {
                    if (!findVariableB(valueArray[1]).equals("ERROR")) {
                        variablesArray[variablesCounter] = pneumonicArray2[0];
                        variableLocationsArray[variablesCounter] = addZerosThree(Integer.toHexString(counter));
                        variablesCounter++;
                        storePneumonic("9" + getVariableLocation(valueArray[1]), counter);
                    } else {
                        returnToFirstPassFlag = false;
                    }
                } else if (valueArray[0].equals("INPUT") || valueArray[0].equals("input")) {
                    if (!findVariableB(valueArray[1]).equals("ERROR")) {
                        variablesArray[variablesCounter] = pneumonicArray2[0];
                        variableLocationsArray[variablesCounter] = addZerosThree(Integer.toHexString(counter));
                        variablesCounter++;
                        storePneumonic("a" + getVariableLocation(valueArray[1]), counter);
                    } else {
                        returnToFirstPassFlag = false;
                    }
                } else {
                    showErrorMessage("Instruction/s not recognized.");
                }
            }
        }
    }

    public void checkPneumonicSecondPass(String pneumonic, int counter) {
        if (pneumonic.equals("OUTPUT") || pneumonic.equals("output")) {
            storePneumonic("b000", counter);
        } else if (pneumonic.equals("HALT") || pneumonic.equals("halt")) {
            storePneumonic("c000", counter);
        } else {
            String pneumonicArray[] = pneumonic.split(" ");
            if (pneumonicArray.length == 2) {
                if (pneumonicArray[0].equals("LOAD") || pneumonicArray[0].equals("load")) {
                    if (pneumonicArray.length == 2) {
                        if (!getVariableLocation(pneumonicArray[1]).equals("ERROR")) {
                            storePneumonic("1" + getVariableLocation(pneumonicArray[1]), counter);
                        }
                    } else {
                        showErrorMessage("Wrong syntax for LOAD X instruction.");
                    }
                } else if (pneumonicArray[0].equals("STORE") || pneumonicArray[0].equals("store")) {
                    if (pneumonicArray.length == 2) {
                        if (!getVariableLocation(pneumonicArray[1]).equals("ERROR")) {
                            storePneumonic("2" + getVariableLocation(pneumonicArray[1]), counter);
                        }
                    } else {
                        showErrorMessage("Wrong syntax for STORE X instruction.");
                    }
                } else if (pneumonicArray[0].equals("ADD") || pneumonicArray[0].equals("add")) {
                    if (pneumonicArray.length == 2) {
                        if (!getVariableLocation(pneumonicArray[1]).equals("ERROR")) {
                            storePneumonic("3" + getVariableLocation(pneumonicArray[1]), counter);
                        }
                    } else {
                        showErrorMessage("Wrong syntax for ADD X instruction.");
                    }
                } else if (pneumonicArray[0].equals("SUBT") || pneumonicArray[0].equals("subt")) {
                    if (pneumonicArray.length == 2) {
                        if (!getVariableLocation(pneumonicArray[1]).equals("ERROR")) {
                            storePneumonic("4" + getVariableLocation(pneumonicArray[1]), counter);
                        }
                    } else {
                        showErrorMessage("Wrong syntax for SUBT X instruction.");
                    }
                } else if (pneumonicArray[0].equals("MULTI") || pneumonicArray[0].equals("multi")) {
                    if (pneumonicArray.length == 2) {
                        if (!getVariableLocation(pneumonicArray[1]).equals("ERROR")) {
                            storePneumonic("5" + getVariableLocation(pneumonicArray[1]), counter);
                        }
                    } else {
                        showErrorMessage("Wrong syntax for MULTI X instruction.");
                    }
                } else if (pneumonicArray[0].equals("SQUARE") || pneumonicArray[0].equals("square")) {
                    if (pneumonicArray.length == 2) {
                        if (!getVariableLocation(pneumonicArray[1]).equals("ERROR")) {
                            storePneumonic("6" + getVariableLocation(pneumonicArray[1]), counter);
                        }
                    } else {
                        showErrorMessage("Wrong syntax for SQUARE X instruction.");
                    }
                } else if (pneumonicArray[0].equals("SKIPCOMP") || pneumonicArray[0].equals("skipcomp")) {
                    if (pneumonicArray.length == 2) {
                        if (!getVariableLocation(pneumonicArray[1]).equals("ERROR")) {
                            storePneumonic("7" + getVariableLocation(pneumonicArray[1]), counter);
                        }
                    } else {
                        showErrorMessage("Wrong syntax for SKIPCOMP X instruction.");
                    }
                } else if (pneumonicArray[0].equals("JUMP") || pneumonicArray[0].equals("jump")) {
                    if (pneumonicArray.length == 2) {
                        if (!getVariableLocation(pneumonicArray[1]).equals("ERROR")) {
                            storePneumonic("8" + getVariableLocation(pneumonicArray[1]), counter);
                        }
                    } else {
                        showErrorMessage("Wrong syntax for JUMP X instruction.");
                    }
                } else if (pneumonicArray[0].equals("CNJUMP") || pneumonicArray[0].equals("cnjump")) {
                    if (pneumonicArray.length == 2) {
                        if (!getVariableLocation(pneumonicArray[1]).equals("ERROR")) {
                            storePneumonic("9" + getVariableLocation(pneumonicArray[1]), counter);
                        }
                    } else {
                        showErrorMessage("Wrong syntax for CNJUMP X instruction.");
                    }
                } else if (pneumonicArray[0].equals("INPUT") || pneumonicArray[0].equals("input")) {
                    if (pneumonicArray.length == 2) {
                        if (!getVariableLocation(pneumonicArray[1]).equals("ERROR")) {
                            storePneumonic("a" + getVariableLocation(pneumonicArray[1]), counter);
                        }
                    } else {
                        showErrorMessage("Wrong syntax for INPUT X instruction.");
                    }
                } else {
                    if (pneumonicArray[1].equals("OUTPUT") || pneumonicArray[1].equals("output")
                            || pneumonicArray[1].equals("HALT") || pneumonicArray[1].equals("halt")) {
                        /**
                         * DO NOTHING
                         */
                    } else {
                        showErrorMessage("Instruction/s not recognized.");
                    }
                }
            } else {
                try {
                    String pneumonicArray2[] = pneumonic.split(", ");
                    String pneumonicArray3[] = pneumonicArray2[1].split(" ");
                    if (pneumonicArray3.length != 2) {
                        showErrorMessage("Instruction/s not recognized.");
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    showErrorMessage("Instruction/s not recognized.");
                }
            }
        }
    }

    public String getVariableLocation(String variable) {
        int findCounter = 0;
        String variableLocation = "";
        for (int a = 0; a < variablesArray.length; a++) {
            if (variable.equals(variablesArray[a])) {
                variableLocation = variableLocationsArray[a];
                findCounter = 1;
                break;
            }
        }
        if (findCounter == 0) {
            showErrorMessage("Cannot find variable " + variable);
            variableLocation = "ERROR";
        }
        return variableLocation;
    }

    public String findVariableB(String variable) {
        int findCounter = 0;
        String variableLocation = "";
        for (int a = 0; a < variablesArray.length; a++) {
            if (variable.equals(variablesArray[a])) {
                variableLocation = variableLocationsArray[a];
                findCounter = 1;
                break;
            }
        }
        if (findCounter == 0) {
            variableLocation = "ERROR";
        }
        return variableLocation;
    }

    public void showErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        initializeProgram();
        errorFlag = true;
    }

    public void storePneumonic(String pneumonic, int counter) {
        if ((counter + 1) % 16 != 0) {
            addressTable.setValueAt(pneumonic, (counter / 16) + 1, (counter + 1) % 16);
        } else {
            addressTable.setValueAt(pneumonic, (counter / 16) + 1, 16);
        }
    }

    public void executePneumonic() {
        int executionCounter = 0;
        while (haltFlag == false) {
            fetch();
            decodeANDexecute();
            executionCounter++;
            if (executionCounter == 500) {
                showErrorMessage("Instruction HALT can't be reached");
            }
        }
    }

    public void fetch() {
        // MAR <-- PC
        MARtextfield.setText(PCtextfield.getText());

        // IR <-- M[MAR]
        int MAR = convertHexadecimalToDecimal(MARtextfield.getText());
        if ((MAR + 1) % 16 != 0) {
            IRtextfield.setText("" + addressTable.getValueAt((MAR / 16) + 1, (MAR + 1) % 16));
        } else {
            IRtextfield.setText("" + addressTable.getValueAt((MAR / 16) + 1, 16));
        }

        // PC <-- PC+1
        String hexadecimal = PCtextfield.getText();
        int decimal = convertHexadecimalToDecimal(hexadecimal);
        decimal = decimal + 1;
        PCtextfield.setText(addZerosThree(Integer.toHexString(decimal)));
    }

    public void decodeANDexecute() {
        String IR = IRtextfield.getText();
        if (IR.equals("b000")) { //OUTPUT
            if (outputTextArea.getText().equals("")) {
                outputTextArea.setText(ACtextfield.getText());
            } else {
                outputTextArea.setText(outputTextArea.getText() + "\n" + ACtextfield.getText());
            }
        } else if (IR.equals("c000")) { //HALT
            statusTextField.setText("Machine halted normally.");
            stopButton.setEnabled(false);
            editorTextArea.setEnabled(true);
            haltFlag = true;
        } else {
            // MAR <-- IR[11-0]
            String variableLocation = "";
            for (int a = 1; a <= 3; a++) {
                variableLocation += IR.charAt(a);
            }
            MARtextfield.setText(variableLocation);

            // Decode IR[15-12]
            String operation = "" + IR.charAt(0);
            if (operation.equals("1")) { //LOAD X
                // MBR <-- M[MAR]
                valueMARtoMBR();

                //  AC <-- MBR
                ACtextfield.setText(MBRtextfield.getText());
            } else if (operation.equals("2")) { //STORE X
                // MBR <-- AC
                MBRtextfield.setText(ACtextfield.getText());

                //M[MAR] <-- MBR
                int variableLocationDecimal = convertHexadecimalToDecimal(MARtextfield.getText());
                if ((variableLocationDecimal + 1) % 16 != 0) {
                    addressTable.setValueAt(MBRtextfield.getText(), (variableLocationDecimal / 16) + 1, (variableLocationDecimal + 1) % 16);
                } else {
                    addressTable.setValueAt(MBRtextfield.getText(), (variableLocationDecimal / 16) + 1, 16);
                }
            } else if (operation.equals("3")) { //ADD X
                // MBR <-- M[MAR]
                valueMARtoMBR();

                // AC <-- AC + MBR
                String previousAC = ACtextfield.getText();
                int previousACDecimal = convertHexadecimalToDecimal(previousAC);
                int MBRDecimal = convertHexadecimalToDecimal(MBRtextfield.getText());
                ACtextfield.setText(addZerosFour(Integer.toHexString(previousACDecimal + MBRDecimal)));
            } else if (operation.equals("4")) { //SUBT X
                // MBR <-- M[MAR]
                valueMARtoMBR();

                // AC <-- AC - MBR
                String previousAC = ACtextfield.getText();
                int previousACDecimal = convertHexadecimalToDecimal(previousAC);
                int MBRDecimal = convertHexadecimalToDecimal(MBRtextfield.getText());
                ACtextfield.setText(addZerosFour(Integer.toHexString(previousACDecimal - MBRDecimal)));
            } else if (operation.equals("5")) { //MULTI X
                // MBR <-- M[MAR]
                valueMARtoMBR();

                // AC <-- AC * MBR
                String previousAC = ACtextfield.getText();
                int previousACDecimal = convertHexadecimalToDecimal(previousAC);
                int MBRDecimal = convertHexadecimalToDecimal(MBRtextfield.getText());
                ACtextfield.setText(addZerosFour(Integer.toHexString(previousACDecimal * MBRDecimal)));
            } else if (operation.equals("6")) { //SQUARE X
                //AC <-- AC * AC
                int ACDeci = convertHexadecimalToDecimal(ACtextfield.getText());
                int squareDeci = ACDeci * ACDeci;
                ACtextfield.setText(addZerosFour(Integer.toHexString(squareDeci)));

                // MBR <-- AC
                MBRtextfield.setText(ACtextfield.getText());

                //M[MAR] <-- MBR
                int variableLocationDecimal = convertHexadecimalToDecimal(MARtextfield.getText());
                if ((variableLocationDecimal + 1) % 16 != 0) {
                    addressTable.setValueAt(MBRtextfield.getText(), (variableLocationDecimal / 16) + 1, (variableLocationDecimal + 1) % 16);
                } else {
                    addressTable.setValueAt(MBRtextfield.getText(), (variableLocationDecimal / 16) + 1, 16);
                }
            } else if (operation.equals("7")) { //SKIPCOMP X
                int ACvalue = convertHexadecimalToDecimal(ACtextfield.getText());
                valueMARtoMBR();
                int Xvalue = convertHexadecimalToDecimal(MBRtextfield.getText());
                if (ACvalue > Xvalue) {
                    // PC <-- PC+1
                    String hexadecimal = PCtextfield.getText();
                    int decimal = convertHexadecimalToDecimal(hexadecimal);
                    decimal = decimal + 1;
                    PCtextfield.setText(addZerosThree(Integer.toHexString(decimal)));
                }
            } else if (operation.equals("8")) { //JUMP X
                // PC <-- MAR
                PCtextfield.setText(MARtextfield.getText());
            } else if (operation.equals("9")) { //CNJUMP X
                String hold = MARtextfield.getText();

                initializeRegisters();

                // PC <-- MAR
                PCtextfield.setText(hold);


            } else if (operation.equals("a")) { //INPUT X
                String inputString = "";
                int inputInteger = 0;

                while (inputString.equals("")) {
                    inputString = JOptionPane.showInputDialog(null, "Enter input (DEC): ", "Input", JOptionPane.OK_OPTION);
                    try {
                        inputInteger = Integer.parseInt(inputString);
                    } catch (NumberFormatException ex) {
                        inputString = "";
                    }
                }

                // AC <-- INPUT
                ACtextfield.setText(addZerosFour(Integer.toHexString(inputInteger)));

                // MBR <-- AC
                MBRtextfield.setText(ACtextfield.getText());

                //M[MAR] <-- MBR
                int variableLocationDecimal = convertHexadecimalToDecimal(MARtextfield.getText());
                if ((variableLocationDecimal + 1) % 16 != 0) {
                    addressTable.setValueAt(MBRtextfield.getText(), (variableLocationDecimal / 16) + 1, (variableLocationDecimal + 1) % 16);
                } else {
                    addressTable.setValueAt(MBRtextfield.getText(), (variableLocationDecimal / 16) + 1, 16);
                }
            } else {
                int addressDecimal = convertHexadecimalToDecimal(PCtextfield.getText()) - 1;
                showErrorMessage("Cannot decode instruction at " + addZerosThree(Integer.toHexString(addressDecimal)));
                haltFlag = true;
            }
        }
    }

    public void valueMARtoMBR() {
        int variableLocationDecimal = convertHexadecimalToDecimal(MARtextfield.getText());
        String value;
        if ((variableLocationDecimal + 1) % 16 != 0) {
            value = "" + addressTable.getValueAt((variableLocationDecimal / 16) + 1, (variableLocationDecimal + 1) % 16);
        } else {
            value = "" + addressTable.getValueAt((variableLocationDecimal / 16) + 1, 16);
        }
        MBRtextfield.setText(value);
    }

    public String addZerosThree(String value) {
        switch (value.length()) {
            case 1:
                return "00" + value;
            case 2:
                return "0" + value;
            case 3:
                return value;
            default:
                return "";
        }
    }

    public String addZerosFour(String value) {
        switch (value.length()) {
            case 1:
                return "000" + value;
            case 2:
                return "00" + value;
            case 3:
                return "0" + value;
            case 4:
                return value;
            default:
                return "";
        }
    }

    public boolean checkIfVariableIsTaken(String var) {
        try {
            for (String variable : variablesArray) {
                if (variable.equals(var)) {
                    return true;
                }
            }
            return false;
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public void initializeProgram() {
        initializeAddressTable();
        initializeRegisters();
        editorTextArea.setEnabled(true);
        checkButton.setEnabled(true);
        editButton.setEnabled(false);
        executeButton.setEnabled(false);
        checkMenuItem.setEnabled(true);
        editMenuItem.setEnabled(false);
        executeMenuItem.setEnabled(false);
        statusTextField.setText("--");
        stopButton.setEnabled(false);
        outputHexButton.setEnabled(false);
        outputDecButton.setEnabled(false);
        outputTextArea.setText("");
        variablesArray = new String[100];
        variableLocationsArray = new String[100];
        variablesCounter = 0;
        errorFlag = false;
        haltFlag = false;
        returnToFirstPassFlag = false;
    }
    
    public int convertHexadecimalToDecimal(String hexadecimal) {
        int decimal = 0;
        char hexadecimalArray[] = hexadecimal.toCharArray();
        for (int a = 0; a < hexadecimalArray.length; a++) {
            int value = 0;
            switch (hexadecimalArray[a]) {
                case '0':
                    value = 0;
                    break;
                case '1':
                    value = 1;
                    break;
                case '2':
                    value = 2;
                    break;
                case '3':
                    value = 3;
                    break;
                case '4':
                    value = 4;
                    break;
                case '5':
                    value = 5;
                    break;
                case '6':
                    value = 6;
                    break;
                case '7':
                    value = 7;
                    break;
                case '8':
                    value = 8;
                    break;
                case '9':
                    value = 9;
                    break;
                case 'a':
                    value = 10;
                    break;
                case 'b':
                    value = 11;
                    break;
                case 'c':
                    value = 12;
                    break;
                case 'd':
                    value = 13;
                    break;
                case 'e':
                    value = 14;
                    break;
                case 'f':
                    value = 15;
                    break;
            }
            decimal += value * Math.pow(16, (hexadecimalArray.length - 1) - Double.parseDouble("" + a));
        }
        return decimal;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        editorTextArea = new javax.swing.JTextArea();
        ACtextfield = new javax.swing.JTextField();
        MARtextfield = new javax.swing.JTextField();
        MBRtextfield = new javax.swing.JTextField();
        IRtextfield = new javax.swing.JTextField();
        PCtextfield = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        addressTable = new javax.swing.JTable();
        executeButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        outputTextArea = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        statusTextField = new javax.swing.JTextField();
        stopButton = new javax.swing.JButton();
        outputHexButton = new javax.swing.JButton();
        outputDecButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        checkButton = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        editButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        open_menu = new javax.swing.JMenuItem();
        save_menu = new javax.swing.JMenuItem();
        saveAs_menu = new javax.swing.JMenuItem();
        restartMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        checkMenuItem = new javax.swing.JMenuItem();
        editMenuItem = new javax.swing.JMenuItem();
        executeMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MARIA Simulator");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(91, 125, 135));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(46, 50, 60), 10));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        editorTextArea.setBackground(new java.awt.Color(204, 204, 204));
        editorTextArea.setColumns(20);
        editorTextArea.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        editorTextArea.setRows(5);
        jScrollPane1.setViewportView(editorTextArea);

        ACtextfield.setEditable(false);
        ACtextfield.setBackground(new java.awt.Color(204, 204, 204));
        ACtextfield.setFont(new java.awt.Font("Arial Unicode MS", 0, 11)); // NOI18N
        ACtextfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ACtextfield.setText("0000");
        ACtextfield.setFocusable(false);

        MARtextfield.setEditable(false);
        MARtextfield.setBackground(new java.awt.Color(204, 204, 204));
        MARtextfield.setFont(new java.awt.Font("Arial Unicode MS", 0, 11)); // NOI18N
        MARtextfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MARtextfield.setText("000");
        MARtextfield.setFocusable(false);

        MBRtextfield.setEditable(false);
        MBRtextfield.setBackground(new java.awt.Color(204, 204, 204));
        MBRtextfield.setFont(new java.awt.Font("Arial Unicode MS", 0, 11)); // NOI18N
        MBRtextfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        MBRtextfield.setText("0000");
        MBRtextfield.setFocusable(false);

        IRtextfield.setEditable(false);
        IRtextfield.setBackground(new java.awt.Color(204, 204, 204));
        IRtextfield.setFont(new java.awt.Font("Arial Unicode MS", 0, 11)); // NOI18N
        IRtextfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        IRtextfield.setText("0000");
        IRtextfield.setFocusable(false);

        PCtextfield.setEditable(false);
        PCtextfield.setBackground(new java.awt.Color(204, 204, 204));
        PCtextfield.setFont(new java.awt.Font("Arial Unicode MS", 0, 11)); // NOI18N
        PCtextfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PCtextfield.setText("000");
        PCtextfield.setFocusable(false);

        jLabel1.setFont(new java.awt.Font("Arial Unicode MS", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("AC");

        jLabel6.setFont(new java.awt.Font("Arial Unicode MS", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("MAR");

        jLabel7.setFont(new java.awt.Font("Arial Unicode MS", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("MBR");

        jLabel8.setFont(new java.awt.Font("Arial Unicode MS", 1, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("IR");

        jLabel9.setFont(new java.awt.Font("Arial Unicode MS", 1, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("PC");

        jScrollPane2.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setFont(new java.awt.Font("Arial Unicode MS", 0, 11)); // NOI18N

        addressTable.setBackground(new java.awt.Color(204, 204, 204));
        addressTable.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        addressTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "", "+0", "+1", "+2", "+3", "+4", "+5", "+6", "+7", "+8", "+9", "+a", "+b", "+c", "+d", "+e", "+f"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        addressTable.setFocusable(false);
        addressTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(addressTable);
        if (addressTable.getColumnModel().getColumnCount() > 0) {
            addressTable.getColumnModel().getColumn(0).setResizable(false);
            addressTable.getColumnModel().getColumn(1).setResizable(false);
            addressTable.getColumnModel().getColumn(1).setPreferredWidth(100);
            addressTable.getColumnModel().getColumn(2).setResizable(false);
            addressTable.getColumnModel().getColumn(3).setResizable(false);
            addressTable.getColumnModel().getColumn(4).setResizable(false);
            addressTable.getColumnModel().getColumn(5).setResizable(false);
            addressTable.getColumnModel().getColumn(6).setResizable(false);
            addressTable.getColumnModel().getColumn(7).setResizable(false);
            addressTable.getColumnModel().getColumn(8).setResizable(false);
            addressTable.getColumnModel().getColumn(9).setResizable(false);
            addressTable.getColumnModel().getColumn(10).setResizable(false);
            addressTable.getColumnModel().getColumn(11).setResizable(false);
            addressTable.getColumnModel().getColumn(12).setResizable(false);
            addressTable.getColumnModel().getColumn(13).setResizable(false);
            addressTable.getColumnModel().getColumn(14).setResizable(false);
            addressTable.getColumnModel().getColumn(15).setResizable(false);
            addressTable.getColumnModel().getColumn(16).setResizable(false);
        }

        executeButton.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        executeButton.setText("Execute");
        executeButton.setEnabled(false);
        executeButton.setFocusable(false);
        executeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                executeButtonActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial Unicode MS", 0, 11)); // NOI18N
        jLabel2.setText("(Hex)");

        jLabel3.setFont(new java.awt.Font("Arial Unicode MS", 0, 11)); // NOI18N
        jLabel3.setText("(Hex)");

        jLabel4.setFont(new java.awt.Font("Arial Unicode MS", 0, 11)); // NOI18N
        jLabel4.setText("(Hex)");

        jLabel5.setFont(new java.awt.Font("Arial Unicode MS", 0, 11)); // NOI18N
        jLabel5.setText("(Hex)");

        jLabel11.setFont(new java.awt.Font("Arial Unicode MS", 0, 11)); // NOI18N
        jLabel11.setText("(Hex)");

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        outputTextArea.setEditable(false);
        outputTextArea.setBackground(new java.awt.Color(204, 204, 204));
        outputTextArea.setColumns(20);
        outputTextArea.setFont(new java.awt.Font("Arial Unicode MS", 0, 13)); // NOI18N
        outputTextArea.setRows(5);
        outputTextArea.setFocusable(false);
        jScrollPane3.setViewportView(outputTextArea);

        jLabel13.setFont(new java.awt.Font("Arial Unicode MS", 1, 14)); // NOI18N
        jLabel13.setText("OUTPUT");

        jLabel12.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        jLabel12.setText("Status:");

        statusTextField.setEditable(false);
        statusTextField.setBackground(new java.awt.Color(204, 204, 204));
        statusTextField.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        statusTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        statusTextField.setText("--");
        statusTextField.setFocusable(false);

        stopButton.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        stopButton.setText("Stop");
        stopButton.setEnabled(false);
        stopButton.setFocusable(false);
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        outputHexButton.setFont(new java.awt.Font("Arial Unicode MS", 0, 13)); // NOI18N
        outputHexButton.setText("Hex");
        outputHexButton.setEnabled(false);
        outputHexButton.setFocusable(false);
        outputHexButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputHexButtonActionPerformed(evt);
            }
        });

        outputDecButton.setFont(new java.awt.Font("Arial Unicode MS", 0, 13)); // NOI18N
        outputDecButton.setText("Dec");
        outputDecButton.setEnabled(false);
        outputDecButton.setFocusable(false);
        outputDecButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputDecButtonActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial Unicode MS", 0, 36)); // NOI18N
        jLabel10.setText("MARIA");

        checkButton.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        checkButton.setText("Check");
        checkButton.setFocusable(false);
        checkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkButtonActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Arial Unicode MS", 0, 10)); // NOI18N
        jLabel14.setText("Machine Architecture that is Really");

        jLabel15.setFont(new java.awt.Font("Arial Unicode MS", 0, 10)); // NOI18N
        jLabel15.setText("Intuitive and Awesome");

        editButton.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        editButton.setText("Edit");
        editButton.setEnabled(false);
        editButton.setFocusable(false);
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                    .addComponent(checkButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(executeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(MARtextfield)
                            .addComponent(ACtextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                            .addComponent(MBRtextfield)
                            .addComponent(IRtextfield)
                            .addComponent(PCtextfield))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel11)
                            .addComponent(jLabel2))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(outputHexButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(outputDecButton))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15)))
                            .addComponent(statusTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(executeButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(ACtextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(outputHexButton)
                                        .addComponent(outputDecButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(MARtextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel3))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(MBRtextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(IRtextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel5))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(PCtextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel11)))
                                    .addComponent(jScrollPane3))
                                .addGap(10, 10, 10))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel15))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(jLabel14)))
                                .addGap(16, 16, 16)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(statusTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stopButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        fileMenu.setMnemonic('F');
        fileMenu.setText("File");
        fileMenu.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N

        open_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        open_menu.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        open_menu.setMnemonic('O');
        open_menu.setText("Open");
        open_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                open_menuActionPerformed(evt);
            }
        });
        fileMenu.add(open_menu);

        save_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        save_menu.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        save_menu.setMnemonic('S');
        save_menu.setText("Save");
        save_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_menuActionPerformed(evt);
            }
        });
        fileMenu.add(save_menu);

        saveAs_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        saveAs_menu.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        saveAs_menu.setMnemonic('A');
        saveAs_menu.setText("Save as");
        saveAs_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAs_menuActionPerformed(evt);
            }
        });
        fileMenu.add(saveAs_menu);

        restartMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        restartMenuItem.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        restartMenuItem.setMnemonic('R');
        restartMenuItem.setText("Restart");
        restartMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restartMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(restartMenuItem);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        exitMenuItem.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        jMenuBar1.add(fileMenu);

        editMenu.setMnemonic('E');
        editMenu.setText("Edit");
        editMenu.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N

        checkMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        checkMenuItem.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        checkMenuItem.setMnemonic('C');
        checkMenuItem.setText("Check");
        checkMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(checkMenuItem);

        editMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK));
        editMenuItem.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        editMenuItem.setMnemonic('d');
        editMenuItem.setText("Edit");
        editMenuItem.setEnabled(false);
        editMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(editMenuItem);

        executeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK));
        executeMenuItem.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        executeMenuItem.setMnemonic('x');
        executeMenuItem.setText("Execute");
        executeMenuItem.setEnabled(false);
        executeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                executeMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(executeMenuItem);

        jMenuBar1.add(editMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void executeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executeButtonActionPerformed
        checkButton.setEnabled(true);
        checkMenuItem.setEnabled(true);
        editButton.setEnabled(false);
        editMenuItem.setEnabled(false);
        executeButton.setEnabled(false);
        executeMenuItem.setEnabled(false);
        outputDecButton.setEnabled(true);
        statusTextField.setText("Running.");
        stopButton.setEnabled(true);
        executePneumonic();
    }//GEN-LAST:event_executeButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        statusTextField.setText("Halted at user request.");
        stopButton.setEnabled(false);
        editorTextArea.setEnabled(true);
    }//GEN-LAST:event_stopButtonActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void restartMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restartMenuItemActionPerformed
        editorTextArea.setText("");
        Main.this.setTitle("MARIA Simulator");
        initializeProgram();
    }//GEN-LAST:event_restartMenuItemActionPerformed

    private void outputHexButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outputHexButtonActionPerformed
        String outputText = outputTextArea.getText();
        if (!outputText.equals("")) {
            String outputTextArray[] = outputText.split("\n");
            String newOutput = "";
            for (int a = 0; a < outputTextArray.length; a++) {
                newOutput += addZerosFour(Integer.toHexString(Integer.parseInt(outputTextArray[a]))) + "\n";
            }
            outputTextArea.setText(newOutput);
        }
        outputHexButton.setEnabled(false);
        outputDecButton.setEnabled(true);
    }//GEN-LAST:event_outputHexButtonActionPerformed

    private void outputDecButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outputDecButtonActionPerformed
        String outputText = outputTextArea.getText();
        if (!outputText.equals("")) {
            String outputTextArray[] = outputText.split("\n");
            String newOutput = "";
            for (int a = 0; a < outputTextArray.length; a++) {
                newOutput += convertHexadecimalToDecimal(outputTextArray[a]) + "\n";
            }
            outputTextArea.setText(newOutput);
        }
        outputDecButton.setEnabled(false);
        outputHexButton.setEnabled(true);
    }//GEN-LAST:event_outputDecButtonActionPerformed

    private void checkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkButtonActionPerformed
        initializeProgram();
        String pneumonic = editorTextArea.getText();
        int pneumonicFirstPassCounter = 0;
        int pneumonicSecondPassCounter = 0;
        if (!pneumonic.equals("")) {
            String pneumonicArray[] = pneumonic.split("\n");

            while (returnToFirstPassFlag == false) {
                pneumonicFirstPassCounter = 0;
                returnToFirstPassFlag = true;
                for (int a = 0; a < pneumonicArray.length; a++) {
                    if (errorFlag == false) {
                        if (!pneumonicArray[a].equals("")) {
                            checkPneumonicFirstPass(pneumonicArray[a], pneumonicFirstPassCounter);
                            pneumonicFirstPassCounter++;
                        }
                    }
                }
            }

            for (int b = 0; b < pneumonicArray.length; b++) {
                if (errorFlag == false) {
                    if (!pneumonicArray[b].equals("")) {
                        checkPneumonicSecondPass(pneumonicArray[b], pneumonicSecondPassCounter);
                        pneumonicSecondPassCounter++;
                    }
                }
            }
            if (errorFlag == false) {
                PCtextfield.setText("000");
                checkButton.setEnabled(false);
                checkMenuItem.setEnabled(false);
                editorTextArea.setEnabled(false);
                executeButton.setEnabled(true);
                executeMenuItem.setEnabled(true);
                statusTextField.setText("Ready to load program instructions");
                editButton.setEnabled(true);
                editMenuItem.setEnabled(true);
            }
        } else {
            showErrorMessage("Please input program instructions.");
        }
    }//GEN-LAST:event_checkButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        editorTextArea.setEnabled(true);
        checkButton.setEnabled(true);
        checkMenuItem.setEnabled(true);
        editButton.setEnabled(false);
        editMenuItem.setEnabled(false);
        executeButton.setEnabled(false);
        executeMenuItem.setEnabled(false);
    }//GEN-LAST:event_editButtonActionPerformed

    private void saveAs_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAs_menuActionPerformed
        FileWriter fw;
        String temp_contents = editorTextArea.getText();
        //String contents = temp_contents.replaceAll("\n", "\r\n");
        JFileChooser opener = new JFileChooser();
        FileFilter ft = new FileNameExtensionFilter("MARIA Object File", "m");
        opener.setFileFilter(ft);
        int clicked = opener.showSaveDialog(this);
        if (clicked == javax.swing.JFileChooser.APPROVE_OPTION) {
            File temp = opener.getSelectedFile();
            String file_name = temp.toString();
            if (!file_name.endsWith(".m")) {
                file_name += ".m";
            }
            File created = new File(file_name);
            try {
                created.createNewFile();
                fw = new FileWriter(created);
                fw.write(temp_contents);
                fw.close();
                Main.this.setTitle("MARIA Simulator" + " - " + file_name);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex);
            }
        }
    }//GEN-LAST:event_saveAs_menuActionPerformed

    private void open_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_open_menuActionPerformed
        JFileChooser opener = new JFileChooser();
        FileFilter ft = new FileNameExtensionFilter("MARIA Object File", "m");
        opener.setFileFilter(ft);
        int clicked = opener.showOpenDialog(this);
        File selected;
        BufferedReader br;
        String text = "", content = "";
        if (clicked == javax.swing.JFileChooser.APPROVE_OPTION) {
            String temp = opener.getSelectedFile().toString();
            selected = new File(temp);
            try {
                br = new BufferedReader(new FileReader(selected));
                while ((text = br.readLine()) != null) {
                    content += text+"\n";
                }
                editorTextArea.setText(content);
                Main.this.setTitle("MARIA Simulator" + " - " + temp);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex);
            }
        }

    }//GEN-LAST:event_open_menuActionPerformed

    private void save_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_menuActionPerformed
        String temp_contents = editorTextArea.getText();
        //String contents = temp_contents.replaceAll("\n", "\r\n");
        String[] temp = Main.this.getTitle().toString().split(" - ");
        String fileName = "";
        File file;
        FileWriter fw;
        if (temp.length == 2) {
            fileName = temp[1];
            file = new File(fileName);
            try {
                fw = new FileWriter(file);
                fw.write(temp_contents);
                fw.close();
                JOptionPane.showMessageDialog(this,"File has been saved", "Saved", 1);
                Main.this.setTitle("MARIA Simulator" + " - " + fileName);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex);
            }
        } else {
            saveAs_menuActionPerformed(evt);
        }
    }//GEN-LAST:event_save_menuActionPerformed

    private void checkMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkMenuItemActionPerformed
        if(checkButton.isEnabled()){
            checkButtonActionPerformed(evt);
        }
    }//GEN-LAST:event_checkMenuItemActionPerformed

    private void editMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuItemActionPerformed
        if(editButton.isEnabled()){
            editButtonActionPerformed(evt);
        }
    }//GEN-LAST:event_editMenuItemActionPerformed

    private void executeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executeMenuItemActionPerformed
        if(executeButton.isEnabled()){
            executeButtonActionPerformed(evt);
        }
    }//GEN-LAST:event_executeMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    new Main().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ACtextfield;
    private javax.swing.JTextField IRtextfield;
    private javax.swing.JTextField MARtextfield;
    private javax.swing.JTextField MBRtextfield;
    private javax.swing.JTextField PCtextfield;
    private javax.swing.JTable addressTable;
    private javax.swing.JButton checkButton;
    private javax.swing.JMenuItem checkMenuItem;
    private javax.swing.JButton editButton;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem editMenuItem;
    private javax.swing.JTextArea editorTextArea;
    private javax.swing.JButton executeButton;
    private javax.swing.JMenuItem executeMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JMenuItem open_menu;
    private javax.swing.JButton outputDecButton;
    private javax.swing.JButton outputHexButton;
    private javax.swing.JTextArea outputTextArea;
    private javax.swing.JMenuItem restartMenuItem;
    private javax.swing.JMenuItem saveAs_menu;
    private javax.swing.JMenuItem save_menu;
    private javax.swing.JTextField statusTextField;
    private javax.swing.JButton stopButton;
    // End of variables declaration//GEN-END:variables
}
