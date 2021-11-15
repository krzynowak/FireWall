package packag.conditions;

import interfaces.ACLi.Condition;
import interfaces.ACLi.Result;
import interfaces.Datagram;

public class ConditionRoot implements Condition
{
    /* Condition 1 for compound statement */
    private final Condition rootCondition;

    /* Stores condition result if amtch was found */
    private final Result    rootResult;

    /* Root node - contains the highest order operation of the tree and the result to be returned if a match was found */
    public ConditionRoot(Condition Cond, Result result)
    {
        this.rootCondition  = Cond;
        this.rootResult     = result;
    }

    /* If match returns True, use this to get the expected end result */
    public Result getResultForMatch()
    {
        return this.rootResult;
    }

    /* Match first condition in tree */
    public boolean match(Datagram datagram)
    {
        return this.rootCondition.match(datagram);
    }
}