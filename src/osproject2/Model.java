package osproject2;

public class Model {
    private String algo;
    private String numOfTracks;
    private String headLoc;
    private String diskQueue;
    private String headDir;

    public Model(String algo, String numOfTracks, String headLoc, String diskQueue, String headDir) {
        this.algo = algo;
        this.numOfTracks = numOfTracks;
        this.headLoc = headLoc;
        this.diskQueue = diskQueue;
        this.headDir = headDir;
    }

    public String getAlgo() {
        return algo;
    }

    public void setAlgo(String algo) {
        this.algo = algo;
    }

    public String getNumOfTracks() {
        return numOfTracks;
    }

    public void setNumOfTracks(String numOfTracks) {
        this.numOfTracks = numOfTracks;
    }

    public String getHeadLoc() {
        return headLoc;
    }

    public void setHeadLoc(String headLoc) {
        this.headLoc = headLoc;
    }

    public String getDiskQueue() {
        return diskQueue;
    }

    public void setDiskQueue(String diskQueue) {
        this.diskQueue = diskQueue;
    }

    public String getHeadDir() {
        return headDir;
    }

    public void setHeadDir(String headDir) {
        this.headDir = headDir;
    }
    
    
}
