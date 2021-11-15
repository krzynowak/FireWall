package packag.elements;

import java.util.*;

public class ACGroup extends IDO
{
    private LinkedHashMap<Integer, IDO> SubObjects;

    /* Base constructor */
    protected ACGroup(Integer assignedId)
    {
        super(assignedId);
        this.SubObjects = new LinkedHashMap<Integer, IDO>();
    }

    /* Assigns any ID Object as a sub object of this group - can take ACList but never should */
    public void addToGroup(IDO newObj)
    {
        this.SubObjects.put(newObj.getId(), newObj);
    }

    /* Performs a tree search for elements in this group to find anything that matches the IP that was provided */
    public boolean matchDatagram(long ip)
    {
        boolean res = false;

        for(Map.Entry<Integer, IDO> tempObj : this.SubObjects.entrySet())
		{
			res = tempObj.getValue().matchDatagram(ip);

            if(res) break;
		}

        return res;
    }
}