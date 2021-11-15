import interfaces.ACLi;

import interfaces.Datagram;
import interfaces.Datagram.*;

import packag.elements.*;
import packag.conditions.*;
import packag.misc.*;
import packag.elements.IDFactory.IdObjects;

import java.util.*;

public class ACL implements ACLi
{
	private HashMap<Integer, ACList	> MapList	;

	private HashMap<Integer, ACGroup> MapGroup	;

	private HashMap<String , Host	> MapHost	;

	private HashMap<String , Net	> MapNet	;

	/* Base class constructor */
    public ACL()
    {
        /* Initialize */
		this.MapList  = new HashMap<	Integer, ACList 	>();
		this.MapGroup = new HashMap<	Integer, ACGroup	>();
		this.MapHost  = new HashMap<	String , Host   	>();
		this.MapNet   = new HashMap<	String , Net		>();
    } 


	/**
	 * Metoda pozwala na powiązanie adresu IPv4 z nazwą hosta.
	 * 
	 * @param hostAddress adres IPv4
	 * @param hostName    nazwa hosta
	 */
	public void addHost(String hostAddress, String hostName)
    {
		long ip = 0;
		Host newHost = null;

		/* Convert IP from string to long */
		ip = Adapter.getIPNum(hostAddress);

		/* Create new Hsot isntance */
		newHost = (Host)IDFactory.getInstance().getObject(IdObjects.HOST);

		/* Assign ip an hostname */
		newHost.assignIP(ip);
		newHost.assignName(hostName);

		/* Pur new Hoost in ACL array */
		this.MapHost.put(hostName, newHost);
    }


	/**
	 * Metoda pozwala na powiązanie sieci z nazwą sieci.
	 * 
	 * @param netID               identyfikator sieci IP
	 * @param networkPrefixLength długość maski (8, 16 lub 24 bity)
	 * @param netName             nazwa sieci
	 */
	public void addNet(String netID, Integer networkPrefixLength, String netName)
    {
		long ip = 0;
		int mask = 0;
		Net newNet = null;

		/* Convert IP to long from string and nextmask to actual mask from number */
		ip = Adapter.getIPNum(netID);
		mask = Adapter.getNetmask(networkPrefixLength);

		/* Create new Net instance */
		newNet = (Net)IDFactory.getInstance().getObject(IdObjects.NET);

		/* Set IP */
		newNet.assignIP(ip);

		/* Set netmask */
		newNet.assignNetmaks(mask);

		/* Set net name */
		newNet.assignName(netID);

		/* Store new Net */
        this.MapNet.put(netName, newNet);
    }


	/**
	 * Metoda generuje nową grupę adresów.
	 * 
	 * @return unikalny identyfikator grupy.
	 */
	public Integer createNewGroup()
    {
        /* Create new group */
		ACGroup newGroup = (ACGroup)IDFactory.getInstance().getObject(IdObjects.GROUP);
		
		/* Store new instance */
		this.MapGroup.put(newGroup.getId(), newGroup);

		/* Return ID of new item */
		return newGroup.getId();
    }


	/**
	 * Metoda dodaje hosta o podanej nazwie do grupy.
	 * 
	 * @param groupID  identyfikator docelowej grupy
	 * @param hostName dodawany host
	 */
	public void addHostToGroup(Integer groupID, String hostName)
    {
		/* Call underlying methods to assign a Host to a group */
		this.MapGroup.get(groupID).addToGroup(this.MapHost.get(hostName));
    }


	/**
	 * Metoda dodaje sieć o podanej nazwie do grupy.
	 * 
	 * @param groupID identyfikator docelowej grupy
	 * @param netName dodawana sieć
	 */
	public void addNetToGroup(Integer groupID, String netName)
    {
		/* Call underlying methods to assign a Net to a group */
		this.MapGroup.get(groupID).addToGroup(this.MapNet.get(netName));
    }


	/**
	 * Metoda pozwala na dodanie grupy do grupy.
	 * 
	 * @param groupID    grupa, do której inna jest dodawana
	 * @param subGroupID grupa, która jest dodawana.
	 */
	public void addGroupToGroup(Integer groupID, Integer subGroupID)
    {
		/* Call underlying methods to assign a group to a group */
		this.MapGroup.get(groupID).addToGroup(this.MapGroup.get(subGroupID));
    }


	/**
	 * Metoda generuje nowy warunek.
	 * 
	 * @param sourceGroupID      grupa adresów źródłowych
	 * @param destinationGroupID grupa adresów docelowych
	 * @param protocol           protokół
	 * @param flag               flagi
	 * @return nowo-wygenerowany warunek
	 */
	public Condition newCondition(Integer sourceGroupID, Integer destinationGroupID, Protocol protocol, Flag flag)
    {
		ACGroup srcGroup = null;
		ACGroup dstGroup = null;

		/* Get groups from their respective lists */
		srcGroup = this.MapGroup.get(sourceGroupID);
		dstGroup = this.MapGroup.get(destinationGroupID);

		/* Create new condition with specified arguments and return it */
        return new ConditionLeaf(srcGroup, dstGroup, protocol, flag);
    }


	/**
	 * Metoda pozwala na złożenie dwóch warunków w jeden za pomocą operatora AND.
	 * 
	 * @param c1 warunek pierwszy
	 * @param c2 warunek drugi
	 * @return warunek wynikowy, który będzie spełniony jeśli spełnione będą warunki
	 *         c1 i c2.
	 */
	public Condition and(Condition c1, Condition c2)
    {
		/* Decorator with AND logic for 2 sub nodes */
        return new ConditionNode(c1, c2, ConditionNode.CondType.AND);
    }


	/**
	 * Metoda pozwala na złożenie dwóch warunków w jeden za pomocą operatora OR.
	 * 
	 * @param c1 warunek pierwszy
	 * @param c2 warunek drugi
	 * @return warunek wynikowy, który będzie spełniony jeśli spełniony będzie co
	 *         najmniej jeden z warunków c1 i c2.
	 */
	public Condition or(Condition c1, Condition c2)
    {
		/* Decorator with OR logic for 2 sub nodes */
        return new ConditionNode(c1, c2, ConditionNode.CondType.OR);
    }


	/**
	 * Metoda pozwala na wytworzenie warunku, który jest zaprzeczeniem podanego
	 * warunku. Jeśli podany warunek jest spełnony, to warunek wygenerowany tą
	 * metodą spełniony nie jest (i odwrotnie).
	 * 
	 * @param c warunek
	 * @return warunek będący zaprzeczeniem warunku c
	 */
	public Condition not(Condition c)
    {
		/* Decorator with NOT logic for sub node */
        return new ConditionInverse(c);
    }


	/**
	 * Metoda generuje nową listę dostępu
	 * 
	 * @return unikalny identyfikator listy
	 */
	public Integer createACL()
    {
        /* Create */
		ACList newList = (ACList)IDFactory.getInstance().getObject(IdObjects.LIST);
		
		/* Store new instance */
		this.MapList.put(newList.getId(), newList);

		/* Return ID of new item */
		return newList.getId();
    }


    /**
	 * Dodanie warunku do listy. Jeśli warunek jest spełniony przetwarzanie listy
	 * ACL kończy się wynikiem result.
	 * 
	 * @param aclID      identyfikator warunku
	 * @param lineNumber linijka
	 * @param condition  warunek
	 * @param result     los datagramu, jeśli podany tu warunek będzie spełniony.
	 */
	public void addConditionToACL(Integer aclID, Integer lineNumber, Condition condition, Result result)
    {
		/* Call underlying methods to assign a condition to a list */
		this.MapList.get(aclID).addCondtion(lineNumber, condition, result);
    }


	/**
	 * Poddanie datagramu testom za pomocą listy ACL o numerze aclID.
	 * 
	 * @param aclID    identyfikator stosowanej listy
	 * @param datagram testowany datagram
	 * @return los datagramu
	 */
	public Result test(Integer aclID, Datagram datagram)
    {
		/* Check datagram and return results based on selected condition list */
        return MapList.get(aclID).matchDatagramToList(datagram);
    }
}