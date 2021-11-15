package packag.conditions;

import interfaces.ACLi.Condition;
import interfaces.Datagram;

/* Remove this? -> TBD */
public abstract class ConditionBase implements Condition
{
    public abstract boolean match(Datagram datagram);
}