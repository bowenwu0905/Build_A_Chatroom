package assignment4;

import java.util.List;
import java.util.TreeMap;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * generate a random sentence
 * @author xiaochong and zitao
 */
public class Grammar {
  private String attribute;
  private TreeMap<String,List<String>> rules;

  private static final String regex = "(?<=\\<).*?(?=\\>)";

  public Grammar(String attribute, TreeMap<String, List<String>> rules) {
    this.attribute = attribute;
    this.rules = rules;
  }

  public Grammar() {
  }

  public String getAttribute() {
    return attribute;
  }

  public void setAttribute(String attribute) {
    this.attribute = attribute;
  }

  public TreeMap<String, List<String>> getRules() {
    return rules;
  }

  public void setRules(TreeMap<String, List<String>> rules) {
    this.rules = rules;
  }

  /**
   * random select a word from TreeMap
   * @param attribute String from attribute select word
   * @param rules TreeMap
   * @return String selected word
   * @throws Exception UndefinedTerminalException and Exception
   */
  public String textGenerator(String attribute, TreeMap<String, List<String>> rules) throws Exception {
    if (rules.isEmpty()) {
      throw new UndefinedTerminalException();
    }

    // whether json have the attribute
    if (rules.containsKey(attribute)) {
      // random generate an index and get its value of attribute
      List<String> values = rules.get(attribute);
      String value = values.get(numberGenerator(values.size()));
      String afterReplace = value;
      // find pattern
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(value);
      // recursively find attributes
      while (matcher.find()) {
        String replaced = '<' + matcher.group() + '>';
        afterReplace = afterReplace.replaceAll(replaced, textGenerator(matcher.group(), rules));
      }
      return afterReplace;
    } else {
      throw new UndefinedTerminalException("Json don't have the " + attribute);
    }
  }

  /**
   * generate random number from upper bound
   * @param size upper bound
   * @return a random number
   */
  public int numberGenerator(int size) {
    Random random = new Random();
    return random.nextInt(size);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Grammar grammar = (Grammar) o;
    return Objects.equals(attribute, grammar.attribute) && Objects.equals(rules,
        grammar.rules);
  }

  @Override
  public int hashCode() {
    return Objects.hash(attribute, rules);
  }

  @Override
  public String toString() {
    return "Grammar{" +
        "attribute=" + attribute +
        ", rules=" + rules +
        '}';
  }
}
