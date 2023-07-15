# Colored Sudoku

This project aims to create an advanced version of the Sudoku game that includes coloring of cells. The rules of this advanced version are as follows:

- Each cell of the grid should contain a number and a color.
- Similar to the classic Sudoku, each row and column of an n×n grid must contain all numbers from 1 to n exactly once.
- Adjacent cells (if any: left, right, top, bottom) must have different colors. For example, if one cell is yellow, its neighboring cells cannot be assigned the same color.
- Colors have priority within a neighborhood. If a cell has more than four neighboring cells (if existent), the color with higher priority must correspond to a larger number.

## Solution Approach

To solve this problem, we employ the Backtracking algorithm along with the MRV (Minimum Remaining Values) and Degree heuristics. Additionally, we utilize the Checking Forward technique to update the domain values of other variables during value assignment.


