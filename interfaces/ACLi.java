package interfaces;

import interfaces.Datagram.Flag;
import interfaces.Datagram.Protocol;

public interface ACLi {

	/**
	 * Interfejs reprezentujący warunek. Umożliwia przetestowanie czy Datagram
	 * spełnia warunek.
	 */
	public interface Condition {
		/**
		 * Test sprawdzający czy warunek jest spełniony przez datagram. Warunek jest
		 * spełniony gdy adresacja datagramu pasuje do adresacji zawartej w warunku,
		 * protokół i flagi datagramu są zgodne.
		 * 
		 * @param datagram testowany datagram
		 * @return true - datagram pasuje do warunku.
		 */
		public boolean match(Datagram datagram);
	}

	/**
	 * Los ramki
	 */
	public enum Result {
		ALLOW, DENY,;
	}

	/**
	 * Metoda pozwala na powiązanie adresu IPv4 z nazwą hosta.
	 * 
	 * @param hostAddress adres IPv4
	 * @param hostName    nazwa hosta
	 */
	public void addHost(String hostAddress, String hostName);

	/**
	 * Metoda pozwala na powiązanie sieci z nazwą sieci.
	 * 
	 * @param netID               identyfikator sieci IP
	 * @param networkPrefixLength długość maski (8, 16 lub 24 bity)
	 * @param netName             nazwa sieci
	 */
	public void addNet(String netID, Integer networkPrefixLength, String netName);

	/**
	 * Metoda generuje nową grupę adresów.
	 * 
	 * @return unikalny identyfikator grupy.
	 */
	public Integer createNewGroup();

	/**
	 * Metoda dodaje hosta o podanej nazwie do grupy.
	 * 
	 * @param groupID  identyfikator docelowej grupy
	 * @param hostName dodawany host
	 */
	public void addHostToGroup(Integer groupID, String hostName);

	/**
	 * Metoda dodaje sieć o podanej nazwie do grupy.
	 * 
	 * @param groupID identyfikator docelowej grupy
	 * @param netName dodawana sieć
	 */
	public void addNetToGroup(Integer groupID, String netName);

	/**
	 * Metoda pozwala na dodanie grupy do grupy.
	 * 
	 * @param groupID    grupa, do której inna jest dodawana
	 * @param subGroupID grupa, która jest dodawana.
	 */
	public void addGroupToGroup(Integer groupID, Integer subGroupID);

	/**
	 * Metoda generuje nowy warunek.
	 * 
	 * @param sourceGroupID      grupa adresów źródłowych
	 * @param destinationGroupID grupa adresów docelowych
	 * @param protocol           protokół
	 * @param flag               flagi
	 * @return nowo-wygenerowany warunek
	 */
	public Condition newCondition(Integer sourceGroupID, Integer destinationGroupID, Protocol protocol, Flag flag);

	/**
	 * Metoda pozwala na złożenie dwóch warunków w jeden za pomocą operatora AND.
	 * 
	 * @param c1 warunek pierwszy
	 * @param c2 warunek drugi
	 * @return warunek wynikowy, który będzie spełniony jeśli spełnione będą warunki
	 *         c1 i c2.
	 */
	public Condition and(Condition c1, Condition c2);

	/**
	 * Metoda pozwala na złożenie dwóch warunków w jeden za pomocą operatora OR.
	 * 
	 * @param c1 warunek pierwszy
	 * @param c2 warunek drugi
	 * @return warunek wynikowy, który będzie spełniony jeśli spełniony będzie co
	 *         najmniej jeden z warunków c1 i c2.
	 */
	public Condition or(Condition c1, Condition c2);

	/**
	 * Metoda pozwala na wytworzenie warunku, który jest zaprzeczeniem podanego
	 * warunku. Jeśli podany warunek jest spełnony, to warunek wygenerowany tą
	 * metodą spełniony nie jest (i odwrotnie).
	 * 
	 * @param c warunek
	 * @return warunek będący zaprzeczeniem warunku c
	 */
	public Condition not(Condition c);

	/**
	 * Metoda generuje nową listę dostępu
	 * 
	 * @return unikalny identyfikator listy
	 */
	public Integer createACL();

	/**
	 * Dodanie warunku do listy. Jeśli warunek jest spełniony przetwarzanie listy
	 * ACL kończy się wynikiem result.
	 * 
	 * @param aclID      identyfikator warunku
	 * @param lineNumber linijka
	 * @param condition  warunek
	 * @param result     los datagramu, jeśli podany tu warunek będzie spełniony.
	 */
	public void addConditionToACL(Integer aclID, Integer lineNumber, Condition condition, Result result);

	/**
	 * Poddanie datagramu testom za pomocą listy ACL o numerze aclID.
	 * 
	 * @param aclID    identyfikator stosowanej listy
	 * @param datagram testowany datagram
	 * @return los datagramu
	 */
	public Result test(Integer aclID, Datagram datagram);
}
