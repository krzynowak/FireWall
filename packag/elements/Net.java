package packag.elements;

/* 
    Network class
*/
public class Net extends Host
{
    /* Netmask */
    private int netmask = 0;

    /* Constructor - assigns object ID */
    protected Net(Integer assignedId)
    {
        super(assignedId);
    }

    /* Assign Netmask for this subnet */
    public void assignNetmaks(int mask)
    {
        this.netmask = mask;
    }

    /* Internal method for matching this instance against the IP from a Datagram */
    public boolean matchDatagram(long datagramIp)
    {
        return ( ( this.retrieveIP() ^ datagramIp ) & this.netmask ) == 0;
    }
}