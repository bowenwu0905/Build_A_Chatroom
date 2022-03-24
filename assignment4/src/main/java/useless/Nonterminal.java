package useless;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Nonterminal extends Rule{

  private List<List<String>> associatedRule = new ArrayList<>();

  public Nonterminal(String word, List<List<String>> associatedRule) {
    super(word);
    this.associatedRule = associatedRule;
  }

  public List<List<String>> getAssociatedRule() {
    return associatedRule;
  }

  public void setAssociatedRule(List<List<String>> associatedRule) {
    this.associatedRule = associatedRule;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Nonterminal that = (Nonterminal) o;
    return Objects.equals(associatedRule, that.associatedRule);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), associatedRule);
  }

  @Override
  public String toString() {
    return "Nonterminal{" +
        "associatedRule=" + associatedRule +
        '}';
  }
}
