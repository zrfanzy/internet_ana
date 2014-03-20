package ix.lab05.smartvote;

import ix.lab05.faces.PCAResult;

import java.util.List;

import Jama.Matrix;
import utils.CandidatesData;
import utils.Common;
import utils.EigenDecomposition;
import utils.SmartVoteUtils;

public class SmartVote {


    public static void variance(CandidatesData data) {
    	Matrix datas = data.answersMatrix;
    	int M = datas.getColumnDimension();
        int N = datas.getRowDimension();
        // get MEAN
        double[] mean = new double[M];
        for (int i = 0; i < M; ++i) {
        	mean[i] = 0;
        	for (int j = 0; j < N; ++j) {
        		mean[i] = mean[i] + datas.get(j, i);
        	}
        	mean[i] = mean[i] / (double)N;
        }
        Matrix tmp_data = datas.copy();
        for (int i = 0; i < M; ++i) {
        	for (int j = 0; j < N; ++j) {
        		tmp_data.set(j, i, datas.get(j, i) - mean[i]);
        	}
        }
        Matrix z = tmp_data.transpose().times(tmp_data).times(1/(double)N);
        EigenDecomposition ed = new EigenDecomposition(z);
        PCAResult result = new PCAResult(ed.eigenvectors, ed.eigenvalues);
        Common.linPlot(result.values);
    	/*
         * TODO:
         *
         * 1. Compute the PCA of the candidates' answers matrix.
         * 2. Plot the magnitude of the eigenvalues (on a linear scale.)
         * 3. Compute and print the amount of variance explained by the 1,2,3 first principal components.
         */
    }


    public static void project(CandidatesData data) {
        /*
         * TODO:
         *
         * 1. Compute the PCA of the candidates' answers matrix.
         * 2. Project the candidates on the two first principal components.
         * 3. Plot the 2D projection using SmartVoteUtils.plotProjection(...).
         */
    }


    public static void questions(CandidatesData data, List<String> qs) {
        /*
         * TODO:
         *
         * 1. Compute the PCA of the candidates answers matrix.
         * 2. Extract the three first principal components
         * 3. For each of the three principal component, print the three questions
         *    that contribute most to the component.
         *
         * Hint: use SmartVoteUtils.topThree(..).
         */
    }


    /** Use this function to run the various parts. */
    public static void main(String[] args) {
        CandidatesData data = SmartVoteUtils.readCandidates();
        System.out.println(String.format("Dataset has N = %d candidates and M = %d questions",
                data.answersMatrix.getRowDimension(),  // TODO Number of rows.
                data.answersMatrix.getColumnDimension()   // TODO Number of columns.
                ));

        // Prompt the user for an action.
        String action = Common.getString("action [variance/project/questions]: ");

        if ("variance".equals(action)) {
            variance(data);

        } else if ("project".equals(action)) {
            project(data);

        } else if ("questions".equals(action)) {
            List<String> qs = SmartVoteUtils.readQuestions();
            questions(data, qs);
        }
    }
}
