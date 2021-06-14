class Process {
    private int[] allocation;
    private int[] max;
    private int[] need;
    boolean hasRun = false;

    public Process(int[] inputMax) {
        allocation = new int[inputMax.length];
        max = new int[inputMax.length];
        need = new int[inputMax.length];
        max = inputMax;
    }


    public int[] getMax() { return max; }
    public int[] getAllocation(){
        return allocation;
    }
    public int[] getNeed() {
        return need;
    }


    public void setAllocation(int[] inputAllocation){
        allocation = inputAllocation;
        need = this.setNeed();
    }


    public int[] setNeed() {
        for (int i = 0; i < need.length; i++) {
            need[i] = max[i] - allocation[i];
        }
        return need;
    }


    public Boolean canRun(int[] available) {
        for (int i = 0; i < need.length; i++) {
            if (need[i] > available[i]) {
                return false;
            }
        } return true;
    }


    public void runProcess() {
        hasRun = true;
    }
}
