import java.util.List;

/***
 * Author: Tyler Desharnais
 * This Leaf is a "Node" in ForwardSlash's blockchain.
 * It takes datablocks (transactions etc) aka Arb data
 * Represents that data in a byte array
 *
 */

public class Leaf
{
    private final List<byte[]> dataBlock;

    public Leaf(final List<byte[]> dataBlock)
    {
        this.dataBlock = dataBlock;
    }

    /**
     * @return The data block associated with this leaf node
     */
    public List<byte[]> getDataBlock()
    {
        return (dataBlock);
    }

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

    /**
     * Returns a string representation of the data block
     */
    public String toString()
    {
        final StringBuilder str = new StringBuilder();

        for(byte[] block: dataBlock)
        {
            str.append(toHexString(block));
        }

        return(str.toString());
    }

}
