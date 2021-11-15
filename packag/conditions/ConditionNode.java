package packag.conditions;

import interfaces.ACLi.Condition;
import interfaces.Datagram;

public class ConditionNode extends ConditionBase
{
    public enum CondType
    {
        AND, 
        OR,
        /* NOT, - No need to make a new calss jsut for this -> use boolean flag and then have it not work and create it anyway... */
	};

    /* Leaf or Node(what type?) */
    private final CondType nodeType;

    /* Condition 1 for compound statement */
    private final Condition cond_1;

    /* Condition 2 for compound statement */
    private final Condition cond_2;


    /* Compound node - evaluates based on 2 of it's sub nodes and it's own type (AND/OR) and possibliy a NOT operation */
    public ConditionNode(Condition C1, Condition C2, CondType type)
    {
        this.nodeType    = type;
        this.cond_1       = C1;
        this.cond_2       = C2;
    }

    public boolean match(Datagram datagram)
    {
        boolean result = false;

        switch(this.nodeType)
        {
            /* Reminder: If cond_1 is false cond_2 won't be evaluated */
            case AND: { result = this.cond_1.match(datagram) && this.cond_2.match(datagram);  break; }

            /* Reminder: If cond_1 is true cond_2 won't be evaluated */
            case OR:  { result = this.cond_1.match(datagram) || this.cond_2.match(datagram);  break; }
        }

        /* Check if this statement was to be inverted */
        return result;
    }
}