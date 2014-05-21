package ix.lab08;

import ix.lab08.pagerank.PageRankAlgorithm;
import ix.lab08.pagerank.RandomSurfer;

import org.junit.Before;

public class RandomSurferTest extends PageRankAlgorithmTest {

    private static final double DELTA = 0.01;

    @Override
    public PageRankAlgorithm getInstance() {
        return new RandomSurfer();
    }

    @Before
    public void changeDelta() {
        this.setDelta(DELTA);
    }

}
