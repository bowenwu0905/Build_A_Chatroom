package assignment4;

import java.util.Objects;

public abstract class Rule {
  private String word;
  public Rule(String word){
    this.word = word;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Rule rule = (Rule) o;
    return Objects.equals(word, rule.word);
  }

  @Override
  public int hashCode() {
    return Objects.hash(word);
  }

  @Override
  public String toString() {
    return "Rule{" +
        "word='" + word + '\'' +
        '}';
  }
}
