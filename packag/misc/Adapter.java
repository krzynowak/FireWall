
package packag.misc;

/* 
    Class for misc. methods that don't fit anywhere else and perform generic data translations
*/
public class Adapter
{
    /**
    * Extract netmask from IP "xxx.xxx.xxx.xxx/yy" -> yy
    */
    public static Integer getNetmask(String ip)
    {
        String[] address = ip.split("/");

        return Integer.parseInt(address[1]);
    }

    /**
    * Extract netmask from bit counter, e.g. 18 -> 0x0002FFFF
    */
    public static int getNetmask(Integer netMask)
    {
        int mask = 0x00000000;

        /* Should be error but we can just block everything - and possibly log msg later */
        if( ( netMask >= 32 ) || ( netMask < 0 ) )
        {
            mask = 0xFFFFFFFF;
        }
        else
        {
            mask = ( ( 1 << netMask ) - 1 ) << (32 - netMask);
        }

        return mask;
    }


    /**
    * Convert an IP address to a number (string -> long)
    */
    public static long getIPNum(String ip)
    {
        long result = 0x00000000;

        String[] address = ip.split("/");

        String[] octetStr = address[0].split("\\.");
        
        for (int i = 0; i < 4; i++) result |= (Long.parseLong(octetStr[i]) << ((3 - i) * 8));

        return result & 0xFFFFFFFF;
    }
}