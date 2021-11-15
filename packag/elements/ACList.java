package packag.elements;

import packag.conditions.*;
import interfaces.ACLi.Condition;
import interfaces.ACLi.Result;
import interfaces.Datagram;

import java.util.*;

public class ACList extends IDO
{
    TreeMap<Integer, ConditionRoot> conditionList;

    /* Base constructor */
    protected ACList(Integer assignedId)
    {
        super(assignedId);
        this.conditionList = new TreeMap<Integer, ConditionRoot>();
    }

    /* Adds conditions to list with assigned priorite for matching */
    public void addCondtion(Integer lineNumber, Condition condition, Result result)
    {
        ConditionRoot newRootCondition = new ConditionRoot(condition, result);

        this.conditionList.put(lineNumber, newRootCondition);
    }

    /* Replaces generic matchDatagram due to different arguments and return type - Matches Datagram to conditions untill match is found
    *   Return false by default -> Deny
    */
    public Result matchDatagramToList(Datagram datagram)
    {
        /* Default result for no match */
        Result res = Result.DENY;

        /* Iterate over conditions and see if any matches */
        for( Map.Entry<Integer, ConditionRoot> entry : this.conditionList.entrySet() )
        {
            ConditionRoot rootCond = entry.getValue();
            
            /* If match occured check what reults is expected for that Condtion and return it */
            if( rootCond.match(datagram) )
            {
                res = rootCond.getResultForMatch();
                break;
            }
        }

        return res;
    }

    /* AC list contains matchable objects but can't be matched itself -> sohuld never be called */
    public boolean matchDatagram(long ip)
    {
        return false;
    }
}