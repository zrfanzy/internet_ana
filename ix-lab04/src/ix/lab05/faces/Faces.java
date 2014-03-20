package ix.lab05.faces;

import java.awt.List;
import java.util.Arrays;
import java.util.Collections;

import utils.Common;
import utils.EigenDecomposition;
import utils.FacesUtils;
import utils.NotYetImplementedException;
import Jama.Matrix;

@SuppressWarnings("unused")
public class Faces {

    /**
     * Computes the variance of the dataset along each dimension (i.e. each  column).
     * @return an array of doubles containing the variance for each column
     */
    public static double[] variance(Matrix data) {
        int M = data.getColumnDimension();
        int N = data.getRowDimension();
        double[] variances = new double[M];
        // TODO Complete the function.
        //throw new NotYetImplementedException();  // Remove.
        for (int i = 0; i < M; ++i) {
        	double avg = 0;
        	for (int j = 0; j < N; ++j) {
        		avg += data.get(j, i);
        	}
        	avg = avg / (double)N;
        	for (int j = 0; j < N; ++j) {
        		variances[i] += (data.get(j, i) - avg) * (data.get(j, i) - avg);
        	}
        	variances[i] /= N;
        }
        // Don't forget to center the data!
        // Hint: util.Common provides a few handy functions.
        // Arrays.sort(variances);
        /* for (int i = 0; i < M; ++i) {
        	System.out.println(variances[i]);
        } */
        return variances;
    }


    /**
     * Computes the PCA of the dataset (i.e. the eigendecomposition of
     * the covariance matrix.)
     * @return a container with with the result of the PCA
     */
    public static PCAResult pca(Matrix data) {
        int M = data.getColumnDimension();
        int N = data.getRowDimension();
        // get MEAN
        double[] mean = new double[M];
        for (int i = 0; i < M; ++i) {
        	mean[i] = 0;
        	for (int j = 0; j < N; ++j) {
        		mean[i] = mean[i] + data.get(j, i);
        	}
        	mean[i] = mean[i] / (double)N;
        }
        Matrix tmp_data = data.copy();
        for (int i = 0; i < M; ++i) {
        	for (int j = 0; j < N; ++j) {
        		tmp_data.set(j, i, data.get(j, i) - mean[i]);
        	}
        }
        Matrix z = tmp_data.transpose().times(tmp_data).times(1/(double)N);
        EigenDecomposition ed = new EigenDecomposition(z);
        PCAResult result = new PCAResult(ed.eigenvectors, ed.eigenvalues);
        
        return result;
    }


    /**
     * Projects the dataset on the new basis formed by the PCA.
     * @return a new matrix with the projected version of the data
     */
    public static Matrix project(Matrix data) {
        PCAResult result = pca(data);
        Matrix projected = null;
        int M = data.getColumnDimension();
        int N = data.getRowDimension();
        double[] mean = new double[M];
        for (int i = 0; i < M; ++i) {
        	mean[i] = 0;
        	for (int j = 0; j < N; ++j) {
        		mean[i] = mean[i] + data.get(j, i);
        	}
        	mean[i] = mean[i] / (double)N;
        }
        Matrix tmp_data = data.copy();
        for (int i = 0; i < M; ++i) {
        	for (int j = 0; j < N; ++j) {
        		tmp_data.set(j, i, data.get(j, i) - mean[i]);
        	}
        }
        projected = tmp_data.times(result.rotation);
        // TODO Complete the function.
        // Warning: don't forget to center the data before projecting it!
        //throw new NotYetImplementedException();  // Remove.

        return projected;
    }


    /** Use this function to run the various parts. */
    public static void main(String[] args) {
        Matrix data = FacesUtils.readFacesData();
        System.out.println(String.format(
                "Dataset has %d rows (items, faces) and %d columns (measurements per item).",
                data.getRowDimension(),  // TODO number of rows.
                data.getColumnDimension()  // TODO number of columns.
                ));

        // Prompt the user for an action.
        String action = Common.getString("action [variance/pca/project/extremes]: ");

        if ("variance".equals(action)) {
            // Plot the variance of each dimension of the dataset.
            double[] variances = variance(data);
            Common.linPlot(variances);

        } else if ("pca".equals(action)) {
            // Plot the magnitude of the components in log scale.
            PCAResult result = pca(data);
            Common.logPlot(result.values);

        } else if ("project".equals(action)) {
            // Print a row of the data projected on the new basis.
            Matrix projected = project(data);
            int id = Common.getInt("row / item: ");
            FacesUtils.printRow(projected, id);

        } else if ("extremes".equals(action)) {
            // Print items with extreme values (both min and max) along a
            // given principalcomponent.
            Matrix projected = project(data);
            int dim = Common.getInt("dimension: ");
            FacesUtils.printExtremes(projected, dim, 10);
        }
    }
}
