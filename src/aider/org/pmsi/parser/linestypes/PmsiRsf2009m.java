package aider.org.pmsi.parser.linestypes;

import java.util.regex.Pattern;

/**
 * Définition d'une ligne M de RSF version 2009
 * @author delabre
 *
 */
public class PmsiRsf2009m extends PmsiLineType {

	private static final Pattern pattern = Pattern.compile("^(M)(\\d{9})(.{20})(.{13})(.{2})(.{3})(.{9})(.{2})(.{3})(.{8})(.{13})(.{1})" +
			"(.{1})(.{1})(.{1})(.{1})(.{1})(.{1})(.{1})(.{1})(.{2})(.{2})(.{2})(.{2})(.{2})(.{2})(.{2})(.{2})(.{2})(.{2})" +
			"(.{2})(.{2})(.{2})(.{2})(.{2})(.{2})");
	
	private static final String[] names = {
		"TypeEnregistrement", "Finess", "NumRSS", "CodeSS", "CleCodeSS", "RangBeneficiaire", "NumFacture",
		"ModeTraitement", "DisciplinePrestation", "DateActe", "CodeCCAM", "ExtensionDocumentaire",
		"Activite", "Phase", "Modificateur1", "Modificateur2", "Modificateur3",
		"Modificateur4", "AssociationNonPrevue", "CodeRemboursementExceptionnel",
		"NumDent1", "NumDent2", "NumDent3", "NumDent4", "NumDent5", "NumDent6",
		"NumDent7", "NumDent8", "NumDent9", "NumDent10", "NumDent11", "NumDent12",
		"NumDent13", "NumDent14", "NumDent15", "NumDent16"
	};

	private static final String name = "RsfM";

	private String[] content = new String[names.length];

	@Override
	public Pattern getPattern() {
		return pattern;
	}
	
	@Override
	public String[] getNames() {
		return names;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setContent(int index, String content) {
		this.content[index] = content;
	}
	
	@Override
	public String[] getContent() {
		return content;
	}
}
