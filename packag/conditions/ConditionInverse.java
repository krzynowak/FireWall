package packag.conditions;

import interfaces.Datagram;
import interfaces.ACLi.Condition;

public class ConditionInverse extends ConditionBase
{
    /* Condition 1 for compound statement */
    private final Condition rootCondition;

    public ConditionInverse(Condition C)
    {
        this.rootCondition = C;
    }

    /* Returns NOT of decorated condition */
    public boolean match(Datagram datagram)
    {
        return !this.rootCondition.match(datagram);
    }
}
