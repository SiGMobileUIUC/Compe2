package company.com.compe2code;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private int gridWidth = 4;
    private int gridHeight = 4;
    GridLayout gridLayout;
    Button[][] buttons;
    int[][] numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridLayout = (GridLayout) findViewById(R.id.grid_layout);
        gridLayout.setColumnCount(gridWidth);
        gridLayout.setRowCount(gridHeight);
        buttons = new Button[gridHeight][gridWidth];
        numbers = new int[gridHeight][gridWidth];
        generateCells();
        generateNumbers();

    }

    private void generateCells(){
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                Button button = new Button(MainActivity.this);
                gridLayout.addView(button);
                button.setVisibility(View.VISIBLE);
                final int row = i, col = j;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonClick(row, col);
                    }
                });
                buttons[i][j] = button;
            }
        }
    }

    private void generateNumbers(){
        int total = gridHeight * gridWidth;
        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            nums.add(i);
        }
        Collections.shuffle(nums, new Random());
        int row = 0, col = 0;
        for (int i : nums) {
            numbers[row][col] = i;
            buttons[row][col].setText(i != 0 ? String.valueOf(i) : "");
            col += 1;
            if (col == gridWidth){
                col = 0;
                row += 1;
            }
        }
    }

    private void buttonClick(int row, int col){
        if (numbers[row][col] != 0){
            if (row > 0 && numbers[row - 1][col] == 0){
                swap(row, col, row - 1, col);
            } else if (col > 0 && numbers[row][col - 1] == 0){
                swap(row, col, row, col - 1);
            } else if (row < gridHeight - 1 && numbers[row + 1][col] == 0){
                swap(row, col, row + 1, col);
            } else if (col < gridWidth - 1 && numbers[row][col + 1] == 0){
                swap(row, col, row, col + 1);
            }
            if (isWin()){
                Toast.makeText(MainActivity.this, "WIN!!!!!!!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void swap(int startRow, int startCol, int endRow, int endCol){
        numbers[startRow][startCol] += numbers[endRow][endCol];
        numbers[endRow][endCol] = numbers[startRow][startCol] - numbers[endRow][endCol];
        numbers[startRow][startCol] -= numbers[endRow][endCol];

        buttons[startRow][startCol].setText(numbers[startRow][startCol] != 0 ?
                String.valueOf(numbers[startRow][startCol]) : "");
        buttons[endRow][endCol].setText(numbers[endRow][endCol] != 0 ?
                String.valueOf(numbers[endRow][endCol]) : "");
    }


    private boolean isWin(){
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                if ((i == gridHeight - 1 && j == gridWidth - 1) ){
                    continue;
                }
                if ((numbers[i][j] != i * gridHeight + j + 1)){
                    return false;
                }
            }
        }
        return true;
    }
}
