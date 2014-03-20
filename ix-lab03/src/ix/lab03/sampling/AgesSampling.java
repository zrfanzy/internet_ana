package ix.lab03.sampling;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ix.utils.SocialAPI;
import ix.utils.SocialNode;

@SuppressWarnings("unused")
public class AgesSampling {

    public static int N = 10000;
    public static int GRAPH = SocialAPI.AGES;

    public static void main(String[] args) {
    	SocialAPI api = new SocialAPI();
        int graphId = SocialAPI.AGES;
        String nodeId = new String();
        if ("u".equals(args[0])) nodeId= SocialAPI.SEED_U;
        else if ("v".equals(args[0])) nodeId = SocialAPI.SEED_V;
        else if ("w".equals(args[0])) nodeId = SocialAPI.SEED_W;
        ArrayList<String> nn = new ArrayList<String>();
        long agesum = 0;
        Random random = new Random();
        for (int i = 0; i < N; ++i) {
        	SocialNode node = api.getNode(graphId, nodeId);
        	agesum += node.age;
        	List<String>nbs = node.neighbors;
        	nn.add(nodeId);
        	int l = 0;
        	int k = 0;
        	String node1Id = nodeId;
        	while (l == 0 || (nn.contains(nbs.get(k)) && l < 10)) {
        		SocialNode node1 = api.getNode(graphId, node1Id);
        		nbs = node1.neighbors;
        		for (int j = 0; j < 3; ++j) {
        			node1 = api.getNode(graphId, node1Id);
        			nbs = node1.neighbors;
        			k = Math.abs((random.nextInt() % nbs.size()));
        			node1Id = nbs.get(k);
        		}
        		++l;
    		}
        	nodeId = nbs.get(k);
    		if (i % 100 == 0) {
    			System.out.print(i);
    			System.out.print(',');
    			System.out.println((double)agesum/(i+1));
    		}
        }
        System.out.println(((double)agesum / N));
    }
}
