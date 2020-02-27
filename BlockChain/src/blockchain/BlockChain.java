package blockchain;
import java.util.*;
import java.sql.*;
import java.math.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Mujaddid
 */
public class BlockChain {

    public static String[] hash=new String[2];
    public static int no = 0;
    public static Koneksi conn=new Koneksi();
    
    public static Block CreateBlock(String data)
    {
        LocalDate tgl=java.time.LocalDate.now();
        String time = tgl.toString();
        int nonce=0,tempNonce;
        Block bl=new Block();
        String temp,temp1;
        Random rand = new Random();
        
        bl.setIndex(no+1);
        bl.setTimeStamp(time);
        bl.setData(data);
        bl.setPrevHash(hash[1]);
        bl.setNonce(nonce);
        bl.setCurrHash(getMd5(Integer.toString(no+1)+time+data+hash[1]+nonce));
        temp=bl.getCurrHash();
        temp1=temp.substring(0, 5);
        
        while (!"11111".equals(temp1)) //Proses MINING Syarat berhenti ketika 5 karakter pertama currHash adalah 11111
        {
            tempNonce=nonce;
            temp=getMd5(Integer.toString(no+1)+time+data+hash[1]+nonce);
            temp1=temp.substring(0, 5);
            System.out.println("mine");
            if (!"11111".equals(temp1)){
                nonce=rand.nextInt(2147483647);
            } 
        }

        bl.setNonce(nonce);
        bl.setCurrHash(getMd5(Integer.toString(no+1)+time+data+hash[1]+nonce));
        bl.setCurrHash(getMd5(Integer.toString(no+1)+time+data+hash[1]+nonce));
        return bl;
    }
    
    public static void insertBlock(String nama,Block bl)
    {
        
        conn.insertData(nama, bl);
    }
    
    public static boolean cekBlockchain(String nama)
    {
        ArrayList<Block> listDataBlockchain=new ArrayList();
        listDataBlockchain=conn.cekBlockchain(nama);
        String temp="";
        String temp1="";
        Block lok=lok=listDataBlockchain.get(0);
        int i=1;
        int x=listDataBlockchain.size()-1;
        
        while(i<=x)
        {
            if (i<x)
            {
                lok=listDataBlockchain.get(i);
                temp=getMd5(Integer.toString(lok.getIndex())+lok.getTimeStamp()+lok.getData()+lok.getPrevHash()+lok.getNonce());
                lok=listDataBlockchain.get(i+1);
                temp1=lok.getPrevHash();
                if(temp.equals(temp1))
                {
                    
                }
                else
                {
                    break;
                }
            }
            if(i==x)
            {
                lok=listDataBlockchain.get(i-1);
                temp=lok.getCurrHash();
                if(temp==null)
                {
                    temp="null";
                }
                lok=listDataBlockchain.get(i);
                temp1=lok.getPrevHash();
                if(temp.equals(temp1))
                {
                    return true;
                }
            }
            i++;   
        }
        if(x==0)
        { 
            return true;
        }
        return false;
    }
    
    public static String getMd5(String input) 
    { 
        try { 
  
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("SHA-256"); 
  
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8)); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
//            // Convert message digest into hex value 
//            String hashtext = no.toString(16); 
//            while (hashtext.length() < 32) { 
//                hashtext = "0" + hashtext; 
//            } 
            
            // Convert message digest into hex value  
            StringBuilder hexString = new StringBuilder(no.toString(16));  

            // Pad with leading zeros 
            while (hexString.length() < 32)  
            {  
                hexString.insert(0, '0');  
            }  

            return hexString.toString();
//            return hashtext; 
        }  
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
        
    } 
    
    public static void main(String[] args) throws SQLException 
    {
        Koneksi con;
        String nama="DaftarNama"; //nama BlockChain
        String data="Jaddid"; //Data Block
        
        con = new Koneksi();
        con.connect();
//        con.createBlockchain(nama); //create table dalam database atau suatu block chain
        con.cekhash(nama);      //untuk mendapatkan currHash dan prevHash block yg baru saja diinput
        Block blok=new Block(); //membuat obj block


        blok=CreateBlock(data); //membuat block dan memasukkan data kedalam block
        insertBlock(nama,blok); //menambahkan block kedalam blockchain

        boolean te=cekBlockchain(nama); //validasi block chain
        System.out.println(te);  
    }
    
}
