package com.github.aiderpmsi.pims.grouper.model;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.github.aiderpmsi.pims.grouper.model.SimpleDictionary.Type;

public class Utils {
	
	/** Dictionaries used (for caching and sharing between classes) */
	private Dictionaries dicos;
	
	/**
	 * Constructs from an existing list of dictionaries (for sharing these buffers)
	 * @param dicos
	 */
	public Utils(Dictionaries dicos) {
		this.dicos = dicos;
	}
	
	public Integer count(String resource, String key, Object value) throws IOException {
		Set<String> dicoContent = null;
		try {
			// GETS THE DICO AND THROW EXCEPTION (NULLPOINTER OR ILLEGALARGUMENT) IF DICO NOT FOUND
			SimpleDictionary dico = dicos.get(SimpleDictionary.Type.valueOf(resource));
			// GETS THE DEFINITION IN DICO AND THROW EXCEPTION (IOEXCEPTION) IF KEY NOT FOUND
			dicoContent = dico.getDefinition(key);
		} catch (NullPointerException | IllegalArgumentException e) {
			throw new IOException(e);
		}

		// NUMBER OF COUNTS
		Integer matches = 0;
		
		// IF VALUE IS NULL, WE RETURN NO MATCH
		if (value == null) {
			// DO NOTHING, MATCHES IS ALREADY AT 0
		}
		
		// IF WE HAVE TO CHECK AGAINST A COLLECTION, CHECK EACH ITEM
		else if (value instanceof Collection<?>) {
			for (Object element : (Collection<?>) value) {
				if (element instanceof String) {
					if (dicoContent.contains(((String) element).trim())) {
						matches++;
					}
				}
			}
		}
		// IF WE HAVE TO CHECK AGAINST A STRING, CHECK THIS STRING
		else if (value instanceof String) {
			if (dicoContent.contains(((String) value).trim())) {
				matches++;
			}
		}
		
		return matches;

	}

	/**
	 * Insert values (or elements in values if values is collection) into a single hashset of strings, eliminating duplicates
	 * @param values list of {@link String} or {@link Collection}
	 * @return {@link HashSet} of {@link String}
	 * @throws IOException if an element in {@code values} is not a {@link String} or a {@link Collection}
	 */
	public HashSet<String> concat(Object... values) throws IOException {
		
		// SETS THE RESULT HASH
		HashSet<String> resultHash = new HashSet<>();
		
		for (Object value : values) {
			
			// VALUE IS NULL
			if (value == null) {
				// DO NOTHING
			}
			
			// VALUE IS A STRING
			else if (value instanceof String) {
				resultHash.add((String) value);
			}
			
			// VALUE IS A COLLECTION
			else if (value instanceof Collection<?>) {
				for (Object valueObject : (Collection<?>) value) {
					if (valueObject instanceof String) {
						resultHash.add((String) valueObject);
					}
				}
			}
			else {
				throw new IOException(value + " is not a known type for Concatenate");
			}
		}
		return resultHash;
	}

	/**
	 * Calculate duration from {@code begining} to {@code end} in {@code type}
	 * @param begining
	 * @param end
	 * @param type {@code "year"} if result in years, {@code "day"} if result in days
	 * @return
	 * @throws IOException
	 */
	public Integer duration(Calendar begining, Calendar end, String type) throws IOException {

		// CALCULATE AGE
		Integer age;
		switch (type) {
		case "year":
			age = end.get(Calendar.YEAR) - begining.get(Calendar.YEAR);
			if (end.get(Calendar.MONTH) < begining.get(Calendar.MONTH))
				age--;
			else if (end.get(Calendar.MONTH) == begining.get(Calendar.MONTH) &&
					end.get(Calendar.DAY_OF_MONTH) < begining.get(Calendar.DAY_OF_MONTH))
				age--;
			break;
		case "day":
			age = new Long((end.getTimeInMillis() - begining.getTimeInMillis()) / 86400000L).intValue();
			break;
		default:
			throw new IOException(type + " is not an accepted type");
		}

		return age;
	}
	
	public void awrdp(List<String> actes) throws IOException {
		// GETS NEEDED DICTIONARIES, THROWING AN IOEXCEPTION IF NOT EXISTING
		HashSet<String> dicoacteschirmineur = dicos.get(Type.acteMineurChirReclassant).getDefinition("all");
		HashSet<String> dicoacteschir = dicos.get(Type.classeActe).getDefinition("ADC");

		// LIST OF ACTES CHIR MINEUR
		HashSet<String> acteschirmineur = new HashSet<>();
		for (String acte : actes) {
			if (dicoacteschirmineur.contains(acte))
				acteschirmineur.add(acte);
		}
		// LIST OF ACTES CHIR
		HashSet<String> acteschir = new HashSet<>();
		for (String acte : actes) {
			if (dicoacteschir.contains(acte))
				acteschir.add(acte);
		}
		
		if (acteschir.equals(acteschirmineur)) {
			// IF EVERY ACTE CHIR BELONGS TO ACTES CHIR MINEUR LIST, IGNORE THEM
			remove(actes, acteschir);
		} else {
			// ELSE IF THERE IS AT LEAST ONE ACTE CLASSANT OP, IGNORE IT
			HashSet<String> dicoactesclassantsop = dicos.get(Type.acteClassantOp).getDefinition("all");
			remove(actes, dicoactesclassantsop);
		}
	}

	/**
	 * Removes a list of {@link String} from an {@link HashSet}
	 * @param toRemove
	 * @param setToModify
	 */
	private void remove(List<String> toRemove, HashSet<String> setToModify) {
		// REMOVE CORRESPONDING ACTES
		Iterator<String> it = toRemove.iterator();
		while (it.hasNext()) {
			String acte = it.next();
			// CHECK IF THIS FORMATTED VALUE EXISTS IN DICOCONTENT
			if (setToModify.contains(acte)) {
				// REMOVE THIS ELEMENT FROM CONTENT IF EXISTS IN DICOCONTENT
				it.remove();
			} // ELSE DO NOTHING
		}
	}

}
