package firstportfolio.wordcharger.controller.join;

public class RegExConstants {

    static final String LENGTH_PATTERN = "^.{8,16}$";
    static final String LETTER_PATTERN = ".*[A-Za-z].*";
    static final String NUMBER_PATTERN = ".*[0-9].*";
    static final String SPECIAL_CHAR_PATTERN = ".*[!@#&()–[{}]:;',?/*~$^+=<>].*";
}
