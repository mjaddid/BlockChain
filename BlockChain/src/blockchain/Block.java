package blockchain;

/**
 *
 * @author Mujaddid
 */
public class Block {

    private int Index;
    private String Data;
    private String PrevHash;
    private String CurrHash;
    private String TimeStamp;

    public Block(int Index, String TimeStamp, String Data, String PrevHash, String CurrHash) 
    {
        setIndex(Index);
        setTimeStamp(TimeStamp);
        setData(Data);
        setPrevHash(PrevHash);
        setCurrHash(CurrHash);
    }
    
    public Block() 
    {

    }

    /**
     * @return the Index
     */
    public int getIndex() {
        return Index;
    }

    /**
     * @param Index the Index to set
     */
    public void setIndex(int Index) {
        this.Index = Index;
    }

    /**
     * @return the Data
     */
    public String getData() {
        return Data;
    }

    /**
     * @param Data the Data to set
     */
    public void setData(String Data) {
        this.Data = Data;
    }

    /**
     * @return the PrevHash
     */
    public String getPrevHash() {
        return PrevHash;
    }

    /**
     * @param PrevHash the PrevHash to set
     */
    public void setPrevHash(String PrevHash) {
        this.PrevHash = PrevHash;
    }

    /**
     * @return the CurrHash
     */
    public String getCurrHash() {
        return CurrHash;
    }

    /**
     * @param CurrHash the CurrHash to set
     */
    public void setCurrHash(String CurrHash) {
        this.CurrHash = CurrHash;
    }

    /**
     * @return the TimeStamp
     */
    public String getTimeStamp() {
        return TimeStamp;
    }

    /**
     * @param TimeStamp the TimeStamp to set
     */
    public void setTimeStamp(String TimeStamp) {
        this.TimeStamp = TimeStamp;
    }

}
