import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;



/**
 * MerkleTree Current Driver
 * String transactions are converted into Hexadecimal. Hex is then converted into a byte array and sorted into blocks of the chain
 */
public class Driver
{
    /** Merkle Tree consisting of two subtrees, each with two leaf nodes, each of these consisting
     * of two data blocks. Then fulltreeprint() shows struct of tree
     *
     */
    public static void main(String[] noargs)
    {
        /***
         * DEFINITIONS
         */

        ArrayList<String> transactionList;
        ArrayList<String> hexList;
        ArrayList<byte[]> byteList;
        MessageDigest digestMessage;

        List<byte[]> tBlockA;
        List<byte[]> tBlockB;
        List<byte[]> tBlockC;
        List<byte[]> tBlockD;

        Leaf leaf1;
        Leaf leaf2;
        Leaf leaf3;
        Leaf leaf4;

        MerkleTree branchA;
        MerkleTree branchB;
        MerkleTree merkleTree;

        digestMessage = null;

        try
        {
            //SHA-256 will always be a valid algorithm
            digestMessage = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e)
        {
            assert false;
        }

        // Sample Transaction strings to be encoded
        transactionList = new ArrayList<>();

        //ArrayList of StringtoHex conversions
        //ArrayList of byteList conversions
        hexList = new ArrayList<>();
        byteList = new ArrayList<>();

        tBlockA = new ArrayList<byte[]>();
        tBlockB = new ArrayList<byte[]>();
        tBlockC = new ArrayList<byte[]>();
        tBlockD = new ArrayList<byte[]>();

        transactionList.add("transaction #1 : from user@ty(100) -> to user@alisyn(100) : 5.01 FSH");
        transactionList.add("transaction #2 : from user@brian(59) -> to user@anonymous(777) : 40.99 FSH");
        transactionList.add("transaction #3 : from user@adam(201) -> to user@assad(12) : 2.01 FSH");
        transactionList.add("transaction #4 : from user@arben(5124) -> to user@rassad(1) : 129.2342 FSH");
        transactionList.add("transaction #5 : from user@192.503.23(34) -> to user@192.168.1.1(2) : 234.11 FSH");
        transactionList.add("transaction #6 : from user@walletid:1201494 -> user@walletid:1034132 : 1 FSH");
        transactionList.add("transaction #7 : from user@walletid:01294 -> to user@walletid:129041 : 1.99 FSH");
        transactionList.add("transaction #8 : from user@walletid:1104821 -> to user@walletid:14209113 : 56.812 FSH");



        //Polymorphism to create new blocks

        for(int i =0; i < transactionList.size(); i++){
            String list = stringtoHex(transactionList.get(i));
            hexList.add(list);
            byte[] block = strByteArray(list);
            byteList.add(block);
        }

        //Very OverSimplified Combining of Blocks to create leaf nodes

        tBlockA.add(byteList.get(0));
        tBlockA.add(byteList.get(1));

        tBlockB.add(byteList.get(2));
        tBlockB.add(byteList.get(3));

        tBlockC.add(byteList.get(4));
        tBlockC.add(byteList.get(5));

        tBlockD.add(byteList.get(6));
        tBlockD.add(byteList.get(7));

        leaf1 = new Leaf(tBlockA);
        leaf2 = new Leaf(tBlockB);
        leaf3 = new Leaf(tBlockC);
        leaf4 = new Leaf(tBlockD);

        // Build merkle tree from leaves (in reality, this would be a much more efficient, automatic sorting process within the tree)
        branchA = new MerkleTree(digestMessage);
        branchA.add(leaf1, leaf2);

        branchB = new MerkleTree(digestMessage);
        branchB.add(leaf3, leaf4);

        merkleTree = new MerkleTree(digestMessage);
        merkleTree.add(branchA, branchB);

        // Return the digest for the entire tree
        merkleTree.printfullTree();
    }

    /***
     * Encode string to Hex
     *
     * @param string to be encoded
     * @return Hexstring of String
     */
    public static String stringtoHex(String string){

        StringBuffer sb = new StringBuffer();
        //Converting string to character array
        char[] ch = string.toCharArray();
        for(int i = 0; i < ch.length; i++) {
            String hexString = Integer.toHexString(ch[i]);
            sb.append(hexString);
        }
        String result = sb.toString();
        return result;
    }

    /***
     * Convert String (or HexString) to bytes
     *
     * @param string to be encoded
     * @return byteArray of HexString
     */
    public static byte[] strByteArray(String string){
        byte[] byteArray= string.getBytes(StandardCharsets.UTF_8);
        return byteArray;
    }
}
