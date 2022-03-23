package assignment4;

import java.util.HashMap;
import java.util.Objects;

public class Grammar {
  private Rule start;
  private HashMap<String,Rule> rules;


  public Grammar(Rule start, HashMap<String, Rule> rules) {
    this.start = start;
    this.rules = rules;
  }

  public Rule getStart() {
    return start;
  }

  public void setStart(Rule start) {
    this.start = start;
  }

  public HashMap<String, Rule> getRules() {
    return rules;
  }

  public void setRules(HashMap<String, Rule> rules) {
    this.rules = rules;
  }

  public String textGenerator(Rule start, HashMap<String, Rule> rules) throws UndefinedTerminalException {
//    if (jsonMap.isEmpty()) {
//      throw new UndefinedTerminalException();
//    }
    return "";
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
    return Objects.equals(start, grammar.start) && Objects.equals(rules,
        grammar.rules);
  }

  @Override
  public int hashCode() {
    return Objects.hash(start, rules);
  }

  @Override
  public String toString() {
    return "Grammar{" +
        "start=" + start +
        ", rules=" + rules +
        '}';
  }
}
