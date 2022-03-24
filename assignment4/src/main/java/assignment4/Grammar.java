package assignment4;

import java.util.HashMap;
import java.util.Objects;

public class Grammar {
  private String start;
  private HashMap<String,Object> rules;


  public Grammar(String start, HashMap<String, Object> rules) {
    this.start = start;
    this.rules = rules;
  }

  public String getStart() {
    return start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public HashMap<String, Object> getRules() {
    return rules;
  }

  public void setRules(HashMap<String, Object> rules) {
    this.rules = rules;
  }

  public String textGenerator(String start, HashMap<String, Object> rules) throws UndefinedTerminalException {
    /*
    base: <> not found return string
    Else: dfs iterate all <>
     */
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
