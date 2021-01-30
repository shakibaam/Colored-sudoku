import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

class saveBoard {
    public board2 board;
    public int i;
    public int j;
    ArrayList<String> attributed = new ArrayList<>();

    public saveBoard(board2 board, int i, int j) {
        this.board = board;
        this.i = i;
        this.j = j;


    }

    public void setAttributed(String str) {
        attributed.add(str);
        board.cellMap[i][j].addAttributed(str);
    }
}


class cell2 {

    public int num;
    public char color;
    public String numAndColor;
    public boolean done = false;
    public ArrayList<Integer> numbersDomain = new ArrayList();
    public ArrayList<Character> colorsDomain = new ArrayList();
    public ArrayList<String> combineDomain = new ArrayList<>();
    public ArrayList<String> attributed = new ArrayList<>();

    public cell2() {
    }

    public cell2(cell2 cell) {
        this.num = cell.num;
        this.color = cell.color;
        this.numAndColor = cell.numAndColor;
        this.done = cell.done;
        this.numbersDomain = new ArrayList<>(cell.numbersDomain);
        this.colorsDomain = new ArrayList<>(cell.colorsDomain);
        this.combineDomain = new ArrayList<>(cell.combineDomain);
        this.attributed = new ArrayList<>(cell.attributed);
    }

    @Override
    public String toString() {
        return numAndColor;
    }


    public void removeFromNumbersDomain(int x) {
        numbersDomain.remove(Integer.valueOf(x));
    }


    public void setCombineDomain() {

        for (int i = 0; i < numbersDomain.size(); i++) {
            for (int j = 0; j < colorsDomain.size(); j++) {
                String temp = numbersDomain.get(i) + "" + colorsDomain.get(j);

                combineDomain.add(temp);
            }
        }
    }

    public void addAttributed(String str) {
        System.out.println("attributing " + str);
        attributed.add(str);
    }
}


class board2 implements Cloneable {
    public cell2[][] cellMap;
    public int size;


    public board2(int n) {
        cellMap = new cell2[n][n];
        this.size = n;


    }

    public board2(board2 board) {
        this.size = board.size;
        this.cellMap = new cell2[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.cellMap[i][j] = board.cellMap[i][j];
            }
        }

    }

    public static board2 newInstance(board2 board2) {
        return new board2(board2);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(cellMap[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printNumbersDomains() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(cellMap[i][j].numbersDomain + " ");
            }
            System.out.println();
        }
    }

    public void printColorsDomain() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(cellMap[i][j].colorsDomain + " ");
            }
            System.out.println();
        }
    }

    public void printCombineDomain() {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(cellMap[i][j].combineDomain + " ");
            }
            System.out.println();
        }
    }

    public boolean someThingEmpty() {
        boolean flag = false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cellMap[i][j].done == false && cellMap[i][j].combineDomain.size() == 0) {
                    System.out.println(i + " " + j);
                    flag = true;
                }
            }
        }

        return flag;
    }
}


public class soduku {
    public static ArrayList<Character> Colors = new ArrayList<>();

    public void FC(int i, int j, board2 board) {
        String updated = board.cellMap[i][j].numAndColor;

        int number = updated.charAt(0) - '0';

        // forward checking numbers
        System.out.println();
        for (int k = 0; k < board.size; k++) {
            if (k != j) {

                if (board.cellMap[i][k].done == false) {

                    for (int l = board.cellMap[i][k].combineDomain.size() - 1; l >= 0; l--) {
//                        System.out.print(board.cellMap[i][k].combineDomain.get(l).charAt(0)+" ");
                        if (board.cellMap[i][k].combineDomain.get(l).charAt(0) == updated.charAt(0)) {

                            board.cellMap[i][k].combineDomain.remove(l);
                        }

                    }
                }


            }
        }

        for (int k = 0; k < board.size; k++) {
            if (k != i) {

                if (board.cellMap[k][j].done == false) {

                    for (int l = board.cellMap[k][j].combineDomain.size() - 1; l >= 0; l--) {
                        if (board.cellMap[k][j].combineDomain.get(l).charAt(0) == updated.charAt(0)) {
                            board.cellMap[k][j].combineDomain.remove(l);
                        }
                    }
                }

            }
        }

        //forward checking colors

        if (i - 1 >= 0) {
            if (board.cellMap[i - 1][j].done == false) {

                for (int k = board.cellMap[i - 1][j].combineDomain.size() - 1; k >= 0; k--) {
                    if (board.cellMap[i - 1][j].combineDomain.get(k).charAt(1) == updated.charAt(1)) {
                        board.cellMap[i - 1][j].combineDomain.remove(k);
                    } else {
                        int updatedColorindex = Colors.indexOf(updated.charAt(1));
                        int index2 = Colors.indexOf(board.cellMap[i - 1][j].combineDomain.get(k).charAt(1));
                        int tempNum = board.cellMap[i - 1][j].combineDomain.get(k).charAt(0) - '0';
                        if (number < tempNum) {
                            if (index2 > updatedColorindex) {
                                board.cellMap[i - 1][j].combineDomain.remove(k);
                            }

                        } else if (number > tempNum) {
                            if (index2 < updatedColorindex) {
                                board.cellMap[i - 1][j].combineDomain.remove(k);
                            }
                        }
                    }
                }
            }
        }

        if (i + 1 < board.size) {

            if (board.cellMap[i + 1][j].done == false) {

                for (int k = board.cellMap[i + 1][j].combineDomain.size() - 1; k >= 0; k--) {
                    if (board.cellMap[i + 1][j].combineDomain.get(k).charAt(1) == updated.charAt(1)) {
                        board.cellMap[i + 1][j].combineDomain.remove(k);
                    } else {
                        int updatedColorindex = Colors.indexOf(updated.charAt(1));
                        int index2 = Colors.indexOf(board.cellMap[i + 1][j].combineDomain.get(k).charAt(1));
                        int tempNum = board.cellMap[i + 1][j].combineDomain.get(k).charAt(0) - '0';
                        if (number < tempNum) {
                            if (index2 > updatedColorindex) {
                                board.cellMap[i + 1][j].combineDomain.remove(k);
                            }

                        } else if (number > tempNum) {
                            if (index2 < updatedColorindex) {
                                board.cellMap[i + 1][j].combineDomain.remove(k);
                            }
                        }
                    }
                }
            }

        }

        if (j - 1 >= 0) {


            if (board.cellMap[i][j - 1].done == false) {

                for (int k = board.cellMap[i][j - 1].combineDomain.size() - 1; k >= 0; k--) {
                    if (board.cellMap[i][j - 1].combineDomain.get(k).charAt(1) == updated.charAt(1)) {
                        board.cellMap[i][j - 1].combineDomain.remove(k);
                    } else {
                        int updatedColorindex = Colors.indexOf(updated.charAt(1));
                        int index2 = Colors.indexOf(board.cellMap[i][j - 1].combineDomain.get(k).charAt(1));
                        int tempNum = board.cellMap[i][j - 1].combineDomain.get(k).charAt(0) - '0';
                        if (number < tempNum) {
                            if (index2 > updatedColorindex) {
                                board.cellMap[i][j - 1].combineDomain.remove(k);
                            }

                        } else if (number > tempNum) {
                            if (index2 < updatedColorindex) {
                                board.cellMap[i][j - 1].combineDomain.remove(k);
                            }
                        }
                    }
                }
            }
        }

        if (j + 1 < board.size) {


            if (board.cellMap[i][j + 1].done == false) {

                for (int k = board.cellMap[i][j + 1].combineDomain.size() - 1; k >= 0; k--) {
                    if (board.cellMap[i][j + 1].combineDomain.get(k).charAt(1) == updated.charAt(1)) {
                        board.cellMap[i][j + 1].combineDomain.remove(k);
                    } else {
                        int updatedColorindex = Colors.indexOf(updated.charAt(1));
                        int index2 = Colors.indexOf(board.cellMap[i][j + 1].combineDomain.get(k).charAt(1));
                        int tempNum = board.cellMap[i][j + 1].combineDomain.get(k).charAt(0) - '0';
                        if (number < tempNum) {
                            if (index2 > updatedColorindex) {
                                board.cellMap[i][j + 1].combineDomain.remove(k);
                            }

                        } else if (number > tempNum) {
                            if (index2 < updatedColorindex) {
                                board.cellMap[i][j + 1].combineDomain.remove(k);
                            }
                        }
                    }
                }
            }
        }


    }

    public int[] MRV(board2 board) {

           /*
        array to save each cell  domain size
         */
        int domainNum[][] = new int[board.size][board.size];
        int[] returns = new int[2];


        for (int i = 0; i < board.size; i++) {
            for (int j = 0; j < board.size; j++) {
                if (board.cellMap[i][j].done == true) {
                    domainNum[i][j] = -1;
                } else {
                    domainNum[i][j] = board.cellMap[i][j].combineDomain.size();
                }
            }
        }


        /*
        going to find minimum remaing value
         */

        int min = 100;
        for (int i = 0; i < board.size; i++) {
            for (int j = 0; j < board.size; j++) {
                if (domainNum[i][j] != (-1)) {
                    if (domainNum[i][j] <= min) {
                        min = domainNum[i][j];
                    }
                }
            }
        }

           /*
        going to count how many cell have this MRV
         */
        int count = 0;

        for (int i = 0; i < board.size; i++) {
            for (int j = 0; j < board.size; j++) {
                if (domainNum[i][j] == min) {
                    count++;
                }
            }
        }

        System.out.println("Domains:");

        for (int i = 0; i < board.size; i++) {
            for (int j = 0; j < board.size; j++) {
                System.out.print(domainNum[i][j] + " ");
            }
            System.out.println();
        }


        if (count > 1) {
            //must call degree hioristic
            System.out.println("call degree");
            System.out.println(count);

            return degree(board);
        } else if (count == 1) {

            int x = 0, y = 0;
            for (int i = 0; i < board.size; i++) {
                for (int j = 0; j < board.size; j++) {
                    if (domainNum[i][j] == min) {
                        x = i;
                        y = j;
                    }
                }
            }
            System.out.println("going to choose:" + x + " " + y);


            returns[0] = x;
            returns[1] = y;


        }

        return returns;
    }

    public int[] degree(board2 board) {
        int degrees[][] = new int[board.size][board.size];
        int[] returns = new int[2];

                /*/
        going to calculate each cell degree
         */

        for (int i = 0; i < board.size; i++) {

            for (int j = 0; j < board.size; j++) {

                if (board.cellMap[i][j].done == true) {
                    degrees[i][j] = -1;

                } else {

                    int cell_count = 0;

                /*
                degree in row
                 */

                    for (int k = 0; k < board.size; k++) {
                        if (k != j) {
                            if (board.cellMap[i][k].done == false) {

                                cell_count++;
                            }

                        }
                    }


                     /*
                degree in column
                 */
                    for (int k = 0; k < board.size; k++) {
                        if (k != i) {
                            if (board.cellMap[k][j].done == false) {

                                cell_count++;
                            }
                        }
                    }


                    degrees[i][j] = cell_count;


                }

            }
        }


//        System.out.println("Degrees :");
//        for (int i = 0; i <board.size ; i++) {
//            for (int j = 0; j <board.size ; j++) {
//                System.out.print(degrees[i][j]+" ");
//            }
//            System.out.println();
//        }

              /*
        going to say which cell has max degree
         */
        int max_degree = degrees[0][0];
        for (int i = 0; i < board.size; i++) {
            for (int j = 0; j < board.size; j++) {
                if (degrees[i][j] != -1 && degrees[i][j] >= max_degree) {
                    max_degree = degrees[i][j];
                }
            }
        }
//        System.out.println("max: "+max_degree);


           /*
        going to calculate how many cell have this max degree
         */
        int count = 0;
        for (int i = 0; i < board.size; i++) {
            for (int j = 0; j < board.size; j++) {
                if (degrees[i][j] == max_degree) {
                    count++;
                }
            }
        }


        if (count == 1) {
            int x, y;
            for (int i = 0; i < board.size; i++) {
                for (int j = 0; j < board.size; j++) {
                    if (degrees[i][j] == max_degree) {
                        x = i;
                        y = j;

                        System.out.println("going to choose:" + x + " " + y);
//                        System.out.println(board.cellMap[x][y]);
                        returns[0] = x;
                        returns[1] = y;
//                        return;
                    }
                }
            }
        } else if (count > 1) {

            int x, y;
            for (int i = 0; i < board.size; i++) {
                for (int j = 0; j < board.size; j++) {
                    if (degrees[i][j] == max_degree) {
                        x = i;
                        y = j;

                        System.out.println("going to choose:" + x + " " + y);
//                        System.out.println(board.cellMap[x][y]);
                        returns[0] = x;
                        returns[1] = y;
                        return returns;
                    }
                }
            }
        }


        return returns;
    }

    public String update(int i, int j, board2 board, ArrayList<String> strings) {
        ArrayList<String> temp = (ArrayList<String>) board.cellMap[i][j].attributed.clone();
        ArrayList<String> combineCopy = (ArrayList<String>) board.cellMap[i][j].combineDomain.clone();
        System.out.println(combineCopy);
        Iterator iterator = combineCopy.iterator();

        for (int k = 0; k < strings.size(); k++) {

//            System.out.println(strings.get(k) + "###");

            for (int l = combineCopy.size() - 1; l >= 0; l--) {
                if (combineCopy.get(l).equals(strings.get(k))) {
//                    System.out.println(combineCopy.get(l));
                    combineCopy.remove(l);
                }

            }
        }
//        System.out.println(combineCopy);


        board.cellMap[i][j].numAndColor = combineCopy.get(0);
        board.cellMap[i][j].done = true;
        System.out.println("after Updating: ");
        board.printBoard();

        return board.cellMap[i][j].numAndColor;
    }

    public boolean goalTest(board2 board) {
        boolean flag = true;
        for (int i = 0; i < board.size; i++) {
            for (int j = 0; j < board.size; j++) {
                if (board.cellMap[i][j].done == false) {
                    flag = false;
                }
            }
        }
        return flag;
    }

    public int[] heuristic(board2 board) {

        return MRV(board);
    }


    public void solve2(board2 board) {
        boolean flag = true;
        int count = 0;
        ArrayList<saveBoard> saveBoards = new ArrayList<>();
        boolean goForward = true;
        boolean backTrack = false;
        while (flag) {


            if (goForward) {
                System.out.println("going forward");

                int[] goingToUpdate = heuristic(board);
                int n = board.size;
                board2 cloneBoard = new board2(n);
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {

                        cloneBoard.cellMap[i][j] = new cell2(board.cellMap[i][j]);
                    }
                }

                saveBoard saveBoard = new saveBoard(cloneBoard, goingToUpdate[0], goingToUpdate[1]);

                String updatedNumAndColor = update(goingToUpdate[0], goingToUpdate[1], board, saveBoard.attributed);
                saveBoard.setAttributed(updatedNumAndColor);
                FC(goingToUpdate[0], goingToUpdate[1], board);
                saveBoards.add(saveBoard);
                for (int i = 0; i <saveBoards.size() ; i++) {
                    saveBoards.get(i).board.printBoard();
                    System.out.println("^^^^");
                }
                System.out.println("FC: ");

                board.printCombineDomain();
                if (goalTest(board)) {
                    System.out.println("Goal Here!!");
                    board.printBoard();
                    return;
                }


                if (board.someThingEmpty()) {
                    backTrack = true;

                }
                else {

                }
            }
            if (backTrack) {

//                    saveBoard temp=backTrack(saveBoards);
                System.out.println("Back Tracking..");

                boolean flag1 = true;
                int i = saveBoards.size() - 1;
                int count1 = 0;
                saveBoard temp = null;

//                for (int j = 0; j <saveBoards.size() ; j++) {
//                   saveBoards.get(j).board.printBoard();
//                    System.out.println("^^^^^^");
//                }

                while (flag1) {
                    temp = saveBoards.get(i);
//                    System.out.println("******");
//                    temp.board.printBoard();
                    ArrayList<String> combineCopy = (ArrayList<String>) temp.board.cellMap[temp.i][temp.j].combineDomain.clone();
                    Iterator iterator = combineCopy.iterator();
                    for (int j = 0; j < temp.attributed.size(); j++) {
                        System.out.println(temp.attributed.get(j) + ";;");
                        for (int k = combineCopy.size() - 1; k >= 0; k--) {
                            if (combineCopy.get(k).equals(temp.attributed.get(j))) {
                                System.out.println("removing");
                                combineCopy.remove(k);
                            }
                        }
//                            while (iterator.hasNext()) {
//                                String str = (String) iterator.next();
//                                if (str.equals(temp.attributed.get(j))) {
//                                    System.out.println("removing "+str);
//                                    iterator.remove();
//                                }
//                            }
                    }


                    if (combineCopy.size() != 0) {

                        System.out.println("save board size : "+saveBoards.size());


                        int x=count1;
                        int j=saveBoards.size() - 1;
                        while (count1>=0){
                            saveBoards.get(j).board.cellMap[saveBoards.get(j).i][saveBoards.get(j).j].done = false;
                            saveBoards.remove(j);
                            j--;
                            count1--;
                        }

//                        for (int j = saveBoards.size() - 1; j >= saveBoards.size() - count1; j--) {
//                            System.out.println("shakibaaaa");
//                            saveBoards.get(j).board.cellMap[saveBoards.get(j).i][saveBoards.get(j).j].done = false;
//                            saveBoards.remove(j);
//                        }
                        System.out.println("save board size : "+saveBoards.size());
                        System.out.println("BackTracking say: ");
                        temp.board.printBoard();
                        flag1 = false;


                    } else {
                        System.out.println("here");
//                        saveBoards.remove(i);
                        i--;
                        count1++;
                    }
                    if (i == -1) {
                        System.out.println("This Problem Is Not Solvable ");
                        return;
                    }

                }
                int x = temp.i;
                int y = temp.j;
                board2 temp1 = new board2(temp.board.size);
                for (int j = 0; j < temp.board.size; j++) {
                    for (int k = 0; k < temp.board.size; k++) {
                        temp1.cellMap[j][k] = new cell2(temp.board.cellMap[j][k]);
                    }
                }

                saveBoard saveBoard1 = new saveBoard(temp1, x, y);
                saveBoard1.attributed = (ArrayList<String>) temp.attributed.clone();
                saveBoards.add(saveBoard1);
                board=temp.board;

                String updatedNumAndColor = update(x, y, temp.board, temp.attributed);
//                    temp.board.printBoard();

                saveBoard1.setAttributed(updatedNumAndColor);


                FC(x, y, temp.board);
                for (int j = 0; j <saveBoards.size() ; j++) {
                    saveBoards.get(j).board.printBoard();
                    System.out.println("^^^^");
                }
                System.out.println("FC:");
                temp.board.printCombineDomain();


                if (goalTest(temp.board)) {
                    System.out.println("Goal Here!!");
                    temp.board.printBoard();
                    return;
                }


                if (temp.board.someThingEmpty()) {
                    System.out.println("here");

//                        backTrack=true;
                    goForward = false;
//                        continue;

                } else {
                    System.out.println("hii");
                    goForward = true;
                    backTrack = false;
                    board = temp.board;
                }

            } else {
                System.out.println("hello");
                continue;
            }


            count++;
//            if (count==4){
//                flag=false;
//            }
        }

    }


    public static void main(String[] args) throws CloneNotSupportedException {


        Scanner scanner = new Scanner(System.in);
        int colors = scanner.nextInt();
        int n = scanner.nextInt();
        soduku suduko = new soduku();
        ArrayList<Integer> numDomain = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            numDomain.add(i);
        }


        board2 board = new board2(n);

        for (int i = 0; i < colors; i++) {
            char color = scanner.next().charAt(0);

            Colors.add(color);

        }
        ArrayList<String> temp = new ArrayList<>();
        for (int j = 0; j <= n; j++) {

            String str = scanner.nextLine();

            temp.add(str);
        }
        temp.remove(0);

        for (int i = 0; i < temp.size(); i++) {
            String string = temp.get(i);
            String[] splited = string.split("\\s+");
            for (int j = 0; j < splited.length; j++) {

                String cell = splited[j];
                char num = cell.charAt(0);
                char color = cell.charAt(1);
                cell2 cell1;
                if (num == '*') {
                    cell1 = new cell2();
                    cell1.num = 0;
                    cell1.numbersDomain = (ArrayList<Integer>) numDomain.clone();
                    cell1.color = color;
                    if (color == '#') {
                        cell1.colorsDomain = (ArrayList<Character>) Colors.clone();
                    } else {
                        cell1.colorsDomain.add(color);
                    }

                } else {
                    cell1 = new cell2();
                    int number = num - '0';
                    cell1.num = number;
                    cell1.color = color;
                    cell1.numbersDomain.add(number);
                    if (color == '#') {
                        cell1.colorsDomain = (ArrayList<Character>) Colors.clone();
                    } else {
                        cell1.done = true;
                    }

                }
                cell1.setCombineDomain();
                board.cellMap[i][j] = cell1;
                board.cellMap[i][j].numAndColor = board.cellMap[i][j].num + "" + board.cellMap[i][j].color;
            }
        }

        board.printCombineDomain();


        /*
        in this section we are going to satisfy some constraint
         */

        for (int i = 0; i < n; i++) {

            int j = 0;
            boolean flag = true;
            while (flag) {

                if (board.cellMap[i][j].num == 0) {

                    /*
                    check numbers in that row to reduce from domain
                     */
                    for (int k = 0; k < n; k++) {
                        if (j != k) {
                            board.cellMap[i][j].removeFromNumbersDomain(board.cellMap[i][k].num);
                        }
                    }
                     /*
                    check numbers in that column to reduce from domain
                     */

                    for (int k = 0; k < n; k++) {
                        if (k != i) {
                            board.cellMap[i][j].removeFromNumbersDomain(board.cellMap[k][j].num);
                        }
                    }

                }
                j++;
                if (j == n) {
                    flag = false;
                }
            }
        }

        for (int i = 0; i <board.size ; i++) {
            for (int j = 0; j <board.size ; j++) {
                if (board.cellMap[i][j].numbersDomain.size()==0){
                    System.out.println("this Problem Has No Solution..");
                    return;
                }
            }
        }


        board.printNumbersDomains();

        for (int i = 0; i < board.size; i++) {
            for (int j = 0; j < board.size; j++) {
                if (board.cellMap[i][j].color != '#') {

                    if (i - 1 >= 0) {
                        if (board.cellMap[i - 1][j].color != '#') {
                            if (board.cellMap[i - 1][j].color == board.cellMap[i][j].color) {
                                System.out.println("this Problem Has No Solution..");
                                return;
                            } else {
                                if (board.cellMap[i - 1][j].num != 0 && board.cellMap[i][j].num != 0) {

                                    if (board.cellMap[i - 1][j].num < board.cellMap[i][j].num) {
                                        if (Colors.indexOf(board.cellMap[i - 1][j].color) < Colors.indexOf(board.cellMap[i][j].color)) {
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }
                                    }
                                } else if (board.cellMap[i - 1][j].num == 0 && board.cellMap[i][j].num!=0) {
                                    if (Colors.indexOf(board.cellMap[i - 1][j].color) <= Colors.indexOf(board.cellMap[i][j].color)) {
                                        for (int k = board.cellMap[i - 1][j].numbersDomain.size()-1; k >= 0; k--) {
                                            if (board.cellMap[i - 1][j].numbersDomain.get(k) <= board.cellMap[i][j].num) {
                                                board.cellMap[i - 1][j].numbersDomain.remove(k);
                                            }
                                        }
                                        if (board.cellMap[i-1][j].numbersDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }


                                    } else if (Colors.indexOf(board.cellMap[i - 1][j].color) >= Colors.indexOf(board.cellMap[i][j].color)) {
                                        for (int k = board.cellMap[i - 1][j].numbersDomain.size()-1; k >= 0; k--) {
                                            if (board.cellMap[i - 1][j].numbersDomain.get(k) > board.cellMap[i][j].num) {
                                                board.cellMap[i - 1][j].numbersDomain.remove(k);
                                            }
                                        }

                                        if (board.cellMap[i-1][j].numbersDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }
                                    }
                                } else if (board.cellMap[i][j].num == 0 && board.cellMap[i-1][j].num!=0) {
                                    if (Colors.indexOf(board.cellMap[i][j].color) <= Colors.indexOf(board.cellMap[i - 1][j].color)) {
                                        for (int k = board.cellMap[i][j].numbersDomain.size() - 1; k >= 0; k--) {
                                            if (board.cellMap[i][j].numbersDomain.get(k) < board.cellMap[i - 1][j].num) {
                                                board.cellMap[i][j].numbersDomain.remove(k);
                                            }
                                        }

                                        if (board.cellMap[i][j].numbersDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }
                                    } else if (Colors.indexOf(board.cellMap[i][j].color) >= Colors.indexOf(board.cellMap[i - 1][j].color)) {
                                        for (int k = board.cellMap[i][j].numbersDomain.size() - 1; k >= 0; k--) {
                                            if (board.cellMap[i][j].numbersDomain.get(k) > board.cellMap[i - 1][j].num) {
                                                board.cellMap[i][j].numbersDomain.remove(k);
                                            }
                                        }
                                        if (board.cellMap[i][j].numbersDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }
                                    }
                                }

                            }

                        } else if (board.cellMap[i - 1][j].color == '#') {
                            int index= board.cellMap[i-1][j].colorsDomain.indexOf(board.cellMap[i][j].color);
                            board.cellMap[i-1][j].colorsDomain.remove(index);
                            if (board.cellMap[i-1][j].colorsDomain.size()==0){
                                System.out.println("this Problem Has No Solution..");
                                return;
                            }

                            if (board.cellMap[i][j].num != 0) {
                                if (board.cellMap[i - 1][j].num != 0) {

                                    if (board.cellMap[i - 1][j].num < board.cellMap[i][j].num) {
                                        for (int k = board.cellMap[i - 1][j].colorsDomain.size() - 1; k >= 0; k--) {
//                                            System.out.println(Colors.indexOf(board.cellMap[i - 1][j].colorsDomain.get(k)) + " " + Colors.indexOf(board.cellMap[i][j].color));
                                            if (Colors.indexOf(board.cellMap[i - 1][j].colorsDomain.get(k)) <= Colors.indexOf(board.cellMap[i][j].color)) {
                                                board.cellMap[i - 1][j].colorsDomain.remove(k);
                                            }
                                        }
                                        if (board.cellMap[i-1][j].colorsDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }


                                    } else if (board.cellMap[i - 1][j].num > board.cellMap[i][j].num) {
                                        for (int k = board.cellMap[i - 1][j].colorsDomain.size() - 1; k >= 0; k--) {
                                            if (Colors.indexOf(board.cellMap[i - 1][j].colorsDomain.get(k)) >= Colors.indexOf(board.cellMap[i][j].color)) {
                                                board.cellMap[i - 1][j].colorsDomain.remove(k);
                                            }
                                        }

                                        if (board.cellMap[i-1][j].colorsDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }

                                    }
                                }
                            }

                        }
                    }

                    if (i+1<board.size){
                        if (board.cellMap[i + 1][j].color != '#') {
                            if (board.cellMap[i + 1][j].color == board.cellMap[i][j].color) {
                                System.out.println("this Problem Has No Solution..");
                                return;
                            } else {
                                if (board.cellMap[i + 1][j].num != 0 && board.cellMap[i][j].num != 0) {

                                    if (board.cellMap[i + 1][j].num < board.cellMap[i][j].num) {
                                        if (Colors.indexOf(board.cellMap[i + 1][j].color) < Colors.indexOf(board.cellMap[i][j].color)) {
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }
                                    }
                                } else if (board.cellMap[i + 1][j].num == 0 && board.cellMap[i][j].num!=0) {
                                    if (Colors.indexOf(board.cellMap[i + 1][j].color) <= Colors.indexOf(board.cellMap[i][j].color)) {
                                        for (int k = board.cellMap[i + 1][j].numbersDomain.size()-1; k >= 0; k--) {
                                            if (board.cellMap[i + 1][j].numbersDomain.get(k) <= board.cellMap[i][j].num) {
                                                board.cellMap[i + 1][j].numbersDomain.remove(k);
                                            }
                                        }
                                        if (board.cellMap[i+1][j].numbersDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }


                                    } else if (Colors.indexOf(board.cellMap[i + 1][j].color) >= Colors.indexOf(board.cellMap[i][j].color)) {
                                        for (int k = board.cellMap[i + 1][j].numbersDomain.size()-1; k >= 0; k--) {
                                            if (board.cellMap[i + 1][j].numbersDomain.get(k) > board.cellMap[i][j].num) {
                                                board.cellMap[i + 1][j].numbersDomain.remove(k);
                                            }
                                        }

                                        if (board.cellMap[i+1][j].numbersDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }

                                    }
                                } else if (board.cellMap[i][j].num == 0 && board.cellMap[i+1][j].num!=0) {
                                    if (Colors.indexOf(board.cellMap[i][j].color) <= Colors.indexOf(board.cellMap[i + 1][j].color)) {
                                        for (int k = board.cellMap[i][j].numbersDomain.size() - 1; k >= 0; k--) {
                                            if (board.cellMap[i][j].numbersDomain.get(k) < board.cellMap[i + 1][j].num) {
                                                board.cellMap[i][j].numbersDomain.remove(k);
                                            }
                                        }

                                        if (board.cellMap[i][j].numbersDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }


                                    } else if (Colors.indexOf(board.cellMap[i][j].color) >= Colors.indexOf(board.cellMap[i + 1][j].color)) {
                                        for (int k = board.cellMap[i][j].numbersDomain.size() - 1; k >= 0; k--) {
                                            if (board.cellMap[i][j].numbersDomain.get(k) > board.cellMap[i + 1][j].num) {
                                                board.cellMap[i][j].numbersDomain.remove(k);
                                            }
                                        }

                                        if (board.cellMap[i][j].numbersDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }
                                    }
                                }

                            }

                        } else if (board.cellMap[i + 1][j].color == '#') {
                            int index= board.cellMap[i+1][j].colorsDomain.indexOf(board.cellMap[i][j].color);
                            board.cellMap[i+1][j].colorsDomain.remove(index);
                            if (board.cellMap[i+1][j].colorsDomain.size()==0){
                                System.out.println("this Problem Has No Solution..");
                                return;
                            }
                        }



                        if (board.cellMap[i][j].num != 0) {
                            if (board.cellMap[i + 1][j].num != 0) {

                                if (board.cellMap[i + 1][j].num < board.cellMap[i][j].num) {
                                    for (int k = board.cellMap[i + 1][j].colorsDomain.size() - 1; k >= 0; k--) {
//                                            System.out.println(Colors.indexOf(board.cellMap[i - 1][j].colorsDomain.get(k)) + " " + Colors.indexOf(board.cellMap[i][j].color));
                                        if (Colors.indexOf(board.cellMap[i + 1][j].colorsDomain.get(k)) <= Colors.indexOf(board.cellMap[i][j].color)) {
                                            board.cellMap[i + 1][j].colorsDomain.remove(k);
                                        }
                                    }
                                    if (board.cellMap[i+1][j].colorsDomain.size()==0){
                                        System.out.println("this Problem Has No Solution..");
                                        return;
                                    }



                                } else if (board.cellMap[i + 1][j].num > board.cellMap[i][j].num) {
                                    for (int k = board.cellMap[i + 1][j].colorsDomain.size() - 1; k >= 0; k--) {
                                        if (Colors.indexOf(board.cellMap[i + 1][j].colorsDomain.get(k)) >= Colors.indexOf(board.cellMap[i][j].color)) {
                                            board.cellMap[i + 1][j].colorsDomain.remove(k);
                                        }
                                    }


                                    if (board.cellMap[i+1][j].colorsDomain.size()==0){
                                        System.out.println("this Problem Has No Solution..");
                                        return;
                                    }
                                }
                            }
                        }

                    }

                    if (j-1>=0){
                        if (board.cellMap[i][j-1].color != '#') {
                            if (board.cellMap[i][j-1].color == board.cellMap[i][j].color) {
                                System.out.println("this Problem Has No Solution..");
                                return;
                            } else {
                                if (board.cellMap[i][j-1].num != 0 && board.cellMap[i][j].num != 0) {

                                    if (board.cellMap[i][j-1].num < board.cellMap[i][j].num) {
                                        if (Colors.indexOf(board.cellMap[i][j-1].color) < Colors.indexOf(board.cellMap[i][j].color)) {
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }
                                    }
                                } else if (board.cellMap[i][j-1].num == 0 && board.cellMap[i][j].num!=0) {
                                    if (Colors.indexOf(board.cellMap[i][j-1].color) <= Colors.indexOf(board.cellMap[i][j].color)) {
                                        for (int k = board.cellMap[i][j-1].numbersDomain.size()-1; k >= 0; k--) {
                                            if (board.cellMap[i][j-1].numbersDomain.get(k) <= board.cellMap[i][j].num) {
                                                board.cellMap[i][j-1].numbersDomain.remove(k);
                                            }
                                        }

                                        if (board.cellMap[i][j-1].numbersDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }


                                    } else if (Colors.indexOf(board.cellMap[i][j-1].color) >= Colors.indexOf(board.cellMap[i][j].color)) {
                                        for (int k = board.cellMap[i][j-1].numbersDomain.size()-1; k >= 0; k--) {
                                            if (board.cellMap[i][j-1].numbersDomain.get(k) > board.cellMap[i][j].num) {
                                                board.cellMap[i][j-1].numbersDomain.remove(k);
                                            }
                                        }

                                        if (board.cellMap[i][j-1].numbersDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }

                                    }
                                } else if (board.cellMap[i][j].num == 0 && board.cellMap[i][j-1].num!=0) {
                                    if (Colors.indexOf(board.cellMap[i][j].color) <= Colors.indexOf(board.cellMap[i][j-1].color)) {
                                        for (int k = board.cellMap[i][j].numbersDomain.size() - 1; k >= 0; k--) {
                                            if (board.cellMap[i][j].numbersDomain.get(k) < board.cellMap[i][j-1].num) {
                                                board.cellMap[i][j].numbersDomain.remove(k);
                                            }
                                        }

                                        if (board.cellMap[i][j].numbersDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }

                                    } else if (Colors.indexOf(board.cellMap[i][j].color) >= Colors.indexOf(board.cellMap[i][j-1].color)) {
                                        for (int k = board.cellMap[i][j].numbersDomain.size() - 1; k >= 0; k--) {
                                            if (board.cellMap[i][j].numbersDomain.get(k) > board.cellMap[i][j-1].num) {
                                                board.cellMap[i][j].numbersDomain.remove(k);
                                            }
                                        }

                                        if (board.cellMap[i][j].numbersDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }
                                    }
                                }

                            }

                        } else if (board.cellMap[i][j-1].color == '#') {
                            int index= board.cellMap[i][j-1].colorsDomain.indexOf(board.cellMap[i][j].color);
                            board.cellMap[i][j-1].colorsDomain.remove(index);

                            if (board.cellMap[i][j-1].colorsDomain.size()==0){
                                System.out.println("this Problem Has No Solution..");
                                return;
                            }

                            if (board.cellMap[i][j].num != 0) {
                                if (board.cellMap[i][j-1].num != 0) {

                                    if (board.cellMap[i][j-1].num < board.cellMap[i][j].num) {
                                        for (int k = board.cellMap[i][j-1].colorsDomain.size() - 1; k >= 0; k--) {
//                                            System.out.println(Colors.indexOf(board.cellMap[i - 1][j].colorsDomain.get(k)) + " " + Colors.indexOf(board.cellMap[i][j].color));
                                            if (Colors.indexOf(board.cellMap[i][j-1].colorsDomain.get(k)) <= Colors.indexOf(board.cellMap[i][j].color)) {
                                                board.cellMap[i][j-1].colorsDomain.remove(k);
                                            }
                                        }

                                        if (board.cellMap[i][j-1].colorsDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }

                                    } else if (board.cellMap[i][j-1].num > board.cellMap[i][j].num) {
                                        for (int k = board.cellMap[i][j-1].colorsDomain.size() - 1; k >= 0; k--) {
                                            if (Colors.indexOf(board.cellMap[i][j-1].colorsDomain.get(k)) >= Colors.indexOf(board.cellMap[i][j].color)) {
                                                board.cellMap[i][j-1].colorsDomain.remove(k);
                                            }
                                        }

                                        if (board.cellMap[i][j-1].colorsDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }
                                    }
                                }
                            }

                        }
                    }

                    if (j+1<board.size){
                        if (board.cellMap[i][j+1].color != '#') {
                            if (board.cellMap[i][j+1].color == board.cellMap[i][j].color) {
                                System.out.println("this Problem Has No Solution..");
                                return;
                            } else {
                                if (board.cellMap[i][j+1].num != 0 && board.cellMap[i][j].num != 0) {

                                    if (board.cellMap[i][j+1].num < board.cellMap[i][j].num) {
                                        if (Colors.indexOf(board.cellMap[i][j+1].color) < Colors.indexOf(board.cellMap[i][j].color)) {
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }
                                    }
                                } else if (board.cellMap[i][j+1].num == 0 && board.cellMap[i][j].num!=0) {
                                    if (Colors.indexOf(board.cellMap[i][j+1].color) <= Colors.indexOf(board.cellMap[i][j].color)) {
                                        for (int k = board.cellMap[i][j+1].numbersDomain.size()-1; k >= 0; k--) {
                                            if (board.cellMap[i][j+1].numbersDomain.get(k) <= board.cellMap[i][j].num) {
                                                board.cellMap[i][j+1].numbersDomain.remove(k);
                                            }
                                        }

                                        if (board.cellMap[i][j+1].numbersDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }

                                    } else if (Colors.indexOf(board.cellMap[i][j+1].color) >= Colors.indexOf(board.cellMap[i][j].color)) {
                                        for (int k = board.cellMap[i][j+1].numbersDomain.size()-1; k >= 0; k--) {
                                            if (board.cellMap[i][j+1].numbersDomain.get(k) > board.cellMap[i][j].num) {
                                                board.cellMap[i][j+1].numbersDomain.remove(k);
                                            }
                                        }

                                        if (board.cellMap[i][j+1].numbersDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }

                                    }
                                } else if (board.cellMap[i][j].num == 0 && board.cellMap[i][j+1].num!=0) {
                                    if (Colors.indexOf(board.cellMap[i][j].color) <= Colors.indexOf(board.cellMap[i][j+1].color)) {
                                        for (int k = board.cellMap[i][j].numbersDomain.size() - 1; k >= 0; k--) {
                                            if (board.cellMap[i][j].numbersDomain.get(k) < board.cellMap[i][j+1].num) {
                                                board.cellMap[i][j].numbersDomain.remove(k);
                                            }
                                        }

                                        if (board.cellMap[i][j].numbersDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }

                                    } else if (Colors.indexOf(board.cellMap[i][j].color) >= Colors.indexOf(board.cellMap[i][j+1].color)) {
                                        for (int k = board.cellMap[i][j].numbersDomain.size() - 1; k >= 0; k--) {
                                            if (board.cellMap[i][j].numbersDomain.get(k) > board.cellMap[i][j+1].num) {
                                                board.cellMap[i][j].numbersDomain.remove(k);
                                            }
                                        }

                                        if (board.cellMap[i][j].numbersDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }

                                    }
                                }

                            }

                        } else if (board.cellMap[i][j+1].color == '#') {
                            int index= board.cellMap[i][j+1].colorsDomain.indexOf(board.cellMap[i][j].color);
                            board.cellMap[i][j+1].colorsDomain.remove(index);

                            if (board.cellMap[i][j+1].colorsDomain.size()==0){
                                System.out.println("this Problem Has No Solution..");
                                return;
                            }

                            if (board.cellMap[i][j].num != 0) {
                                if (board.cellMap[i][j+1].num != 0) {

                                    if (board.cellMap[i][j+1].num < board.cellMap[i][j].num) {
                                        for (int k = board.cellMap[i][j-1].colorsDomain.size() - 1; k >= 0; k--) {
//                                            System.out.println(Colors.indexOf(board.cellMap[i - 1][j].colorsDomain.get(k)) + " " + Colors.indexOf(board.cellMap[i][j].color));
                                            if (Colors.indexOf(board.cellMap[i][j+1].colorsDomain.get(k)) <= Colors.indexOf(board.cellMap[i][j].color)) {
                                                board.cellMap[i][j+1].colorsDomain.remove(k);
                                            }
                                        }

                                        if (board.cellMap[i][j+1].colorsDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }

                                    } else if (board.cellMap[i][j+1].num > board.cellMap[i][j].num) {
                                        for (int k = board.cellMap[i][j+1].colorsDomain.size() - 1; k >= 0; k--) {
                                            if (Colors.indexOf(board.cellMap[i][j+1].colorsDomain.get(k)) >= Colors.indexOf(board.cellMap[i][j].color)) {
                                                board.cellMap[i][j+1].colorsDomain.remove(k);
                                            }
                                        }

                                        if (board.cellMap[i][j+1].colorsDomain.size()==0){
                                            System.out.println("this Problem Has No Solution..");
                                            return;
                                        }

                                    }
                                }
                            }

                        }
                    }


                }
            }
        }

        board.printColorsDomain();
        board.printNumbersDomains();


        board.printBoard();


        for (int i = 0; i <board.size ; i++) {
            for (int j = 0; j <board.size ; j++) {
                board.cellMap[i][j].combineDomain.clear();
                board.cellMap[i][j].setCombineDomain();
            }


        }

        System.out.println("------");

        board.printCombineDomain();
        suduko.solve2(board);


    }
}
