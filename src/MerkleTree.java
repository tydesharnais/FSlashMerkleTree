import java.security.MessageDigest;
import java.util.List;

/**
 * @author Tyler Desharnais, Forward/Slash F/
 * Extremely oversimplified merkle tree. Uses SHA-256
 * A very simple take on blockchain but an effective one nonetheless
 * No maximum blcokchain size (which could be an issue and may need to resolve)
 * Uses SHA-256 encryption for P2P mining and checking
 *
 */
public class MerkleTree
{
    // Child trees
    private MerkleTree leftTree = null;
    private MerkleTree rightTree = null;

    // Child leaves
    private Leaf leftLeaf = null;
    private Leaf rightLeaf = null;

    // The hash value of this node
    private byte[] digest;

    // The digest algorithm
    private final MessageDigest md;

    /**
     * Generates a digest for the specified leaf node.
     *
     * @param leaf The leaf node
     *
     * @return The digest generated from the leaf
     */
    private byte[] digest(Leaf leaf)
    {
        final List<byte[]> dataBlock = leaf.getDataBlock();
        final int numBlocks = dataBlock.size();
        for (int index=0; index<numBlocks-1; index++)
        {
            md.update(dataBlock.get(index));
        }
        // Complete the digest with the final block
        digest = md.digest(dataBlock.get(numBlocks-1));

        return (digest);
    }

    /**
     * Initialises an empty Merkle Tree using the specified
     * digest algorithm.
     *
     * @param md The message digest algorithm to be used by the tree
     */
    public MerkleTree(MessageDigest md)
    {
        this.md = md;
    }

    /**
     * Adds two child subtrees to this Merkle Tree.
     *
     * @param leftTree The left child tree
     * @param rightTree The right child tree
     */
    public void add(final MerkleTree leftTree, final MerkleTree rightTree)
    {
        this.leftTree = leftTree;
        this.rightTree = rightTree;
        md.update(leftTree.digest());
        digest = md.digest(rightTree.digest());
    }

    /**
     * Adds two child leaves to this Merkle Tree.
     *
     * @param leftLeaf The left child leaf
     * @param rightLeaf  The right child leaf
     */
    public void add(final Leaf leftLeaf, final Leaf rightLeaf)
    {
        this.leftLeaf = leftLeaf;
        this.rightLeaf = rightLeaf;
        md.update(digest(leftLeaf));
        digest = md.digest(digest(rightLeaf));
    }

    /**
     * @return The left child tree if there is one, else returns null
     */
    public MerkleTree leftTree()
    {
        return (leftTree);
    }

    /**
     * @return The right child tree if there is one, else returns null
     */
    public MerkleTree rightTree()
    {
        return (rightTree);
    }

    /**
     * @return The left child leaf if there is one, else returns null
     */
    public Leaf leftLeaf()
    {
        return (leftLeaf);
    }

    /**
     * @return The right child leaf if there is one, else returns null
     */
    public Leaf rightLeaf()
    {
        return (rightLeaf);
    }

    /**
     * @return The digest associate with the root node of this
     * Merkle Tree
     */
    public byte[] digest()
    {
        return (digest);
    }

    /**
     * Returns a string representation of the specified
     * byte array, with the values represented in hex. The
     * values are comma separated and enclosed within square
     * brackets.
     *
     * @param array The byte array
     *
     * @return Bracketed string representation of hex values
     */
    private String toHexString(final byte[] array)
    {
        final StringBuilder str = new StringBuilder();

        str.append("[");

        boolean isFirst = true;
        for(int idx=0; idx<array.length; idx++)
        {
            final byte b = array[idx];

            if (isFirst)
            {
                isFirst = false;
            }
            else
            {
                str.append(",");
            }

            final int hiVal = (b & 0xF0) >> 4;
            final int loVal = b & 0x0F;
            str.append((char) ('0' + (hiVal + (hiVal / 10 * 7))));
            str.append((char) ('0' + (loVal + (loVal / 10 * 7))));
        }

        str.append("]");

        return(str.toString());
    }

    //Work in progress
    private String toStringfromSHA(byte[] array){
        String finalString  = new String(array);
        return finalString;
    }

    /**
     *Full tree print
     * @param indent The number of spaces to indent
     */
    private void printfullTree(final int indent)
    {
        for(int idx=0; idx<indent; idx++)
        {
            System.out.print(" ");
        }

        // Print root digest
       // System.out.println("Node digest: "+ toStringfromSHA(digest()));
        System.out.println("Node digest: " + toHexString(digest()));

        // Print children on subsequent line, further indented
        if (rightLeaf!=null && leftLeaf!=null)
        {
            // Children are leaf nodes
            // Indent children an extra space
            for(int idx=0; idx<indent+1; idx++)
            {
                System.out.print(" ");
            }

            System.out.println("Left leaf: " + rightLeaf.toString() +
                    " Right leaf: " + leftLeaf.toString());

        }
        else if (rightTree!=null && leftTree!=null)
        {
            // Children are Merkle Trees
            // Indent children an extra space
            rightTree.printfullTree(indent+1);
            leftTree.printfullTree(indent+1);
        }
        else
        {
            // Tree is empty
            System.out.println("Empty tree");
        }
    }

    /**
     * Formatted print out of the contents of the tree
     */
    public void printfullTree()
    {
        // Full print tree
        printfullTree(0);
    }
}
