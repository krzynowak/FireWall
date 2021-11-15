package interfaces;

import java.util.Set;

public interface Datagram {
	/**
	 * Typ wyliczeniowy okreĹlajÄcy przenoszony przez datagram protokĂłĹ. <br>
	 * ANY oznacza dowolny protokĂłĹ - istotne wyĹÄczenie dla warunkĂłw.
	 *
	 */
	public enum Protocol {
		ANY, TCP, UDP, ICMP,;
	}

	/**
	 * Typ wyliczeniowy okreĹlajÄcy ustawione w datagramie flagi. <br>
	 * ANY oznacza dowolnÄ flagÄ - istotne wyĹÄcznie dla warunkĂłw. <br>
	 * NON oznacza brak ustawionych flag.
	 */
	public enum Flag {
		NON, ANY, SYN, ACL, RST, FIN,;
	}

	/**
	 * Metoda zwraca ĹşrĂłdĹowy adres IPv4.
	 * 
	 * @return adres ĹşrĂłdĹa datagramu
	 */
	public String getSourceAddress();

	/**
	 * Metoda zwraca docelowy adres IPv4.
	 * 
	 * @return adres calu datagramu
	 */

	public String getDestinationAddress();

	/**
	 * Metoda zwraca informacjÄ o przenoszonym przez datagram protokĂłĹ.
	 * 
	 * @return przenoszony protokĂłĹ
	 */
	public Protocol getProtocol();

	/**
	 * Ustawione flagi
	 * 
	 * @return flagi
	 */
	public Set<Flag> getFlags();
}