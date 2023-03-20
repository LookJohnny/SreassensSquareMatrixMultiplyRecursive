import java.util.Vector;

public class StrassensSquareMatrixMultiplyRecursive {
    public static Vector<Vector<Double>> matrix_add(Vector<Vector<Double>> inputA, Vector<Vector<Double>> inputB) {
        Vector<Vector<Double>> ret = new Vector<Vector<Double>>();
        int n = inputA.size();
        for (int i = 0; i < n; i++) {
            Vector<Double> tempRow = new Vector<Double>();
            for (int j = 0; j < n; j++) {
                tempRow.addElement(inputA.elementAt(i).elementAt(j) + inputB.elementAt(i).elementAt(j));
            }
            ret.addElement(tempRow);
        }
        return ret;
    }

    public static Vector<Vector<Double>> matrix_subtract (Vector<Vector<Double>> inputA, Vector<Vector<Double>> inputB) {
        Vector<Vector<Double>> ret = new Vector<Vector<Double>> ();
        int n = inputA.size();
        for (int i = 0; i < n; i++) {
            Vector<Double> tempRow = new Vector<Double>();
            for (int j = 0; j < n; j++) {
                tempRow.addElement(inputA.elementAt(i).elementAt(j) -
                        inputB.elementAt(i).elementAt(j));
            }
            ret.addElement(tempRow);
        }
        return ret;
    }

    public static Vector<Vector<Double>> strassens_square_matrix_multiply_recursive (Vector<Vector<Double>> inputA, Vector<Vector<Double>> inputB) {
        int n = inputA.size();

        // Base case: if matrices are 1x1, simply multiply and return
        if (n == 1) {
            Vector<Vector<Double>> ret = new Vector<Vector<Double>>();
            Vector<Double> tempRow = new Vector<Double>();
            tempRow.addElement(inputA.elementAt(0).elementAt(0) * inputB.elementAt(0).elementAt(0));
            ret.addElement(tempRow);
            return ret;
        }

        // Split matrices into four equal-sized submatrices
        int mid = n / 2;
        Vector<Vector<Double>> A11 = new Vector<Vector<Double>>(mid);
        Vector<Vector<Double>> A12 = new Vector<Vector<Double>>(mid);
        Vector<Vector<Double>> A21 = new Vector<Vector<Double>>(mid);
        Vector<Vector<Double>> A22 = new Vector<Vector<Double>>(mid);

        Vector<Vector<Double>> B11 = new Vector<Vector<Double>>(mid);
        Vector<Vector<Double>> B12 = new Vector<Vector<Double>>(mid);
        Vector<Vector<Double>> B21 = new Vector<Vector<Double>>(mid);
        Vector<Vector<Double>> B22 = new Vector<Vector<Double>>(mid);

        for (int i = 0; i < mid; i++) {
            A11.addElement(new Vector<Double>(inputA.elementAt(i).subList(0, mid)));
            A12.addElement(new Vector<Double>(inputA.elementAt(i).subList(mid, n)));
            A21.addElement(new Vector<Double>(inputA.elementAt(i + mid).subList(0, mid)));
            A22.addElement(new Vector<Double>(inputA.elementAt(i + mid).subList(mid, n)));

            B11.addElement(new Vector<Double>(inputB.elementAt(i).subList(0, mid)));
            B12.addElement(new Vector<Double>(inputB.elementAt(i).subList(mid, n)));
            B21.addElement(new Vector<Double>(inputB.elementAt(i + mid).subList(0, mid)));
            B22.addElement(new Vector<Double>(inputB.elementAt(i + mid).subList(mid, n)));
        }

        // Recursively compute products of submatrices
        Vector<Vector<Double>> P1 = strassens_square_matrix_multiply_recursive(matrix_add(A11, A22), matrix_add(B11, B22));
        Vector<Vector<Double>> P2 = strassens_square_matrix_multiply_recursive(matrix_add(A21, A22), B11);
        Vector<Vector<Double>> P3 = strassens_square_matrix_multiply_recursive(A11, matrix_subtract(B12, B22));
        Vector<Vector<Double>> P4 = strassens_square_matrix_multiply_recursive(A22, matrix_subtract(B21, B11));
        Vector<Vector<Double>> P5 = strassens_square_matrix_multiply_recursive(matrix_add(A11, A12), B22);
        Vector<Vector<Double>> P6 = strassens_square_matrix_multiply_recursive(matrix_subtract(A21, A11), matrix_add(B11, B12));
        Vector<Vector<Double>> P7 = strassens_square_matrix_multiply_recursive(matrix_subtract(A12, A22), matrix_add(B21, B22));

        // Combine products to get result
        Vector<Vector<Double>> C11 = matrix_subtract(matrix_add(matrix_add(P1, P4), P7), P5);
        Vector<Vector<Double>> C12 = matrix_add(P3, P5);
        Vector<Vector<Double>> C21 = matrix_add(P2, P4);
        Vector<Vector<Double>> C22 = matrix_subtract(matrix_add(matrix_add(P1, P3), P6), P2);

        // Combine submatrices to get final result
        Vector<Vector<Double>> ret = new Vector<Vector<Double>>();
        for (int i = 0; i < n; i++) {
            Vector<Double> tempRow = new Vector<Double>();
            for (int j = 0; j < n; j++) {
                if (i < mid && j < mid) {
                    tempRow.addElement(C11.elementAt(i).elementAt(j));
                } else if (i < mid && j >= mid) {
                    tempRow.addElement(C12.elementAt(i).elementAt(j - mid));
                } else if (i >= mid && j < mid) {
                    tempRow.addElement(C21.elementAt(i - mid).elementAt(j));
                } else {
                    tempRow.addElement(C22.elementAt(i - mid).elementAt(j - mid));
                }
            }
            ret.addElement(tempRow);
        }

        return ret;
    }


    public static void main(String[] args) {
        Vector<Vector<Double>> myDataA = new Vector<Vector<Double>>();
        Vector<Vector<Double>> myDataB = new Vector<Vector<Double>>();
        Vector<Vector<Double>> myDataC = new Vector<Vector<Double>>();

        Integer n = 16;

        for (int i = 0; i < n; i++) {
            Vector<Double> tempRow = new Vector<Double>();
            for (int j = 0; j < n; j++) {
                Double temp = (double) (i + j);
                tempRow.addElement(temp);
            }
            myDataA.addElement(tempRow);
        }
        for (int i = 0; i < n; i++) {
            Vector<Double> tempRow = new Vector<Double>();
            for (int j = 0; j < n; j++) {
                Double temp = (double) (i * j);
                tempRow.addElement(temp);
            }
            myDataB.addElement(tempRow);
        }
        System.out.println(myDataA);
        System.out.println(myDataB);
        myDataC = strassens_square_matrix_multiply_recursive(myDataA, myDataB);
        System.out.println(myDataC);
    }
}