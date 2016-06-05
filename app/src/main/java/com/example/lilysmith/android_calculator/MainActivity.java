/**
 * Android Calculator
 * Created by Lillian Smith
 * Version 1.0
 * 5/28/2016
 */
package com.example.lilysmith.android_calculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button changeSignButton;
    Button additionButton;
    Button subtractionButton;
    Button multiplicationButton;
    Button divisionButton;
    Button rootButton;
    Button decimalButton;
    Button clearButton;
    Button equalsButton;
    Button oneButton;
    Button twoButton;
    Button threeButton;
    Button fourButton;
    Button fiveButton;
    Button sixButton;
    Button sevenButton;
    Button eightButton;
    Button nineButton;
    Button zeroButton;

    TextView textBox;

    boolean equalsButtonPressedLast = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textBox = (TextView) findViewById(R.id.text);

        changeSignButton = (Button) findViewById(R.id.changeSignButton);
        additionButton = (Button) findViewById(R.id.additionButton);
        subtractionButton = (Button) findViewById(R.id.subtractionButton);
        multiplicationButton = (Button) findViewById(R.id.multiplicationButton);
        divisionButton = (Button) findViewById(R.id.divisionButton);
        rootButton = (Button) findViewById(R.id.rootButton);
        decimalButton = (Button) findViewById(R.id.decimalButton);
        equalsButton = (Button) findViewById(R.id.equalsButton);
        oneButton = (Button) findViewById(R.id.oneButton);
        twoButton = (Button) findViewById(R.id.twoButton);
        threeButton = (Button) findViewById(R.id.threeButton);
        fourButton = (Button) findViewById(R.id.fourButton);
        fiveButton = (Button) findViewById(R.id.fiveButton);
        sixButton = (Button) findViewById(R.id.sixButton);
        sevenButton = (Button) findViewById(R.id.sevenButton);
        eightButton = (Button) findViewById(R.id.eightButton);
        nineButton = (Button) findViewById(R.id.nineButton);
        zeroButton = (Button) findViewById(R.id.zeroButton);
        clearButton = (Button) findViewById(R.id.clearButton);

        // Assign an onClickListener to all calculator buttons
        changeSignButton.setOnClickListener(buttonListener);
        additionButton.setOnClickListener(buttonListener);
        subtractionButton.setOnClickListener(buttonListener);
        multiplicationButton.setOnClickListener(buttonListener);
        divisionButton.setOnClickListener(buttonListener);
        rootButton.setOnClickListener(buttonListener);
        decimalButton.setOnClickListener(buttonListener);
        clearButton.setOnClickListener(buttonListener);
        equalsButton.setOnClickListener(buttonListener);
        oneButton.setOnClickListener(buttonListener);
        twoButton.setOnClickListener(buttonListener);
        threeButton.setOnClickListener(buttonListener);
        fourButton.setOnClickListener(buttonListener);
        fiveButton.setOnClickListener(buttonListener);
        sixButton.setOnClickListener(buttonListener);
        sevenButton.setOnClickListener(buttonListener);
        eightButton.setOnClickListener(buttonListener);
        nineButton.setOnClickListener(buttonListener);
        zeroButton.setOnClickListener(buttonListener);

    }

    private final View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button clickButton = ((Button) v);
            // if the last equation is still visible, clear the textBox
            if (equalsButtonPressedLast == true){
                textBox.setText("");
                equalsButtonPressedLast = false;
            }
            switch (clickButton.getText().toString()) {
                case "=":
                    equalsButtonPressedLast = true;
                    parseEquation(); // grabs the text from the textBox
                    break;
                case "clear":
                    textBox.setText("");
                    break;
                case "+/-": // used the hyphen symbol alone for the negative sign
                    // used option + hyphen symbol for minus sign
                    textBox.setText(textBox.getText() + " -");
                    break;
                default:
                    textBox.setText(textBox.getText().toString().
                            concat(clickButton.getText().toString()));
                    break;

            }

        }

    };

    private void parseEquation(){
        /** This method takes string input from the textBox and parses it into a 1d operatorsArray
         * that contains all of the operators used and a 2d array with each of the individual
         * numbers used in the equation. Note: The square root sign is not considered an operator
         * here, it is dealt with separately.
         */

        String text = (textBox.getText().toString());
        char [] equationArray = text.toCharArray();
        String[][] numberArray = new String[equationArray.length][equationArray.length];
        String[] operatorsArray = new String[equationArray.length];
        try {
            int operatorsCount = 0; // keeps track of the number of operators
            int innerNumberCount = 0; // keeps track of the position within an individual number
            int outerNumberCount = 0; // keeps track how many individual numbers are in the equation
            boolean squareRoot = false;
            int numberOfSquareRoots = 0;
            for (int x = 0; x < equationArray.length; x++) {
                if (equationArray[x] == ('+') || equationArray[x] == ('–') ||
                        equationArray[x] == ('x') || equationArray[x] == ('/')) {
                    if (x == 0)
                        throw new firstOperatorException(); //equations can't start with an operator
                    if (innerNumberCount == 0)
                        throw new operatorsInARowException(); //equations can't have two consecutive
                    //operators
                    operatorsArray[operatorsCount] = String.valueOf(equationArray[x]);
                    operatorsCount++;
                    outerNumberCount ++;
                    innerNumberCount = 0;
                } else if (equationArray[x] != ' ') { // ignores the blank space added in front
                    // of the negative sign
                    numberArray[outerNumberCount][innerNumberCount] =
                            String.valueOf(equationArray[x]);
                    innerNumberCount++;
                    if (equationArray[x] == ('√')) {
                        squareRoot = true;
                        numberOfSquareRoots ++;
                    }
                }
            }
            if (innerNumberCount == 0)
                throw new endsWithOperatorException(); // throw an exception if the last character
            // in the equation is an operator
            if (operatorsCount == 0 && squareRoot == false)
                throw new noOperatorsException(); // throw an exception if there are no operators
            // (+, -, /, x) and no square root symbol
            if (operatorsCount == 0 && squareRoot == true && numberOfSquareRoots == 1
                    && numberArray[0].equals("√"))
                throw new onlySquareRootException(); // special handling for equations that
            // contain a square root symbol without any other operators (ex: √5). Equations that
            // have a square root symbol and an operator are treated normally (ex: √5 + 2)
            if (operatorsCount == 0 && squareRoot == true)
                throw new badSquareRootInput(); // throw an exception if there are only square
            // root signs present and there are > 1 of them (deals with bad inputs such as √5√10√)


            Double[] doubleArray = new Double[numberArray.length];
            // create a new 1d array, concatenate each element of an individual number's array,
            // parse that sting to a double, and add it to the newly created array.
            for (int x = 0; x < numberArray.length; x++) {
                int y = 0;
                String number = "";
                while (numberArray[x][y] != null) {
                    number = number.concat(numberArray[x][y]);
                    y++;
                }
                if (number.contains("√")) {
                    if (number.startsWith("√")) {
                        number = number.replace("√", ""); // do square root calculations here
                        doubleArray[x] = Math.sqrt(Double.parseDouble(number));
                    } else
                        throw new badSquareRootInput();
                } else if (number.contains("-")) {
                    if (number.startsWith("-")) {
                        number = number.replace("-", "");
                        doubleArray[x] = -1 * (Double.parseDouble(number));
                    }
                    else
                        throw new badNegativeSignInput(); // throw an exception if the negative sign
                        // comes after the number (ex 4 + 8-)
                } else if (number != "")
                    doubleArray[x] = Double.parseDouble(number);

            }

            // Make two new arrays and fill them with only the non-null elements of the original
            // operatorsArray and doubleArray
            Double [] newDoubleArray = new Double[outerNumberCount + 1];
            int index = 0;
            for (int x = 0; x < doubleArray.length; x++){
                if (doubleArray[x] != null)
                    newDoubleArray[index++] = doubleArray[x];
            }
            String [] newOperatorsArray = new String[operatorsCount];
            index = 0;
            for (int x = 0; x < operatorsArray.length; x ++) {
                if (operatorsArray[x] != null)
                    newOperatorsArray[index++] = operatorsArray[x];
            }

            try {
                textBox.setText(textBox.getText() + " = " +
                calculate(newOperatorsArray, newDoubleArray));
            }
            catch (divideByZeroException e) {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, e.getMessage(), duration);
                toast.show(); // use a toast to display the error message to the user
            }

        }
        catch (firstOperatorException | operatorsInARowException | endsWithOperatorException |
                noOperatorsException | badSquareRootInput | badNegativeSignInput e) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, e.getMessage(), duration);
            toast.show(); // use a toast to display the error message to the user
        }
        catch (onlySquareRootException e) { // deals with equations that contain no operators and
            // only square root functions
            String number = "";
            for (int x = 0; x < numberArray.length; x++) {
                for (int y = 0; y < numberArray.length; y++) {
                    if (numberArray[x][y] != null) {
                        number = number.concat(numberArray[x][y]);
                    }
                }
            }
            if (number.startsWith("√")) {
                number = number.replace("√", "");
                number = Double.toString(Math.sqrt((Double.parseDouble(number))));
                textBox.setText(textBox.getText() + " = " + number);
            }

        }


    }
    public String calculate (String [] operators, Double [] numbers) throws divideByZeroException {
        // takes the newDoubleArray and the newOperatorsArray as arguments and returns a string
        for (int x = 0; x < operators.length; x ++)
            if (operators[x].equals("x") || operators[x].equals("/")){
                /** To preserve order of operations, first deal with all the numbers that are being
                 *  multiplied or divided. Move the temporary answer into the second index and
                 *  replace the first index with Nan so we can keep track of which numbers remain.
                 */
                switch (operators[x]) {
                    case "/":
                        if (numbers[x + 1] == 0)
                            throw new divideByZeroException();
                        numbers[x+1] = numbers[x] / numbers[x+1];
                        numbers[x] = Double.NaN;
                        break;
                    case "x":
                        numbers[x+1] = numbers[x] * numbers[x+1];
                        numbers[x] = Double.NaN;
                        break;
                }
            }
        int y = 0;
        int w = 1;
        for (int x = 0; x < numbers.length -1 ; x ++) {
            if (numbers[y].isNaN()) // keep moving through the array until you find a number
                do {
                    y++;
                } while (numbers[y].isNaN());
            if (y == (numbers.length -1))
                break;
            switch (operators[x]) { // now we deal with addition and subtraction
                case "–":
                    if (numbers[y+w].isNaN())
                        do {
                            w++;
                        } while (numbers[y+w].isNaN());
                    numbers[y+w] = numbers[y]-numbers[y+w];
                    y = y+w;
                    w = 1;
                    break;
                case "+":
                    if (numbers[y+w].isNaN())
                        do {
                            w++;
                        } while (numbers[y+w].isNaN());
                    numbers[y+w] = numbers[y]+ numbers[y+w];
                    y = y+w;
                    w = 1;
                    break;
            }
        }
        String answer = "";
        if (numbers[numbers.length-1]%1 == 0){ // if the answer is a whole number, cast it to an int
            int Int = numbers[numbers.length-1].intValue();
            answer = String.valueOf(Int);
            return answer;
        }
        else {
            answer = String.valueOf(numbers[numbers.length-1]);
            return answer;
        }
    }


}
