package blockchain;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Mujaddid
 */
public class Koneksi {
    static Connection con;
    
    LocalDate time=java.time.LocalDate.now();
    public static Statement s;

    public void connect() {
        try {
            String url = "jdbc:mysql://localhost:3306/blockchain";
            String username = "root";
            String pass = "";
            con = DriverManager.getConnection(url, username, pass);
            System.out.println("connected");
        } catch (SQLException ex) {
            System.out.println("connection error");
            ex.printStackTrace();
        }
    }
    
    public void createBlockchain(String nama) 
    {
        try {
            String query = "create table "+nama+" (no INT NOT NULL  , time DATE NOT NULL, data VARCHAR(255), prevhash VARCHAR(255), currhash VARCHAR(255),nonce VARCHAR(255), PRIMARY KEY (no));";
            Statement s = con.createStatement();
            s.executeUpdate(query);
            query ="insert into "+nama+" (no, time, data, prevhash, currhash, nonce) VALUES ('1', '"+time+"', 'Genesis', NULL, NULL, '0')";
            s = con.createStatement();
            s.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("BlockChain Creation error");
            ex.printStackTrace();
        }
        System.out.println("Blockchain Creation success");
    }
    
    public void cekhash(String nama) 
    {
        try {
            String query = "select * from "+nama+"";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                BlockChain.no++;
            }
            
            query = "select * from "+nama+" where no='"+BlockChain.no+"'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            rs.next();
            BlockChain.hash[0]=rs.getString(4);
            BlockChain.hash[1]=rs.getString(5);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void insertData(String nama,Block bl) 
    {
        try {
            String query = "insert into "+nama+" (no, time, data, prevhash, currhash, nonce) VALUES ("+bl.getIndex()+",'"+bl.getTimeStamp()+"', '"+bl.getData()+"', '"+bl.getPrevHash()+"', '"+bl.getCurrHash()+"', '"+bl.getNonce()+"')";
            s = con.createStatement();
            s.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("BlockChain insert error");
            ex.printStackTrace();
        }
        System.out.println("Blockchain insert success");
    }
    
    public static ArrayList<Block> cekBlockchain(String nama) 
    {
        try {
            String query = "select * from "+nama+"";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Block> listDataBlockchain=new ArrayList();
            Block i=null;
            
            while (rs.next()) {
                int no=Integer.parseInt(rs.getString(1));
                String time =rs.getString(2);
                String data =rs.getString(3);
                String prevHash =rs.getString(4);
                String currHash =rs.getString(5);
                int nonce =Integer.parseInt(rs.getString(6));
                i = new Block(no, time, data, prevHash, currHash,nonce);
                listDataBlockchain.add(i);
            }
            return listDataBlockchain;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }
}
