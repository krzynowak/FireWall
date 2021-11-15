package packag.conditions;

import interfaces.Datagram.*;
import interfaces.Datagram;
import packag.elements.*;
import packag.misc.*;
import java.util.Set;

public class ConditionLeaf extends ConditionBase
{
    private final ACGroup   sourceGroup;
    private final ACGroup   destGroup;
    private final Protocol  proto;
    private final Flag      flg;

    /* Create Leaf node for operation tree - this contains a single check for the datagram and possibliy a NOT operation */
    public ConditionLeaf(ACGroup sourceGr, ACGroup destGr, Protocol protocol, Flag flag)
    {
        this.sourceGroup = sourceGr;
        this.destGroup   = destGr;
        this.proto       = protocol;
        this.flg         = flag;
    }

    public boolean match(Datagram datagram)
    {
        boolean result = false;

        /* Obtain all datagram informations and convert IP to match class methods */
        long srcIp              = Adapter.getIPNum(datagram.getSourceAddress());
        long dstIp              = Adapter.getIPNum(datagram.getDestinationAddress());
        Protocol dataProtocol   = datagram.getProtocol();
        Set<Flag> falgs         = datagram.getFlags();

        
        if( this.sourceGroup.matchDatagram(srcIp) )     /* Match source group */
        {
            if( this.destGroup.matchDatagram(dstIp) )   /* Match destination group */
            {
                if( checkProtocol(dataProtocol) )       /* Check for protocol match */
                {
                    if( checkFlag(falgs) )              /* Check for flag pass */
                    {   
                        result = true;                  /* Return true if all tests passed */
                    }
                }
            }
        }

        return result;
    }

    /* Logic for matching protocol */
    private boolean checkProtocol(Protocol dataProtocol)
    {
        boolean protocolMatch = false;

        if( ( this.proto == Protocol.ANY ) || ( this.proto == dataProtocol ) ) protocolMatch = true;

        return protocolMatch;
    }

    /* Protocol for check flags */
    private boolean checkFlag(Set<Flag> falgs)
    {
        boolean flagMatch = false;

        /* Refactored test - more readable now */
        switch(this.flg)
        {
            case ANY:
            {
                /* NON or empty set -> FALSE */
                flagMatch = !( falgs.contains(Flag.NON) || falgs.isEmpty() );
                break;
            }

            case NON:
            {
                /* NON or empty set -> TRUE */
                flagMatch = ( falgs.contains(Flag.NON) || falgs.isEmpty() );
                break;
            }

            default:
            {
                /* If neither of the "special" cases apply just check for generic match */
                flagMatch = falgs.contains(this.flg);
                break;
            }
        }


        return flagMatch;
    }
}