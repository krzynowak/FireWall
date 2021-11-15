package packag.elements;

public class Host extends IDO
{
    protected long ip = 0;
    private String name = null;

    /* Base constructor for ID */
    protected Host(Integer assignedId)
    {
        super(assignedId);
    }

    /* Check if this object already exists - DEPRECATED will be removed */
    public boolean alreadyExists(String name)
    {
        boolean result = false;

        if (this.name != null) result = this.name.equals(name);

        return result;
    }

    /* Bind IP to instance */
    public void assignIP(long ipAddress)
    {
        this.ip = ipAddress;
    }

    /* Get instance IP */
    public long retrieveIP()
    {
        return this.ip;
    }

    /* Bind name to instance */
    public void assignName(String name)
    {
        this.name = name;
    }

        /* Internal method for matching this instance against the IP from a Datagram */
    public boolean matchDatagram(long datagramIp)
    {
        return this.ip == datagramIp;
    }
}