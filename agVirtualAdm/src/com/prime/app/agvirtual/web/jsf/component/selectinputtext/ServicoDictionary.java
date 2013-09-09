package com.prime.app.agvirtual.web.jsf.component.selectinputtext;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.prime.app.agvirtual.entity.AgvTabServico;
import com.prime.app.agvirtual.to.ServicoTO;

public class ServicoDictionary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8550163339181996998L;

	private static Log log = LogFactory.getLog(ServicoDictionary.class);

	// initialized flag, only occures once ber deployment.
	private static boolean initialized;

	// list of cities.
	private static ArrayList<SelectItem> servicoDictionary;
	
	private static ArrayList<ServicoTO> lista;

//	private static final String DATA_RESOURCE_PATH = "/WEB-INF/classes/org/icefaces/application/showcase/view/resources/city.xml.zip";

	/**
	 * Creates a new instnace of CityDictionary. The city dictionary is unpacked
	 * and initialized during construction. This will result in a short delay of
	 * 2-3 seconds on the server as a result of processing the large file.
	 */
	public ServicoDictionary() {

		try {
			// initialized the bean, load xml database.
			synchronized (this) {
				init();
			}

		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log	.error(e.getMessage());
			}
		}
	}

	/**
	 * Comparator utility for sorting city names.
	 */
	private static final Comparator LABEL_COMPARATOR = new Comparator() {

		// compare method for city entries.
		public int compare(Object o1, Object o2) {
			SelectItem selectItem1 = (SelectItem) o1;
			SelectItem selectItem2 = (SelectItem) o2;
			// compare ignoring case, give the user a more automated feel when
			// typing
			return selectItem1.getLabel().compareToIgnoreCase(
					selectItem2.getLabel());
		}
	};

	/**
	 * Gets the cityDictionary of cities.
	 * 
	 * @return cityDictionary list in sorted by city name, ascending.
	 */
	public List getDictionary() {
		return servicoDictionary;
	}

	/**
	 * Generates a short list of cities that match the given searchWord. The
	 * length of the list is specified by the maxMatches attribute.
	 * 
	 * @param searchWord
	 *            city name to search for
	 * @param maxMatches
	 *            max number of possibilities to return
	 * @return list of SelectItem objects which contain potential city names.
	 */
	public static ArrayList generateCityMatches(String searchWord, int maxMatches) {

		ArrayList matchList = new ArrayList(maxMatches);

		// ensure the autocomplete search word is present
		if ((searchWord == null) || (searchWord.trim().length() == 0)) {
			return matchList;
		}

		try {
			SelectItem searchItem = new SelectItem("", searchWord);
			int insert = Collections.binarySearch(servicoDictionary, searchItem,
					LABEL_COMPARATOR);

			// less then zero if we have a partial match
			if (insert < 0) {
				insert = Math.abs(insert) - 1;
			} else {
				// If there are duplicates in a list, ensure we start from the
				// first one
				if (insert != servicoDictionary.size()
						&& LABEL_COMPARATOR.compare(searchItem, servicoDictionary
								.get(insert)) == 0) {
					while (insert > 0
							&& LABEL_COMPARATOR.compare(searchItem,
									servicoDictionary.get(insert - 1)) == 0) {
						insert = insert - 1;
					}
				}
			}

			for (int i = 0; i < maxMatches; i++) {
				// quit the match list creation if the index is larger than
				// max entries in the cityDictionary if we have added
				// maxMatches.
				if ((insert + i) >= servicoDictionary.size() || i >= maxMatches) {
					break;
				}
				matchList.add(servicoDictionary.get(insert + i));
			}
		} catch (Throwable e) {
			log	.error(e.getMessage());
							
		}
		// assign new matchList
		return matchList;
	}

	/**
	 * Reads the zipped xml city cityDictionary and loads it into memory.
	 */
	private static void init() throws IOException {

		if (!initialized) {

			initialized = true;
			
			List servicoList =  new ArrayList<ServicoTO>();
			
			servicoDictionary = new ArrayList(getLista().size());
			ServicoTO tmpServ;
			for (int i = 0, max = getLista().size(); i < max; i++) {
				tmpServ = (ServicoTO) servicoList.get(i);
				if (tmpServ != null && tmpServ.getNmServico() != null) {
					servicoDictionary.add(new SelectItem(tmpServ, tmpServ.getNmServico()));
				}
			}
			servicoList.clear();

			Collections.sort(servicoDictionary, LABEL_COMPARATOR);
		}
	}

	public static ArrayList<ServicoTO> getLista() {
		return lista;
	}

	public static void setLista(List<ServicoTO> list) {
		ServicoDictionary.lista = (ArrayList<ServicoTO>) list;
	}
	
	
}
