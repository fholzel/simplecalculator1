
package com.codecademy.simplecalculator1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.lang.Math;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Double.parseDouble;
import static java.math.RoundingMode.HALF_UP;

import com.codecademy.simplecalculator1.databinding.ActivityMainBinding;
import com.codecademy.simplecalculator1.databinding.ListStackTextBinding;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivityTag";
    private static Integer bigDecimalPrecision = 16;
    private static Integer bigDecimalStack = 12;
    private ActivityMainBinding binding;
    private ListStackTextBinding bindingStackText;
    private ListView listViewStack;
    private ArrayList<HashMap<String, Object>> listStack = new ArrayList<>();
    public static ArrayList<Button> buttonViews = new ArrayList<>();
    public static ArrayList<Button> keyboardViews = new ArrayList<>();
    public static ArrayList<Object> stackString = new ArrayList<>();
    public static ArrayList<Object> stackValues = new ArrayList<>();
    // operation with 2 arguments
    // remove operand from top of stack
    // remove operand from top of stack
    // add result to top of stack
    // add empty string to bottom of stack
    public void onClick1Operators(BigDecimal stack0) {
        stack0 = stack0.setScale(bigDecimalPrecision, HALF_UP);
        stack0.stripTrailingZeros();
        Log.d(TAG, "stack0         : " + stack0);
        stackValues.remove(0);
        stackValues.add(0, stack0);
    }
    public void onClick2Operators(BigDecimal stack0) {
        stack0 = stack0.setScale(bigDecimalPrecision, HALF_UP);
        stack0.stripTrailingZeros();
        Log.d(TAG, "stack0         : " + stack0);
        stackValues.remove(0);
        stackValues.remove(0);
        stackValues.add(0, stack0);
        stackValues.add(7, getString(R.string.empty_stack));
    }
    // error message if operation requires 2 operands and less available
    public void onClickTooFewArguments() {
        AlertDialog.Builder argCountDialogBuilder = new AlertDialog.Builder(MainActivity.this,
                R.style.CustomDialog);
        argCountDialogBuilder.setCancelable(false);
        argCountDialogBuilder.setTitle("Error:");
        argCountDialogBuilder.setMessage("Too Few Arguments");
        argCountDialogBuilder.setNeutralButton("Continue", new DialogInterface.OnClickListener() {
            @Override
                public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        // positive button response : setPositiveButton
        // negative button response : setNegativeButton
        AlertDialog dialog = argCountDialogBuilder.create();
        dialog.show();
        Button buttonNeutral = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
    }
    // display stack
    public void onClickDisplayStack() {
        createArrayList(listStack, stackValues, stackString, "stackName", "stackString");
        SimpleAdapter simpleAdapterStack = (SimpleAdapter) listViewStack.getAdapter();
        if (simpleAdapterStack != null) {
            simpleAdapterStack.notifyDataSetChanged();
        }
    }
    private void createArrayList(
            ArrayList<HashMap<String, Object>> arrayList,
            ArrayList<Object> nameStringArray,
            ArrayList<Object> nameObjectArray,
            String nameString,
            String nameObject) {
        // if any items in list then remove
        arrayList.removeAll(arrayList);
        // loop and add data in hashmap
        // add map including data into arrayList as list item
        for (int i = 0; i < nameStringArray.size(); i++) {
            // create object of hashmap class
            HashMap<String, Object> map = new HashMap<>();
            // data entry : key : value
            Object nameStringI, nameObjectI;
            BigDecimal bigDecimalI;
            nameStringI = nameStringArray.get(i);
            nameObjectI = nameObjectArray.get(i);
            if (nameStringI instanceof BigDecimal) {
                bigDecimalI = (BigDecimal) (nameStringI);
                bigDecimalI = bigDecimalI.setScale(bigDecimalStack, HALF_UP);
                bigDecimalI = bigDecimalI.stripTrailingZeros();
                map.put(nameString, bigDecimalI);
                map.put(nameObject, nameObjectI);
            } else {
                map.put(nameString, nameStringI);
                map.put(nameObject, nameObjectI);
            }
            arrayList.add(0, map);
        }
    }
    @Override
    public void onClick(View v) {
        Integer opTag;
        Integer opButton;
        Boolean correctArgCount;
        String inputLine;
        opTag = (Integer) v.getTag();
        opButton = (Integer) v.getId();
        BigDecimal stackResult, stack0, stack1;
        Double double0, double1;
        stackResult = new BigDecimal(-1.0);
        stack0 = new BigDecimal(-1.0);
        stack1 = new BigDecimal(-1.0);
        double0 = new Double(-1.0);
        double1 = new Double(-1.0);
        correctArgCount = false;
        // Log.d(TAG,"correct    : " + correctArgCount);
        switch (opTag) {
            // 1/x
            case 4:
                // step 1 : if entry string not empty
                if (!binding.number.getText().toString().isEmpty()) {
                    try {
                        stackResult = BigDecimal.valueOf(parseDouble(binding.number.getText().toString()));
                        stackResult = stackResult.setScale(bigDecimalPrecision, HALF_UP);
                        stackResult.stripTrailingZeros();
                        Log.d(TAG, "stackresult    : " + stackResult);
                        correctArgCount = true;
                        Log.d(TAG, "operands       : inputline");
                        if (stackValues.size() >= 8) {
                            stackValues.remove(7);
                        }
                        stackValues.add(0, stackResult);
                        binding.number.setText("");
                        // else display too few arguments popup if conversion error
                    } catch (NumberFormatException e) {
                        onClickTooFewArguments();
                    }
                }
                // if stack0 available operation can be done
                if (stackValues.get(0) instanceof BigDecimal) {
                    stack0 = (BigDecimal) stackValues.get(0);
                    stack1 = new BigDecimal(-1.0);
                    double0 = stack0.doubleValue();
                    double1 = new Double(-1.0);
                    correctArgCount = true;
                    Log.d(TAG, "operands       : stack0");
                    // else display too few arguments popup
                } else {
                    onClickTooFewArguments();
                }
                break;
            // add
            // subtract
            // multiply
            // divide
            // y^x
            case 0:
            case 1:
            case 2:
            case 3:
            case 5:
                // step 1 : if entry string not empty
                if (!binding.number.getText().toString().isEmpty()) {
                    try {
                        stackResult = BigDecimal.valueOf(parseDouble(binding.number.getText().toString()));
                        stackResult = stackResult.setScale(bigDecimalPrecision, HALF_UP);
                        stackResult.stripTrailingZeros();
                        Log.d(TAG, "stackresult    : " + stackResult);
                        correctArgCount = true;
                        Log.d(TAG, "operands       : stack0 and inputline");
                        if (stackValues.size() >= 8) {
                            stackValues.remove(7);
                        }
                        stackValues.add(0, stackResult);
                        binding.number.setText("");
                    // else display too few arguments popup if conversion error
                    } catch (NumberFormatException e) {
                        onClickTooFewArguments();
                    }
                }
                // if stack0 and stack1 available operation can be done
                if ((stackValues.get(1) instanceof BigDecimal) && (stackValues.get(0) instanceof BigDecimal)) {
                    stack0 = (BigDecimal) stackValues.get(0);
                    stack1 = (BigDecimal) stackValues.get(1);
                    double0 = stack0.doubleValue();
                    double1 = stack1.doubleValue();
                    correctArgCount = true;
                    Log.d(TAG, "operands       : stack0 and stack1");
                // else display too few arguments popup
                } else {
                    onClickTooFewArguments();
                }
                break;
            // delete
            case 98:
                // if stack0 available operation can be done
                if (stackValues.get(0) instanceof BigDecimal) {
                    correctArgCount = true;
                    Log.d(TAG, "operands       : stack0");
                // else display too few arguments popup
                } else {
                    onClickTooFewArguments();
                }
                break;
            // enter
            case 99:
                // if entry string not empty
                if (!binding.number.getText().toString().isEmpty()) {
                    try {
                        stackResult = BigDecimal.valueOf(parseDouble(binding.number.getText().toString()));
                        stackResult = stackResult.setScale(bigDecimalPrecision, HALF_UP);
                        stackResult = stackResult.stripTrailingZeros();
                        correctArgCount = true;
                        Log.d(TAG, "operands       : inputline");
                        Log.d(TAG, "stackresult    : " + stackResult);
                        // else display too few arguments popup if conversion error
                    } catch (NumberFormatException e) {
                        onClickTooFewArguments();
                    }
                // duplicate top of stack if available
                } else {
                    if (stackValues.get(0) instanceof BigDecimal) {
                        stackResult = (BigDecimal) stackValues.get(0);
                        correctArgCount = true;
                        Log.d(TAG, "operands       : stack0");
                    // else display too few arguments popup
                    } else {
                        onClickTooFewArguments();
                    }
                // convert entry string to operand if possible
                }
                break;
            // keys 0 to 9
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
                inputLine = binding.number.getText().toString() + (opTag - 100);
                binding.number.setText(inputLine);
                break;
            // period
            case 110:
                inputLine = binding.number.getText().toString() + ".";
                binding.number.setText(inputLine);
                break;
            default:
                break;
        }
        if (correctArgCount) {
            switch (opTag) {
                // add
                case 0:
                    stackResult = stack1.add(stack0);
                    onClick2Operators(stackResult);
                    break;
                // subtract
                case 1:
                    stackResult = stack1.subtract(stack0);
                    onClick2Operators(stackResult);
                    break;
                // multiply
                case 2:
                    stackResult = stack1.multiply(stack0);
                    onClick2Operators(stackResult);
                    break;
                // divide
                case 3:
                    stackResult = stack1.divide(stack0, bigDecimalPrecision, HALF_UP);
                    onClick2Operators(stackResult);
                    break;
                case 4:
                    stackResult = new BigDecimal(1.0).divide(stack0, bigDecimalPrecision, HALF_UP);
                    onClick1Operators(stackResult);
                    break;
                case 5:
                    stackResult = BigDecimal.valueOf(Math.exp(double0 * Math.log(double1)));
                    onClick2Operators(stackResult);
                    break;
                // delete
                case 98:
                    stackValues.remove(0);
                    stackValues.add(7, getString(R.string.empty_stack));
                    break;
                // enter
                case 99:
                    if (stackValues.size() >= 8) {
                        stackValues.remove(7);
                    }
                    stackValues.add(0, stackResult);
                    binding.number.setText("");
                    break;
                default:
                    break;
            }
        }
        Log.d(TAG, "stackN         : " + stackValues.size());
        Log.d(TAG, "opTag          : " + opTag);
        Log.d(TAG, "opButton       : " + opButton);
        /*
        for (int i = 0; i < 8; i++) {
            Log.d(TAG, "stack" + i + "    : " + stackValues.get(i));
        }
        */
        // Log.d(TAG,"correct    : " + correctArgCount);
        onClickDisplayStack();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        bindingStackText = ListStackTextBinding.inflate((getLayoutInflater()));
        View view = binding.getRoot();
        setContentView(view);
        listViewStack = binding.stackView;
        binding.add.setOnClickListener((MainActivity.this));
        binding.subtract.setOnClickListener((MainActivity.this));
        binding.multiply.setOnClickListener((MainActivity.this));
        binding.divide.setOnClickListener((MainActivity.this));
        binding.x1.setOnClickListener((MainActivity.this));
        binding.xy.setOnClickListener((MainActivity.this));
        binding.delete.setOnClickListener((MainActivity.this));
        binding.enter.setOnClickListener((MainActivity.this));
        binding.key0.setOnClickListener((MainActivity.this));
        binding.key1.setOnClickListener((MainActivity.this));
        binding.key2.setOnClickListener((MainActivity.this));
        binding.key3.setOnClickListener((MainActivity.this));
        binding.key4.setOnClickListener((MainActivity.this));
        binding.key5.setOnClickListener((MainActivity.this));
        binding.key6.setOnClickListener((MainActivity.this));
        binding.key7.setOnClickListener((MainActivity.this));
        binding.key8.setOnClickListener((MainActivity.this));
        binding.key9.setOnClickListener((MainActivity.this));
        binding.keyPeriod.setOnClickListener((MainActivity.this));
        binding.keySpace.setOnClickListener((MainActivity.this));
        binding.add.setTag(0);
        binding.subtract.setTag(1);
        binding.multiply.setTag(2);
        binding.divide.setTag(3);
        binding.x1.setTag(4);
        binding.xy.setTag(5);
        binding.delete.setTag(98);
        binding.enter.setTag(99);
        binding.key0.setTag(100);
        binding.key1.setTag(101);
        binding.key2.setTag(102);
        binding.key3.setTag(103);
        binding.key4.setTag(104);
        binding.key5.setTag(105);
        binding.key6.setTag(106);
        binding.key7.setTag(107);
        binding.key8.setTag(108);
        binding.key9.setTag(109);
        binding.keyPeriod.setTag(110);
        binding.keySpace.setTag(111);
        buttonViews.add(binding.add);
        buttonViews.add(binding.subtract);
        buttonViews.add(binding.multiply);
        buttonViews.add(binding.divide);
        buttonViews.add(binding.x1);
        buttonViews.add(binding.xy);
        buttonViews.add(binding.enter);
        buttonViews.add(binding.delete);
        keyboardViews.add(binding.key0);
        keyboardViews.add(binding.key1);
        keyboardViews.add(binding.key2);
        keyboardViews.add(binding.key3);
        keyboardViews.add(binding.key4);
        keyboardViews.add(binding.key5);
        keyboardViews.add(binding.key6);
        keyboardViews.add(binding.key7);
        keyboardViews.add(binding.key8);
        keyboardViews.add(binding.key9);
        keyboardViews.add(binding.keyPeriod);
        keyboardViews.add(binding.keySpace);
        for (int i = 1; i <= 8; i++) {
            stackValues.add(getString(R.string.empty_stack));
            stackString.add("S " + i + ":");
        }
        onClickDisplayStack();
        // hashmap : key = string : value : object
        // private ArrayList<HashMap<String, Object>> listStack = new ArrayList<>();
        // second paramter of the simpleadapter
        createArrayList(listStack, stackValues, stackString, "stackName", "stackString");
        // create string type array (from) which contains
        // column names for each view in each row of the list
        // forth paramter of the simpleadapter
        String[] fromArrayNamesStack = { "stackName", "stackString"};
        // create int type array (to) which constrains
        // ids for each view in each row of the list
        // fifth paramter of the simpleadapter
        int toArraysIdsStack[] = { bindingStackText.textViewRight.getId(), bindingStackText.textViewLeft.getId() };
        // create and object of simpleaapter class with required parameters
        SimpleAdapter simpleAdapterStack = new SimpleAdapter(
                getApplicationContext(),
                listStack,
                R.layout.list_stack_text,
                fromArrayNamesStack,
                toArraysIdsStack
        );
        // set adapter to listview
        listViewStack.setAdapter(simpleAdapterStack);
        // simpleAdapterStack.notifyDataSetChanged();
    }
}
